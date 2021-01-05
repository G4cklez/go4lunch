package com.app.go4lunch.view.activities;

import androidx.test.espresso.Espresso;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.app.go4lunch.R;

import org.junit.Rule;
import org.junit.Test;

public class AuthActivityTest
{
    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void authActivity_GoogleButton_isDisplayed()
    {
        Espresso.onView(ViewMatchers.withId(R.id.auth_activity_google_button)).check(matches(isDisplayed()));
    }

    @Test
    public void authActivity_FacebookButton_isDisplayed()
    {
        Espresso.onView(ViewMatchers.withId(R.id.auth_activity_facebook_button)).check(matches(isDisplayed()));
    }
}