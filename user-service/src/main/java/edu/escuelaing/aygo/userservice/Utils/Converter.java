package edu.escuelaing.aygo.userservice.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.escuelaing.aygo.userservice.DTOs.RequestCreateUser;
import edu.escuelaing.aygo.userservice.DTOs.RequestUpdateUser;
import edu.escuelaing.aygo.userservice.Domain.User;

public class Converter {
    
    public static Map<String, String> createHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X_amazon-author", "Maria Torres");
        headers.put("X-amazon-apiVersion", "v1");
        return headers;
    }

    public static String convertObToString(User user, Context context) {
        String jsonBody = null;
        try {
            jsonBody = new ObjectMapper().writeValueAsString(user);
        } catch (JsonProcessingException e) {
            context.getLogger().log("Error al convertir objeto a string:::" + e.getMessage());
        }
        return jsonBody;
    }

    public static RequestCreateUser convertStringToObj(String jsonBody, Context context) {
        RequestCreateUser user = null;
        try {
            user = new ObjectMapper().readValue(jsonBody, RequestCreateUser.class);
        } catch (JsonProcessingException e) {
            context.getLogger().log("Error al convertir string a objeto:::" + e.getMessage());
        }
        return user;
    }

    public static RequestUpdateUser convertStringToObjUp(String jsonBody, Context context) {
        RequestUpdateUser user = null;
        try {
            user = new ObjectMapper().readValue(jsonBody, RequestUpdateUser.class);
        } catch (JsonProcessingException e) {
            context.getLogger().log("Error al convertir string a objeto:::" + e.getMessage());
        }
        return user;
    }

    public static String convertListOfObjToString(List<User> users, Context context) {
        String jsonBody = null;
        try {
            jsonBody = new ObjectMapper().writeValueAsString(users);
        } catch (JsonProcessingException e) {
            context.getLogger().log("Error while converting obj to string:::" + e.getMessage());
        }
        return jsonBody;
    }
}
