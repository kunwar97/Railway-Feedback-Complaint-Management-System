package com.shreytripathi.ir_feedbackmanagement;

import android.app.Application;
import android.content.Context;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Hp on 20-03-2018.
 */

public class App extends Application {

    public static Context sContext;


    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/SignikaNegative-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        sContext=getApplicationContext();
    }


}
