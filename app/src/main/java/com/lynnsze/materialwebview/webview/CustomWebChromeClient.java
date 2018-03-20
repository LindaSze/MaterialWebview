package com.lynnsze.materialwebview.webview;

import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.lynnsze.materialwebview.base.MWConfig;

public class CustomWebChromeClient extends WebChromeClient {
    private CustomWebview webview;

    public CustomWebChromeClient() {
    }

    public CustomWebChromeClient(CustomWebview webView) {
        this.webview=webView;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        webview.refreshProgress(newProgress);
    }

    @Override
    public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
        return super.onJsBeforeUnload(view, url, message, result);
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        return super.onJsConfirm(view, url, message, result);
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage cm) {
        if (MWConfig.isDebug)
            android.util.Log.d("", "webview console " + cm.lineNumber() + " of " + cm.sourceId() + " : " + cm.message());
        return true;
    }

}
