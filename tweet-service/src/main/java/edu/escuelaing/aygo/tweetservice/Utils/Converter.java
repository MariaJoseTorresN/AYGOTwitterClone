package edu.escuelaing.aygo.tweetservice.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.escuelaing.aygo.tweetservice.DTOs.RequestCreateTweet;
import edu.escuelaing.aygo.tweetservice.DTOs.RequestUpdateTweet;
import edu.escuelaing.aygo.tweetservice.Domain.Tweet;

public class Converter {
    
    public static Map<String, String> createHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X_amazon-author", "Maria Torres");
        headers.put("X-amazon-apiVersion", "v1");
        return headers;
    }

    public static String convertObToString(Tweet tweet, Context context) {
        String jsonBody = null;
        try {
            jsonBody = new ObjectMapper().writeValueAsString(tweet);
        } catch (JsonProcessingException e) {
            context.getLogger().log("Error al convertir objeto a string:::" + e.getMessage());
        }
        return jsonBody;
    }

    public static RequestCreateTweet convertStringToObj(String jsonBody, Context context) {
        RequestCreateTweet tweet = null;
        try {
            tweet = new ObjectMapper().readValue(jsonBody, RequestCreateTweet.class);
        } catch (JsonProcessingException e) {
            context.getLogger().log("Error al convertir string a objeto:::" + e.getMessage());
        }
        return tweet;
    }

    public static RequestUpdateTweet convertStringToObjUp(String jsonBody, Context context) {
        RequestUpdateTweet tweet = null;
        try {
            tweet = new ObjectMapper().readValue(jsonBody, RequestUpdateTweet.class);
        } catch (JsonProcessingException e) {
            context.getLogger().log("Error al convertir string a objeto:::" + e.getMessage());
        }
        return tweet;
    }

    public static String convertListOfObjToString(List<Tweet> tweets, Context context) {
        String jsonBody = null;
        try {
            jsonBody = new ObjectMapper().writeValueAsString(tweets);
        } catch (JsonProcessingException e) {
            context.getLogger().log("Error while converting obj to string:::" + e.getMessage());
        }
        return jsonBody;
    }
}
