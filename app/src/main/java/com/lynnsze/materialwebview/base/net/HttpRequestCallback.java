package com.lynnsze.materialwebview.base.net;


public abstract class HttpRequestCallback<T> {
    public HttpRequestCallback() {
    }

    public void before(int requestCode) {
    }

    public void progress(int requestCode, int progress) {
    }

    public void after(int requestCode) {
    }

    public void error(int requestCode, int errorCode, String msg) {
    }

    public void success(int requestCode, T t) {
    }
}
