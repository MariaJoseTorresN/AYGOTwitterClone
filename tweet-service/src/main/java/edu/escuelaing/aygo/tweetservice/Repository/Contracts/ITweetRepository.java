package edu.escuelaing.aygo.tweetservice.Repository.Contracts;

import java.util.List;

import edu.escuelaing.aygo.tweetservice.DTOs.RequestCreateTweet;
import edu.escuelaing.aygo.tweetservice.DTOs.RequestUpdateTweet;
import edu.escuelaing.aygo.tweetservice.Domain.Tweet;

public interface ITweetRepository {
    
    public Tweet getTweetById(String tweetId);

    public List<Tweet> getAllTweets();

    public Tweet createTweet(RequestCreateTweet newTweet);

    public String deleteTweetById(String tweetId);

    public Tweet updateTweet(RequestUpdateTweet updateTweet);
}
