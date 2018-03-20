package com.lynnsze.materialwebview.base.net;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class HttpResponseTest {
    private  HttpResponse response;

    @Before
    public void setUp() throws Exception {
        response=new HttpResponse(0);
    }


    @Test
    public void setStatusCode() throws Exception {
        response.setStatusCode(0);
    }

    @Test
    public void getStatusCode() throws Exception {
       assertEquals(0,response.getStatusCode());
    }

    @Test
    public void getMessage() throws Exception {
        assertNull(response.getMessage());
    }

    @Test
    public void setJson() throws Exception {
        String test_json = "[{\"phrase\":\"qoo10\"},{\"phrase\":\"qoo10.com.sg\"},{\"phrase\":\"qooxi\"},{\"phrase\":\"qoogle\"},{\"phrase\":\"qooapp\"},{\"phrase\":\"qoo10 indonesia\"},{\"phrase\":\"qooto qooto\"},{\"phrase\":\"qooqle.com\"},{\"phrase\":\"qooxinet\"},{\"phrase\":\"qoo10 japan\"}]";
        response=new HttpResponse(0,test_json);
    }

    @Test
    public void getJson() throws Exception {
        assertNull(response.getJson());

    }

}