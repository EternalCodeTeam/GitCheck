package com.eternalcode.eternalupdater.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class HttpClient {
    public static String baseUri = "https://api.github.com/";
    public static OkHttpClient client = new OkHttpClient();

    public static JSONObject doRequest(String url)
    {
        Request request = new Request.Builder().url(baseUri + "/" + url).build();
        try (Response response = client.newCall(request).execute()){
            return response.body() != null ? (JSONObject) new JSONParser().parse(response.body().string()) : null;
        } catch (Exception e) {
            return null;
        }
    }

}
