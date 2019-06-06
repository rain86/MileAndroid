package com.yulore.customviewgroup.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.yulore.customviewgroup.R;

/*
* 自定义开场动画，6个圆转圈扩撒加水波纹
* */
public class SplashView extends View {
    //旋转圆的画笔
    private Paint mPaint;
    //扩散圆的画笔
    private Paint mHolePaint;
    //属性动画
    private ValueAnimator animator;

    //背景色
    private int mBackgroundColor = Color.WHITE;
    //小圆的各个颜色
    private int[] mCircleColors;

    //表示旋转圆的中心坐标
    private float mCenterX;
    private float mCenterY;
    //表示斜对角线长度的一半，扩散圆最大半径
    private float mDistance;

    //6个小球的半径
    private float mCircleRadius = 18;

    //旋转大圆的半径
    private float mRotateRadius = 90;

    //当前大圆旋转的角度
    private float mCurrentRotateAngle = 0f;
    //当前大圆的半径
    private float mCurrentRotateRadius = mRotateRadius;
    //扩散圆的半径
    private float mCurrentHoleRadius = 0f;
    //表示旋转动画的时长
    private int mRotateDuration = 800;
    private Bitmap bitmap;


    public SplashView(Context context) {
        this(context,null);
    }

    public SplashView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SplashView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHolePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHolePaint.setStyle(Paint.Style.FILL_AND_STROKE);


        mCircleColors = context.getResources().getIntArray(R.array.splash_circle_colors);
        bitmap = BitmapFactory.decodeResource(context.getResources(),R.mipmap.content);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w * 1f/2;
        mCenterY = h * 1f/2;
        mDistance = (float) (Math.hypot(w,h)/2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mState == null){
            mState = new RotateState();
        }
        mState.drawState(canvas);
    }

    private abstract class SplashState{
        abstract void drawState(Canvas canvas);
    }

    private SplashState mState;

    //1旋转动画

    private class RotateState extends SplashState{

        public RotateState() {
            animator = ValueAnimator.ofFloat(0,(float)(Math.PI *2));
            animator.setRepeatCount(2);
            animator.setDuration(mRotateDuration);
            animator.setInterpolator(new LinearInterpolator());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mCurrentRotateAngle
                             = (float)animation.getAnimatedValue();
                    invalidate();

//                    Log.d("mile","mile vale:"+mCurrentRotateAngle);
                }
            });
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mState = new MerginState();
                }
            });

            animator.start();
        }

        @Override
        void drawState(Canvas canvas) {
            //绘制背景
            drawBackground(canvas);
            
            //绘制6个小球
            drawCircles(canvas);
        }
    }

    private void drawCircles(Canvas canvas) {
        float rotateAngle = (float)(Math.PI *2 /mCircleColors.length);
        for (int i = 0; i <mCircleColors.length; i++){
            float angle = i * rotateAngle + mCurrentRotateAngle;
//            Log.d("mile","angle:"+angle);
            float cx = (float)(Math.cos(angle) * mCurrentRotateRadius + mCenterX);
            float cy = (float)(Math.sin(angle) * mCurrentRotateRadius + mCenterY);
            mPaint.setColor(mCircleColors[i]);
            canvas.drawCircle(cx,cy,mCircleRadius,mPaint);
        }
    }

    private void drawBackground(Canvas canvas) {

        if (mCurrentHoleRadius > 0){
            setLayerType(View.LAYER_TYPE_SOFTWARE,null);//关闭硬件加速
            canvas.drawBitmap(bitmap,0,0,mHolePaint);
            int layerId = canvas.saveLayer(0,0, getWidth(), getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);
            mHolePaint.setColor(Color.WHITE);
            canvas.drawRect(0,0,getWidth(),getHeight(),mHolePaint);
            mHolePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            mHolePaint.setColor(Color.RED);
            canvas.drawCircle(mCenterX,mCenterY,mCurrentHoleRadius,mHolePaint);
            mHolePaint.setXfermode(null);
            canvas.restoreToCount(layerId);
        }else {
            canvas.drawColor(mBackgroundColor);
        }
    }

    //2扩散聚合
    private class MerginState extends SplashState{

        public MerginState() {
            animator = ValueAnimator.ofFloat(mCircleRadius,mRotateRadius);
            animator.setDuration(mRotateDuration);
            animator.setInterpolator(new OvershootInterpolator(10f));
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mCurrentRotateRadius = (float)animation.getAnimatedValue();
                    invalidate();
                }
            });
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mState = new ExpandState();
                }
            });
            animator.start();
        }

        @Override
        void drawState(Canvas canvas) {
            drawBackground(canvas);
            drawCircles(canvas);
        }
    }

    //3水波纹
    private class ExpandState extends SplashState{
        public ExpandState() {
            animator = ValueAnimator.ofFloat(mCircleRadius,mDistance);
            animator.setDuration(mRotateDuration);
            animator.setInterpolator(new LinearInterpolator());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mCurrentHoleRadius = (float)animation.getAnimatedValue();
                    Log.d("mile","mCurrentHoleRadius:"+mCurrentHoleRadius);
                    invalidate();
                }
            });
            animator.start();
        }

        @Override
        void drawState(Canvas canvas) {
            drawBackground(canvas);
        }
    }


}
