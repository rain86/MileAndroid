package mile.com.customviewdemo;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

/**
 * Created by mile on 2019/5/30.
 * 粒子动画
 */

public class ExplosionAnimator extends ValueAnimator {
    public static final int DEFAULT_DURATION = 1500;
    private static final String TAG = "ExplosionAnimator";
    private Particle[][] mParticles;
    private Paint mPaint;
    private View mContainer;
    private ParticleFactory mPartcleFactory;

    public ExplosionAnimator(View view, Bitmap bitmap, Rect bound, ParticleFactory partcleFactory) {
        mPaint = new Paint();
        mContainer = view;
        mPartcleFactory = partcleFactory;
        setFloatValues(0.0f, 1.0f);
        setDuration(DEFAULT_DURATION);
        mParticles = mPartcleFactory.generateParticles(bitmap,bound);
    }

    public void draw(Canvas canvas){
        Log.d(TAG,"draw come in");
        if (!isStarted()){//动画结束时停止
            return;
        }
        //所有粒子运动
        for (Particle[] particle : mParticles){
            for (Particle p : particle){
                Log.d(TAG,"Particle-------------");
                p.advance(canvas,mPaint,(Float)(getAnimatedValue()));
            }
        }
        mContainer.invalidate();
    }

    @Override
    public void start() {
        super.start();
        mContainer.invalidate();
    }
}
