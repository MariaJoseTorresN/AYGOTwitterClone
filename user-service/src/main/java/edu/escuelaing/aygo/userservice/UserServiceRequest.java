package edu.escuelaing.aygo.userservice;

import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import edu.escuelaing.aygo.userservice.Application.UserApplication;
import edu.escuelaing.aygo.userservice.Application.Contracts.IUserApplication;
import edu.escuelaing.aygo.userservice.DTOs.RequestCreateUser;
import edu.escuelaing.aygo.userservice.DTOs.RequestUpdateUser;
import edu.escuelaing.aygo.userservice.Domain.User;
import edu.escuelaing.aygo.userservice.Exception.ExistingUserException;
import edu.escuelaing.aygo.userservice.Exception.UserNotFoundException;
import edu.escuelaing.aygo.userservice.Utils.Converter;
import edu.escuelaing.aygo.userservice.Utils.RequestMethod;
import edu.escuelaing.aygo.userservice.Utils.RouteMapping;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;

public class UserServiceRequest implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    IUserApplication userApplication = new UserApplication();
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

    @RouteMapping(route = "/users/{userId}", method = RequestMethod.GET)
    public APIGatewayProxyResponseEvent getUserById(APIGatewayProxyRequestEvent input, Context context) throws Exception {
        try {
            String userId = input.getPathParameters().get("userId");
            User user = userApplication.getUserById(userId);
            jsonBody = Converter.convertObToString(user, context);
            context.getLogger().log("Consultar usuario por Id:::" + jsonBody);
            return new APIGatewayProxyResponseEvent().withBody(jsonBody).withStatusCode(200).withHeaders(Converter.createHeaders());
        } catch (UserNotFoundException u) {
            context.getLogger().log("Usuario no encontrado:::" + u.getMessage());
            return new APIGatewayProxyResponseEvent().withBody(u.getMessage()).withStatusCode(404).withHeaders(Converter.createHeaders());
        } catch (Exception e) {
            context.getLogger().log("Error interno del servidor:::" + e.getMessage());
            return new APIGatewayProxyResponseEvent().withBody(e.getMessage()).withStatusCode(500).withHeaders(Converter.createHeaders());
        }
    }

    @RouteMapping(route = "/users", method = RequestMethod.GET)
    public APIGatewayProxyResponseEvent getUsers(APIGatewayProxyRequestEvent input, Context context) throws Exception {
        try {
            List<User> users = userApplication.getAllUsers();
            jsonBody = Converter.convertListOfObjToString(users, context);
            context.getLogger().log("Consultar usuarios:::" + jsonBody);
            return new APIGatewayProxyResponseEvent().withBody(jsonBody).withStatusCode(200).withHeaders(Converter.createHeaders());
        } catch (Exception e) {
            context.getLogger().log("Error interno del servidor:::" + e.getMessage());
            return new APIGatewayProxyResponseEvent().withBody(e.getMessage()).withStatusCode(500).withHeaders(Converter.createHeaders());
        }
    }

    @RouteMapping(route = "/users", method = RequestMethod.POST)
    public APIGatewayProxyResponseEvent createUser(APIGatewayProxyRequestEvent input, Context context) throws Exception {
        try {
            RequestCreateUser requestCreateUser = Converter.convertStringToObj(input.getBody(), context);
            User newUser = userApplication.createUser(requestCreateUser);
            jsonBody = Converter.convertObToString(newUser, context);
            context.getLogger().log("Creación nuevo usuario:::" + jsonBody);
            return new APIGatewayProxyResponseEvent().withBody(jsonBody).withStatusCode(200).withHeaders(Converter.createHeaders());
        } catch (ExistingUserException eu) {
            context.getLogger().log("Error interno del servidor:::" + eu.getMessage());
            return new APIGatewayProxyResponseEvent().withBody(eu.getMessage()).withStatusCode(400).withHeaders(Converter.createHeaders());
        } catch (Exception e) {
            context.getLogger().log("Error interno del servidor:::" + e.getMessage());
            return new APIGatewayProxyResponseEvent().withBody(e.getMessage()).withStatusCode(500).withHeaders(Converter.createHeaders());
        }
    }

    @RouteMapping(route = "/users/{userId}", method = RequestMethod.DELETE)
    public APIGatewayProxyResponseEvent deleteUserById(APIGatewayProxyRequestEvent input, Context context) throws Exception {
        try {
            String userId = input.getPathParameters().get("userId");
            String deleteResult = userApplication.deleteUserById(userId);
            context.getLogger().log("Usuario eliminado:::" + deleteResult);
            return new APIGatewayProxyResponseEvent().withBody(deleteResult).withStatusCode(200).withHeaders(Converter.createHeaders());
        } catch (UserNotFoundException u) {
            context.getLogger().log("Usuario no encontrado:::" + u.getMessage());
            return new APIGatewayProxyResponseEvent().withBody(u.getMessage()).withStatusCode(404).withHeaders(Converter.createHeaders());
        } catch (Exception e) {
            context.getLogger().log("Error interno del servidor:::" + e.getMessage());
            return new APIGatewayProxyResponseEvent().withBody(e.getMessage()).withStatusCode(500).withHeaders(Converter.createHeaders());
        }
    }

    @RouteMapping(route = "/users/{userId}", method = RequestMethod.PUT)
    public APIGatewayProxyResponseEvent updateUser(APIGatewayProxyRequestEvent input, Context context) throws Exception {
        try {
            RequestUpdateUser requestUpdatedUser = Converter.convertStringToObjUp(input.getBody(), context);
            User user = userApplication.updateUser(requestUpdatedUser);
            jsonBody = Converter.convertObToString(user, context);
            context.getLogger().log("Usuario actualizado:::" + jsonBody);
            return new APIGatewayProxyResponseEvent().withBody(jsonBody).withStatusCode(200).withHeaders(Converter.createHeaders());
        } catch (UserNotFoundException u) {
            context.getLogger().log("Usuario no encontrado:::" + u.getMessage());
            return new APIGatewayProxyResponseEvent().withBody(u.getMessage()).withStatusCode(404).withHeaders(Converter.createHeaders());
        } catch (Exception e) {
            context.getLogger().log("Error interno del servidor:::" + e.getMessage());
            return new APIGatewayProxyResponseEvent().withBody(e.getMessage()).withStatusCode(500).withHeaders(Converter.createHeaders());
        }
    }
    
}
