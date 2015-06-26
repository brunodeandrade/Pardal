package com.modesteam.pardal.models;

import android.util.Log;

import com.modesteam.pardal.Pardal;

import junit.framework.TestCase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;

import helpers.Condition;
import helpers.Operator;
import libraries.NotNullableException;
import models.Model;
import models.Type;


/**
 * Created by laerciojunior on 04/05/15.
 */
public class ModelTest  extends TestCase {
        Model model1, model2;
        Type type1;
        public void setUp() throws SQLException, NotNullableException, ClassNotFoundException {
            Pardal.getInstance().setDatabaseName("database_test.sqlite3.db");
            for (Type type: Type.getAll()){
                type.delete();
            }
            for (Model model: Model.getAll()) {
                model.delete();
            }
            type1 = new Type("tipo passagerio","PASSAGEIRO");
            type1.save();
            model1 = new Model("Model1",true,1,type1.getId());
            model2 = new Model("Model2",false,2,2);
            model1.save();
            model2.save();

        }
        public void tearDown() throws SQLException, ClassNotFoundException {
            model1.delete();
            model2.delete();
            type1.delete();
        }
        public void testShouldGetFirstmodelFromDatabase() throws SQLException, ClassNotFoundException {
            assertEquals(model1.getName(), Model.first().getName());
            assertEquals(model1.getBrand(), Model.first().getBrand());
            assertEquals(model1.getIdType(), Model.first().getIdType());
            assertEquals(model1.getId(), Model.first().getId());
        }

        public void testShouldGetLastmodelFromDatabase() throws SQLException, ClassNotFoundException {
            assertEquals(model2.getName(), Model.last().getName());
            assertEquals(model2.getBrand(), Model.last().getBrand());
            assertEquals(model2.getIdType(),Model.last().getIdType());
            assertEquals(model2.getId(), Model.last().getId());
        }

        public void testShouldCountmodelFromDatabase() throws SQLException, ClassNotFoundException {
            assertEquals(2, Model.count());
        }

        public void testShouldGetAllmodelFromDatabase() throws SQLException, ClassNotFoundException {
            assertEquals(2, Model.getAll().size());
        }

        public void testShouldSavemodelFromDatabase() throws SQLException, ClassNotFoundException, NotNullableException {
            assertEquals(2, Model.getAll().size());
            Model model3 = new Model("Model3",false,3,3);
            model3.save();
            assertEquals(3, Model.getAll().size());
            model3.delete();
        }

        public void testShouldDeletemodelFromDatabase() throws SQLException, ClassNotFoundException, NotNullableException {
            Model model3 = new Model("Model3",false,3,3);
            model3.save();
            assertEquals(3, Model.getAll().size());
            model3.delete();
            assertEquals(2, Model.getAll().size());
        }

        public void testShouldGetWheremodelFromDatabase() throws SQLException, ClassNotFoundException, NotNullableException {
            Condition condition = new Condition(new Model(),"name", Operator.EQUAL,"Model1");
            assertEquals(model1.getName(), Model.getWhere(condition).get(0).getName());
        }
        public void testShouldGetTypeFromModelFromDatabase() throws SQLException, ClassNotFoundException {
           assertEquals(model1.getType().getName(), "PASSAGEIRO");
        }
    public void testShouldSetIsNationalModel() throws SQLException, ClassNotFoundException, NotNullableException {
        Model model3 = new Model();
        model3.setIdType(5);
        model3.setName("CARGA");
        model3.setIdBrand(5);
        model3.setNational(true);
        model3.save();
        Condition condition = new Condition(new Model(),"name", Operator.EQUAL,"CARGA");
        assertEquals(model3.getName(), Model.getWhere(condition).get(0).getName());
        model3.delete();
    }

    public void testShouldGetModelFromDatabase() throws SQLException, ClassNotFoundException, NotNullableException {
        assertEquals(model1.getName(), Model.get(Model.first().getId()).getName());
    }

    public void testShouldShowModelSorted() throws SQLException, ClassNotFoundException, NotNullableException {
        Model modelZ = new Model("Z",true,1,1);
        modelZ.save();
        Model modelA = new Model ("A",true,2,2);
        modelA.save();
        ArrayList<Model> list = Model.getAll();
        assertEquals(modelA.getName(), list.get(0).getName());
        assertEquals(modelZ.getName(), list.get(list.size() - 1).getName());
        modelA.delete();
        modelZ.delete();
    }


}
