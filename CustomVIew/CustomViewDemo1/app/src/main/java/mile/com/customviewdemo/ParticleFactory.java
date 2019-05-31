package mile.com.customviewdemo;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Created by mile on 2019/5/30.
 */

public abstract class ParticleFactory {
    public abstract Particle[][] generateParticles(Bitmap bitmap, Rect bound);
}
