package edu.escuelaing.aygo.tweetservice;

import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import edu.escuelaing.aygo.tweetservice.Application.TweetApplication;
import edu.escuelaing.aygo.tweetservice.Application.Contracts.ITweetApplication;
import edu.escuelaing.aygo.tweetservice.DTOs.RequestCreateTweet;
import edu.escuelaing.aygo.tweetservice.DTOs.RequestUpdateTweet;
import edu.escuelaing.aygo.tweetservice.Domain.Tweet;
import edu.escuelaing.aygo.tweetservice.Exception.ExistingTweetException;
import edu.escuelaing.aygo.tweetservice.Exception.TweetNotFoundException;
import edu.escuelaing.aygo.tweetservice.Utils.Converter;
import edu.escuelaing.aygo.tweetservice.Utils.RequestMethod;
import edu.escuelaing.aygo.tweetservice.Utils.RouteMapping;

public class TweetServiceRequest implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    
    ITweetApplication tweetApplication = new TweetApplication();
    private static String jsonBody = null;

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        Method[] methods = this.getClass().getMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(RouteMapping.class)) {
                RouteMapping annotation = method.getAnnotation(RouteMapping.class);
                String requestRoute = annotation.route();
                RequestMethod requestMethod = annotation.method();
                
                if (input.getHttpMethod().equals(requestMethod.name())) {
                    Pattern pattern = Pattern.compile("^" + requestRoute.replaceAll("\\{\\w+\\}", "([^/]+)") + "$");
                    Matcher matcher = pattern.matcher(input.getPath());

                    if (matcher.matches()) {
                        try {
                            return (APIGatewayProxyResponseEvent) method.invoke(this, input, context);
                        } catch (Exception e) {
                            return new APIGatewayProxyResponseEvent().withStatusCode(500).withBody("Ups el servidor fallo, intente en otro momento");
                        }
                    }
                }
            }
        }

        response.setStatusCode(404);
        response.setBody("Método no encontrado");
        return response;
    }

    @RouteMapping(route = "/tweets/{tweetId}", method = RequestMethod.GET)
    public APIGatewayProxyResponseEvent getTweetById(APIGatewayProxyRequestEvent input, Context context) throws Exception {
        try {
            String tweetId = input.getPathParameters().get("tweetId");
            Tweet tweet = tweetApplication.getTweetById(tweetId);
            jsonBody = Converter.convertObToString(tweet, context);
            context.getLogger().log("Consultar tweet por Id:::" + jsonBody);
            return new APIGatewayProxyResponseEvent().withBody(jsonBody).withStatusCode(200).withHeaders(Converter.createHeaders());
        } catch (TweetNotFoundException t) {
            context.getLogger().log("Tweet no encontrado:::" + t.getMessage());
            return new APIGatewayProxyResponseEvent().withBody(t.getMessage()).withStatusCode(404).withHeaders(Converter.createHeaders());
        } catch (Exception e) {
            context.getLogger().log("Error interno del servidor:::" + e.getMessage());
            return new APIGatewayProxyResponseEvent().withBody(e.getMessage()).withStatusCode(500).withHeaders(Converter.createHeaders());
        }
    }

    @RouteMapping(route = "/tweets", method = RequestMethod.GET)
    public APIGatewayProxyResponseEvent getTweets(APIGatewayProxyRequestEvent input, Context context) throws Exception {
        try {
            List<Tweet> tweets = tweetApplication.getAllTweets();
            jsonBody = Converter.convertListOfObjToString(tweets, context);
            context.getLogger().log("Consultar tweets:::" + jsonBody);
            return new APIGatewayProxyResponseEvent().withBody(jsonBody).withStatusCode(200).withHeaders(Converter.createHeaders());
        } catch (Exception e) {
            context.getLogger().log("Error interno del servidor:::" + e.getMessage());
            return new APIGatewayProxyResponseEvent().withBody(e.getMessage()).withStatusCode(500).withHeaders(Converter.createHeaders());
        }
    }

    @RouteMapping(route = "/tweets", method = RequestMethod.POST)
    public APIGatewayProxyResponseEvent createTweet(APIGatewayProxyRequestEvent input, Context context) throws Exception {
        try {
            RequestCreateTweet requestCreateTweet = Converter.convertStringToObj(input.getBody(), context);
            Tweet newTweet = tweetApplication.createTweet(requestCreateTweet);
            jsonBody = Converter.convertObToString(newTweet, context);
            context.getLogger().log("Creación nuevo tweet:::" + jsonBody);
            return new APIGatewayProxyResponseEvent().withBody(jsonBody).withStatusCode(200).withHeaders(Converter.createHeaders());
        } catch (ExistingTweetException et) {
            context.getLogger().log("Error interno del servidor:::" + et.getMessage());
            return new APIGatewayProxyResponseEvent().withBody(et.getMessage()).withStatusCode(400).withHeaders(Converter.createHeaders());
        } catch (Exception e) {
            context.getLogger().log("Error interno del servidor:::" + e.getMessage());
            return new APIGatewayProxyResponseEvent().withBody(e.getMessage()).withStatusCode(500).withHeaders(Converter.createHeaders());
        }
    }

    @RouteMapping(route = "/tweets/{tweetId}", method = RequestMethod.DELETE)
    public APIGatewayProxyResponseEvent deleteTweetById(APIGatewayProxyRequestEvent input, Context context) throws Exception {
        try {
            String tweetId = input.getPathParameters().get("tweetId");
            String deleteResult = tweetApplication.deleteTweetById(tweetId);
            context.getLogger().log("Tweet eliminado:::" + deleteResult);
            return new APIGatewayProxyResponseEvent().withBody(deleteResult).withStatusCode(200).withHeaders(Converter.createHeaders());
        } catch (TweetNotFoundException t) {
            context.getLogger().log("Tweet no encontrado:::" + t.getMessage());
            return new APIGatewayProxyResponseEvent().withBody(t.getMessage()).withStatusCode(404).withHeaders(Converter.createHeaders());
        } catch (Exception e) {
            context.getLogger().log("Error interno del servidor:::" + e.getMessage());
            return new APIGatewayProxyResponseEvent().withBody(e.getMessage()).withStatusCode(500).withHeaders(Converter.createHeaders());
        }
    }

    @RouteMapping(route = "/tweets/{tweetId}", method = RequestMethod.PUT)
    public APIGatewayProxyResponseEvent updateTweet(APIGatewayProxyRequestEvent input, Context context) throws Exception {
        try {
            RequestUpdateTweet requestUpdatedtTweet = Converter.convertStringToObjUp(input.getBody(), context);
            Tweet tweet = tweetApplication.updateTweet(requestUpdatedtTweet);
            jsonBody = Converter.convertObToString(tweet, context);
            context.getLogger().log("Tweet actualizado:::" + jsonBody);
            return new APIGatewayProxyResponseEvent().withBody(jsonBody).withStatusCode(200).withHeaders(Converter.createHeaders());
        } catch (TweetNotFoundException t) {
            context.getLogger().log("Tweet no encontrado:::" + t.getMessage());
            return new APIGatewayProxyResponseEvent().withBody(t.getMessage()).withStatusCode(404).withHeaders(Converter.createHeaders());
        } catch (Exception e) {
            context.getLogger().log("Error interno del servidor:::" + e.getMessage());
            return new APIGatewayProxyResponseEvent().withBody(e.getMessage()).withStatusCode(500).withHeaders(Converter.createHeaders());
        }
    }
}
