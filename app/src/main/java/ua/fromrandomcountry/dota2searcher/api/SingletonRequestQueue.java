package ua.fromrandomcountry.dota2searcher.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SingletonRequestQueue {
    private static SingletonRequestQueue instant;
    private RequestQueue requestQueue;
    private static Context ctx;

    private SingletonRequestQueue(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        return requestQueue;
    }

    public static synchronized SingletonRequestQueue getInstant(Context context) {
        if (instant == null) {
            instant = new SingletonRequestQueue(context);
        }
        return instant;
    }

   public<T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
   }


}
