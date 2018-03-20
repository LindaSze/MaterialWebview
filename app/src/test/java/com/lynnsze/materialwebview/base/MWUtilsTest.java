package com.lynnsze.materialwebview.base;

import org.junit.Test;

import static org.junit.Assert.*;


public class MWUtilsTest {
    @Test
    public void isUrl() throws Exception {
        assertTrue(MWUtils.isUrl("http://baidu.com"));
        assertFalse(MWUtils.isUrl("http:/baidu.com"));
    }

}