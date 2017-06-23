package com.mancng.catchaeventex;

import android.support.test.runner.AndroidJUnit4;

import com.mancng.catchaeventex.activities.LoginActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class CreateEventTest {

    @Rule
    public IntentsTestRule<LoginActivity> mLoginActivityIntentsTestRule =
            new IntentsTestRule<LoginActivity>(LoginActivity.class);

    @Before
    public void LogUserIn() {

        //Enter a valid login
        onView(withId(R.id.activity_login_userEmail))
                .perform(typeText("xyc@gmail.com"));
        onView(withId(R.id.activity_login_userPassword))
                .perform(typeText("mypass"), closeSoftKeyboard());

        //Click Log-in button
        onView(withId(R.id.activity_login_loginButton)).perform(click());
        intended(hasComponent(EventsActivity.class.getName()));

    }
    @Test
    public void clickCreateButton_checkEventsActivity() throws Exception {

        //Click add event button
        onView(withId(R.id.action_add_event)).perform(click());

        //Enter event name
        onView(withId(R.id.add_event_event_name))
                .perform(typeText("Shopping"), closeSoftKeyboard()); //ViewAction

        //Select Number of Party spinner
        onView(withId(R.id.spinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(1).perform(click());


        //Select Friends
        onView(withId(R.id.event_friend_picker)).perform(click());

    }

}

