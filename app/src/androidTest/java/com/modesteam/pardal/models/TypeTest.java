package com.modesteam.pardal.models;

import com.modesteam.pardal.Pardal;

import junit.framework.TestCase;

import java.sql.SQLException;
import java.util.ArrayList;

import helpers.Condition;
import helpers.Operator;
import libraries.NotNullableException;
import models.Brand;
import models.Model;
import models.State;
import models.Type;

/**
 * Created by luisresende on 03/05/15.
 */
public class TypeTest extends TestCase {

    //Metodos Iguais por classe(Mude o nome e os parametros)
    Type type1, type2;
    public void setUp() throws SQLException, NotNullableException, ClassNotFoundException {
        Pardal.getInstance().setDatabaseName("database_test.sqlite3.db");
        for (Type type: Type.getAll()){
            type.delete();
        }
        type1 = new Type("Carro tipo passageiro","PASSAGEIRO");
        type2 = new Type("Carro tipo carga","CARGA");
        type1.save();
        type2.save();

    }
    public void tearDown() throws SQLException, ClassNotFoundException {
        type1.delete();
        type2.delete();
    }

    public void testShouldGetFirstTypeFromDatabase() throws SQLException, ClassNotFoundException {
        assertEquals(type1.getName(), Type.first().getName());
    }

    public void testShouldGetLastTypeFromDatabase() throws SQLException, ClassNotFoundException {
        assertEquals(type2.getName(), Type.last().getName());
    }

    public void testShouldGetTypeFromDatabase() throws SQLException, ClassNotFoundException {
        assertEquals(type2.getName(), Type.get(Type.last().getId()).getName());
    }

    public void testShouldCountTypeFromDatabase() throws SQLException, ClassNotFoundException {
        assertEquals(2, Type.count());
    }

    public void testShouldGetAllTypeFromDatabase() throws SQLException, ClassNotFoundException {
        assertEquals(2, Type.getAll().size());
    }
    public void testShouldSaveTypeFromDatabase() throws SQLException, ClassNotFoundException, NotNullableException {
        assertEquals(2, Type.getAll().size());
        Type type3 = new Type("Carro tipo misto","MISTO");
        type3.save();
        assertEquals(3, Type.getAll().size());
        type3.delete();
    }
    public void testShouldDeleteTypeFromDatabase() throws SQLException, ClassNotFoundException, NotNullableException {
        Type type3 = new Type("Carro tipo misto", "MISTO");
        type3.save();
        assertEquals(3, Type.getAll().size());
        type3.delete();
        assertEquals(2, Type.getAll().size());
    }

    public void testShouldGetWhereTypeFromDatabase() throws SQLException, ClassNotFoundException, NotNullableException {
        Condition condition = new Condition(new Type(),"name", Operator.EQUAL,"PASSAGEIRO");
        assertEquals(type1.getName(), Type.getWhere(condition).get(0).getName());
    }

    public void testShouldShowTypeSorted() throws SQLException, ClassNotFoundException, NotNullableException {
        Type typeZ = new Type ("1","Z");
        typeZ.save();
        Type typeA = new Type ("2","A");
        typeA.save();
        ArrayList<Type> list = Type.getAll();
        assertEquals(typeA.getName(), list.get(0).getName());
        assertEquals(typeZ.getName(), list.get(list.size() - 1).getName());
        typeA.delete();
        typeZ.delete();
    }
//Metodos Diferentes por classe

    public void testShouldGetModelsOfTypeFromDatabase() throws SQLException, ClassNotFoundException, NotNullableException {
        Model model;
        Brand brand;
        brand = new Brand("VW");
        brand.save();
        model = new Model("Gol 1.6",true,Brand.first().getId(),Type.first().getId());
        model.save();
        assertEquals(model.getName(), Type.first().getModels().get(0).getName());
        brand.delete();
        model.delete();
    }
}
