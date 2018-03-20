package com.lynnsze.materialwebview.base.net;

import org.junit.Before;
import org.junit.Test;
import com.lynnsze.materialwebview.base.MWConfig;
import com.lynnsze.materialwebview.search.util.SuggestionParser;

import static org.junit.Assert.assertEquals;

public class HttpBuilderTest {
    private HttpBuilder testBuilder;

    @Before
    public void setUp() throws Exception {
        testBuilder=new HttpBuilder().tag(HttpBuilderTest.class.getName()).url("http://baidu.com").headers(null).params(null);
    }

    @Test
    public void tag() throws Exception {
        testBuilder.tag(HttpBuilderTest.class.getName());
    }

    @Test
    public void requestCode() throws Exception {
        testBuilder.requestCode(-1);

    }

    @Test
    public void url() throws Exception {
        testBuilder.url("http://baidu.com");
    }



    @Test
    public void connectTimeOut() throws Exception {
         testBuilder.connectTimeOut(MWConfig.CONNECTION_TIME_OUT);
    }


    @Test
    public void parser() throws Exception {
        testBuilder.parser(new SuggestionParser());
    }

    @Test
    public void getTag() throws Exception {
        assertEquals(HttpBuilderTest.class.getName(),testBuilder.getTag());
    }

    @Test
    public void getRequestCode() throws Exception {
        assertEquals(0,testBuilder.getRequestCode());

    }

    @Test
    public void getUrl() throws Exception {
        assertEquals("http://baidu.com",testBuilder.getUrl());
    }


    @Test
    public void getParser() throws Exception {

    }

}