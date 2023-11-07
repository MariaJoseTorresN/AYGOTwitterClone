package edu.escuelaing.aygo.tweetservice.Exception;

public class ExistingTweetException extends RuntimeException{
    public ExistingTweetException(String msg) {
        super(msg);
    }
}
