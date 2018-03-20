package com.lynnsze.materialwebview.base.net;


import java.util.Map;

public class HttpUtil {
    private static HttpUtil mInstance = null;

    public HttpUtil() {
    }

    public static HttpUtil getInstance() {
        if (mInstance == null) {
            synchronized (HttpUtil.class) {
                if (mInstance == null) {
                    mInstance = new HttpUtil();
                }
            }
        }
        return mInstance;
    }


    public void requestWithoutHeader(String url, Map<String, String> params, BaseParser parser, final HttpRequestCallback callBack) {
        request(url, 0, params, null, parser, callBack);
    }


    public void request(String url, int requestCode, Map<String, String> params, Map<String, String> headers, BaseParser parser, final HttpRequestCallback callBack) {
        new HttpBuilder().url(url)
                .requestCode(requestCode)
                .parser(parser)
                .params(params)
                .headers(headers)
                .build()
                .excute(callBack);

    }
}

