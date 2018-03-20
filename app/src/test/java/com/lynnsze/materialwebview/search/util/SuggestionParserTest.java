package com.lynnsze.materialwebview.search.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class SuggestionParserTest {

    private SuggestionParser testParser;
    String test_json;


    @Before
    public void setUp() throws Exception {
        testParser = new SuggestionParser();
        test_json = "[{\"phrase\":\"qoo10\"},{\"phrase\":\"qoo10.com.sg\"},{\"phrase\":\"qooxi\"},{\"phrase\":\"qoogle\"},{\"phrase\":\"qooapp\"},{\"phrase\":\"qoo10 indonesia\"},{\"phrase\":\"qooto qooto\"},{\"phrase\":\"qooqle.com\"},{\"phrase\":\"qooxinet\"},{\"phrase\":\"qoo10 japan\"}]";
    }


    @Test
    public void parse() throws Exception {
        assertNotNull(testParser.parse(test_json));
    }

}