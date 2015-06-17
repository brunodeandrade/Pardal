package com.modesteam.pardal.unit;

import com.modesteam.pardal.Pardal;
import com.modesteam.pardal.RankingCategory;

import junit.framework.TestCase;

import java.sql.SQLException;
import java.util.ArrayList;

import libraries.NotNullableException;
import models.Brand;

/**
 * Created by lucas on 12/06/15.
 */

public class TestRankingCategory extends TestCase{

    Brand brand1, brand2, genericBrand;
    RankingCategory rankCategory;
    ArrayList<Object> arrayListRanking;

    public void setUp() throws SQLException, NotNullableException, ClassNotFoundException {
        Pardal.getInstance().setDatabaseName("database_test.sqlite3.db");

        rankCategory = new RankingCategory();
        genericBrand = new Brand();
        arrayListRanking = null;

        brand1 = new Brand("VW");
        brand2 = new Brand("FIAT");
        brand1.setTotalTickets(10);
        brand2.setTotalTickets(20);
        brand1.setMaximumMeasuredVelocity((double) 190);
        brand2.setMaximumMeasuredVelocity((double) 100);
        brand1.setAverageExceded((double) 40);
        brand2.setAverageExceded((double) 50);
        brand1.save();
        brand2.save();
    }
    public void tearDown() throws SQLException, ClassNotFoundException {
        brand1.delete();
        brand2.delete();
    }

    public void testShouldRankCategoryWithTotalTickets() throws SQLException, ClassNotFoundException{

        arrayListRanking = rankCategory.rankCategoryWithField(genericBrand, "totalTickets");
        assertEquals(arrayListRanking.get(0).toString(), brand2.getName());
    }

    public void testShouldRankCategoryWithMaximumMeasuredVelocity() throws SQLException, ClassNotFoundException{

        arrayListRanking = rankCategory.rankCategoryWithField(genericBrand, "maximumMeasuredVelocity");
        assertEquals(arrayListRanking.get(0).toString(),brand1.getName());
    }

    public void testShouldRankCategoryWithAverageExceded() throws SQLException, ClassNotFoundException{

        arrayListRanking = rankCategory.rankCategoryWithField(genericBrand, "averageExceded");
        assertEquals(arrayListRanking.get(0).toString(),brand2.getName());
    }

    public void testShouldGetNewInstanceoOfRankingCategory(){
        RankingCategory rankingCategoryInstance = null;
        rankingCategoryInstance = new RankingCategory();
        assertNotNull(rankingCategoryInstance);
    }
}
