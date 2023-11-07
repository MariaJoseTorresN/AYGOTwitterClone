package edu.escuelaing.aygo.tweetservice.Application.Contracts;

import java.util.List;

import edu.escuelaing.aygo.tweetservice.DTOs.RequestCreateTweet;
import edu.escuelaing.aygo.tweetservice.DTOs.RequestUpdateTweet;
import edu.escuelaing.aygo.tweetservice.Domain.Tweet;

public interface ITweetApplication {
    
    public Tweet getTweetById(String tweetId) throws Exception;

    public List<Tweet> getAllTweets() throws Exception;

    public Tweet createTweet(RequestCreateTweet newTweet) throws Exception;

    public String deleteTweetById(String tweetId) throws Exception;

    public Tweet updateTweet(RequestUpdateTweet updateTweet) throws Exception;
}
