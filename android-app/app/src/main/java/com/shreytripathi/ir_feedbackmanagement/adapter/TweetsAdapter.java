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

import com.shreytripathi.ir_feedbackmanagement.FeedbackFragment;
import com.shreytripathi.ir_feedbackmanagement.R;
import com.shreytripathi.ir_feedbackmanagement.ReplyDialog;
import com.shreytripathi.ir_feedbackmanagement.model.Removed;
import com.shreytripathi.ir_feedbackmanagement.model.Tweet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hp on 20-03-2018.
 */

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.TweetsHolder> {


    private Fragment fragment;
    private List<Tweet> tweets;

    public TweetsAdapter(List<Tweet> tweets, Fragment fragment) {

        this.tweets = tweets;
        this.fragment = fragment;
    }

    @Override
    public TweetsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.tweet_row, parent, false);
        return new TweetsHolder(view);
    }

    @Override
    public void onBindViewHolder(TweetsHolder holder, final int position) {
        final Tweet tweet = tweets.get(position);
        holder.senderName.setText("" + tweet.getUserName());
        holder.tweetMessage.setText("" + tweet.getTweet());
        holder.predictionStatus.setText("" + tweet.getPredictionType());
        if (tweet.getReplyStatus() == 0) {
            holder.replyButton.setVisibility(View.VISIBLE);
            holder.replyStatus.setVisibility(View.GONE);
            holder.replyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openReplyDialog(position);
                }
            });

        } else {
            holder.replyButton.setVisibility(View.GONE);
            holder.replyStatus.setVisibility(View.VISIBLE);
            holder.replyStatus.setText("Alread");

        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTweet(tweet);
            }
        });

    }

    private void openTweet(Tweet removed){
        String url = "https://twitter.com/"+removed.getUserName()+"/status/"+removed.getTweetId();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        fragment.startActivity(intent);
    }

    private void openReplyDialog(int pos){
        FragmentManager manager = fragment.getChildFragmentManager();
        ReplyDialog dialog = ReplyDialog.newInstance(tweets.get(pos), pos);
        dialog.show(manager,"reply");
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public class TweetsHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tweet_message)
        TextView tweetMessage;

        @BindView(R.id.user_name)
        TextView senderName;

        @BindView(R.id.prediction_status)
        TextView predictionStatus;

        @BindView(R.id.reply_status)
        TextView replyStatus;

        @BindView(R.id.reply_button)
        Button replyButton;

        @BindView(R.id.card)
        CardView cardView;

        public TweetsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
