package com.yulore.customviewgroup;

import android.app.Activity;
import android.graphics.BlurMaskFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yulore.customviewgroup.view.Explosion.ClickCallback;
import com.yulore.customviewgroup.view.Explosion.ExplosionField;
import com.yulore.customviewgroup.view.Explosion.FallingParticleFactory;

public class ExplodeTwoActivity extends Activity {


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explode_two);

        ExplosionField explosionField = new ExplosionField(this, new FallingParticleFactory());
        explosionField.setClickCallback(clickCallback);
        explosionField.addListener(findViewById(R.id.text));
        explosionField.addListener(findViewById(R.id.image));
        explosionField.addListener(findViewById(R.id.layout));
    }


    ClickCallback clickCallback = new ClickCallback() {
        @Override
        public void onClick(View v) {
            Log.e("mile",v.toString());
        }
    };

}
