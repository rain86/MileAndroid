package com.yulore.customviewgroup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.yulore.customviewgroup.util.UIUtils;
import com.yulore.customviewgroup.util.ViewCalculateUtil;
import com.yulore.customviewgroup.view.CustomRippleAnimation;


public class CustomRippleAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIUtils.getInstance(this);
        setContentView(R.layout.custom_ripple_animation);
        ImageView img = findViewById(R.id.button_img);
        ViewCalculateUtil.setViewLayoutParam(img,200,200,0,0,0,0,true);
        final CustomRippleAnimation customRippleAnimation = findViewById(R.id.custom_ripple_animation);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customRippleAnimation.isAnimationRunning()){
                    customRippleAnimation.stopRippleAnimation();

                }else {
                    customRippleAnimation.startRippleAnimation();
                }
            }
        });
    }
}
