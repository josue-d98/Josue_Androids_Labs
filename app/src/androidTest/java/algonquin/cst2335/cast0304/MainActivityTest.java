package algonquin.cst2335.cast0304;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import algonquin.cst2335.cast0304.ui.MainActivity;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Checks a password without any letters
     */
    @Test
    public void testFindMissingLetters() {

        // find the view
        ViewInteraction appCompatEditText = onView( ViewMatchers.withId(R.id.editText) );
        // type in 12345
        appCompatEditText.perform(replaceText("12345"));

        // find the button
        ViewInteraction materialButton = onView( withId(R.id.button) );
        // click the button
        materialButton.perform(click());

        // find the text view
        ViewInteraction textView = onView( withId(R.id.textView) );
        // checks the text
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Checks a passsword that is missing an uppercase letter
     */
    @Test
    public void testFindMissingUppercase() {

        // find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.editText) );
        // type in password123#$*
        appCompatEditText.perform(replaceText("password123#$*"));

        // find the button
        ViewInteraction materialButton = onView( withId(R.id.button) );
        // click the button
        materialButton.perform(click());

        // find the text view
        ViewInteraction textView = onView( withId(R.id.textView) );
        // checks the text
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Checks for a password that is missing a lower case letter
     */
    @Test
    public void testFindMissingLowercase() {

        // find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.editText) );
        // type in PASSWORD123#$*
        appCompatEditText.perform(replaceText("PASSWORD123#$*"));

        // find the button
        ViewInteraction materialButton = onView( withId(R.id.button) );
        // click the button
        materialButton.perform(click());

        // find the text view
        ViewInteraction textView = onView( withId(R.id.textView) );
        // checks the text
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Tests a password that has no digits
     */
    @Test
    public void testFindMissingDigit() {

        // find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.editText) );
        // type in Password#$*
        appCompatEditText.perform(replaceText("Password#$*"));

        // find the button
        ViewInteraction materialButton = onView( withId(R.id.button) );
        // click the button
        materialButton.perform(click());

        // find the text view
        ViewInteraction textView = onView( withId(R.id.textView) );
        // checks the text
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Tests a password without special characters
     */
    @Test
    public void testFindMissingSpecial() {

        // find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.editText) );
        // type in Password123
        appCompatEditText.perform(replaceText("Password123"));

        // find the button
        ViewInteraction materialButton = onView( withId(R.id.button) );
        // click the button
        materialButton.perform(click());

        // find the text view
        ViewInteraction textView = onView( withId(R.id.textView) );
        // checks the text
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Tests an example of a correct password that meets all the requirements
     */
    @Test
    public void testCorrectPassword() {

        // find the view
        ViewInteraction appCompatEditText = onView( withId(R.id.editText) );
        // type in Password123!
        appCompatEditText.perform(replaceText("Password123!"));

        // find the button
        ViewInteraction materialButton = onView( withId(R.id.button) );
        // click the button
        materialButton.perform(click());

        // find the text view
        ViewInteraction textView = onView( withId(R.id.textView) );
        // checks the text
        textView.check(matches(withText("Your password meets the requirements")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
