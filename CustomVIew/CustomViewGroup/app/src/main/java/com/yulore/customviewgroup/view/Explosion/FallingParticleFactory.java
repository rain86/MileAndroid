package com.yulore.customviewgroup.view.Explosion;

import android.graphics.Bitmap;
import android.graphics.Rect;


public class FallingParticleFactory extends ParticleFactory{
    public static final int PART_WH = 8; //默认小球宽高

    public Particle[][] generateParticles(Bitmap bitmap, Rect bound) {
        int w = bound.width();
        int h = bound.height();

        int partW_Count = w / PART_WH; //横向个数
        int partH_Count = h / PART_WH; //竖向个数
        partW_Count=partW_Count>0?partW_Count:1;
        partH_Count=partH_Count>0?partH_Count:1;
        int bitmap_part_w = bitmap.getWidth() / partW_Count;
        int bitmap_part_h = bitmap.getHeight() / partH_Count;

        Particle[][] particles = new Particle[partH_Count][partW_Count];
        for (int row = 0; row < partH_Count; row ++) { //行
            for (int column = 0; column < partW_Count; column ++) { //列
                //取得当前粒子所在位置的颜色
                int color = bitmap.getPixel(column * bitmap_part_w, row * bitmap_part_h);

                float x = bound.left + PART_WH * column;
                float y = bound.top +PART_WH * row;
                particles[row][column] = new FallingParticle(color,x,y,bound);
            }
        }

        return particles;
    }

}
