package com.lynnsze.materialwebview.base;

import android.content.Context;
import android.net.ConnectivityManager;

import java.util.regex.Pattern;


public class MWUtils {

//    public boolean isNetworkAvailable(Context context) {
//        ConnectivityManager manager = (ConnectivityManager) context.getSystemService("connectivity");
//        return manager.getActiveNetworkInfo() != null ? manager.getActiveNetworkInfo().isAvailable() : false;
//    }

    public static boolean isUrl(String url) {
        Pattern pattern = Pattern
                .compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");
        return pattern.matcher(url).matches();
    }
}
