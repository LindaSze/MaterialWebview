package com.lynnsze.materialwebview.main;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import com.lynnsze.materialwebview.R;
import com.lynnsze.materialwebview.base.CustomViewHolder;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
//import android.support.test.espresso.contrib.RecyclerViewActions;


import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;


public class RecyclerViewTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void scrollToItemBelowFold_checkItsText() {
//        onView(ViewMatchers.withId(R.id.search))
//                .perform(RecyclerViewActions.scrollToHolder(isInTheMiddle()));
    }


    private static Matcher<CustomViewHolder> isInTheMiddle() {
        return new TypeSafeMatcher<CustomViewHolder>() {
            @Override
            protected boolean matchesSafely(CustomViewHolder customHolder) {
                 customHolder.setText(R.id.suggest_item_website,"text");
                return true;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("item in the middle");
            }
        };
    }
}
