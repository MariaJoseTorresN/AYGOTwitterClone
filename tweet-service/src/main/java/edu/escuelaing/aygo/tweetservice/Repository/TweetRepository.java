package edu.escuelaing.aygo.tweetservice.Repository;

import java.time.LocalDate;
import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

import edu.escuelaing.aygo.tweetservice.DTOs.RequestCreateTweet;
import edu.escuelaing.aygo.tweetservice.DTOs.RequestUpdateTweet;
import edu.escuelaing.aygo.tweetservice.Domain.Tweet;
import edu.escuelaing.aygo.tweetservice.Exception.ExistingTweetException;
import edu.escuelaing.aygo.tweetservice.Exception.TweetNotFoundException;
import edu.escuelaing.aygo.tweetservice.Repository.Contracts.ITweetRepository;

public class TweetRepository implements ITweetRepository{

    private DynamoDBMapper dynamoDBMapper;

    private void initDynamoDB() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        dynamoDBMapper = new DynamoDBMapper(client);
    }

    @Override
    public Tweet getTweetById(String tweetId) {
        initDynamoDB();
        Tweet tweet = dynamoDBMapper.load(Tweet.class, tweetId);
        if (tweet == null) {
            throw new TweetNotFoundException("No se encontró un tweet con el Id: " + tweetId);
        }
        return tweet;
    }

    @Override
    public List<Tweet> getAllTweets() {
        initDynamoDB();
        List<Tweet> tweets = dynamoDBMapper.scan(Tweet.class, new DynamoDBScanExpression());
        return tweets;
    }

    @Override
    public Tweet createTweet(RequestCreateTweet newTweet) {
        initDynamoDB();
        Tweet tweet = new Tweet();
        LocalDate actualDate = LocalDate.now();
        String dateString = actualDate.toString();
        String tweetId = newTweet.getUserId() + dateString;
        Tweet searchTweet = dynamoDBMapper.load(Tweet.class, tweetId);
        if (searchTweet != null) {
            throw new ExistingTweetException("Ya existe un tweet con el id: " + tweetId);
        }
        tweet.setTweetId(tweetId);
        tweet.setUserId(newTweet.getUserId());;
        tweet.setTweetMessage(newTweet.getTweetMessage());;
        tweet.setTweetCreated(dateString);;
        dynamoDBMapper.save(tweet);
        return tweet;
    }

    @Override
    public String deleteTweetById(String tweetId) {
        initDynamoDB();
        Tweet tweet = dynamoDBMapper.load(Tweet.class, tweetId);
        if (tweet != null) {
            dynamoDBMapper.delete(tweet);
            return "Tweet eliminado correctamente.";
        } else {
            throw new TweetNotFoundException("No se encontró un tweet con el ID: " + tweetId);
        }
    }

    @Override
    public Tweet updateTweet(RequestUpdateTweet updateTweet) {
        initDynamoDB();
        Tweet modifiedTweet = dynamoDBMapper.load(Tweet.class, updateTweet.getTweetId());
        if (modifiedTweet == null) {
            throw new TweetNotFoundException("No se encontró un tweet con el ID: " + updateTweet.getTweetId());
        }
        dynamoDBMapper.delete(modifiedTweet);
        Tweet tweet = new Tweet();
        LocalDate actualDate = LocalDate.now();
        String dateString = actualDate.toString();
        tweet.setTweetId(updateTweet.getTweetId());
        tweet.setUserId(updateTweet.getUserId());
        tweet.setTweetMessage(updateTweet.getTweetMessage());
        tweet.setTweetCreated(dateString);
        dynamoDBMapper.save(tweet);
        return tweet;
    }
    
}
