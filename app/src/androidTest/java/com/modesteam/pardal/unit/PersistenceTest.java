package com.modesteam.pardal.unit;

import com.modesteam.pardal.Pardal;

import junit.framework.TestCase;

import helpers.Condition;
import helpers.GenericPersistence;
import helpers.Joiner;
import helpers.Operator;
import libraries.NotNullableException;
import models.Brand;
import models.City;
import models.Model;
import models.State;

/**
 * Created by andrebsguedes on 19/06/15.
 */
public class PersistenceTest extends TestCase {

    public void setUp(){
        Pardal.getInstance().setDatabaseName("database.dev.sqlite3.db");

    }

    public void testShouldInsertMany() throws NotNullableException {
        State state = new State("STATE");
        state.save();
        City city = new City();
        city.setCode("000");
        city.setName("CITY");

        GenericPersistence gP = new GenericPersistence();
        gP.insertMany(state,city);

        assertEquals(state.getId(), ((City) City.last()).getIdState());
        city.delete();
        state.delete();
    }

    public void testShouldSelectManyCondition() throws NotNullableException {
        State state = new State("STATE");
        state.save();
        City city = new City();
        city.setCode("000");
        city.setName("CITY");

        GenericPersistence gP = new GenericPersistence();
        gP.insertMany(state,city);

        assertEquals(state.getId(), ((City) City.last()).getIdState());

        Condition condition = new Condition(
                new Condition(city,"name", Operator.EQUAL, "CITY"),
                Joiner.AND,
                new Condition(state, "id", Operator.EQUAL, state.getId())
        );

        assertEquals(City.last().getId(), ((City)City.getWhere(condition).get(0)).getId());
        city.delete();
        state.delete();
    }

    public void testShouldSelectOneCondition() throws NotNullableException {
        State state = new State("STATE");
        state.save();
        City city = new City();
        city.setCode("000");
        city.setName("CITY");

        GenericPersistence gP = new GenericPersistence();
        gP.insertMany(state,city);

        assertEquals(state.getId(), ((City) City.last()).getIdState());

        Condition condition = new Condition(
                new Condition(city,"name", Operator.EQUAL, "CITY"),
                Joiner.AND,
                new Condition(state, "id", Operator.EQUAL, state.getId())
        );

        assertEquals(State.last().getId(), ((State)State.getWhere(condition).get(0)).getId());
        city.delete();
        state.delete();
    }


}
