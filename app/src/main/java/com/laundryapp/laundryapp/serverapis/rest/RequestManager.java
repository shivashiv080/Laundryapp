package com.laundryapp.laundryapp.serverapis.rest;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.laundryapp.laundryapp.serverapis.rest.volley.custom.requests.CustomStringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides basic Rest Request handling
 * An effort has been made to hide most of the underlying request implementation stack, but at present volley Request objects are returned directly to callers of the doXRequest methods
 * This is to allow cancellation of requests, but going forward we should look to hide these also and provide our own Request objects which expose the underlying functionality.
 */
public class RequestManager {
    public interface OnResponseListener<T> {
        void onResponse(T object);

        void onResponseError(RestError error);
    }

    private static final String TAG = "RequestManager";
    private static final int REQUEST_TIMEOUT = 10000;
    private static final int REQUEST_RETRIES = 10;
    private static final float REQUEST_BACKOFF_MULTIPLIER = 1.25f;

    private static RequestManager mInstance;

    private Context mContext;
    private RequestQueue mRequestQueue;
    private Map<String, String> mHeaders;

    public static synchronized void startup(Context context) {
        if (mInstance == null) {
            mInstance = new RequestManager(context.getApplicationContext());
        }
    }

    public static synchronized void shutdown() {
        if (mInstance == null) {
            return;
        }

        mInstance._shutdown();
        mInstance = null;
    }

    public static RequestManager getInstance() {
        return mInstance;
    }

    private RequestManager(Context context) {
        mContext = context;

        initRequestQueue();
    }

    private void initRequestQueue() {
        Cache cache = new DiskBasedCache(mContext.getCacheDir(), 1024 * 1024); // 1MB cap

        Network network = new BasicNetwork(new HurlStack());

        mRequestQueue = new RequestQueue(cache, network);

        mRequestQueue.start();
    }

    private void _shutdown() {
        mRequestQueue.stop();
        mRequestQueue = null;
    }

    public <T> Request<T> queueRequest(Request<T> request) {
        if (mInstance == null) {
            Log.e(TAG, "Not initialised");
            return null;
        }

        request.setRetryPolicy(createRetryPolicy());

        logRequest(request);

        return mRequestQueue.add(request);
    }

    public void setHeaders(Map<String, String> headers) {
        mHeaders = headers;
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public void cancelAll(Object tag) {
        if (tag != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    //------------------------------------------------------------------------
    //region "String Request"

    //region "GET"
    public Request<String> doStringGet(final String url, final OnResponseListener<String> listener) {
        return doStringGet(null, url, listener);
    }

    public Request<String> doStringGet(final Object tag, final String url, final OnResponseListener<String> listener) {
        return doStringGet(tag, url, null, listener);
    }

    public Request<String> doStringGet(final String url, final Map<String, String> headers, final OnResponseListener<String> listener) {
        return doStringGet(null, url, headers, listener);
    }

    public Request<String> doStringGet(final Object tag, final String url, final Map<String, String> headers, final OnResponseListener<String> listener) {
        return doStringGet(tag, url, null, headers, listener);
    }

    public Request<String> doStringGet(final String url, final Map<String, String> params, final Map<String, String> headers, final OnResponseListener<String> listener) {
        return doStringGet(null, url, params, headers, listener);
    }

    public Request<String> doStringGet(final Object tag, final String url, final Map<String, String> params, final Map<String, String> headers, final OnResponseListener<String> listener) {
        return stringRequest(Request.Method.GET, tag, url, params, (JSONObject) null, headers, listener);
    }

    //endregion
    //region "POST"
    public Request<String> doStringPost(final String url, final JSONObject postData, final OnResponseListener<String> listener) {
        return doStringPost(url, null, postData, listener);
    }

    public Request<String> doStringPost(final String url, final Map<String, String> params, final JSONObject postData, final OnResponseListener<String> listener) {
        return doStringPost(url, params, postData, null, listener);
    }

    public Request<String> doStringPost(final Object tag, final String url, final Map<String, String> params, final JSONObject postData, final OnResponseListener<String> listener) {
        return doStringPost(tag, url, params, postData, null, listener);
    }

    public Request<String> doStringPost(final String url, final Map<String, String> params, final JSONObject postData, final Map<String, String> headers,
                                        final OnResponseListener<String> listener) {
        return doStringPost(null, url, params, postData, headers, listener);
    }

    public Request<String> doStringPost(final Object tag, final String url, final Map<String, String> params, final JSONObject postData, final Map<String, String> headers,
                                        final OnResponseListener<String> listener) {
        return stringRequest(Request.Method.POST, tag, url, params, postData, headers, listener);
    }
    //endregion

    private Request<String> stringRequest(int method, final Object tag, final String url, final Map<String, String> params, final JSONObject postData, final Map<String, String> headers,
                                          final OnResponseListener<String> listener) {
//        headers.put("AppToken", "SRLDIAG2017");

        VolleyResponseListenerAdapter<String> listenerAdapter = new VolleyResponseListenerAdapter<>(listener);
        CustomStringRequest request = new CustomStringRequest(method, url, listenerAdapter, listenerAdapter);
        if (tag != null) {
            request.setTag(tag);
        }
        request.setParams(params);
        request.setHeaders(getCombinedHeaders(headers));
        request.setRequestBody(postData);
        return queueRequest(request);
    }

    //endregion
    //------------------------------------------------------------------------

    public Request<JSONArray> doJsonArrayGet(final String url, final OnResponseListener<JSONArray> listener) {
        return doJsonArrayGet(url, null, listener);
    }

    public Request<JSONArray> doJsonArrayGet(final String url, final Map<String, String> headers, final OnResponseListener<JSONArray> listener) {
        return jsonArrayRequest(Request.Method.GET, url, null, headers, listener);
    }

    public Request<JSONArray> doJsonArrayPost(final String url, final JSONArray postData, final OnResponseListener<JSONArray> listener) {
        return doJsonArrayPost(url, postData, null, listener);
    }

    public Request<JSONArray> doJsonArrayPost(final String url, final JSONArray postData, final Map<String, String> headers, final OnResponseListener<JSONArray> listener) {
        return jsonArrayRequest(Request.Method.POST, url, postData, headers, listener);
    }

    public Request<JSONObject> doJsonPost(final String url, final JSONObject postData, final OnResponseListener<JSONObject> listener) {
        return doJsonPost(url, postData, null, listener);
    }

    public Request<JSONObject> doJsonPost(final String url, final JSONObject postData, final Map<String, String> headers, final OnResponseListener<JSONObject> listener) {
        return jsonRequest(Request.Method.POST, url, postData, headers, listener);
    }

    public Request<JSONObject> doJsonGet(final String url, final OnResponseListener<JSONObject> listener) {
        return doJsonGet(url, null, listener);
    }

    public Request<JSONObject> doJsonGet(final String url, final Map<String, String> headers, final OnResponseListener<JSONObject> listener) {
        return jsonRequest(Request.Method.GET, url, (JSONObject) null, headers, listener);   //Cast required to remove ambiguity
    }

    private Request<JSONObject> jsonRequest(int method, final String url, final JSONObject postData, final Map<String, String> headers,
                                            final OnResponseListener<JSONObject> listener) {
        VolleyResponseListenerAdapter<JSONObject> listenerAdapter = new VolleyResponseListenerAdapter<>(listener);

        final Map<String, String> requestHeaders = getCombinedHeaders(headers);

        JsonObjectRequest request = new JsonObjectRequest(method, url, postData, listenerAdapter, listenerAdapter) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
//                requestHeaders.put("iToken", "SRLDIAG2017");
                return requestHeaders;
            }
        };

        return queueRequest(request);
    }

    private Request<JSONArray> jsonArrayRequest(int method, final String url, final JSONArray postData, final Map<String, String> headers,
                                                final OnResponseListener<JSONArray> listener) {
        VolleyResponseListenerAdapter<JSONArray> listenerAdapter = new VolleyResponseListenerAdapter<>(listener);

        final Map<String, String> requestHeaders = getCombinedHeaders(headers);

        JsonArrayRequest request = new JsonArrayRequest(method, url, postData, listenerAdapter, listenerAdapter) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
//                requestHeaders.put("iToken", "SRLDIAG2017");
                return requestHeaders;
            }
        };

        return queueRequest(request);
    }

    private Map<String, String> getCombinedHeaders(final Map<String, String> headers) {
        Map<String, String> commonHeaders = getHeaders();
        Map<String, String> combinedHeaders = new HashMap<>();

        if (commonHeaders != null) {
            combinedHeaders.putAll(commonHeaders);
        }

        if (headers != null) {
            combinedHeaders.putAll(headers);
        }

        return combinedHeaders;
    }

    private RetryPolicy createRetryPolicy() {
        return new DefaultRetryPolicy(REQUEST_TIMEOUT, REQUEST_RETRIES, REQUEST_BACKOFF_MULTIPLIER);
    }

    private void logRequest(Request<?> request) {
        if (request != null) {
            Log.d(TAG, "URL : " + request.getUrl());

            try {
                Log.d(TAG, "Body : " + StringUtil.getValidString(request.getBody()));
            } catch (AuthFailureError authFailureError) {
                authFailureError.printStackTrace();
            }

            try {
                Map<String, String> headers = request.getHeaders();
                if (headers != null && !headers.isEmpty()) {
                    String prefix = "";
                    StringBuilder builder = new StringBuilder("Headers : [ ");
                    for (String key : headers.keySet()) {
                        builder.append(prefix);
                        builder.append(key);
                        builder.append(" = ");
                        builder.append(headers.get(key));
                        prefix = ", ";
                    }
                    builder.append(" ]");
                    Log.d(TAG, builder.toString());
                }
            } catch (AuthFailureError authFailureError) {
                authFailureError.printStackTrace();
            }
        }
    }
}
