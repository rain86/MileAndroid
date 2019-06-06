package com.yulore.customviewgroup.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.yulore.customviewgroup.R;

import java.util.ArrayList;
import java.util.List;

/*
* 点击图像爆炸成粒子效果
* */
public class SplitView extends View {

    private Paint mPaint;
    private Bitmap mBitmap;
    private static final int ballR = 4;
    private static final int ballR2 = ballR/2;
    private ValueAnimator valueAnimator;
    private List<Ball> mBalls = new ArrayList<>();

    public SplitView(Context context) {
        this(context,null);
    }

    public SplitView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SplitView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public SplitView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mile);

        int widthSize = mBitmap.getWidth()/ballR;
        int heightSize = mBitmap.getHeight()/ballR;
        for (int i = 0; i < widthSize; i++){
            for (int j = 0; j <heightSize; j ++){
                Ball ball = new Ball();
                ball.color = mBitmap.getPixel(i*ballR,j*ballR);
                ball.x = i*ballR + ballR2;
                ball.y = j*ballR + ballR2;
                ball.r = ballR2;


                //速度 （-20，20）
                ball.vX = (float)(Math.pow(-1,Math.ceil(Math.random() * 1000)) * 20 * Math.random());
                ball.vY = rangInt(-15 ,35);

                //加速度
                ball.aX = 0;
                ball.aY = 0.98f;

                mBalls.add(ball);



            }
        }
        valueAnimator = ValueAnimator.ofFloat(0,1);
        valueAnimator.setRepeatCount(-1);
        valueAnimator.setDuration(2000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updataBall();
                invalidate();
            }
        });


    }

    private void updataBall() {
        for (Ball ball : mBalls){
            ball.x += ball.vX;
            ball.y += ball.vY;

            ball.vX += ball.aX;
            ball.vY += ball.aY;
        }
    }

    private float rangInt(int i, int j) {
        int max = Math.max(i, j);
        int min = Math.min(i, j) - 1;
        //在0到(max - min)范围内变化，取大于x的最小整数 再随机
        return (int) (min + Math.ceil(Math.random() * (max - min)));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Ball ball : mBalls){
            mPaint.setColor(ball.color);
            canvas.drawCircle(ball.x,ball.y,ball.r,mPaint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            valueAnimator.start();
        }
        return super.onTouchEvent(event);
    }

}
