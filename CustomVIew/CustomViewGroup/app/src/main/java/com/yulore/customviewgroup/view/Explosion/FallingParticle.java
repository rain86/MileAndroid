package com.yulore.customviewgroup.view.Explosion;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import static com.yulore.customviewgroup.view.Explosion.Utils.RANDOM;

public class FallingParticle extends Particle{

    private static final String TAG = "FallingParticle";
    float radius = FallingParticleFactory.PART_WH;
    float alpha = 1.0f;
    Rect mBound;
    /**
     * @param color 颜色
     * @param x
     * @param y
     */
    public FallingParticle(int color, float x, float y, Rect bound) {
        super(color, x, y);
        mBound = bound;
    }


    protected void draw(Canvas canvas,Paint paint){
        paint.setColor(color);
        paint.setAlpha((int) (Color.alpha(color) * alpha)); //这样透明颜色就不是黑色了
        canvas.drawCircle(cx, cy, radius, paint);
    }

    protected void caculate(float factor){
        cx = cx + factor * RANDOM.nextInt(mBound.width()) * (RANDOM.nextFloat() - 0.5f);
        cy = cy + factor * RANDOM.nextInt(mBound.height() / 2);

        radius = radius - factor * RANDOM.nextInt(2);

        alpha = (1f - factor) * (1 + RANDOM.nextFloat());

        Log.d(TAG,"cx:"+cx);
        Log.d(TAG,"cy:"+cy);
    }
}
