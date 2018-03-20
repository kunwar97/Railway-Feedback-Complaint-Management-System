package com.shreytripathi.ir_feedbackmanagement.network;


import com.shreytripathi.ir_feedbackmanagement.model.Removed;
import com.shreytripathi.ir_feedbackmanagement.model.Tweet;


import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("data.php")
    Call<ListResponse<Tweet>> getAllTweets(@Query("class") int classType);

    @GET("/twitter/index.php")
    Call<Void> replyToTweet(@Query("tweet_id") String tweetId,
                                @Query("screen_name") String userName,
                                @Query("tweet_reply") String replyMessage);

    @GET("removed.php")
    Call<ListResponse<Removed>> getRemovedTweets();
}
