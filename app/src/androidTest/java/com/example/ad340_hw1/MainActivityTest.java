package com.example.ad340_hw1;

import android.util.Log;
import android.widget.DatePicker;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    //every time this test is run, startup the main activity to start testing

    //want to user wither ActivityScenarioRule OR ActivityTestRule
//    @Rule
//    public ActivityScenarioRule<MainActivity> activityScenarioRule
//            = new ActivityScenarioRule<>(MainActivity.class);
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    private static final String TAG = MainActivityTest.class.getSimpleName();

    //checks that text on screen matches myText
    @Test
    public void hasTextOnScreen()
    {
        onView(withId(R.id.textViewInfo))
                .check(matches(withText(R.string.my_text)));

        onView(withId(R.id.textViewSignup))
                .check(matches(withText(R.string.sign_up)));

        onView(withId(R.id.textViewDOB))
                .check(matches(withText(R.string.dob)));

    }


    @Test
    public void testSuccessfulSignUp()
    {
        //test input all entries properly and submit
        testEnterFields(13, true);
    }

    @Test
    public void testUnsuccessfulFirstName()
    {
        //Test first name w/ toast
        testEnterFields(0, false);

        //check toast
        onView(withText(R.string.invalid_first))
                .inRoot(withDecorView(not(mActivityRule.getActivity()
                        .getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void testUnsuccessfulLastName()
    {
        //test last name
        testEnterFields(7, true);

        onView(withText(R.string.invalid_last))
                .inRoot(withDecorView(not(mActivityRule.getActivity()
                        .getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void testUnsuccessfulEmail()
    {
        //test email
        testEnterFields(8, true);

        onView(withText(R.string.invalid_email))
                .inRoot(withDecorView(not(mActivityRule.getActivity()
                        .getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void testUnsuccessfulUsername()
    {
        //test user
        testEnterFields(9, true);

        onView(withText(R.string.invalid_user))
                .inRoot(withDecorView(not(mActivityRule.getActivity()
                        .getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void testUnsuccessfulOccupation()
    {
        //test occupation
        testEnterFields(10, true);
        onView(withText(R.string.invalid_occu))
                .inRoot(withDecorView(not(mActivityRule.getActivity()
                        .getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void testUnsuccessfulDescription()
    {
        //test description
        testEnterFields(11, true);
        onView(withText(R.string.invalid_desc))
                .inRoot(withDecorView(not(mActivityRule.getActivity()
                        .getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void testUnsuccessfulDOB()
    {
        //test dob
        testEnterFields(12, true);

        onView(withText(R.string.invalid_dob))
                .inRoot(withDecorView(not(mActivityRule.getActivity()
                .getWindow().getDecorView()))).check(matches(isDisplayed()));

//        testEnterFields(6, false);
    }


    //check toast
//        onView(withText(R.string.invalid_first)).
//                inRoot(withDecorView(not(is(mActivityRule.getWindow().getDecorView())))).
//                check(matches(isDisplayed()));

    //test successful and unsuccessful data input whether hasContent == true
    //can call single or multiple typeText depending on num param
//    @Test
    private void testEnterFields(int num, boolean hasContent)
    {
        //edit text fields input up until number param
        if(num >= 7 || num == 0)
        {
            if(hasContent)
            {
                onView(withId(R.id.editTextFirstName)).perform(typeText("Ben"));
            }

            else{
                onView(withId(R.id.editTextFirstName)).perform(typeText(""));
            }
//            Log.i(TAG,"in testEnterFields");
        }

        //LAST
        if(num >= 8 || num == 1)
        {
            if (hasContent)
            {
                onView(withId(R.id.editTextLastName)).perform(typeText("Chang"));
            }
            else {
                onView(withId(R.id.editTextLastName)).perform(typeText(""));
            }
        }

        //EMAIL
        if (num >= 9 || num == 2)
        {
            if (hasContent)
            {
                onView(withId(R.id.editTextEmail)).perform(typeText("ELTigre@yahoo.com"));
            }

            else {
                onView(withId(R.id.editTextEmail)).perform(typeText(""));
            }
        }

        //USER
        if (num >= 10 || num == 3)
        {
            if (hasContent)
            {
                onView(withId(R.id.editTextUsername)).perform(typeText("El Trigre"));
            }

            else {
                onView(withId(R.id.editTextUsername)).perform(typeText(""));
            }
        }

        //OCCU
        if (num >= 11 || num == 4)
        {
            if (hasContent)
            {
                onView(withId(R.id.editTextOccupation)).perform(typeText("Spanish Teacher"), closeSoftKeyboard());
            }

            else {
                onView(withId(R.id.editTextOccupation)).perform(typeText(""), closeSoftKeyboard());
            }
        }

        ///DESC
        if (num >= 12 || num == 5)
        {
            if (hasContent)
            {
                onView(withId(R.id.editTextDescription)).perform(typeText("Lookin for love"), closeSoftKeyboard());
            }

            else {
                onView(withId(R.id.editTextDescription)).perform(typeText(""), closeSoftKeyboard());
            }
        }

        //DOB
        if(num >= 13 || num == 6)
        {
            if(hasContent)
            {
                onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                        .perform(PickerActions.setDate(1990, 1, 1), closeSoftKeyboard());
            }

            //invalid birth date
            else{
                onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                        .perform(PickerActions.setDate(2010, 1, 1), closeSoftKeyboard());
            }
        }

        //scroll to bottom and submit data entry in form
        onView(withId(R.id.btnSubmit)).perform(ViewActions.scrollTo(), click());
    }


//    @Test
//    public void testDatePicker()
//    {
//        //datePicker field input
////        onData(withId(R.id.dobDatePicker))
////                .perform(doubleClick(), typeText("2000"), closeSoftKeyboard())
////                .
//        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2010, 1, 1));
////        onView(withId(R.id.btnSubmit)).perform(ViewActions.scrollTo(), click());
////        onData(withId(R.id.dobDatePicker)).perform(PickerActions.setDate(2002, 10, 1));
//    }


    //test the description editText view's max char limit of 50
//    @Test
//    public void testDescriptionMaxChar()
//    {
//
//    }


}