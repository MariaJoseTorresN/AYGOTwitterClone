package edu.escuelaing.aygo.tweetservice.Domain;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "tweet")
public class Tweet {
    
    @DynamoDBHashKey(attributeName = "tweetId")
    private String tweetId;
    
    @DynamoDBAttribute(attributeName = "userId")
    private String userId;
    
    @DynamoDBAttribute(attributeName = "tweetMessage")
    private String tweetMessage;

    @DynamoDBAttribute(attributeName = "tweetCreated")
    private String tweetCreated;

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTweetMessage(String tweetMessage) {
        this.tweetMessage = tweetMessage;
    }

    public void setTweetCreated(String tweetCreated) {
        this.tweetCreated = tweetCreated;
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

    public String getTweetCreated() {
        return tweetCreated;
    }

}
