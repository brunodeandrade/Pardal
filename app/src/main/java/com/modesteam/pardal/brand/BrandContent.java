package com.modesteam.pardal.brand;

import com.modesteam.pardal.Pardal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Brand;

/**
 * Created by luisresende on 06/05/15.
 */
public class BrandContent {

    public static List<Brand> ITEMS = new ArrayList<Brand>();

    /**
     * A map of sample (dummy) items, by ID.
     */

    static {
        // Add all type items.
        ArrayList<Brand> allBrands = null;
        allBrands = Brand.getAll();
        for (Brand brand: allBrands){
            addItem(brand);
        }
    }
    private static void addItem(Brand item) {

        ITEMS.add(item);
    }
}
