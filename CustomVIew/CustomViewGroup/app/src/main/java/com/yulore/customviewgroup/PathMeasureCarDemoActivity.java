package com.yulore.customviewgroup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yulore.customviewgroup.view.CarView;

public class PathMeasureCarDemoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CarView(this));
    }
}
