package com.shreytripathi.ir_feedbackmanagement;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by user on 15-Jan-18.
 */

public class Snacky {

    public OnActionListener onActionListener;
    private Activity activity;
    private String actionButtonTitle="";


    public Snacky(Context context){
        activity= (Activity) context;
    }

    public Snacky(Activity activity) {
        this.activity=activity;
    }

    public void show(String message){
       View view= activity.getWindow().getDecorView().getRootView();
        Snackbar snackbar=Snackbar.make(view,message,Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public void setActionButtonTitle(String actionButtonTitle) {
        this.actionButtonTitle = actionButtonTitle;
    }


    @SuppressLint("ResourceAsColor")
    public void showWithActionButtonEnabled(String message, Fragment fragment, int color){
        View view= activity.getWindow().getDecorView().getRootView();
        onActionListener= (OnActionListener) fragment;
        Snackbar snackbar=Snackbar.make(view,message,Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(color);
        snackbar.setAction(actionButtonTitle, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snacky.this.onActionListener.action();
            }
        });
        snackbar.show();
    }

    public interface OnActionListener{

        void action();

    }

}
