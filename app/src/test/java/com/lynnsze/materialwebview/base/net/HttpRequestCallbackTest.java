package com.lynnsze.materialwebview.base.net;

import org.junit.Test;

public class HttpRequestCallbackTest {
    CallBackTest testCallback;


    @Test
    public void before() throws Exception {
        testCallback = new CallBackTest();
    }

    @Test
    public void progress() throws Exception {
        testCallback.progress(0, 1);
    }

    @Test
    public void after() throws Exception {
        testCallback.after(0);
    }

    @Test
    public void error() throws Exception {
        testCallback.after(-1);

    }

    @Test
    public void success() throws Exception {
        testCallback.success(0,null);

    }

    public class CallBackTest<T> extends HttpRequestCallback<T> {

    }
}