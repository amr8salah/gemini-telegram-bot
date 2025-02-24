package org.example;

import java.util.ArrayList;
import java.util.Map;


public class ChatRequest {
    private ArrayList<Message> contents;

    ChatRequest(){
        contents = new ArrayList<>();
    }

    void addMessage(String text, String role){
        Message message = new Message(text, role);
        contents.add(message);
    }

}
class Message{
    String role;
    ArrayList<Map<String,String>> parts;

    Message(String text, String role){
        parts = new ArrayList<>();
        this.role = role;

        parts.add(Map.of("text", text));
    }
}
