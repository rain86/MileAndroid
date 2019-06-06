package com.yulore.customviewgroup.view.Explosion;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * 粒子工厂，用于生产所需的粒子
 */
public abstract class ParticleFactory {
    public abstract Particle[][] generateParticles(Bitmap bitmap, Rect bound);
}
