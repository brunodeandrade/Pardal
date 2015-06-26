package com.modesteam.pardal.highwayStretch;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.HighwayStretch;

/**
 * Created by luisresende on 06/05/15.
 */
public class HighwayStretchContent {

    public static List<HighwayStretch> ITEMS = new ArrayList<HighwayStretch>();

    /**
     * A map of sample (dummy) items, by ID.
     */

    static {
        // Add all type items.
        ArrayList<HighwayStretch> allHighwayStretches = null;
        allHighwayStretches = HighwayStretch.getAll();
        for (HighwayStretch highwayStretch: allHighwayStretches){
            addItem(highwayStretch);
        }
    }
    private static void addItem(HighwayStretch item) {

        ITEMS.add(item);
    }
}
