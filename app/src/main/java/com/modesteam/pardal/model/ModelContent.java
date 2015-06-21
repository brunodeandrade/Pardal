package com.modesteam.pardal.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Model;

/**
 * Created by laerciojunior on 30/04/15.
* */
public class ModelContent {
    /**
     * An array of sample (dummy) items.
     */
    public static List<Model> ITEMS = new ArrayList<Model>();

    /**
     * A map of sample (dummy) items, by ID.
     */

    static {
        // Add all type items.
        ArrayList<Model> allModels = null;
        allModels = Model.getAll();
        for (Model model : allModels) {
            addItem(model);
        }
    }

    private static void addItem(Model item) {
        ITEMS.add(item);
    }
}