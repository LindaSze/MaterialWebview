package com.lynnsze.materialwebview.webview;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AbsoluteLayout;
import android.widget.ProgressBar;
import android.widget.ZoomButtonsController;

import com.lynnsze.materialwebview.R;


import java.lang.reflect.Method;
import java.util.Map;


@SuppressWarnings("deprecation")
public class CustomWebview extends WebView {
    private ProgressBar mProgressBar;
    private Context mContext;
    private WebSettings mSettings;

    public CustomWebview(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        mProgressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        mProgressBar.setLayoutParams(new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.MATCH_PARENT, dp2sp(3), 0, 0));
        mProgressBar.setProgressDrawable(getResources().getDrawable(R.drawable.webview_progressbar_bg));
        addView(mProgressBar);
        init();
    }

    private void init() {
        mSettings = getSettings();
        if (mSettings != null) {
            mSettings.setJavaScriptEnabled(true);
            mSettings.setBuiltInZoomControls(true);
            mSettings.setUseWideViewPort(true);
            mSettings.setLoadWithOverviewMode(true);
            mSettings.setDomStorageEnabled(true);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (mSettings != null) {
                mSettings.setDisplayZoomControls(false);
            }
        } else {
            getControlls();
        }
        doSecurityControll();
        setCacheSettings();
        setWebChromeClient(new CustomWebChromeClient(this));
        setWebViewClient(new CustomWebViewClient());

    }

    private void setCacheSettings() {
        //getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        //setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

    private void getControlls() {
        try {
            final ZoomButtonsController zoom_controll = (ZoomButtonsController) getClass().getMethod("getZoomButtonsController")
                    .invoke(this, new Object());
            zoom_controll.getContainer().setVisibility(View.GONE);
        } catch (Exception e) {
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void doSecurityControll() {
        try {
            removeJavascriptInterface("searchBoxJavaBridge_");
        } catch (NoSuchMethodError e) {

        }
    }

    private void initLoadUrlMethod() {
        try {
            Class<?> classType = Class.forName("android.webkit.WebView");
            Method method = classType.getMethod("loadUrl", String.class, Map.class);
        } catch (Exception e) {
        }
    }

    private int dp2sp(float dpVal) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, getResources().getDisplayMetrics()));
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) mProgressBar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        mProgressBar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public void setJavaScriptEnabled(boolean isEnabled) {
        if (mSettings != null) {
            mSettings.setJavaScriptEnabled(isEnabled);
        }
    }

    public void loadWebUrl(final String url) {
        post(new Runnable() {
            @Override
            public void run() {
                loadUrl(url);
            }
        });
    }

    public void refresh() {
        reload();
    }

    public void refreshProgress(int progress){
        if (progress == 100) {
            mProgressBar.setVisibility(GONE);
        } else {
            if (mProgressBar.getVisibility() == GONE)
                mProgressBar.setVisibility(VISIBLE);
            mProgressBar.setProgress(progress);
        }
    }
}
