package ua.fromrandomcountry.dota2searcher.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import ua.fromrandomcountry.dota2searcher.netconnection.HttpParser;

public class JsonParser {

    public static JSONArray parseJSONArrayByURL(String url) throws JSONException, InterruptedException, ExecutionException, TimeoutException {
        return new JSONArray(new HttpParser().execute(url).get());
    }

    public static JSONObject parseJSONObjectByURL(String url) throws JSONException, InterruptedException, ExecutionException, TimeoutException {
        return new JSONObject(new HttpParser().execute(url).get());
    }

}
