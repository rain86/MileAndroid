package com.yulore.customviewgroup;

import android.app.Activity;
import android.os.Bundle;

import com.yulore.customviewgroup.view.SplashView;

/**
 * @author mile
 * @title: OpeningAnimationActivity
 * @projectName CustomViewGroup
 * @description: 开场动画
 * @date 2019-06-0616:57
 */
public class OpeningAnimationActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SplashView(this));
    }
}
