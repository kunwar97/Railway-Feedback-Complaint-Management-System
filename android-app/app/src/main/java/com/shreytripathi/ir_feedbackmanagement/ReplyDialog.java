package com.shreytripathi.ir_feedbackmanagement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shreytripathi.ir_feedbackmanagement.model.ReplyEvent;
import com.shreytripathi.ir_feedbackmanagement.model.Tweet;
import com.shreytripathi.ir_feedbackmanagement.network.ApiClient;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hp on 20-03-2018.
 */

public class ReplyDialog extends DialogFragment {


    public ReplyDialog() {
    }

    private static final String TWEET = "tweet";
    private static final String TWEET_POSITION = "tweet_id";
    private Tweet tweet;

    @BindView(R.id.reply_message)
    EditText replyMessage;

    @BindView(R.id.tweet)
    TextView tweetEdit;

    public static ReplyDialog newInstance(Tweet tweet, int position) {
        ReplyDialog dialog = new ReplyDialog();
        Bundle bundle = new Bundle();
        bundle.putString(TWEET, new Gson().toJson(tweet));
        bundle.putInt(TWEET_POSITION, position);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_reply, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String tweetText = getArguments().getString(TWEET);


        if (tweetText != null) {
            tweet = new Gson().fromJson(tweetText, Tweet.class);
            Log.d("kunwar", "" + tweet.getUserName());
            tweetEdit.setText(""+tweet.getTweet());
        } else {
            dismiss();
        }
    }

    @OnClick(R.id.send)
    void doSend() {
        sendReply();
    }


    private void sendReply() {
        if (tweet != null) {

            String message = replyMessage.getText().toString().trim();
            if (message != null) {

                ApiClient
                        .getApiClient()
                        .replyToTweet(
                                tweet.getTweetId(),
                                tweet.getUserName(),
                                message

                        )
                        .enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    new Snacky(ReplyDialog.this.getActivity()).show("Tweeted Successfully");
                                    EventBus.getDefault().post(new ReplyEvent(getArguments().getInt(TWEET_POSITION), tweet));
                                    dismiss();
                                } else {
                                    new Snacky(ReplyDialog.this.getActivity()).show("Some Error Occured !");
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                new Snacky(ReplyDialog.this.getActivity()).show("Some Error Occured !");

                            }
                        });

            } else {

                new Snacky(this.getActivity()).show("Please Enter Reply Message");
            }
        }

    }



}
