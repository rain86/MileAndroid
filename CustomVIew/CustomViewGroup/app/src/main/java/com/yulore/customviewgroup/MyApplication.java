package com.yulore.customviewgroup;

import android.app.Application;

import com.yulore.customviewgroup.util.UIUtils;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UIUtils.getInstance(this);
    }
}
