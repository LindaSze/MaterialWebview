package com.lynnsze.materialwebview.base.net;

import com.lynnsze.materialwebview.base.MWConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpRequest {

    protected HttpBuilder httpBuilder = null;
    protected HttpRequestThread httpThread = null;

    public HttpRequest(HttpBuilder builder) {
        this.httpBuilder = builder;

    }

    public void excute(final HttpRequestCallback callBack) {
        int code = checkValidity();
        if(callBack!=null)
        callBack.before(httpBuilder.getRequestCode());
        if (code != 0&&callBack!=null) {
            callBack.error(httpBuilder.getRequestCode(), code, "");
            callBack.after(httpBuilder.getRequestCode());
        } else {
            if (httpThread != null && httpThread.isFinish())
                httpThread.isFinish();
            httpThread = new HttpRequestThread(callBack);
            httpThread.start();
        }
    }

    public void cancel() {
        if(httpThread!=null)
        httpThread.cancel();
    }

    class HttpRequestThread extends BaseThread {
        private HttpRequestCallback callBack;

        HttpRequestThread(HttpRequestCallback callBack) {
            this.callBack = callBack;
        }

        @Override
        public void runThread() {
            requestGet(httpBuilder.getUrl(), callBack,httpBuilder.getParams());
            finish();
        }
    }


    public void requestGet(String urlStr, HttpRequestCallback callBack, Map<String, String> params) {
        HttpResponse response = new HttpResponse();
        HttpURLConnection httpURLConnection = null;
        InputStream is = null;
        BufferedReader in = null;
        try {
            URL url = new URL(urlStr);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(MWConfig.CONNECTION_TIME_OUT);
            httpURLConnection.setReadTimeout(MWConfig.CONNECTION_TIME_OUT);
            httpURLConnection.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    httpURLConnection.addRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            int statusCode = httpURLConnection.getResponseCode();
            response.setStatusCode(statusCode);
            if (statusCode == httpURLConnection.HTTP_OK) {
                is = httpURLConnection.getInputStream();
            } else {
                is = httpURLConnection.getErrorStream();
            }
            StringBuilder sb = new StringBuilder();
            if (is != null) {
                in = new BufferedReader(new InputStreamReader(is));
                String line = "";
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }
            }
            response.setJson(sb.toString());
            callBack.success(response.getStatusCode(), httpBuilder.getParser().parse(response.getJson()));
        } catch (Exception e) {
            if (MWConfig.isDebug) {
                response.setStatusCode(httpURLConnection.HTTP_OK);
                response.setJson("[{\"phrase\":\"qoo10\"},{\"phrase\":\"qoo10.com.sg\"},{\"phrase\":\"qooxi\"},{\"phrase\":\"qoogle\"},{\"phrase\":\"qooapp\"},{\"phrase\":\"qoo10 indonesia\"},{\"phrase\":\"qooto qooto\"},{\"phrase\":\"qooqle.com\"},{\"phrase\":\"qooxinet\"},{\"phrase\":\"qoo10 japan\"}]");
                if(callBack!=null)
                callBack.success(response.getStatusCode(), httpBuilder.getParser().parse(response.getJson()));
            } else callBack.error(response.getStatusCode(), 0, "");
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }

        }
    }

    private int checkValidity() {
        return 0;
    }

}
