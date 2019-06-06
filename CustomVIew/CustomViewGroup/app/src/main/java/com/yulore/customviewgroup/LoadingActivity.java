package com.yulore.customviewgroup;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.yulore.customviewgroup.view.PathMeasureView;

/**
 * @author mile
 * @title: LoadingActivity
 * @projectName CustomViewGroup
 * @description: TODO
 * @date 2019-06-0617:26
 */
public class LoadingActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new PathMeasureView(this));
    }
}
