package helpers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import annotations.Column;
import annotations.Entity;
import annotations.HasMany;
import annotations.HasOne;
import annotations.Ignore;
import annotations.ManyRelations;
import annotations.OneRelations;
import annotations.OrderBy;
import libraries.Database;
import libraries.NotNullableException;
import models.Tickets;

public class GenericPersistence extends Database {

    private SQLiteStatement pst;

    public static String FIELDS = "fields";
    public static String PARAMETERS = "parameters";

    public GenericPersistence(){
        super();
    }

    public SQLiteDatabase getConnection(){
        return this.database;
    }

    public void beginTransaction(){
        this.database.beginTransaction();
    }

    public void endTransaction(){
        this.database.endTransaction();
    }

    public boolean insertBean(Object bean) throws  NotNullableException {
        this.openConnection();
        boolean result = insertBean(bean, this.database);
        this.closeConnection();
        return result;
    }

    public boolean insertBean(Object bean, SQLiteDatabase conn) throws  NotNullableException {
        long result = 0;
        try {
            Entity entity = bean.getClass().getAnnotation(Entity.class);

            ArrayList<Field> beanFields = getFields(bean);

            OneRelations oneRelations = bean.getClass().getAnnotation(OneRelations.class);
            if (oneRelations != null) {
                for (int i = 0; i < oneRelations.value().length; i++) {
                    HasOne hasOne = oneRelations.value()[i];
                    Field relationField = getField(bean, hasOne.reference());
                    if (isNullRelation(bean, relationField)) {
                        if (relationField.getAnnotation(Column.class).nullable()) {
                            beanFields.remove(relationField);
                        } else {
                            throw new NotNullableException();
                        }
                    }
                }
            }

            Field primaryField = primaryField(bean);

            beanFields.remove(primaryField);

            HashMap<String, String> sqlSets = buildStrings(beanFields);

            String sql = "INSERT INTO " + entity.table() + " (" + sqlSets.get(FIELDS) + ")";
            sql += " VALUES(" + sqlSets.get(PARAMETERS) + ")";
            this.pst = conn.compileStatement(sql);

            prepare(pst, bean, beanFields);

            result = this.pst.executeInsert();

        }catch (SQLException s){
            s.printStackTrace();
        }

        return (result != -1) ? true : false;
    }

    public boolean insertMany(Object mainBean, Object linkedBean) {
        this.openConnection();
        boolean result = insertMany(mainBean, linkedBean, this.database);
        this.closeConnection();
        return result;
    }

    public boolean insertMany(Object mainBean, Object linkedBean, SQLiteDatabase conn) {
        ManyRelations hasMultiple = mainBean.getClass().getAnnotation(ManyRelations.class);

        if (hasMultiple != null){
            HasMany hasMany = null;
            for (int i = 0; i < hasMultiple.value().length; i++) {
                if(hasMultiple.value()[i].entity().equals(linkedBean.getClass())){
                    hasMany = hasMultiple.value()[i];
                }
            }
            if (hasMany != null){
                Field foreignField = getField(linkedBean, hasMany.foreignKey());
                setValue(linkedBean, foreignField, getValue(mainBean, primaryField(mainBean)));

                try {
                    return this.insertBean(linkedBean, conn);
                } catch (NotNullableException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }


        return false;
    }

    public Object selectBean(Object bean) {
        this.openConnection();
        Object result = selectBean(bean, this.database);
        this.closeConnection();
        return result;
    }

    public Object selectBean(Object bean, SQLiteDatabase conn) {
        Object result = null;
        try{
            Entity entity = bean.getClass().getAnnotation(Entity.class);
            ArrayList<Field> beanFields = getFields(bean);
            Field primaryField = primaryField(bean);

            String sql = "SELECT * FROM " + entity.table() + " WHERE " + primaryColumn(primaryField) + " = ?";

            Cursor rs = prepare(bean, sql, primaryField, conn);

            if (rs.moveToFirst()) {
                result = result(rs, bean, beanFields);
            }
        }catch (SQLException s){
            s.printStackTrace();
        }

        return result;
    }

    public Object selectOne(Object bean, Object one) {
        this.openConnection();
        Object result = selectOne(bean, one, this.database);
        this.closeConnection();
        return result;
    }

    public Object selectOne(Object bean, Object one, SQLiteDatabase conn) {
        Object result = null;
        try{
            Entity entity = one.getClass().getAnnotation(Entity.class);
            ArrayList<Field> beanFields = getFields(one);
            Field primaryField = primaryField(one);
            OneRelations oneRelations = bean.getClass().getAnnotation(OneRelations.class);
            if(oneRelations != null){
                HasOne hasOne = null;
                for (int i = 0; i < oneRelations.value().length; i++) {
                    if(oneRelations.value()[i].entity() == one.getClass()){
                        hasOne = oneRelations.value()[i];
                    }
                }

                String sql = "SELECT * FROM " + entity.table() + " WHERE " +
                        primaryColumn(primaryField) + " = ?";

                Cursor rs = prepare(bean, sql, getField(bean, hasOne.reference()), conn);

                if (rs.moveToFirst()) {
                    result = result(rs, one, beanFields);
                }

            }
        }catch (SQLException s){
            s.printStackTrace();
        }
        return result;
    }

    public ArrayList<Object> selectAllBeans(Object bean) {
        return selectAllBeans(bean, getOrderField(bean));
    }
    public ArrayList<Object> selectAllBeans(Object bean, Field orderBy) {
        this.openConnection();
        ArrayList<Object> beans = selectAllBeans(bean, orderBy, this.database);
        this.closeConnection();
        return beans;
    }
    public ArrayList<Object> selectAllBeans(Object bean, SQLiteDatabase conn) {
        return selectAllBeans(bean, getOrderField(bean), conn);
    }
    public ArrayList<Object> selectAllBeans(Object bean, Field orderBy, SQLiteDatabase conn) {
        ArrayList<Object> beans = new ArrayList<Object>();
        try{
            Entity entity = bean.getClass().getAnnotation(Entity.class);
            ArrayList<Field> beanFields = getFields(bean);

            String sql = "SELECT * FROM " + entity.table();

            System.out.println(orderBy);

            if(orderBy != null){
                sql+= " ORDER BY "+ databaseColumn(orderBy);
            }

            Cursor rs = conn.rawQuery(sql, null);
            while (rs.moveToNext()) {
                beans.add(result(rs, bean, beanFields));
            }
        }catch (SQLException s){
            s.printStackTrace();
        }
        return beans;
    }

    public ArrayList<Object> selectBeansOrderByField(Object bean, String fieldName) {
        Field field = getField(bean, fieldName);
        //System.out.println("field "+field+" Nome "+fieldName + getFields(bean).toString());
        return selectBeansOrderByField(bean, field);
    }
    public ArrayList<Object> selectBeansOrderByField(Object bean, Field orderBy) {
        this.openConnection();
        ArrayList<Object> beans = selectBeansOrderByField(bean, orderBy, this.database);
        this.closeConnection();
        return beans;
    }
    public ArrayList<Object> selectBeansOrderByField(Object bean, Field orderBy, SQLiteDatabase conn) {
        ArrayList<Object> beans = new ArrayList<Object>();
        try{
            Entity entity = bean.getClass().getAnnotation(Entity.class);
            ArrayList<Field> beanFields = getFields(bean);
            String sql = "SELECT * FROM " + entity.table() + " ORDER BY "+ databaseColumn(orderBy) + " DESC LIMIT 10";

            Cursor rs = conn.rawQuery(sql, null);
            while (rs.moveToNext()) {
                beans.add(result(rs, bean, beanFields));
            }
        }catch (SQLException s){
            s.printStackTrace();
        }
        return beans;
    }
    public ArrayList<Object> selectMany(Object bean, Object target) {
        return selectMany(bean, target, getOrderField(target));
    }
    public ArrayList<Object> selectMany(Object bean, Object target, Field orderBy) {
        this.openConnection();
        ArrayList<Object> beans = selectMany(bean, target, orderBy, this.database);
        this.closeConnection();
        return beans;
    }
    public ArrayList<Object> selectMany(Object bean, Object target, SQLiteDatabase conn) {
        return selectMany(bean, target, getOrderField(target), conn);
    }
    public ArrayList<Object> selectMany(Object bean, Object target, Field orderBy, SQLiteDatabase conn) {
        ArrayList<Object> beans = new ArrayList<Object>();

        try{
            ManyRelations hasMultiple = bean.getClass().getAnnotation(ManyRelations.class);

            if (hasMultiple != null){
                HasMany hasMany = null;
                for (int i = 0; i < hasMultiple.value().length; i++) {
                    if(hasMultiple.value()[i].entity().equals(target.getClass())){
                        hasMany = hasMultiple.value()[i];
                    }
                }
                if (hasMany != null){
                    Entity entity = target.getClass().getAnnotation(Entity.class);
                    String sql = "SELECT * FROM " + entity.table() +" WHERE "
                            + databaseColumn(getField(target, hasMany.foreignKey())) + " = ?";

                    if(orderBy != null){
                        sql+= " ORDER BY "+ databaseColumn(orderBy);
                    }

                    ArrayList<Field> targetFields = getFields(target);

                    Cursor rs = prepare(bean, sql, primaryField(bean), conn);
                    while (rs.moveToNext()) {
                        beans.add(result(rs, target, targetFields));
                    }
                }
            }
        }catch (SQLException s){
            s.printStackTrace();
        }
        return beans;
    }

    public Integer countBean(Object bean) {
        this.openConnection();
        Integer count = countBean(bean, this.database);
        this.closeConnection();
        return count;
    }

    public Integer countBean(Object bean, SQLiteDatabase conn) {
        Integer count = 0;

        Entity entity = bean.getClass().getAnnotation(Entity.class);
        String sql = "SELECT COUNT(*) FROM " + entity.table();

        Cursor rs = conn.rawQuery(sql, null);
        if (rs.moveToFirst())
            count = rs.getInt(0);

        return count;
    }

    public Object firstOrLastBean(Object bean, boolean last) {
        this.openConnection();
        Object result = firstOrLastBean(bean, last, this.database);
        this.closeConnection();
        return result;
    }


    public Object firstOrLastBean(Object bean, boolean last, SQLiteDatabase conn) {
        Object result = null;
        try{
            Entity entity = bean.getClass().getAnnotation(Entity.class);
            ArrayList<Field> beanFields = getFields(bean);

            String sql = "SELECT * FROM " + entity.table() + " ORDER BY "
                    + primaryColumn(primaryField(bean));

            if(last)
                sql += " DESC";

            sql+=" LIMIT 1";

            Cursor rs = conn.rawQuery(sql, null);
            if (rs.moveToFirst()) {
                result = result(rs, bean, beanFields);
            }
        }catch (SQLException s){
            s.printStackTrace();
        }
        return result;
    }

    public ArrayList<Object> selectWhere(Object bean,Condition condition) {
        return selectWhere(bean, condition, getOrderField(bean));
    }
    public ArrayList<Object> selectWhere(Object bean, Condition condition, Field orderBy) {
        this.openConnection();
        ArrayList<Object> beans = selectWhere(bean, condition, orderBy, this.database);
        this.closeConnection();
        return beans;
    }
    public ArrayList<Object> selectWhere(Object bean, Condition condition, SQLiteDatabase conn) {
        return selectWhere(bean, condition, getOrderField(bean), conn);
    }

    public ArrayList<Object> selectWhere(Object bean, Condition condition, Field orderBy, SQLiteDatabase conn) {
        ArrayList<Object> beans = new ArrayList<Object>();
        try{
            ArrayList<Field> beanFields = getFields(bean);

            condition.prepareSQL(bean);

            String sql = condition.getSql();

            if(orderBy != null){
                sql+= " ORDER BY "+ databaseColumn(orderBy);
            }

            Cursor rs = conn.rawQuery(sql, null);
            while (rs.moveToNext()) {
                beans.add(result(rs, bean, beanFields));
            }
        }catch (SQLException s){
            s.printStackTrace();
        }
        return beans;
    }

    public boolean deleteBean(Object bean) {
        this.openConnection();
        boolean result = deleteBean(bean, this.database);
        this.closeConnection();
        return result;
    }

    public boolean deleteMany(Object bean, SQLiteDatabase conn) {
        ManyRelations hasMultiple = bean.getClass().getAnnotation(ManyRelations.class);

        boolean result = true;

        if(hasMultiple != null){
            try {
                for (int i = 0; i < hasMultiple.value().length; i++) {
                    HasMany hasMany = hasMultiple.value()[i];
                    Class<?> child = hasMany.entity();
                    Object childInstance = child.newInstance();
                    ArrayList<Object> results = selectMany(bean, childInstance , conn);
                    for (Object object : results) {
                        boolean status = deleteBean(object, conn);
                        if (!status){
                            result = false;
                        }
                    }
                }
            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


        return result;
    }

    public boolean deleteBean(Object bean, SQLiteDatabase conn) {
        boolean response = true;
        try {

            Entity entity = bean.getClass().getAnnotation(Entity.class);
            ManyRelations hasMultiple = bean.getClass().getAnnotation(ManyRelations.class);
            Field primaryField = primaryField(bean);

            if(hasMultiple != null){
                response = deleteMany(bean, conn);
            }
            if(response){
                String sql = "DELETE FROM "+ entity.table() + " WHERE "+ primaryColumn(primaryField) +" = ?";
                this.pst = conn.compileStatement(sql);
                prepare(this.pst, bean, 1, primaryField);
                long result = this.pst.executeInsert();
                return (result != -1) ? true : false;
            }

        }catch (SQLException s){
            s.printStackTrace();
        }
        return response;
    }


    //Static Helpers *****************************

    public static ArrayList<Field> filterFields(ArrayList<Field> fields){
        for(Field field : fields){
            if(field.getName().startsWith("$")){
                fields.remove(field);
            }
        }
        return fields;
    }

    public static Field getOrderField(Object bean){
        OrderBy orderBy = bean.getClass().getAnnotation(OrderBy.class);
        if(orderBy != null){
            return getField(bean, orderBy.field());
        }else{
            return null;
        }
    }

    public static boolean isNullRelation(Object bean, Field field){
        if (field.getType() == int.class){
            if ((int)getValue(bean, field) == 0){
                return true;
            }
        } else {
            if (getValue(bean, field) == null){
                return true;
            }
        }
        return false;
    }

    public static Object getValue(Object bean, Field field){
        try {
            return getGetter(field).invoke(bean);
        } catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static void setValue(Object bean, Field field, Object value){
        try {
            getSetter(field).invoke(bean, value);
        } catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static Field getField(Object bean, String fieldName){
        try {
            return bean.getClass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Field> getFields(Object bean){
        return filterFields(new ArrayList<Field>(Arrays.asList(bean.getClass().getDeclaredFields())));
    }

    public static Field primaryField(Object bean){
        Field primaryField = null;

        Entity entity = bean.getClass().getAnnotation(Entity.class);

        try {
            primaryField = bean.getClass().getDeclaredField(entity.primaryKey());
        } catch (NoSuchFieldException | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return primaryField;
    }

    public static String primaryColumn(Field primaryField){
        Column column = primaryField.getAnnotation(Column.class);
        if (column == null){
            return primaryField.getName();
        } else {
            return column.name();
        }
    }

    public static HashMap<String, String> buildStrings(ArrayList<Field> fields){
        HashMap<String, String> result = new HashMap<String,String>();
        ArrayList<String> columns = databaseColumns(fields);
        String fieldString = "";
        String parameterString = "";
        String joiner = ",";

        for (int i = 0; i < columns.size(); i++) {
            fieldString += columns.get(i);
            parameterString += "?";
            if (i < columns.size() - 1) {
                fieldString += joiner;
                parameterString += joiner;
            }
        }

        result.put(FIELDS, fieldString);
        result.put(PARAMETERS, parameterString);

        return result;
    }

    public static ArrayList<Method> getGetters(Object bean, ArrayList<Field> fields) {
        ArrayList<Method> beanGetters = new ArrayList<Method>();

        for (Field field : fields) {
            if (field.getAnnotation(Ignore.class) == null){
                Method getter = getGetter(field);
                if(getter != null) {
                    beanGetters.add(getter);
                }
            }
        }
        return beanGetters;
    }

    public static Method getGetter(Field field){
        Method result = null;
        try {
            if(!field.getName().startsWith("$")) {
                char[] fieldName = field.getName().trim().toCharArray();
                String methodName = "";
                if ((field.getType() == Boolean.class ||
                        field.getType() == boolean.class) &&
                        new String(fieldName).contains("is")) {
                    methodName = new String(fieldName);
                } else {
                    fieldName[0] = Character.toUpperCase(fieldName[0]);
                    methodName = "get" + new String(fieldName);
                }
                result = field.getDeclaringClass().getDeclaredMethod(methodName);
            }
        } catch (NoSuchMethodException | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public static ArrayList<Method> getSetters(Object bean, ArrayList<Field> fields) {
        ArrayList<Method> beanSetters = new ArrayList<Method>();

        for (Field field : fields) {
            if (field.getAnnotation(Ignore.class) == null){
                Method setter = getSetter(field);
                if(setter != null) {
                    beanSetters.add(setter);
                }
            }
        }
        return beanSetters;
    }

    public static Method getSetter(Field field) {
        try {
            if(!field.getName().startsWith("$")) {
                char[] fieldName = field.getName().trim().toCharArray();
                String methodName = "";
                if ((field.getType() == Boolean.class ||
                        field.getType() == boolean.class) &&
                        new String(fieldName).contains("is")) {
                    methodName = new String(fieldName);
                    methodName = methodName.substring(2);
                    methodName = "set" + methodName;
                } else {
                    fieldName[0] = Character.toUpperCase(fieldName[0]);
                    methodName = "set" + new String(fieldName);
                }
                return field.getDeclaringClass().getDeclaredMethod(methodName, field.getType());
            }
        } catch (NoSuchMethodException | SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<String> databaseColumns(ArrayList<Field> fields) {
        ArrayList<String> databaseColumns = new ArrayList<String>();
        for (Field field : fields) {
            if(field.getAnnotation(Ignore.class) == null){
                databaseColumns.add(databaseColumn(field));
            }
        }
        return databaseColumns;
    }

    public static String databaseColumn(Field field){
        Column columnAnnotation = field.getAnnotation(Column.class);
        if (columnAnnotation != null){
            return columnAnnotation.name();
        } else {
            return field.getName();
        }
    }

    public static Cursor prepare (Object bean, String sql, Field parameter, SQLiteDatabase conn){
        return conn.rawQuery(sql,new String[]{getValue(bean , parameter).toString()});
    }

    public static Cursor prepare (Object bean, String sql, ArrayList<Field> parameters, SQLiteDatabase conn){
        String[] params = new String[parameters.size()];
        for (int i = 0; i < parameters.size(); i++) {
            params[i] = parameters.get(i).toString();
        }
        return conn.rawQuery(sql,params);
    }

    public static void prepare(SQLiteStatement pst, Object bean, ArrayList<Field> fields) throws SQLException{
        ArrayList<Method> getters = getGetters(bean, fields);
        for (int i = 0; i < getters.size(); i++) {
            dataSet(pst, i+1, bean, getters.get(i));
        }
    }

    public static void prepare(SQLiteStatement pst, Object bean, int index, Field field) throws SQLException {
        Method getter = null;
        if (field.getAnnotation(Ignore.class) == null){
            try {
                getter = getGetter(field);
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        dataSet(pst, index, bean, getter);
    }

    public static Object result(Cursor rs, Object bean, ArrayList<Field> fields) throws SQLException {
        Object result = null;
        try {
            result = bean.getClass().newInstance();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ArrayList<Method> setters = getSetters(bean, fields);
        for (int i = 0; i < setters.size(); i++) {
            result = dataGet(rs, i+1, result, setters.get(i),fields.get(i));
        }
        return result;
    }

    public static Object result(Cursor rs, Object bean, int index, Field field) throws SQLException {
        Method setter = null;
        if (field.getAnnotation(Ignore.class) == null){
            try {
                setter = getSetter(field);
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return dataGet(rs, index, bean, setter, field);
        }
        return null;
    }


    public static void dataSet(SQLiteStatement pst, int index, Object bean, Method getter) throws SQLException {
        try {
            Object test = getter.invoke(bean);

            if (test instanceof String) {
                pst.bindString(index, (String) test);
            } else if (test instanceof Integer) {
                pst.bindString(index, Integer.toString((Integer) test));
            } else if (test instanceof Double) {
                pst.bindDouble(index, (Double) test);
            }

        } catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static Object dataGet(Cursor rs, int index, Object bean, Method setter, Field field) throws SQLException {
        try {
            Class<?> test = setter.getParameterTypes()[0];

            if (test == String.class) {
                setter.invoke(bean, rs.getString(rs.getColumnIndex(databaseColumn(field))));
            } else if (test == Integer.class || test == int.class) {
                setter.invoke(bean, rs.getInt(rs.getColumnIndex(databaseColumn(field))));
            } else if (test == Double.class) {
                setter.invoke(bean, rs.getDouble(rs.getColumnIndex(databaseColumn(field))));
            }

        } catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return bean;
    }

}