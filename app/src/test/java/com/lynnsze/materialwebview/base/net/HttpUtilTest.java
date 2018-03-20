package com.lynnsze.materialwebview.base.net;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HttpUtilTest {
    HttpUtil httpUtilTest;

    @Before
    public void setUp() throws Exception {
        httpUtilTest = new HttpUtil();
    }

    @Test
    public void getInstance() throws Exception {
        assertNotNull(httpUtilTest.getInstance());
    }

    @Test
    public void requestWithoutHeader() throws Exception {
        httpUtilTest.requestWithoutHeader("http://baidu.com",null,null,null);
    }

    @Test
    public void request() throws Exception {
        httpUtilTest.request("http://baidu.com",0,null,null,null,null);
    }

}