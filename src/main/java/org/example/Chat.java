package org.example;

import java.util.ArrayList;
import java.util.Map;


public class Chat {
    private ArrayList<Message> contents;

    Chat(String text){
        contents = new ArrayList<>();

    }

}
class Message{
    String role;
    ArrayList<Map<String,String>> parts;

    void setText(String role, String text){
        parts = new ArrayList<>();
        this.role = role;

        parts.add(Map.of("text", text));
    }
}
