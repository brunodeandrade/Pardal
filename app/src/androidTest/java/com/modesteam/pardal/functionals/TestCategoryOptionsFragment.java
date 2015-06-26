package com.modesteam.pardal.functionals;

import android.support.v4.app.Fragment;

import com.modesteam.pardal.CategoryOptionsFragment;

import junit.framework.TestCase;

/**
 * Created by luisresende on 07/06/15.
 */
public class TestCategoryOptionsFragment extends TestCase {
    public void testShouldGetNewInstanceOfCategoryOptionsFragment(){
        Fragment fragment = CategoryOptionsFragment.newInstance("A", "B");
        CategoryOptionsFragment categoryOptionsFragment = (CategoryOptionsFragment)fragment;
        assertEquals("A", categoryOptionsFragment.getArguments().getString("param1"));
    }
}
