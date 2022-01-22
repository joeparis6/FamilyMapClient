package com.example.familymapclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import RequestAndResponse.LoginRequest;
import RequestAndResponse.RegisterRequest;
import RequestAndResponse.ServiceResponse;

public class Serializer {

    public String serializeRegister(RegisterRequest request) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(request);
        System.out.println("JSON String: " + jsonString);
        return jsonString;
    }

    public String serializeLogin(LoginRequest request) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(request);
        System.out.println("JSON String: " + jsonString);
        return jsonString;
    }
}
