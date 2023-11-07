package edu.escuelaing.aygo.tweetservice.Application;

import java.util.List;

import edu.escuelaing.aygo.tweetservice.Application.Contracts.ITweetApplication;
import edu.escuelaing.aygo.tweetservice.DTOs.RequestCreateTweet;
import edu.escuelaing.aygo.tweetservice.DTOs.RequestUpdateTweet;
import edu.escuelaing.aygo.tweetservice.Domain.Tweet;
import edu.escuelaing.aygo.tweetservice.Repository.TweetRepository;
import edu.escuelaing.aygo.tweetservice.Repository.Contracts.ITweetRepository;

public class TweetApplication implements ITweetApplication{

    private ITweetRepository tweetRepository = new TweetRepository();
    public TweetApplication() {}

    @Override
    public Tweet getTweetById(String tweetId) throws Exception {
        try {
            return tweetRepository.getTweetById(tweetId);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<Tweet> getAllTweets() throws Exception {
        try {
            return tweetRepository.getAllTweets();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Tweet createTweet(RequestCreateTweet newTweet) throws Exception {
        try {
            return tweetRepository.createTweet(newTweet);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public String deleteTweetById(String tweetId) throws Exception {
        try {
            return tweetRepository.deleteTweetById(tweetId);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Tweet updateTweet(RequestUpdateTweet updateTweet) throws Exception {
        try {
            return tweetRepository.updateTweet(updateTweet);
        } catch (Exception e) {
            throw e;
        }
    }
    
}
