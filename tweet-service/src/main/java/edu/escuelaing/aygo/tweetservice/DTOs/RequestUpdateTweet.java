package edu.escuelaing.aygo.tweetservice.DTOs;

public class RequestUpdateTweet {

    private String tweetId;

    private String userId;

    private String tweetMessage;    

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTweetMessage(String tweetMessage) {
        this.tweetMessage = tweetMessage;
    }

    public String getTweetId() {
        return tweetId;
    }

    public String getUserId() {
        return userId;
    }

    public String getTweetMessage() {
        return tweetMessage;
    }
}
