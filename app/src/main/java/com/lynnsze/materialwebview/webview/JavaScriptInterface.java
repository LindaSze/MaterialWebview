package com.lynnsze.materialwebview.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;


@SuppressLint("JavascriptInterface")
public class JavaScriptInterface {
    private Context mContext;
    private Activity mActivity;
    private Handler mHandler;
    private WebView mWebView;

    public JavaScriptInterface(Context context, Handler handler, WebView webView, Activity activity) {
        this.mContext = context;
        this.mActivity = activity;
        this.mHandler = handler;
        this.mWebView = webView;
    }
}