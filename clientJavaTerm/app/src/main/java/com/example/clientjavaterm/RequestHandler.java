package com.example.clientjavaterm;

import com.example.clientjavaterm.converters.BaseConverter;
import com.example.clientjavaterm.entity.Employees;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class RequestHandler {

    private String baseUrl;
    private String urlResource;
    private String httpMethod;
    private String token;

    public RequestHandler(String baseUrl) {
        setBaseUrl(baseUrl);
        //this.urlResource = "auth/signIn";
        //this.httpMethod = "POST";
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUrlResource() {
        return urlResource;
    }

    public void setUrlResource(String urlResource) {
        this.urlResource = urlResource;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        if (!baseUrl.substring(baseUrl.length() - 1).equals("/")) {
            this.baseUrl += "/";
        }
    }

    public void execute(final CallBack callBack, final String object) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String urlS = baseUrl + urlResource;
                    URL url = new URL(urlS);

                    httpMethod = httpMethod.toUpperCase();

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod(httpMethod);
                    connection.setRequestProperty("User-Agent", "clientTerm");
                    connection.setRequestProperty("Accept", "application/json");
                    connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

                    if (!urlResource.equals("auth/signIn")) {
                        connection.setRequestProperty("Authorization", "Bearer " + token);
                    }
                    if (object != null && (httpMethod.equals("PUT") || httpMethod.equals("POST"))) {
                        connection.setDoOutput(true);
                        OutputStream os = connection.getOutputStream();
                        os.write(object.getBytes("UTF-8"));
                        os.close();
                    }

                    if (connection.getResponseCode() != 200 && connection.getResponseCode() != 201) {
                        System.err.println("connection failed");
                        System.out.println("code: " + connection.getResponseCode());
                        callBack.onFail("error");
                    }

                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(connection.getInputStream(), "utf-8"));

                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    callBack.onSuccess(response.toString());
                } catch (Exception e) { //TODO определить исключения
                    e.printStackTrace();
                    callBack.onFail("error " + e.getMessage());
                }
            }
        });
        thread.start();
    }

}
