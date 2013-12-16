package no.jpg.bcvekst.controllers;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by Fahied on 12/15/13.
 * http://loopj.com/android-async-http/
 */
public class BCRestClient {

    private static final String BASE_URL = "http://cotwapp.eo.no/";

    private static AsyncHttpClient client = new AsyncHttpClient();
    //client.setBasicAuth("username","password");


    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void get(String url, BinaryHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), null, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
