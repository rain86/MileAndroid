package mile.com.customviewdemo;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by mile on 2019/5/30.
 * 爆破粒子
 */


public abstract class Particle {
    private static final String TAG = "Particle";
    float cx;
    float cy;
    int color;

    /**
     * @param cx x坐标
     * @param cy y坐标
     * @param color 颜色
     * */
    public Particle(float cx, float cy, int color) {
        this.cx = cx;
        this.cy = cy;
        this.color = color;
        Log.d(TAG,"color:"+this.color);
    }

    protected abstract void draw(Canvas canvas, Paint paint);

    protected abstract void calculate(float factor);

    public void advance(Canvas canvas, Paint paint, float factor){
        calculate(factor);
        draw(canvas,paint);
    }
}
