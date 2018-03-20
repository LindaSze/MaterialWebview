package com.lynnsze.materialwebview.base.net;

import org.junit.Before;
import org.junit.Test;
import com.lynnsze.materialwebview.search.util.SuggestionParser;

public class BaseParserTest {
    SuggestionParser parser;

    @Before
    public void setUp() throws Exception {
        parser = new SuggestionParser();
    }

    @Test
    public void parse() throws Exception {
        String test_json = "[{\"phrase\":\"qoo10\"},{\"phrase\":\"qoo10.com.sg\"},{\"phrase\":\"qooxi\"},{\"phrase\":\"qoogle\"},{\"phrase\":\"qooapp\"},{\"phrase\":\"qoo10 indonesia\"},{\"phrase\":\"qooto qooto\"},{\"phrase\":\"qooqle.com\"},{\"phrase\":\"qooxinet\"},{\"phrase\":\"qoo10 japan\"}]";
        parser.parse(test_json);
    }

}