package com.modesteam.pardal.city;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.City;

/**
 * Created by luisresende on 03/05/15.
 */
public class CityContent {

    public static List<City> ITEMS = new ArrayList<City>();


    static {
        ArrayList<City> allCities = null;
        allCities = City.getAll();

        for (City city: allCities){
            addItem(city);
        }

    }

    private static void addItem(City item) {
        ITEMS.add(item);
    }
}
