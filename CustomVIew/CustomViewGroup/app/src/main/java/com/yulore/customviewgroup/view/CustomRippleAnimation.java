package com.yulore.customviewgroup.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import com.yulore.customviewgroup.R;
import com.yulore.customviewgroup.util.UIUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomRippleAnimation extends RelativeLayout {

    private float maxScale = 10;//放大半径倍数
    private int rippleDuration = 3500;
    private int rippleCon = 10;
    int singleDelay = rippleDuration/rippleCon;

    private Context mContext;
    private Paint mPaint;
    private int rippleColor;
    private int radius;
    private int strokWidth;
    private List<RippleCircleView> views;
    ArrayList<Animator> animatorList;
    private AnimatorSet animatorSet;
    private boolean animationRunning = false;
    public CustomRippleAnimation(Context context) {
        this(context,null);
    }

    public CustomRippleAnimation(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomRippleAnimation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        views = new ArrayList<>();
        animatorList = new ArrayList<>();
        animatorSet = new AnimatorSet();

        mContext = context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.RippleAnimationView);
        rippleColor = array.getColor(R.styleable.RippleAnimationView_ripple_anim_color, ContextCompat.getColor(mContext,R.color
        .rippleColor));
        int rippleType = array.getInt(R.styleable.RippleAnimationView_ripple_anim_type,0);
        radius = array.getInt(R.styleable.RippleAnimationView_radius,54);
        strokWidth = array.getInt(R.styleable.RippleAnimationView_strokWidth,2);

        if (rippleType == 0){
            mPaint.setStyle(Paint.Style.FILL);
        }else {
            mPaint.setStyle(Paint.Style.STROKE);
        }
        mPaint.setStrokeWidth(UIUtils.getInstance().getWidth(strokWidth));
        mPaint.setColor(rippleColor);

        LayoutParams layoutParams = new LayoutParams(UIUtils.getInstance().getWidth(radius+strokWidth),UIUtils.getInstance().getHeight(radius+strokWidth));
        layoutParams.addRule(CENTER_IN_PARENT,TRUE);

        for (int i = 0; i < rippleCon; i ++){
            RippleCircleView circleView = new RippleCircleView(mContext,mPaint,radius,strokWidth);
            views.add(circleView);
            addView(circleView);

            final ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(circleView,"ScaleX",1.0f,maxScale);
            scaleXAnimator.setRepeatCount(ObjectAnimator.INFINITE);
            scaleXAnimator.setRepeatMode(ObjectAnimator.RESTART);
            scaleXAnimator.setDuration(rippleDuration);
            scaleXAnimator.setStartDelay(i * singleDelay);
            animatorList.add(scaleXAnimator);

            //y
            final ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(circleView, "ScaleY", 1.0f, maxScale);

            scaleYAnimator.setRepeatCount(ObjectAnimator.INFINITE);//无限重复
            scaleYAnimator.setRepeatMode(ObjectAnimator.RESTART);
            scaleYAnimator.setStartDelay(i * singleDelay);
            scaleYAnimator.setDuration(rippleDuration);
            animatorList.add(scaleYAnimator);
            //alpha
            //Alpha渐变
            final ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(circleView, "Alpha", 1.0f, 0f);
            alphaAnimator.setRepeatCount(ObjectAnimator.INFINITE);//无限重复
            alphaAnimator.setRepeatMode(ObjectAnimator.RESTART);
            alphaAnimator.setStartDelay(i * singleDelay);
            alphaAnimator.setDuration(rippleDuration);
            animatorList.add(alphaAnimator);
        }

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.playTogether(animatorList);
    }

    //启动动画   //停止动画

    public void startRippleAnimation() {
        if (!animationRunning) {
            for (RippleCircleView rippleView : views) {
                rippleView.setVisibility(VISIBLE);
            }
            animatorSet.start();
            animationRunning = true;
        }

    }
    public void stopRippleAnimation() {
        if (animationRunning) {
            Collections.reverse(views);
            for (RippleCircleView rippleView : views) {
                rippleView.setVisibility(INVISIBLE);
            }
            animatorSet.end();
            animationRunning = false;
        }

    }

    public boolean isAnimationRunning() {
        return animationRunning;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
