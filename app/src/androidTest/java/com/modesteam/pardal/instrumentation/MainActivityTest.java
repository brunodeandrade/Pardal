package com.modesteam.pardal.instrumentation;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.modesteam.pardal.MainActivity;
import com.modesteam.pardal.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by andrebsguedes on 16/06/15.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void shouldShowBrandDetail() {
        //List
        onView(withId(R.id.bBrand)).perform(click());
        onView(withId(R.id.bList)).perform(click());
        onView(withText("Marca")).check(matches(isDisplayed()));

        //Detail
        onData(hasToString(containsString("ASIA"))).perform(click());

        //Test
        onView(withText("Marca")).check(matches(isDisplayed()));
        onView(withText("ASIA")).check(matches(isDisplayed()));
        onView(withId(R.id.compareButton)).check(matches(isDisplayed()));
    }
    @Test
    public void shouldShowStateDetail() {
        //List
        onView(withId(R.id.bState)).perform(click());
        onView(withId(R.id.bList)).perform(click());
        onView(withText("Estado")).check(matches(isDisplayed()));

        //Detail
        onData(hasToString(containsString("AC"))).perform(click());

        //Test
        onView(withText("Estado")).check(matches(isDisplayed()));
        onView(withText("AC")).check(matches(isDisplayed()));
        onView(withId(R.id.compareButton)).check(matches(isDisplayed()));
    }
    @Test
    public void shouldShowModelDetail() {
        //List
        onView(withId(R.id.bModel)).perform(click());
        onView(withId(R.id.bList)).perform(click());
        onView(withText("Modelo")).check(matches(isDisplayed()));

        //Detail
        onView(withText("1215 C")).check(matches(isDisplayed())).perform(click());

        //Test
        onView(withText("Modelo")).check(matches(isDisplayed()));
        onView(withText("1215 C")).check(matches(isDisplayed()));
        onView(withId(R.id.compareButton)).check(matches(isDisplayed()));
    }
    @Test
    public void shouldShowCityDetail() {
        //List
        onView(withId(R.id.bCity)).perform(click());
        onView(withId(R.id.bList)).perform(click());
        onView(withText("Cidade")).check(matches(isDisplayed()));

        //Detail
        onData(hasToString(containsString("ABADIA DE GOIAS"))).perform(click());

        //Test
        onView(withText("Cidade")).check(matches(isDisplayed()));
        onView(withId(R.id.textViewName)).check(matches(withText(containsString("ABADIA DE GOIAS"))));
        onView(withId(R.id.compareButton)).check(matches(isDisplayed()));
    }
    @Test
    public void shouldShowTypeDetail() {
        //List
        onView(withId(R.id.bType)).perform(click());
        onView(withId(R.id.bList)).perform(click());
        onView(withText("Tipo")).check(matches(isDisplayed()));

        //Detail
        onData(hasToString(containsString("CARGA"))).perform(click());

        //Test
        onView(withText("Tipo")).check(matches(isDisplayed()));
        onView(withText("CARGA")).check(matches(isDisplayed()));
        onView(withId(R.id.compareButton)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldShowHighwayStretchDetail() {
        //List
        onView(withId(R.id.bHighway)).perform(click());
        onView(withId(R.id.bList)).perform(click());
        onView(withText("Rodovia")).check(matches(isDisplayed()));

        //Detail
        onData(hasToString(containsString("BR 010 - Km 18"))).perform(click());

        //Test
        onView(withText("Rodovia")).check(matches(isDisplayed()));
        onView(withId(R.id.textViewName)).check(matches(withText(containsString("BR 010 KM 18"))));
        onView(withId(R.id.compareButton)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldShowAbout() {
        onView(withId(R.id.bAbout)).perform(click());
        onView(withText(containsString("Sobre o Aplicativo"))).check(matches(isDisplayed()));
    }

    @Test
    public void shouldFilterBrandList() {
        //List
        onView(withId(R.id.bBrand)).perform(click());
        onView(withId(R.id.bList)).perform(click());
        onView(withId(R.id.searchEditText)).perform(typeText("vw"), closeSoftKeyboard());

        //Test
        onView((withId(android.R.id.list))).check(matches(withChild(withText(containsString("VW")))));
    }

    @Test
    public void shouldFilterTypeList() {
        //List
        onView(withId(R.id.bType)).perform(click());
        onView(withId(R.id.bList)).perform(click());
        onView(withId(R.id.searchEditText)).perform(typeText("car"), closeSoftKeyboard());

        //Test
        onView((withId(android.R.id.list))).check(matches(withChild(withText(containsString("CARGA")))));
    }

    @Test
    public void shouldFilterModelList() {
        //List
        onView(withId(R.id.bModel)).perform(click());
        onView(withId(R.id.bList)).perform(click());
        onView(withId(R.id.searchEditText)).perform(typeText("13"), closeSoftKeyboard());

        //Test
        onView((withId(android.R.id.list))).check(matches(withChild(withText(containsString("13.180")))));
    }

    @Test
    public void shouldFilterCityList() {
        //List
        onView(withId(R.id.bCity)).perform(click());
        onView(withId(R.id.bList)).perform(click());
        onView(withId(R.id.searchEditText)).perform(typeText("abaete"), closeSoftKeyboard());

        //Test
        onView((withId(android.R.id.list))).check(matches(withChild(withText(containsString("ABAETE")))));
    }

    @Test
    public void shouldFilterStateList() {
        //List
        onView(withId(R.id.bState)).perform(click());
        onView(withId(R.id.bList)).perform(click());
        onView(withId(R.id.searchEditText)).perform(typeText("ba"), closeSoftKeyboard());

        //Test
        onView((withId(android.R.id.list))).check(matches(withChild(withText(containsString("BA")))));
    }

    @Test
    public void shouldFilterHighwayStretchList() {
        //List
        onView(withId(R.id.bHighway)).perform(click());
        onView(withId(R.id.bList)).perform(click());
        onView(withId(R.id.searchEditText)).perform(typeText("22"), closeSoftKeyboard());

        //Test
        onView((withId(android.R.id.list))).check(matches(withChild(withText(containsString("229")))));
    }

    @Test
    public void shouldShowBrandCompare() {
        //List
        onView(withId(R.id.bBrand)).perform(click());
        onView(withId(R.id.bList)).perform(click());
        onView(withText("Marca")).check(matches(isDisplayed()));

        //Detail
        onView(withText("ASIA")).check(matches(isDisplayed())).perform(click());

        //Compare
        onView(withId(R.id.compareButton)).check(matches(isDisplayed())).perform(click());


        //Second item
        onView(withText("AUDI")).check(matches(isDisplayed())).perform(click());

        //Test
        onView(withId(R.id.itemName1textView)).check(matches(withText(containsString("ASIA"))));
        onView(withId(R.id.itemName2textView)).check(matches(withText(containsString("AUDI"))));
    }

    @Test
    public void shouldShowStateCompare() {
        //List
        onView(withId(R.id.bState)).perform(click());
        onView(withId(R.id.bList)).perform(click());
        onView(withText("Estado")).check(matches(isDisplayed()));

        //Detail
        onView(withText("AC")).check(matches(isDisplayed())).perform(click());

        //Compare
        onView(withId(R.id.compareButton)).check(matches(isDisplayed())).perform(click());


        //Second item
        onView(withText("AM")).check(matches(isDisplayed())).perform(click());

        //Test
        onView(withId(R.id.itemName1textView)).check(matches(withText(containsString("AC"))));
        onView(withId(R.id.itemName2textView)).check(matches(withText(containsString("AM"))));
    }

    @Test
    public void shouldShowModelCompare() {
        //List
        onView(withId(R.id.bModel)).perform(click());
        onView(withId(R.id.bList)).perform(click());
        onView(withText("Modelo")).check(matches(isDisplayed()));

        //Detail
        onView(withText("1215 C")).check(matches(isDisplayed())).perform(click());

        //Compare
        onView(withId(R.id.compareButton)).check(matches(isDisplayed())).perform(click());


        //Second item
        onView(withText("13.180")).check(matches(isDisplayed())).perform(click());

        //Test
        onView(withId(R.id.itemName1textView)).check(matches(withText(containsString("1215 C"))));
        onView(withId(R.id.itemName2textView)).check(matches(withText(containsString("13.180"))));
    }

    @Test
    public void shouldShowCityCompare() {
        //List
        onView(withId(R.id.bCity)).perform(click());
        onView(withId(R.id.bList)).perform(click());
        onView(withText("Cidade")).check(matches(isDisplayed()));

        //Detail
        onView(withText("ABADIA DE GOIAS")).check(matches(isDisplayed())).perform(click());

        //Compare
        onView(withId(R.id.compareButton)).check(matches(isDisplayed())).perform(click());


        //Second item
        onView(withText("ABADIANIA")).check(matches(isDisplayed())).perform(click());

        //Test
        onView(withId(R.id.itemName1textView)).check(matches(withText(containsString("ABADIA DE GOIAS"))));
        onView(withId(R.id.itemName2textView)).check(matches(withText(containsString("ABADIANIA"))));
    }

    @Test
    public void shouldShowTypeCompare() {
        //List
        onView(withId(R.id.bType)).perform(click());
        onView(withId(R.id.bList)).perform(click());
        onView(withText("Tipo")).check(matches(isDisplayed()));

        //Detail
        onView(withText("CARGA")).check(matches(isDisplayed())).perform(click());

        //Compare
        onView(withId(R.id.compareButton)).check(matches(isDisplayed())).perform(click());


        //Second item
        onView(withText("COLECAO")).check(matches(isDisplayed())).perform(click());

        //Test
        onView(withId(R.id.itemName1textView)).check(matches(withText(containsString("CARGA"))));
        onView(withId(R.id.itemName2textView)).check(matches(withText(containsString("COLECAO"))));
    }

    @Test
    public void shouldShowHighwayStretchCompare() {
        //List
        onView(withId(R.id.bHighway)).perform(click());
        onView(withId(R.id.bList)).perform(click());
        onView(withText("Rodovia")).check(matches(isDisplayed()));

        //Detail
        onView(withText("BR 010 - Km 18")).check(matches(isDisplayed())).perform(click());

        //Compare
        onView(withId(R.id.compareButton)).check(matches(isDisplayed())).perform(click());


        //Second item
        onView(withText("BR 020 - Km 5")).check(matches(isDisplayed())).perform(click());

        //Test
        onView(withId(R.id.itemName1textView)).check(matches(withText(containsString("BR 010"))));
        onView(withId(R.id.itemName2textView)).check(matches(withText(containsString("BR 020"))));
    }

    @Test
    public void shouldBrandReverseList(){
        //List
        onView(withId(R.id.bBrand)).perform(click());
        onView(withId(R.id.bList)).perform(click());
        onView(withText("Marca")).check(matches(isDisplayed()));

        //Reverse
        onView(withId(R.id.bOrdenate)).perform(click());

        onView((withId(android.R.id.list))).check(matches(withChild(withText(containsString("VW"))))).check(matches(isDisplayed()));

        onView(withId(R.id.bOrdenate)).perform(click());
    }

    @Test
    public void shouldBrandStateList(){
        //List
        onView(withId(R.id.bState)).perform(click());
        onView(withId(R.id.bList)).perform(click());
        onView(withText("Estado")).check(matches(isDisplayed()));

        //Reverse
        onView(withId(R.id.bOrdenate)).perform(click());

        onView((withId(android.R.id.list))).check(matches(withChild(withText(containsString("TO"))))).check(matches(isDisplayed()));

        onView(withId(R.id.bOrdenate)).perform(click());
    }

    @Test
    public void shouldModelReverseList(){
        //List
        onView(withId(R.id.bModel)).perform(click());
        onView(withId(R.id.bList)).perform(click());
        onView(withText("Modelo")).check(matches(isDisplayed()));

        //Reverse
        onView(withId(R.id.bOrdenate)).perform(click());

        onView((withId(android.R.id.list))).check(matches(withChild(withText(containsString("ZAFIRA"))))).check(matches(isDisplayed()));

        onView(withId(R.id.bOrdenate)).perform(click());
    }

    @Test
    public void shouldCityReverseList(){
        //List
        onView(withId(R.id.bCity)).perform(click());
        onView(withId(R.id.bList)).perform(click());
        onView(withText("Cidade")).check(matches(isDisplayed()));

        //Reverse
        onView(withId(R.id.bOrdenate)).perform(click());

        onView((withId(android.R.id.list))).check(matches(withChild(withText(containsString("ZORTEA"))))).check(matches(isDisplayed()));

        onView(withId(R.id.bOrdenate)).perform(click());
    }

    @Test
    public void shouldTypeReverseList(){
        //List
        onView(withId(R.id.bType)).perform(click());
        onView(withId(R.id.bList)).perform(click());
        onView(withText("Tipo")).check(matches(isDisplayed()));

        //Reverse
        onView(withId(R.id.bOrdenate)).perform(click());

        onView((withId(android.R.id.list))).check(matches(withChild(withText(containsString("TRACAO"))))).check(matches(isDisplayed()));

        onView(withId(R.id.bOrdenate)).perform(click());
    }

    @Test
    public void shouldHighwayStretchReverseList(){
        //List
        onView(withId(R.id.bHighway)).perform(click());
        onView(withId(R.id.bList)).perform(click());
        onView(withText("Rodovia")).check(matches(isDisplayed()));

        //Reverse
        onView(withId(R.id.bOrdenate)).perform(click());

        onView((withId(android.R.id.list))).check(matches(withChild(withText(containsString("BR 493"))))).check(matches(isDisplayed()));

        onView(withId(R.id.bOrdenate)).perform(click());
    }

}