package com.example.familymapclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import RequestAndResponse.AllEventsResponse;
import RequestAndResponse.ClearResponse;
import RequestAndResponse.EventResponse;
import RequestAndResponse.LoginRequest;
import RequestAndResponse.LoginResponse;
import RequestAndResponse.PersonResponse;
import RequestAndResponse.RegisterRequest;
import RequestAndResponse.RegisterResponse;

public class ServerProxy {

    private DataCache dataCache = DataCache.getInstance();

    public RegisterResponse register(RegisterRequest registerRequest) {
        try {
            URL url = new URL("http://" + dataCache.getServerHost() + ":" + dataCache.getServerPort() + "/user/register");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.addRequestProperty("Accept", "application/json");
            http.connect();

            Serializer serializer = new Serializer();
            String reqData = serializer.serializeRegister(registerRequest);
            OutputStream reqBody = http.getOutputStream();
            writeString(reqData, reqBody);
            reqBody.close();
            RegisterResponse response = new RegisterResponse();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                Deserializer deserializer = new Deserializer();
                response = deserializer.deserialize(respData, RegisterResponse.class);
                System.out.println("Register successful");

                return response;

            }
            else {

                response.setSuccess(false);
                return response;

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        try {
            URL url = new URL("http://" + dataCache.getServerHost() + ":" + dataCache.getServerPort() + "/user/login");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.addRequestProperty("Accept", "application/json");
            http.connect();

            Serializer serializer = new Serializer();
            String reqData = serializer.serializeLogin(loginRequest);
            OutputStream reqBody = http.getOutputStream();
            writeString(reqData, reqBody);
            reqBody.close();

            LoginResponse response = new LoginResponse();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                Deserializer deserializer = new Deserializer();
                response = deserializer.deserialize(respData, LoginResponse.class);
                System.out.println("Login successful");

                return response;

            } else {
                response.setSuccess(false);
                return response;
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public PersonResponse getPeople(String authToken) {
        try {
            URL url = new URL("http://" + dataCache.getServerHost() + ":" + dataCache.getServerPort() + "/person");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(false);
            http.addRequestProperty("Authorization", authToken);
            http.addRequestProperty("Accept", "application/json");
            http.connect();
            PersonResponse response = new PersonResponse();
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                System.out.println(respData);
                Deserializer deserializer = new Deserializer();
                response = deserializer.deserialize(respData, PersonResponse.class);
                return response;

            } else {
                response.setSuccess(false);
                System.out.println("ERROR: " + http.getResponseMessage());
                return response;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    public AllEventsResponse getEvents(String authToken) {
        try {
            URL url = new URL("http://" + dataCache.getServerHost() + ":" + dataCache.getServerPort() + "/event");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(false);
            http.addRequestProperty("Authorization", authToken);
            http.addRequestProperty("Accept", "application/json");
            http.connect();
            AllEventsResponse response = new AllEventsResponse();
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                System.out.println(respData);
                Deserializer deserializer = new Deserializer();
                response = deserializer.deserialize(respData, AllEventsResponse.class);

                return response;

            } else {
                response.setSuccess(false);
                System.out.println("ERROR: " + http.getResponseMessage());
                return response;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}



