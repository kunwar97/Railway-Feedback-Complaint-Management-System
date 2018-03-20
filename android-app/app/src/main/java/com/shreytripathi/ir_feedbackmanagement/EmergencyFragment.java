package com.shreytripathi.ir_feedbackmanagement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.shreytripathi.ir_feedbackmanagement.adapter.TweetsAdapter;
import com.shreytripathi.ir_feedbackmanagement.model.ReplyEvent;
import com.shreytripathi.ir_feedbackmanagement.model.Tweet;
import com.shreytripathi.ir_feedbackmanagement.network.ApiClient;
import com.shreytripathi.ir_feedbackmanagement.network.ListResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hp on 20-03-2018.
 */

public class EmergencyFragment extends Fragment {


    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.error)
    TextView errorView;

    private List<Tweet> tweets = new ArrayList<>();
    private TweetsAdapter adapter;

    private static final int CLASS_TYPE = 1;

    public EmergencyFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_emergency, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new TweetsAdapter(tweets, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(adapter);
        fetchTweets();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTweets();
            }
        });
    }



    private void fetchTweets(){
        refreshLayout.setRefreshing(true);
        errorView.setVisibility(View.VISIBLE);
        ApiClient.getApiClient().getAllTweets(CLASS_TYPE)
                .enqueue(new ApiClient.Callback<ListResponse<Tweet>>() {
                    @Override
                    public void success(ListResponse<Tweet> response) {
                        if (response.getData().size()!=0){
                            tweets.clear();
                            for (Tweet tweet : response.getData()){
                                if (tweet.getReplyStatus()==0){
                                    tweets.add(tweet);
                                }
                            }
                            adapter.notifyDataSetChanged();
                            refreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void failure(Error error) {
                        refreshLayout.setRefreshing(false);
                        errorView.setVisibility(View.VISIBLE);
                        Toast.makeText(EmergencyFragment.this.getActivity(), "Please Check Your Connection", Toast.LENGTH_SHORT).show();
                        Log.d("kunwar", ""+error.getMessage());
                    }
                });


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ReplyEvent event) {
        Log.d("kunwar", "REC"+event.getPosition());
        Log.d("kunwar", "REC"+event.getTweet().getUserName());
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
