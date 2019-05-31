package mile.com.customviewdemo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import static mile.com.customviewdemo.Utils.RANDOM;
/**
 * Created by mile on 2019/5/30.
 */

public class FallingPartcle extends Particle {
    private static final String TAG = FallingPartcle.class.getSimpleName();
    float radius = FallingParticleFactory.PART_WH;
    float alpha = 1.0f;
    Rect mBound;

    public FallingPartcle(float cx, float cy, int color, Rect bound) {
        super(cx, cy, color);
        mBound = bound;
    }

    @Override
    protected void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        paint.setAlpha((int)(Color.alpha(color)*alpha)); //这样透明颜色就不是黑色
        canvas.drawCircle(cx,cy,radius,paint);
    }

    @Override
    protected void calculate(float factor) {
        cx = cx + factor * RANDOM.nextInt(mBound.width()) * (RANDOM.nextFloat() - 0.5f);
        cy = cy + factor * RANDOM.nextInt(mBound.height() / 2);

        radius = radius - factor * RANDOM.nextInt(2);

        alpha = (1f - factor) * (1 + RANDOM.nextFloat());

        Log.d(TAG,"cx:"+cx);
        Log.d(TAG,"cy:"+cy);
    }

}
