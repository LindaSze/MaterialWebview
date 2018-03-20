package com.lynnsze.materialwebview.main;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

import com.lynnsze.materialwebview.base.MWConfig;
import com.lynnsze.materialwebview.R;
import com.lynnsze.materialwebview.webview.JavaScriptInterface;
import com.lynnsze.materialwebview.webview.WebviewLayout;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("SetJavaScriptEnabled")
public class WebviewFragment extends Fragment implements HomePanel {

    protected String mUrl = MWConfig.URL_DEFAULT;

    protected WebviewLayout webviewLayout = null;
    protected boolean isOnPause;

    protected Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };


    public static WebviewFragment create(String title) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);

        WebviewFragment fragment = new WebviewFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public String getTitle() {
        return "webview";
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);
        initWebView(view);
        return view;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void initWebView(View view) {
        this.webviewLayout = (WebviewLayout) view.findViewById(R.id.frgment_webview);
        JavaScriptInterface jsInterface = new JavaScriptInterface(getActivity(), mHandler, webviewLayout.getWebview(), getActivity());
        webviewLayout.getWebview().addJavascriptInterface(jsInterface, "android");
        webviewLayout.loadWebUrl(mUrl);
    }


    @Override
    public void onDestroy() {
        if (webviewLayout != null) {
            webviewLayout.setVisibility(View.GONE);
            webviewLayout.getWebview().stopLoading();
            webviewLayout.getWebview().destroy();
            webviewLayout = null;
            isOnPause = false;
        }
        super.onDestroy();
    }


    private void invoke(boolean isEnableJs, String method) {
        try {
            webviewLayout.getWebview().setJavaScriptEnabled(isEnableJs);
            if (webviewLayout.getWebview() != null) {
                webviewLayout.getWebview().getClass().getMethod(method).invoke(webviewLayout.getWebview(), (Object[]) null);
                isOnPause = !isEnableJs;
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        invoke(false, "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        invoke(true, "onResume");
    }

}