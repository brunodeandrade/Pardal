package com.modesteam.pardal.instrumentation;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.modesteam.pardal.MainActivity;
import com.modesteam.pardal.R;
import com.modesteam.pardal.SplashScreenActivity;
import com.modesteam.pardal.brand.BrandContent;
import com.modesteam.pardal.city.CityContent;
import com.modesteam.pardal.highwayStretch.HighwayStretchContent;
import com.modesteam.pardal.model.ModelContent;
import com.modesteam.pardal.state.StateContent;
import com.modesteam.pardal.type.TypeContent;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.regex.Matcher;

import models.HighwayStretch;

/**
 * Created by andrebsguedes on 16/06/15.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestSplashScreenActivity {

    @Rule
    public ActivityTestRule<SplashScreenActivity> mActivityRule = new ActivityTestRule(SplashScreenActivity.class);

    @Test
    public void shouldGetNewInstanceOfSplashScreenActivity() {
        assertThat(mActivityRule.getActivity().findViewById(R.id.textViewVersion),isDisplayed());
        Assert.assertNotNull(ModelContent.ITEMS);
        Assert.assertNotNull(HighwayStretchContent.ITEMS);
        Assert.assertNotNull(BrandContent.ITEMS);
        Assert.assertNotNull(CityContent.ITEMS);
        Assert.assertNotNull(StateContent.ITEMS);
        Assert.assertNotNull(TypeContent.ITEMS);
    }
}
