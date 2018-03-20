package com.shreytripathi.ir_feedbackmanagement.model;

/**
 * Created by Hp on 20-03-2018.
 */

public class ReplyEvent {

   public int position;
   public Tweet tweet;

    public ReplyEvent(int position, Tweet tweet) {
        this.position = position;
        this.tweet = tweet;
    }

    public int getPosition() {
        return position;
    }

    public Tweet getTweet() {
        return tweet;
    }
}
