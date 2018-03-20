package com.lynnsze.materialwebview.search.util;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lynnsze.materialwebview.base.net.BaseParser;
import com.lynnsze.materialwebview.base.net.HttpResponse;

import java.util.ArrayList;
import java.util.List;

public class SuggestionParser extends BaseParser {

    public SuggestionParser() {
        super(HttpResponse.class);
    }


    @Override
    public List<SuggestionBean> parse(String json) {
        if (json != null) {
            ArrayList<SuggestionBean> list = new ArrayList<SuggestionBean>();
            try {
                JSONArray jsonArray = new JSONArray(json);
                if (jsonArray != null && jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.optJSONObject(i);
                        if (jsonObject != null)
                            list.add(new SuggestionBean(jsonObject.optString("phrase")));
                    }
                }
            } catch (JSONException e) {
            } finally {
                return list;
            }
        }
        return null;
    }
}
