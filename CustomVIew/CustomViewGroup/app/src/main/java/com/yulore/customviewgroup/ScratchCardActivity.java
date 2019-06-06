package com.yulore.customviewgroup;

import android.app.Activity;
import android.os.Bundle;

import com.yulore.customviewgroup.view.ScratchCardView;

/**
 * @author mile
 * @title: ScratchCardActivity
 * @projectName CustomViewGroup
 * @description: TODO
 * @date 2019-06-0617:21
 */
public class ScratchCardActivity extends Activity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ScratchCardView(this));
    }
}
