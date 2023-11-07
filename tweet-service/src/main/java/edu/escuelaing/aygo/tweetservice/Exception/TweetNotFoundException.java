package edu.escuelaing.aygo.tweetservice.Exception;

public class TweetNotFoundException extends RuntimeException {
    public TweetNotFoundException(String msg) {
        super(msg);
    }
}
