package com.modesteam.pardal.models;

import com.modesteam.pardal.Pardal;

import junit.framework.TestCase;

import java.sql.SQLException;
import java.util.ArrayList;

import helpers.Condition;
import helpers.Operator;
import libraries.NotNullableException;
import models.City;
import models.State;

/**
 * Created by luis on 03/05/15.
 */
public class StateTest extends TestCase {
    //Metodos Iguais por classe(Mude o nome e os parametros)
    City city1;
    State state1, state2;

    @Override
    public void setUp() throws SQLException, NotNullableException, ClassNotFoundException {
        Pardal.getInstance().setDatabaseName("database_test.sqlite3.db");
        for (State state: State.getAll()){
            state.delete();
        }
        state1 = new State("DF");
        state2 = new State("GO");
        state1.save();
        state2.save();
    }
    public void tearDown() throws SQLException, ClassNotFoundException {
        state1.delete();
        state2.delete();
    }
    public void testShouldGetFirstStateFromDatabase() throws SQLException, ClassNotFoundException {
        assertEquals(state1.getName(), State.first().getName());
    }
    public void testShouldGetLastStateFromDatabase() throws SQLException, ClassNotFoundException {
        assertEquals(state2.getName(), State.last().getName());
    }
    public void testShouldGetStateFromDatabase() throws SQLException, ClassNotFoundException {
        assertEquals(state2.getName(), State.get(State.last().getId()).getName());
    }

    public void testShouldCountStateFromDatabase() throws SQLException, ClassNotFoundException {
        assertEquals(2, State.count());
    }

    public void testShouldGetAllStateFromDatabase() throws SQLException, ClassNotFoundException {
        assertEquals(2, State.getAll().size());
    }

    public void testShouldSaveStateFromDatabase() throws SQLException, ClassNotFoundException, NotNullableException {
        assertEquals(2, State.getAll().size());
        State state3 = new State("SE");
        state3.save();
        assertEquals(3, State.getAll().size());
        state3.delete();
    }
    public void testShouldDeleteStateFromDatabase() throws SQLException, ClassNotFoundException, NotNullableException {
        State state3 = new State("PE");
        state3.save();
        assertEquals(3, State.getAll().size());
        state3.delete();
        assertEquals(2, State.getAll().size());
    }

    public void testShouldGetWhereStateFromDatabase() throws SQLException, ClassNotFoundException, NotNullableException {
        Condition condition = new Condition(new State(),"name", Operator.EQUAL,"DF");
        assertEquals(state1.getName(), State.getWhere(condition).get(0).getName());
    }

    public void testShouldGetCityOfStateFromDatabase() throws SQLException, ClassNotFoundException, NotNullableException {
        City city = new City("1", "City1", State.first().getId());
        city.save();
        assertEquals(city.getName(), State.first().getCities().get(0).getName());
        city.delete();
    }

    public void testShouldShowStateSorted() throws SQLException, ClassNotFoundException, NotNullableException {
        State stateZ = new State ("Z");
        stateZ.save();
        State stateA = new State ("A");
        stateA.save();
        ArrayList<State> list = State.getAll();
        assertEquals(stateA.getName(), list.get(0).getName());
        assertEquals(stateZ.getName(), list.get(list.size() - 1).getName());
        stateA.delete();
        stateZ.delete();
    }
}