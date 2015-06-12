package com.modesteam.pardal;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.AvoidXfermode;

import com.modesteam.pardal.highwayStretch.HighwayStretchContent;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import annotations.Entity;
import helpers.Condition;
import helpers.GenericPersistence;
import models.HighwayStretch;
import models.Model;
import models.State;
import models.Type;

import static helpers.GenericPersistence.getFields;

/**
 * Created by luisresende on 08/06/15.
 */
public class RankingCategory {

    private GenericPersistence gp;
    private ArrayList<Object> objectArrayList;

    public void rankCategoryWithField (Object bean, String fieldName) throws SQLException, ClassNotFoundException{

        gp = new GenericPersistence();
        objectArrayList = gp.selectBeansOrderByField(bean,fieldName);
        System.out.println(objectArrayList.toString());

    }

//    public void rankCategoryWithoutField (HighwayStretch highwayStretch) throws SQLException, ClassNotFoundException{
//
//        ArrayList<Double> averageExceded = new ArrayList<>();
//        ArrayList<Double> maximumMeasuredVelocity = new ArrayList<>();
//        ArrayList<Integer> totalTickets = new ArrayList<>();
//
//
//
//        int total = 0;
//        double average = 0.0;
//        double maximumVelocity = 0.0;
//
//        int numberIterator=0;
//
//        for (HighwayStretch highwayStretchItem : HighwayStretchContent.ITEMS){
//            totalTickets.add(highwayStretchItem.getTotalTickets());
//            averageExceded.add(highwayStretchItem.getAverageExceded());
//            maximumMeasuredVelocity.add(highwayStretchItem.getMaximumMeasuredVelocity());
//            numberIterator++;
//        }
//        Collections.sort(averageExceded);
//        Collections.sort(maximumMeasuredVelocity);
//        Collections.sort(totalTickets);
//
//        System.out.println("totalTickets "+ totalTickets.toString());
//
//    }
}
