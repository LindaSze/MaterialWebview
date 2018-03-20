package com.lynnsze.materialwebview.base.net;

import org.junit.Before;
import org.junit.Test;

public class HttpRequestTest {
    private  HttpRequest testHttpRequest;

    @Before
    public void setUp() throws Exception {
        testHttpRequest = new HttpRequest(new HttpBuilder().url("http://baidu.com"));
    }

    @Test
    public void excute() throws Exception {
        testHttpRequest.excute(null);
    }

    @Test
    public void cancel() throws Exception {
        if(testHttpRequest.httpThread!=null)
        testHttpRequest.httpThread.cancel();
    }

    @Test
    public void requestGet() throws Exception {
        testHttpRequest.requestGet(testHttpRequest.httpBuilder.getUrl(),null,null);
    }

}