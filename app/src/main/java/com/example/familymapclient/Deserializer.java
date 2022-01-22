package com.example.familymapclient;

import com.google.gson.Gson;


public class Deserializer {

    public static <T> T deserialize(String value, Class<T> returnType) {
        return (new Gson()).fromJson(value, returnType);
    }


}
