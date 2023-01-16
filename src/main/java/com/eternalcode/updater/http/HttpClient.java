package com.eternalcode.updater.http;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.Contract;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * The HttpClient class is used to send HTTP requests to the GitHub server.
 */
public class HttpClient {

    private final static String baseUri = "https://api.github.com/";
    private final static OkHttpClient client = new OkHttpClient();

    /**
     * Sends an HTTP request to the GitHub server and returns the response as a JSON object.
     *
     * @param url The URL of the request
     * @return The server's response in JSON format
     * @throws RuntimeException If there is a connection problem or the provided repository is not found
     */
    @Contract(pure = true)
    public static JSONObject doRequest(String url) {
        Request request = new Request
                .Builder()
                .url(baseUri + "" + url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            JSONObject jsonResponse = (JSONObject) new JSONParser().parse(response.body().string());

            if (jsonResponse.containsKey("message")) {
                throw new Exception("[EternalUpdater] Provided repository was not found");
            }
            else {
                return jsonResponse;
            }
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
