package com.modesteam.pardal.state;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.State;

/**
 * Created by luisresende on 06/05/15.
 */
public class StateContent {

    public static List<State> ITEMS = new ArrayList<State>();

    /**
     * A map of sample (dummy) items, by ID.
     */

    static {
        // Add all state items.
        ArrayList<State> allStates = null;
        allStates = State.getAll();
        for (State state: allStates){
            addItem(state);
        }
    }
    private static void addItem(State item) {

        ITEMS.add(item);
    }
}
