package com.shreytripathi.ir_feedbackmanagement.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Hp on 20-03-2018.
 */

public class Removed {

    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("tweet")
    private String tweet;

    @Expose
    @SerializedName("username")
    private String userName;

    @Expose
    @SerializedName("prediction")
    private int prediction;

    @Expose
    @SerializedName("tweet_id")
    private String tweetId;

    @Expose
    @SerializedName("reply_status")
    private int replyStatus;

    @Expose
    @SerializedName("ans")
    private String answer;

    public String getAnswer() {
        return answer;
    }

    public int getId() {
        return id;
    }

    public String getTweet() {
        return tweet;
    }

    public String getUserName() {
        return userName;
    }

    public String getPredictionType() {
        if (prediction == 0)
            return "Feedback";
        else {
            return "Emergency";
        }
    }

    public String getTweetId() {
        return tweetId;
    }

    public int getReplyStatus() {
        return replyStatus;
    }
}
