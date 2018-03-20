package com.shreytripathi.ir_feedbackmanagement;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mehdi.sakout.fancybuttons.FancyButton;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fancy_1)
    FancyButton fancy1;

    @BindView(R.id.fancy_2)
    FancyButton fancy2;

    int[] radius1 = {85, 0, 85, 0};
    int[] radius2 = {0, 85, 0, 85};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fancy1.setRadius(radius1);
        fancy2.setRadius(radius2);
        fancy1.setEnabled(false);
        replaceFragment(new FeedbackFragment(), FeedbackFragment.class.getName());
    }



    @OnClick(R.id.removed_tweets)
    void openRemovedTweets(){
        startActivity(new Intent(this, RemovedActivity.class));
    }


    @OnClick({R.id.fancy_1, R.id.fancy_2})
     void handleFancyButtons(){

        if (!fancy1.isEnabled()){
            fancy1.setEnabled(true);
            fancy2.setEnabled(false);
            replaceFragment(new EmergencyFragment(), EmergencyFragment.class.getName());
        }   else if (!fancy2.isEnabled()){
            fancy1.setEnabled(false);
            fancy2.setEnabled(true);
            replaceFragment(new FeedbackFragment(), FeedbackFragment.class.getName());


        }

    }

    protected void replaceFragment(final Fragment fragment, final String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_left);
        List<Fragment> fragments = manager.getFragments();
        if (fragments != null) {
            for (Fragment f : fragments) {
                if (f != null) {
                    transaction.hide(f);
                }
            }
        }
        Fragment f = manager.findFragmentByTag(tag);
        if (f == null) {
            f = fragment;
            transaction.add(R.id.container, f, tag);
        } else {
            transaction.show(f);
        }
        transaction.commit();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
