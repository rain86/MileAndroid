package com.yulore.customviewgroup;

import android.app.Activity;
import android.os.Bundle;

import com.yulore.customviewgroup.view.SplitView;

public class ExplodeOneActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SplitView(this));
    }
}
