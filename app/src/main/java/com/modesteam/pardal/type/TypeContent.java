package com.modesteam.pardal.type;

/**
 * Created by luisresende on 29/04/15.
 */

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Model;
import models.Type;


public class TypeContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<Type> ITEMS = new ArrayList<Type>();

    /**
     * A map of sample (dummy) items, by ID.
     */

    static {
        // Add all type items.
        ArrayList<Type> allTypes = null;
        allTypes = Type.getAll();
        for (Type type: allTypes){
            addItem(type);
        }
    }
    private static void addItem(Type item) {

        ITEMS.add(item);
    }
}
