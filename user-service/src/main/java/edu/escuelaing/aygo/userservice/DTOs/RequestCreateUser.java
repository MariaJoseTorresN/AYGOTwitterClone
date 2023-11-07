package edu.escuelaing.aygo.userservice.DTOs;

public class RequestCreateUser {
    
    private String userName;

    private String userEmail;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

}

