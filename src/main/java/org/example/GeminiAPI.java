package org.example;

import com.google.gson.*;
import com.squareup.okhttp.*;
import io.github.cdimascio.dotenv.Dotenv;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeminiAPI {

    private static Dotenv dotenv = Dotenv.load();
    private static final String API_KEY = dotenv.get("GEMINI_API_KEY");



    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key="+API_KEY;


    public static String sendText(String text) throws URISyntaxException, IOException, InterruptedException {

       OkHttpClient client = new OkHttpClient();
       Gson gson = new Gson();

        /*Map<String, Object> requestBodyMap = new HashMap<>();
        List<Map<String, String>> parts = List.of(Map.of("text", "Hello"));
        requestBodyMap.put("contents", List.of(Map.of("parts", parts)));*/

        Chat chat = new Chat("Hello");
        String jsonRequest = gson.toJson(chat);

        System.out.println(jsonRequest);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonRequest);


        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    };
}
