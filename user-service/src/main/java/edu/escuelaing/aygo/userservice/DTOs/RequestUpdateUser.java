package edu.escuelaing.aygo.userservice.DTOs;

public class RequestUpdateUser {
    
    private String userUpdateId;

    private String userUpdateName;

    private String userUpdateEmail;

    public void setUserId(String userUpdateId) {
        this.userUpdateId = userUpdateId;
    }

    public void setUserName(String userUpdateName) {
        this.userUpdateName = userUpdateName;
    }

    public void setUserEmail(String userUpdateEmail) {
        this.userUpdateEmail = userUpdateEmail;
    }

    public String getUserId() {
        return userUpdateId;
    }

    public String getUserName() {
        return userUpdateName;
    }

    public String getUserEmail() {
        return userUpdateEmail;
    }
    
}
