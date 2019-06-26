package com.yulore.customviewgroup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.explode_one).setOnClickListener(this);
        findViewById(R.id.explode_two).setOnClickListener(this);
        findViewById(R.id.opening_animation).setOnClickListener(this);
        findViewById(R.id.other).setOnClickListener(this);
        findViewById(R.id.qq_Bubble).setOnClickListener(this);
        findViewById(R.id.scratch_card).setOnClickListener(this);
        findViewById(R.id.loading).setOnClickListener(this);
        findViewById(R.id.car_view).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.explode_one:
                intent = new Intent(this,ExplodeOneActivity.class);
                break;
            case R.id.explode_two:
                intent = new Intent(this,ExplodeTwoActivity.class);
                break;
            case R.id.opening_animation:
                intent = new Intent(this,OpeningAnimationActivity.class);
                break;
            case R.id.other:
                intent = new Intent(this,OtherActivity.class);
                break;
            case R.id.qq_Bubble:
                intent = new Intent(this,QQBubbleActivity.class);
                break;
            case R.id.scratch_card:
                intent = new Intent(this,ScratchCardActivity.class);
                break;
            case R.id.loading:
                intent = new Intent(this,LoadingActivity.class);
                break;
            case R.id.car_view:
                intent = new Intent(this,PathMeasureCarDemoActivity.class);
                break;
        }
        if (intent != null){
            startActivity(intent);
            intent = null;
        }
    }
}
