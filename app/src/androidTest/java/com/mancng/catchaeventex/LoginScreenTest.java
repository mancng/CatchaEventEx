package com.mancng.catchaeventex;

import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.EditText;

import com.mancng.catchaeventex.activities.LoginActivity;

import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class LoginScreenTest {

    private String mUserName;
    private String mEmail;
    private String mInvalidEmail;
    private String mPassword;

    @Rule
    public IntentsTestRule<LoginActivity> mLoginActivityIntentsTestRule =
            new IntentsTestRule<LoginActivity>(LoginActivity.class);

    @Before
    public void initVariables() {

        mUserName = "Michael Jackson";
        mEmail = "xyz.gmail.com";
        mInvalidEmail = "abcde";
        mPassword = "mypass";

    }

    @Test
    public void clickLoginButton_opensEventsActivity() throws Exception {

        //Enter a valid login
        onView(withId(R.id.activity_login_userEmail))
                .perform(typeText(mEmail));
        onView(withId(R.id.activity_login_userPassword))
                .perform(typeText(mPassword), closeSoftKeyboard());

        //Click Log-in button
        onView(withId(R.id.activity_login_loginButton)).perform(click());
        intended(hasComponent(EventsActivity.class.getName()));
    }


    @Test
    public void checkEmptyPassword() throws Exception {

        //Enter email only
        onView(withId(R.id.activity_login_userEmail))
                .perform(typeText(mEmail), closeSoftKeyboard());

        onView(withId(R.id.activity_login_loginButton)).perform(click());
        onView(withId(R.id.activity_login_userPassword)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.activity_login_userPassword))
                .check(matches(withError("Please enter your password")));
    }


    @Test
    public void checkEmptyEmail() throws Exception {

        //Enter password only
        onView(withId(R.id.activity_login_userPassword))
                .perform(typeText(mPassword), closeSoftKeyboard());

        onView(withId(R.id.activity_login_loginButton)).perform(click());
        onView(withId(R.id.activity_login_userEmail)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.activity_login_userEmail))
                .check(matches(withError("Please enter your email")));
    }


    @Test
    public void checkInvalidEmail() throws Exception {

        //Enter invalid email
        onView(withId(R.id.activity_login_userEmail))
                .perform(typeText(mInvalidEmail), closeSoftKeyboard());

        onView(withId(R.id.activity_login_userPassword))
                .perform(typeText(mPassword), closeSoftKeyboard());

        onView(withId(R.id.activity_login_loginButton)).perform(click());
        onView(withId(R.id.activity_login_userEmail)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.activity_login_userEmail))
                .check(matches(withError("Please enter a valid email")));
    }


    @Test
    public void checkInvalidPassword() throws Exception {

        //Enter email
        onView(withId(R.id.activity_login_userEmail))
                .perform(typeText(mEmail), closeSoftKeyboard());

        //Enter invalid password
        onView(withId(R.id.activity_login_userPassword))
                .perform(typeText("test"), closeSoftKeyboard());

        onView(withId(R.id.activity_login_loginButton)).perform(click());
        onView(withText("The password is invalid or the user does not have a password."))
                .inRoot(withDecorView(not(mLoginActivityIntentsTestRule.getActivity()
                        .getWindow()
                        .getDecorView())))
                .check(matches(isDisplayed()));
    }


    @Test
    public void clickRegisterButton_opensRegisterActivity() throws Exception {

        //Click Register button
        onView(withId(R.id.activity_login_registerButton)).perform(click());
        intended(hasComponent(RegisterActivity.class.getName()));
    }


    //Create a custom matcher for View.setError
    private static Matcher<View> withError(final String expected) {
        return new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(org.hamcrest.Description description) {

            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof EditText)) {
                    return false;
                }

                EditText editText = (EditText) view;
                return editText.getError().toString().equals(expected);
            }
        };
    }

}