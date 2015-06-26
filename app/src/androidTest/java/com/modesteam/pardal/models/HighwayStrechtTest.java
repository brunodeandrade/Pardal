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
import models.HighwayStretch;
import models.State;

/**
 * Created by lucaslrt on 04/05/15.
 */
public class HighwayStrechtTest extends TestCase {
    //Metodos Iguais por classe(Mude o nome e os parametros)
    HighwayStretch highway1, highway2;
    State testState;
    City testCity;

    @Override
    public void setUp() throws SQLException, NotNullableException, ClassNotFoundException {
        Pardal.getInstance().setDatabaseName("database_test.sqlite3.db");
        for (State state: State.getAll()){
            state.delete();
        }
        for (City city: City.getAll()){
            city.delete();
        }
        for (HighwayStretch highwayStretch: HighwayStretch.getAll()){
            highwayStretch.delete();
        }
        testState = new State("testState");
        testState.save();
        testCity = new City("001","Gama", testState.getId());
        testCity.save();
        highway1 = new HighwayStretch("040", 10, testCity.getId());
        highway2 = new HighwayStretch("040", 11, testCity.getId());
        highway1.save();
        highway2.save();

    }

    @Override
    public void tearDown() throws SQLException, ClassNotFoundException {
        highway1.delete();
        highway2.delete();
        testState.delete();
        testCity.delete();
    }

    public void testShouldGetFirstHighwayFromDatabase() throws SQLException, ClassNotFoundException {
        assertEquals(highway1.getId(), HighwayStretch.first().getId());
    }

    public void testShouldGetLastHighwayFromDatabase() throws SQLException, ClassNotFoundException {
        assertEquals(highway2.getId(), HighwayStretch.last().getId());
    }

    public void testShouldGetHighwayFromDatabase() throws SQLException, ClassNotFoundException {
        assertEquals(highway2.getId(), HighwayStretch.get(HighwayStretch.last().getId()).getId());
    }

    public void testShouldCountHighwayFromDatabase() throws SQLException, ClassNotFoundException {
        assertEquals(2, HighwayStretch.count());
    }

    public void testShouldGetAllHighwayFromDatabase() throws SQLException, ClassNotFoundException {
        assertEquals(2, HighwayStretch.getAll().size());
    }

    public void testShouldSaveHighwayFromDatabase() throws SQLException, ClassNotFoundException, NotNullableException {
        assertEquals(2, HighwayStretch.getAll().size());
        HighwayStretch highway3 =  new HighwayStretch("040", 12, testCity.getId());
        highway3.save();
        assertEquals(3, HighwayStretch.getAll().size());
        highway3.delete();
    }

    public void testShouldDeleteHighwayFromDatabase() throws SQLException, ClassNotFoundException, NotNullableException {
        HighwayStretch highway3 = new HighwayStretch("040", 13, testCity.getId());
        highway3.save();
        assertEquals(3, HighwayStretch.getAll().size());
        highway3.delete();
        assertEquals(2, HighwayStretch.getAll().size());
    }

    public void testShouldGetWhereHighwayFromDatabase() throws SQLException, ClassNotFoundException, NotNullableException {
        Condition condition = new Condition(new HighwayStretch(),"kilometer", Operator.EQUAL,10);
        assertEquals(highway1.getKilometer(), HighwayStretch.getWhere(condition).get(0).getKilometer());
    }

    public void testShouldShowHighwaySorted() throws SQLException, ClassNotFoundException, NotNullableException {
        HighwayStretch highwayStretchZ = new HighwayStretch ("99",14,testCity.getId());
        highwayStretchZ.save();
        HighwayStretch highwayStretchA = new HighwayStretch ("00", 15,testCity.getId());
        highwayStretchA.save();
        ArrayList<HighwayStretch> list = HighwayStretch.getAll();
        assertEquals(highwayStretchA.getNumber(), list.get(0).getNumber());
        assertEquals(highwayStretchZ.getNumber(), list.get(list.size()-1).getNumber());
        highwayStretchA.delete();
        highwayStretchZ.delete();
    }
}
