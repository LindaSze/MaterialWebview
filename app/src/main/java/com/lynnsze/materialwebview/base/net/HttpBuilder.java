package com.lynnsze.materialwebview.base.net;

import java.util.Map;


public class HttpBuilder {
    protected String mTag = null;
    protected int mRequestCode = 0;
    protected String mUrl = null;
    protected BaseParser mParser = null;
    protected Map<String, String> mParams = null;
    protected Map<String, String> mHeaders;
    protected int mConnectTimeout = 5000;

    public HttpBuilder() {
    }

    public HttpBuilder tag(String tag) {
        this.mTag = tag;
        return this;
    }

    public HttpBuilder requestCode(int requestCode) {
        this.mRequestCode = requestCode;
        return this;
    }

    public HttpBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public HttpBuilder params(Map<String, String> params) {
        this.mParams = params;
        return this;
    }

    public HttpBuilder connectTimeOut(int connecttimeout) {
        this.mConnectTimeout = connecttimeout;
        return this;
    }

    public HttpBuilder headers(Map<String, String> headers) {
        this.mHeaders = headers;
        return this;
    }

    public HttpBuilder parser(BaseParser parser) {
        this.mParser = parser;
        return this;
    }

    public String getTag() {
        return this.mTag;
    }

    public int getRequestCode() {
        return this.mRequestCode;
    }


    public String getUrl() {
        return this.mUrl;
    }

    public int getConnecttimeout() {
        return this.mConnectTimeout;
    }

    public BaseParser getParser() {
        return this.mParser;
    }

    public Map<String, String> getParams() {
        return this.mParams;
    }


    public Map<String, String> getHeaders() {
        return this.mHeaders;
    }

    public HttpRequest build() {
        return new HttpRequest(this);
    }
}
