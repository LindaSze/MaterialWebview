package com.lynnsze.materialwebview.webview;

/**
 * the listener is not suitable for multi webview
 */
public class WebviewStateManager {
    private static WebviewStateManager mInstance = null;

    private WebviewStateListener listener;

    private WebviewStateManager() {
    }

    public static synchronized WebviewStateManager getInstance() {
        synchronized (WebviewStateManager.class) {
            if (mInstance == null) {
                mInstance = new WebviewStateManager();
            }
            return mInstance;
        }
    }

    public void setStateListener(WebviewStateListener listener) {
        this.listener = listener;
    }

    public void removeStateListener() {
        listener = null;
    }

    public void showError(String url) {
        if (listener != null)
            listener.showError(url);
    }

    public interface WebviewStateListener {
        void showError(String url);
    }
}



