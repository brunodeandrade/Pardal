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

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.regex.Matcher;

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
        onView(withText("Pardal\n\t v 1.0")).check(matches(isDisplayed()));
    }
}
