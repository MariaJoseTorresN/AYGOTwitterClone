package edu.escuelaing.aygo.tweetservice.DTOs;

public class RequestCreateTweet {

    private String userId;

    private String tweetMessage;    

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTweetMessage(String tweetMessage) {
        this.tweetMessage = tweetMessage;
    }

    public String getUserId() {
        return userId;
    }

    public String getTweetMessage() {
        return tweetMessage;
    }
}
