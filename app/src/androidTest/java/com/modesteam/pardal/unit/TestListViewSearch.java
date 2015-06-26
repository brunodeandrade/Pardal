package com.modesteam.pardal.unit;

import android.support.v4.app.Fragment;

import junit.framework.TestCase;

import helpers.ListViewSearch;

/**
 * Created by luisresende on 30/05/15.
 */
public class TestListViewSearch extends TestCase {

    public void testShouldGetNewInstanceOfListViewSearch(){
        ListViewSearch listViewSearch = null;
        listViewSearch = new ListViewSearch();
        assertNotNull(listViewSearch);
    }
}
