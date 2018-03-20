package com.shreytripathi.ir_feedbackmanagement.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shreytripathi.ir_feedbackmanagement.R;
import com.shreytripathi.ir_feedbackmanagement.RemovedActivity;
import com.shreytripathi.ir_feedbackmanagement.ReplyDialog;
import com.shreytripathi.ir_feedbackmanagement.model.Removed;
import com.shreytripathi.ir_feedbackmanagement.model.Tweet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hp on 20-03-2018.
 */

public class RemovedTweetsAdapter extends RecyclerView.Adapter<RemovedTweetsAdapter.RemovedTweetsHolder>{
    private RemovedActivity activity;
    private List<Removed> tweets;

    public RemovedTweetsAdapter(List<Removed> tweets, RemovedActivity activity) {

        this.tweets = tweets;
        this.activity = activity;
    }

    @Override
    public RemovedTweetsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.removed_tweet_row, parent, false);
        return new RemovedTweetsHolder(view);
    }

    @Override
    public void onBindViewHolder(RemovedTweetsHolder holder, final int position) {
        final Removed tweet = tweets.get(position);
        holder.senderName.setText("" + tweet.getUserName());
        holder.tweetMessage.setText("" + tweet.getTweet());
        holder.predictionStatus.setText("" + tweet.getPredictionType());
        holder.tweetAnswer.setText(""+tweet.getAnswer());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTweet(tweet);
            }
        });


    }

    private void openTweet(Removed removed){
        String url = "https://twitter.com/"+removed.getUserName()+"/status/"+removed.getTweetId();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        activity.startActivity(intent);
    }


    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public class RemovedTweetsHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tweet_message)
        TextView tweetMessage;

        @BindView(R.id.user_name)
        TextView senderName;

        @BindView(R.id.prediction_status)
        TextView predictionStatus;

        @BindView(R.id.tweet_answer)
        TextView tweetAnswer;

        @BindView(R.id.card)
        CardView cardView;


        public RemovedTweetsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
