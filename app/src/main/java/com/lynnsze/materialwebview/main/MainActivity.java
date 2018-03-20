package com.lynnsze.materialwebview.main;

import android.animation.LayoutTransition;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lynnsze.materialwebview.base.MWConfig;
import com.lynnsze.materialwebview.R;
import com.lynnsze.materialwebview.search.ui.SearchFragment;
import com.lynnsze.materialwebview.webview.WebviewLayout;

public class MainActivity extends AppCompatActivity {
    private EditText urlView;
    private ImageView menuView;
    private ImageView switchView;
    private CardView cardView;
    private ViewPager pagerView;
    private LinearLayout containerView;
    private ImageView clearView;


    private TabLayout tabsView;
    private WebviewLayout webViewLayout;
    private LinearLayout pagerLayout;
    private RelativeLayout fragmentLayout;

    private Fragment searchFragment;

    private int containerPadding;

    private String currentUrl = MWConfig.URL_DEFAULT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initView();
        doView();
    }

    private void initView() {
        containerPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());

        containerView = (LinearLayout) findViewById(R.id.container);
        cardView = (CardView) findViewById(R.id.card);
        urlView = (EditText) findViewById(R.id.url);
        menuView = (ImageView) findViewById(R.id.menu);
        switchView = (ImageView) findViewById(R.id.switcher);
        pagerView = (ViewPager) findViewById(R.id.pager);
        clearView = (ImageView) findViewById(R.id.clear);
        tabsView = (TabLayout) findViewById(R.id.tabs);
        webViewLayout = (WebviewLayout) findViewById(R.id.webviewlayout);
        pagerLayout = (LinearLayout) findViewById(R.id.pagerlayout);
        fragmentLayout = (RelativeLayout) findViewById(R.id.fragment_content);
    }

    private void doView() {
        clearView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlView.clearFocus();
            }
        });

        urlView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setUrlViewFocused(hasFocus);
            }
        });

        final LayoutTransition transition = containerView.getLayoutTransition();
        transition.setDuration(100);

        transition.addTransitionListener(new LayoutTransition.TransitionListener() {
            @Override
            public void startTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType) {
                if (!urlView.hasFocus())
                    setCardViewUnfocus();
            }

            @Override
            public void endTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType) {
                if (urlView.hasFocus())
                    setCardViewFocused();

            }
        });

        urlView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                    loadWebView(v.getText().toString().trim(), false);
                    return false;
                }
                return false;
            }
        });

        urlView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (clearView.isShown()) {
                    if (charSequence != null && !TextUtils.isEmpty(charSequence.toString()))
                        showSearchFragment(charSequence.toString().trim());
                    else hideSearchFragment();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        pagerView.setAdapter(new HomeAdapter(getSupportFragmentManager()));
        tabsView.setupWithViewPager(pagerView);

        loadWebView(MWConfig.URL_DEFAULT, false);
    }

    public Fragment getFragment(){
        return searchFragment;
    }

    private void setUrlViewFocused(boolean hasFocus) {
        switchView.setVisibility(hasFocus ? View.GONE : View.VISIBLE);
        menuView.setVisibility(hasFocus ? View.GONE : View.VISIBLE);
    }

    private void setCardViewFocused() {
        clearView.setVisibility(View.VISIBLE);
        setShowWebOrPager(false);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) cardView.getLayoutParams();
        params.setMargins(0, 0, 0, 0);
        cardView.setLayoutParams(params);
    }

    private void setCardViewUnfocus() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(urlView.getWindowToken(), 0);
        clearView.setVisibility(View.GONE);
        setShowWebOrPager(true);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) cardView.getLayoutParams();
        params.setMargins(containerPadding, containerPadding, containerPadding, containerPadding);
        cardView.setLayoutParams(params);
    }

    public void loadWebView(String url, boolean isCleanUrlView) {
        currentUrl = url;
        if (isCleanUrlView) {
            urlView.clearFocus();
        } else
            setShowWebOrPager(true);
        webViewLayout.loadWebUrl(url);
    }

    private void setShowWebOrPager(boolean isShowWeb) {
        if (isShowWeb) {
            webViewLayout.setVisibility(View.VISIBLE);
            pagerLayout.setVisibility(View.GONE);
            urlView.setText(currentUrl);
            hideSearchFragment();
        } else {
            urlView.setText("");
            webViewLayout.setVisibility(View.GONE);
            pagerLayout.setVisibility(View.VISIBLE);
        }
    }


    public void showSearchFragment(String keyStr) {
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        if (searchFragment == null) {
            searchFragment = getFragment(SearchFragment.class.getName());
            setSuggestionKey(searchFragment, keyStr);
            trans.replace(R.id.fragment_content, searchFragment);
            trans.commitAllowingStateLoss();
            trans.show(searchFragment);
        } else {
            if (!searchFragment.isVisible()) {
                trans.commitAllowingStateLoss();
                trans.show(searchFragment);
            }
            setSuggestionKey(searchFragment, keyStr);
        }
    }

    private void setSuggestionKey(Fragment fragment, String keyStr) {
        if (fragment instanceof SearchFragment)
            ((SearchFragment) fragment).refreshSuggestion(keyStr);
    }

    private void hideSearchFragment() {
        if (searchFragment != null && searchFragment.isVisible()) {
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            transaction.hide(searchFragment);
            transaction.commitAllowingStateLoss();
        }
    }


    private Fragment getFragment(String className) {
        Bundle args = getIntent().getExtras();
        Fragment fragment = Fragment.instantiate(this, className, args);
        return fragment;
    }

    @Override
    public void onBackPressed() {
        if (urlView.hasFocus()) {
            urlView.clearFocus();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webViewLayout.getWebview().canGoBack()) {
                webViewLayout.getWebview().goBack();
                return false;
            } else finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webViewLayout.removeListener();
    }

    public static class HomeAdapter extends FragmentPagerAdapter {
        private static HomePanel[] panels = new HomePanel[]{
                new TopSitesFragment(),
                DummyFragment.create("Bookmarks"),
                DummyFragment.create("History"),
                DummyFragment.create("Reading List"),
                DummyFragment.create("Recent Tabs"),
        };

        public HomeAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return (Fragment) panels[position];
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return panels[position].getTitle();
        }

        @Override
        public int getCount() {
            return panels.length;
        }
    }
}
