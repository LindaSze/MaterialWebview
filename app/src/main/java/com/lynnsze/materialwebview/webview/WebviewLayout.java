package com.lynnsze.materialwebview.webview;

import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lynnsze.materialwebview.R;
import com.lynnsze.materialwebview.base.MWUtils;

public class WebviewLayout extends FrameLayout implements WebviewStateManager.WebviewStateListener, View.OnClickListener {

    private Context mContext;

    private CustomWebview webview;
    private LinearLayout errorLayout;
    private TextView infoTV;
    private Button wifiBtn;
    private Button retryBtn;

    public WebviewLayout(Context context) {
        super(context);
    }

    public WebviewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_webview, this, true);
        this.mContext = context;
        WebviewStateManager.getInstance().setStateListener(this);
    }

    public WebviewLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    private void initView() {
        webview = (CustomWebview) findViewById(R.id.webview_view);
        errorLayout = (LinearLayout) findViewById(R.id.webview_error);
        infoTV = (TextView) errorLayout.findViewById(R.id.webview_error_info);
        wifiBtn = (Button) errorLayout.findViewById(R.id.webview_wifi_btn);
        wifiBtn.setOnClickListener(this);
        retryBtn = (Button) errorLayout.findViewById(R.id.webview_retry_btn);
        retryBtn.setOnClickListener(this);
        showErrorShow(false);
    }


    public void loadWebUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            if (MWUtils.isUrl(url)) {
                showErrorShow(false);
                webview.loadWebUrl(url);
            }else showErrorInfo(url);
        }
    }

    public void showErrorShow(boolean isShow) {
        if (isShow) {
            errorLayout.setVisibility(View.VISIBLE);
            webview.setVisibility(View.GONE);
        } else {
            errorLayout.setVisibility(View.GONE);
            webview.setVisibility(View.VISIBLE);
        }
    }

    public void showErrorInfo(String url) {
        infoTV.setText(String.format(getResources().getString(R.string.webview_error_info), url));
        showErrorShow(true);
    }

    public CustomWebview getWebview() {
        return webview;
    }

    private void openWifi() {
        mContext.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.webview_retry_btn:
                showErrorShow(false);
                webview.refresh();
                break;
            case R.id.webview_wifi_btn:
                openWifi();
                break;
            default:
                break;
        }
    }


    @Override
    public void showError(String url) {
        showErrorInfo(url);
    }

    public void removeListener() {
        WebviewStateManager.getInstance().removeStateListener();
    }
}
