package com.shreytripathi.ir_feedbackmanagement;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.shreytripathi.ir_feedbackmanagement.adapter.RemovedTweetsAdapter;
import com.shreytripathi.ir_feedbackmanagement.adapter.TweetsAdapter;
import com.shreytripathi.ir_feedbackmanagement.model.Removed;
import com.shreytripathi.ir_feedbackmanagement.model.Tweet;
import com.shreytripathi.ir_feedbackmanagement.network.ApiClient;
import com.shreytripathi.ir_feedbackmanagement.network.ListResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RemovedActivity extends AppCompatActivity {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.removed_tweets)
    TextView replied;

    @BindView(R.id.error)
    TextView errorView;

    private List<Removed> removedTweets = new ArrayList<>();
    private RemovedTweetsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_removed);
        ButterKnife.bind(this);
        replied.setVisibility(View.GONE);
        adapter = new RemovedTweetsAdapter(removedTweets, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        fetchRemovedTweets();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchRemovedTweets();
            }
        });
    }


    private void fetchRemovedTweets() {
        refreshLayout.setRefreshing(true);
        errorView.setVisibility(View.GONE);
        ApiClient
                .getApiClient()
                .getRemovedTweets()
                .enqueue(new ApiClient.Callback<ListResponse<Removed>>() {
                    @Override
                    public void success(ListResponse<Removed> response) {
                        if (response.getData() != null) {
                            removedTweets.clear();
                            removedTweets.addAll(response.getData());
                            adapter.notifyDataSetChanged();
                            refreshLayout.setRefreshing(false);
                        } else {
                            refreshLayout.setRefreshing(false);
                            errorView.setVisibility(View.VISIBLE);
                            new Snacky(RemovedActivity.this).show("No Tweets Found !");
                        }
                    }

                    @Override
                    public void failure(Error error) {
                        refreshLayout.setRefreshing(false);
                        errorView.setVisibility(View.VISIBLE);
                        Toast.makeText(RemovedActivity.this, "Some Error Occured !", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
