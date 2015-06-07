package com.modesteam.pardal.helpers;

import com.modesteam.pardal.ThreadData;
import com.modesteam.pardal.brand.BrandContent;
import com.modesteam.pardal.city.CityContent;
import com.modesteam.pardal.highwayStretch.HighwayStretchContent;
import com.modesteam.pardal.model.ModelContent;
import com.modesteam.pardal.state.StateContent;
import com.modesteam.pardal.type.TypeContent;

import junit.framework.TestCase;

import java.sql.SQLException;

import models.Brand;
import models.City;
import models.HighwayStretch;
import models.Model;
import models.State;
import models.Type;

/**
 * Created by luisresende on 03/06/15.
 */
public class TestThreadData extends TestCase {

    private ThreadData threadData;
    private Thread thread;

    public void setUp(){
        threadData = new ThreadData();
        thread = new Thread(threadData);
        thread.start();
    }

    public void testShouldGetNewInstanceOfThread(){
        ThreadData threadDataInstance = null;
        threadDataInstance = new ThreadData();
        assertNotNull(threadDataInstance);
    }

    public void testShouldPreLoadTypeContent() throws ClassNotFoundException, SQLException{
        assertEquals(Type.count(), TypeContent.ITEMS.size());
    }

    public void testShouldPreLoadBrandContent() throws ClassNotFoundException, SQLException{
        assertEquals(Brand.count(), BrandContent.ITEMS.size());
    }

    public void testShouldPreLoadStateContent() throws ClassNotFoundException, SQLException{
        assertEquals(State.count(), StateContent.ITEMS.size());
    }

    public void testShouldPreLoadHighwayStretchContent() throws ClassNotFoundException, SQLException{
        assertEquals(HighwayStretch.count(), HighwayStretchContent.ITEMS.size());
    }

    public void testShouldPreLoadCityContent() throws ClassNotFoundException, SQLException{
        assertEquals(City.count(), CityContent.ITEMS.size());
    }

    public void testShouldPreLoadModelContent() throws ClassNotFoundException, SQLException{
        assertEquals(Model.count(), ModelContent.ITEMS.size());
    }
}
