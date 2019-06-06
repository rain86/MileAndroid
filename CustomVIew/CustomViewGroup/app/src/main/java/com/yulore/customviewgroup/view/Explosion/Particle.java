package com.yulore.customviewgroup.view.Explosion;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 爆破粒子
 */
public abstract class Particle {
    float cx;
    float cy;
    int color;


    /**
     * @param color 颜色
     * @param x
     * @param y
     */
    public Particle(int color, float x, float y) {
        this.color = color;
        cx = x;
        cy = y;
    }

    protected abstract void draw(Canvas canvas,Paint paint);

    protected abstract void caculate(float factor);

    public void advance(Canvas canvas,Paint paint,float factor) {
        caculate(factor);
        draw(canvas,paint);
    }
}
