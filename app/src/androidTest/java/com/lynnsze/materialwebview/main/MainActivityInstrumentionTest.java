package com.lynnsze.materialwebview.main;

import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.lynnsze.materialwebview.R;
import com.lynnsze.materialwebview.search.ui.SearchFragment;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.equalTo;


@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentionTest {

    private String mUrl;
    private static String mKey;
    private IdlingResource mWebIdlingResource;
    private IdlingResource mLoadIdlingResource;


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);


    @Before
    public void before() {
        mUrl = "http://sina.cn";
        mKey = "q";

//        mIdlingResource = new WebViewIdlingResource((WebView)mActivityRule.getActivity().findViewById(R.id.webview_view));
//        Espresso.registerIdlingResources(mIdlingResource);
        setSuggestIdle();
    }

    private void setSuggestIdle() {
        if (mLoadIdlingResource == null) {
            SearchFragment fragment = (SearchFragment) mActivityRule.getActivity().getFragment();
            if (fragment != null&&fragment.isVisible()) {
                mLoadIdlingResource = fragment.getIdlingResource();
                Espresso.registerIdlingResources(mLoadIdlingResource);
            }
        }
    }

    @Test
    public void load_url() {
        onView(withId(R.id.url))
                .check(matches(is(isDisplayed())));
        onView(withId(R.id.pagerlayout))
                .check(matches(not(isDisplayed())));
        onView(withId(R.id.url)).perform(typeText(mUrl));
    }


    @Test
    public void suggestion_input() {
        onView(withId(R.id.url)).perform(typeText(mKey));
        setSuggestIdle();
        suggestion_item_click();
    }

    public void suggestion_item_click() {
        setSuggestIdle();
        SearchFragment fragment=(SearchFragment)mActivityRule.getActivity().getFragment();
//        if(fragment.isVisible())
//        onRow("qoo10").onChildView(withId(R.id.suggest_item_webtitle)).perform(click());
    }


    private static DataInteraction onRow(String str) {
        return onData(hasEntry(equalTo(mKey), contains(str)));
    }


    @After
    public void unregisterIdlingResource() {
        if (mLoadIdlingResource != null) {
            Espresso.unregisterIdlingResources(mLoadIdlingResource);
        }
    }
}



