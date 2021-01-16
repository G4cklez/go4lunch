package com.app.go4lunch.view.activities;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import static androidx.test.espresso.action.ViewActions.click;

import com.app.go4lunch.R;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

public class MainActivityTest
{
    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule<>(LoginActivity.class);


    @Test
    public void bottomNavigationView_clickListRestaurants_DisplayGoodFragment()
    {
        Espresso.onView(ViewMatchers.withId(R.id.action_listview)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.fragment_list_restaurants_constraint_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void bottomNavigationView_clickListWorkmates_DisplayGoodFragment()
    {
        Espresso.onView(ViewMatchers.withId(R.id.action_friends)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.rvFriends)).check(matches(isDisplayed()));
    }

    @Test
    public void bottomNavigationView_clickMap_DisplayGoodFragment()
    {
        Espresso.onView(ViewMatchers.withId(R.id.action_mapview)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.map)).check(matches(isDisplayed()));
    }



}