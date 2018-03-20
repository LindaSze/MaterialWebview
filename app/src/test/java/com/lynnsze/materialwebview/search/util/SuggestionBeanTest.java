package com.lynnsze.materialwebview.search.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SuggestionBeanTest {
    private SuggestionBean testSuggestionBean;

    @Before
    public void setUp() throws Exception {
        testSuggestionBean = new SuggestionBean("firefox");
    }

    @Test
    public void setPhrase() throws Exception {
        testSuggestionBean.setPhrase("browser");
    }

    @Test
    public void getPhrase() throws Exception {
        assertEquals("firefox",testSuggestionBean.getPhrase());
    }


}