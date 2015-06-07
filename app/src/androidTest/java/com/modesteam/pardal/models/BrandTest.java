package com.modesteam.pardal.models;

import com.modesteam.pardal.Pardal;

import junit.framework.TestCase;

import java.sql.SQLException;
import java.util.ArrayList;

import helpers.Condition;
import helpers.Operator;
import libraries.NotNullableException;
import models.Brand;
import models.City;
import models.Model;
import models.State;

/**
 * Created by luis on 03/05/15.
 */
public class BrandTest extends TestCase {
    //Metodos Iguais por classe(Mude o nome e os parametros)

    Brand brand1, brand2;
    public void setUp() throws SQLException, NotNullableException, ClassNotFoundException {
        Pardal.getInstance().setDatabaseName("database_test.sqlite3.db");
        for (Brand brand: Brand.getAll()){
            brand.delete();
        }
        brand1 = new Brand("VW");
        brand2 = new Brand("FIAT");
        brand1.save();
        brand2.save();
    }
    public void tearDown() throws SQLException, ClassNotFoundException {
        brand1.delete();
        brand2.delete();
    }
    public void testShouldGetFirstBrandFromDatabase() throws SQLException, ClassNotFoundException {
        assertEquals(brand1.getName(), Brand.first().getName());
    }
    public void testShouldGetLastBrandFromDatabase() throws SQLException, ClassNotFoundException {
        assertEquals(brand2.getName(), Brand.last().getName());
    }
    public void testShouldGetBrandFromDatabase() throws SQLException, ClassNotFoundException {
        assertEquals(brand2.getName(), Brand.get(Brand.last().getId()).getName());
    }

    public void testShouldCountBrandFromDatabase() throws SQLException, ClassNotFoundException {
        assertEquals(2, Brand.count());
    }

    public void testShouldGetAllBrandFromDatabase() throws SQLException, ClassNotFoundException {
        assertEquals(2, Brand.getAll().size());
    }

    public void testShouldSaveBrandFromDatabase() throws SQLException, ClassNotFoundException, NotNullableException {
        assertEquals(2, Brand.getAll().size());
        Brand brand3 = new Brand("CHEVROLET");
        brand3.save();
        assertEquals(3, Brand.getAll().size());
        brand3.delete();
    }
    public void testShouldDeleteBrandFromDatabase() throws SQLException, ClassNotFoundException, NotNullableException {
        Brand brand3 = new Brand("CHEVROLET");
        brand3.save();
        assertEquals(3, Brand.getAll().size());
        brand3.delete();
        assertEquals(2, Brand.getAll().size());
    }

    public void testShouldGetWhereBrandFromDatabase() throws SQLException, ClassNotFoundException, NotNullableException {
        Condition condition = new Condition(new Brand(),"name", Operator.EQUAL,"VW");
        assertEquals(brand1.getName(), Brand.getWhere(condition).get(0).getName());
    }

    public void testShouldGetModelsOfBrandFromDatabase() throws SQLException, ClassNotFoundException, NotNullableException {
        Model model = new Model("Model1",false, Brand.first().getId(),1);
        model.save();
        assertEquals(model.getName(), Brand.first().getModels().get(0).getName());
        model.delete();
    }

    public void testShouldShowBrandSorted() throws SQLException, ClassNotFoundException, NotNullableException {
        Brand brandZ = new Brand ("Z");
        brandZ.save();
        Brand brandA = new Brand ("A");
        brandA.save();
        ArrayList<Brand> list = Brand.getAll();
        assertEquals(brandA.getName(), list.get(0).getName());
        assertEquals(brandZ.getName(), list.get(list.size()-1).getName());
        brandA.delete();
        brandZ.delete();
    }
}