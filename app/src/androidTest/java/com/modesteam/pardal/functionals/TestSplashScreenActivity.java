package com.modesteam.pardal.functionals;

import android.app.Activity;

import com.modesteam.pardal.SplashScreenActivity;

import junit.framework.TestCase;

/**
 * Created by luisresende on 06/06/15.
 */
public class TestSplashScreenActivity extends TestCase {

    public void testShouldGetNewInstanceOfSplashScreenActivity(){
        Activity activity = null;
        activity = new SplashScreenActivity ();
        assertNotNull(activity);
    }
}
