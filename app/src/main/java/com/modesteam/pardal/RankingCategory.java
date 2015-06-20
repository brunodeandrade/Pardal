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
import models.Brand;
import models.City;
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
    private float[] valuesObjectArrayList;

    public ArrayList<Object> rankCategoryWithField (Object bean, String fieldName) throws SQLException, ClassNotFoundException{

        gp = new GenericPersistence();
        objectArrayList = gp.selectBeansOrderByField(bean,fieldName);
        valuesObjectArrayList = new float[objectArrayList.size()];
        return objectArrayList;
    }

    public float[] getValuesRankCategoryWithField (ArrayList<Object> objectArrayList, String fieldName, Object bean) throws SQLException, ClassNotFoundException{


        if (bean.getClass().getSimpleName().equals("Model")){
            for (int i=0; i<objectArrayList.size(); i++){
                Model model = (Model)objectArrayList.get(i);
                if (fieldName.equals("totalTickets")){
                    valuesObjectArrayList[i] = model.getTotalTickets();

                }else if (fieldName.equals("averageExceded")){
                    valuesObjectArrayList[i] = model.getAverageExceded().floatValue();

                }else if (fieldName.equals("maximumMeasuredVelocity")){
                    valuesObjectArrayList[i] = model.getMaximumMeasuredVelocity().floatValue();
                }else {
                    return null;
                }
            }
        }else if (bean.getClass().getSimpleName().equals("Brand")){
            for (int i=0; i<objectArrayList.size(); i++){
                Brand brand = (Brand)objectArrayList.get(i);
                if (fieldName.equals("totalTickets")){
                    valuesObjectArrayList[i] = brand.getTotalTickets();

                }else if (fieldName.equals("averageExceded")){
                    valuesObjectArrayList[i] = brand.getAverageExceded().floatValue();

                }else if (fieldName.equals("maximumMeasuredVelocity")){
                    valuesObjectArrayList[i] = brand.getMaximumMeasuredVelocity().floatValue();
                }else {
                    return null;
                }
            }
        }else if (bean.getClass().getSimpleName().equals("City")){
            for (int i=0; i<objectArrayList.size(); i++){
                City city = (City)objectArrayList.get(i);
                if (fieldName.equals("totalTickets")){
                    valuesObjectArrayList[i] = city.getTotalTickets();

                }else if (fieldName.equals("averageExceded")){
                    valuesObjectArrayList[i] = city.getAverageExceded().floatValue();

                }else if (fieldName.equals("maximumMeasuredVelocity")){
                    valuesObjectArrayList[i] = city.getMaximumMeasuredVelocity().floatValue();
                }else {
                    return null;
                }
            }
        }else if (bean.getClass().getSimpleName().equals("HighwayStretch")){
            for (int i=0; i<objectArrayList.size(); i++){
                HighwayStretch highwayStretch = (HighwayStretch)objectArrayList.get(i);
                if (fieldName.equals("totalTickets")){
                    valuesObjectArrayList[i] = highwayStretch.getTotalTickets();

                }else if (fieldName.equals("averageExceded")){
                    valuesObjectArrayList[i] = highwayStretch.getAverageExceded().floatValue();

                }else if (fieldName.equals("maximumMeasuredVelocity")){
                    valuesObjectArrayList[i] = highwayStretch.getMaximumMeasuredVelocity().floatValue();
                }else {
                    return null;
                }
            }
        }else if (bean.getClass().getSimpleName().equals("State")){
            for (int i=0; i<objectArrayList.size(); i++){
                State state = (State)objectArrayList.get(i);
                if (fieldName.equals("totalTickets")){
                    valuesObjectArrayList[i] = state.getTotalTickets();

                }else if (fieldName.equals("averageExceded")){
                    valuesObjectArrayList[i] = state.getAverageExceded().floatValue();

                }else if (fieldName.equals("maximumMeasuredVelocity")){
                    valuesObjectArrayList[i] = state.getMaximumMeasuredVelocity().floatValue();
                }else {
                    return null;
                }
            }
        }else if (bean.getClass().getSimpleName().equals("Type")){
            for (int i=0; i<objectArrayList.size(); i++){
                Type type = (Type)objectArrayList.get(i);
                if (fieldName.equals("totalTickets")){
                    valuesObjectArrayList[i] = type.getTotalTickets();

                }else if (fieldName.equals("averageExceded")){
                    valuesObjectArrayList[i] = type.getAverageExceded().floatValue();

                }else if (fieldName.equals("maximumMeasuredVelocity")){
                    valuesObjectArrayList[i] = type.getMaximumMeasuredVelocity().floatValue();
                }else {
                    return null;
                }
            }
        }

        return valuesObjectArrayList;
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
