package com.modesteam.pardal.models;

import com.modesteam.pardal.Pardal;

import junit.framework.TestCase;

import java.sql.SQLException;
import java.util.ArrayList;

import helpers.Condition;
import helpers.GenericPersistence;
import helpers.Operator;
import libraries.NotNullableException;
import models.City;
import models.State;
import models.HighwayStretch;

/**
 * Created by luisresende on 03/05/15.
 */
public class CityTest extends TestCase {
    //Metodos Iguais por classe(Mude o nome e os parametros)
    City city1,city2;
    State state;
    public void setUp() throws SQLException, NotNullableException, ClassNotFoundException {
        Pardal.getInstance().setDatabaseName("database_test.sqlite3.db");
        for (State state: State.getAll()){
            state.delete();
        }
        for (City city: City.getAll()){
            city.delete();
        }
        state = new State("DF");
        state.save();
        city1 = new City("1","City1",1);
        city2 = new City("2","City2",1);
        city1.save();
        city2.save();

    }
    public void tearDown() throws SQLException, ClassNotFoundException {
        city1.delete();
        city2.delete();
        state.delete();
    }

    public void testShouldGetFirstCityFromDatabase() throws SQLException, ClassNotFoundException {
        assertEquals(city1.getName(), City.first().getName());
    }

    public void testShouldGetLastCityFromDatabase() throws SQLException, ClassNotFoundException {
        assertEquals(city2.getName(), City.last().getName());
    }

    public void testShouldGetCityFromDatabase() throws SQLException, ClassNotFoundException {
        assertEquals(city2.getName(), City.get(City.last().getId()).getName());
    }

    public void testShouldCountCityFromDatabase() throws SQLException, ClassNotFoundException {
       assertEquals(2, City.count());
    }

    public void testShouldGetAllCityFromDatabase() throws SQLException, ClassNotFoundException {
        assertEquals(2, City.getAll().size());
    }
    public void testShouldSaveCityFromDatabase() throws SQLException, ClassNotFoundException, NotNullableException {
        assertEquals(2, City.getAll().size());
        City city3 = new City("3","City3",1);
        city3.save();
        assertEquals(3, City.getAll().size());
        city3.delete();
    }
    public void testShouldDeleteCityFromDatabase() throws SQLException, ClassNotFoundException, NotNullableException {
        City city3 = new City("3","City3",1);
        city3.save();
        assertEquals(3, City.getAll().size());
        city3.delete();
        assertEquals(2, City.getAll().size());
    }

    public void testShouldShowCitySorted() throws SQLException, ClassNotFoundException, NotNullableException {
        City cityZ = new City ("1","Z", 1);
        cityZ.save();
        City cityA = new City ("2","A", 2);
        cityA.save();
        ArrayList<City> list = City.getAll();
        assertEquals(cityA.getName(), list.get(0).getName());
        assertEquals(cityZ.getName(), list.get(list.size() - 1).getName());
    }

    //Metodos Diferentes por classe

    public void testShouldGetWhereCityFromDatabase() throws SQLException, ClassNotFoundException, NotNullableException {
        Condition condition = new Condition(new City(),"name", Operator.EQUAL,"City1");
        assertEquals(city1.getName(), City.getWhere(condition).get(0).getName());
    }

//    public void testShouldGetStateOfCityFromDatabase() throws SQLException, ClassNotFoundException, NotNullableException {
//        assertEquals(state.getName(), City.first().getState().getName());
//    }

    public void testShouldGetHighwayStretchesOfCityFromDatabase() throws SQLException, ClassNotFoundException, NotNullableException {
        HighwayStretch highwayStretches = new HighwayStretch ("1",0,City.first().getId());
        highwayStretches.save();
        assertEquals(highwayStretches.getNumber(), City.first().getHighwayStretches().get(0).getNumber());
        highwayStretches.delete();
    }


}
