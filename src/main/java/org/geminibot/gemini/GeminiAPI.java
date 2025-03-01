package org.geminibot.gemini;

import com.google.gson.*;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;


import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class GeminiAPI {

    private static Dotenv dotenv = Dotenv.load();
    private static final String API_KEY = dotenv.get("GEMINI_API_KEY");

    //TEMP
    static HashMap<Long,ChatRequest> chats = new HashMap<>();

    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key="+API_KEY;


    public static String sendText(String text,long chatId){

       OkHttpClient client = new OkHttpClient.Builder()
               .connectTimeout(10, TimeUnit.SECONDS)  // Time to establish a connection
               .readTimeout(20, TimeUnit.SECONDS)     // Time to wait for response (increased for large responses)
               .writeTimeout(20, TimeUnit.SECONDS)    // Time to send data
               .retryOnConnectionFailure(true)        // Auto-retry on failures
               .build();
       Gson gson = new Gson();

       if(!chats.containsKey(chatId)){
           chats.put(chatId,new ChatRequest());
       }

        chats.get(chatId).addMessage(text,"user");

        String chatRequest = gson.toJson(chats.get(chatId));

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), chatRequest);


        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .build();

        try {
            Response response = client.newCall(request).execute();

            ChatResponse chatResponse = gson.fromJson(response.body().string(), ChatResponse.class);

            String botResponse = chatResponse.candidates.get(0).content.parts.get(0).text;

            chats.get(chatId).addMessage(botResponse,"model");

            return botResponse;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
