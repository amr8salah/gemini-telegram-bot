package org.example;

import com.google.gson.*;
import com.squareup.okhttp.*;
import io.github.cdimascio.dotenv.Dotenv;


import java.io.IOException;
import java.util.HashMap;

public class GeminiAPI {

    private static Dotenv dotenv = Dotenv.load();
    private static final String API_KEY = dotenv.get("GEMINI_API_KEY");

    //TEMP
    static HashMap<String,ChatRequest> chats = new HashMap<>();

    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key="+API_KEY;


    public static String sendText(String text,String userId){

       OkHttpClient client = new OkHttpClient();
       Gson gson = new Gson();

       if(!chats.containsKey(userId)){
           chats.put(userId,new ChatRequest());
       }

        chats.get(userId).addMessage(text,"user");

        String chatRequest = gson.toJson(chats.get(userId));

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), chatRequest);


        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .build();

        try {
            Response response = client.newCall(request).execute();

            ChatResponse chatResponse = gson.fromJson(response.body().string(), ChatResponse.class);

            String botResponse = chatResponse.candidates.get(0).content.parts.get(0).text;

            chats.get(userId).addMessage(botResponse,"model");

            return botResponse;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    };

    public static void printChat() {
        Gson gson = new Gson();
        String json = gson.toJson(chats);
        System.out.println(json);
    }
}
