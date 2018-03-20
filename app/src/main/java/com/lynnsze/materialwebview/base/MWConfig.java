package com.lynnsze.materialwebview.base;

import com.lynnsze.materialwebview.BuildConfig;

public class MWConfig {
    public static  boolean isDebug= BuildConfig.IS_DEBUG;;
    public static final String EXTRA_FRAGMENT_TYPE = "intent-fragment-type";

    public  static  final String URL_DEFAULT="http://www.baidu.com/";

    public  static  final String URL_SEARCH_SUGGESTION_PREFIX="https://ac.duckduckgo.com/ac/";

    public static final int CONNECTION_TIME_OUT = 5000;

}
