package com.yulore.customviewgroup.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class RippleCircleView extends View {

    private Context mContext;
    private Paint mPaint;
    private float mRadius;
    private int mStrokWidth;

    public RippleCircleView(Context context, Paint paint, float radius, int strokWidth) {
        super(context);
        mContext = context;
        mPaint = paint;
        mRadius = radius;
        mStrokWidth = strokWidth;
        setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int radius = (Math.min(getWidth(), getHeight())) / 2;
        canvas.drawCircle(radius, getHeight()/2,mRadius-mStrokWidth,mPaint);
    }
}
