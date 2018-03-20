package com.lynnsze.materialwebview.base.net;

public class HttpResponse<T> {
    public static final int DEFAULT = 0;
    public static final int SUCCSESS = 200;

    private int mStatusCode = DEFAULT;
    private String mMsg;
    private String mJson;
    private T mData;

    public HttpResponse() {
    }

    public HttpResponse(int code) {
        this.init(code, null);
    }

    public HttpResponse(int code, String json) {
        this.init(code, json);
    }

    private void init(int code, String json) {
        this.mStatusCode = code;
        this.mJson = json;
    }


    public void setStatusCode(int code) {
        this.mStatusCode = code;
    }

    public int getStatusCode() {
        return mStatusCode;
    }

    public void setMessage(String msg) {
        this.mMsg = msg;
    }

    public String getMessage() {
        return mMsg;
    }


    public void setJson(String json) {
        this.mJson = json;
    }


    public String getJson() {
        return mJson;
    }

    public T getTarget() {
        return mData;
    }

    public void setTarget(T data) {
        this.mData = data;
    }
}