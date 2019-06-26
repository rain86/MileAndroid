package com.yulore.customviewgroup.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.yulore.customviewgroup.R;


public class CarView extends View {
    private Bitmap carBitmap;
    private Path path;
    private Path carPath;
    private PathMeasure pathMeasure;//路径计算
    private float distanceRatio = 0;//小车在路径上行走的距离比 从0 --1
    private Paint circlePaint; //画圆圈的画笔
    private Paint carPaint;     //画小车的画笔
    private Matrix carMatrix;   //针对car bitmap图片操作的矩阵
    private float sudo = 0.006f; //加速度

    public CarView(Context context) {
        this(context, null);
    }

    public CarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        carBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_car);
        path = new Path();
        path.addCircle(0, 0, 200, Path.Direction.CW);
        carPath = new Path();
        carPath.addCircle(0, 0, 200+carBitmap.getWidth()/2 -5, Path.Direction.CW);//这里图片有5的边距
        pathMeasure = new PathMeasure(carPath, false);


        circlePaint = new Paint();
        circlePaint.setStrokeWidth(5);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.BLACK);

        carPaint = new Paint();
        carPaint.setColor(Color.DKGRAY);
        carPaint.setStrokeWidth(2);
        carPaint.setStyle(Paint.Style.STROKE);

        carMatrix = new Matrix();


        //要开启无限加速度的模式需要注释掉该段代码start
        ValueAnimator animator = ValueAnimator.ofFloat(0,1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                distanceRatio = (float) animation.getAnimatedValue();
                if (distanceRatio >= 0 && distanceRatio <= 0.25){
                    distanceRatio += 0.75;
                }else if (distanceRatio >= 0.25){
                    distanceRatio -= 0.25;
                }
                invalidate();
            }
        });
        //添加差值器
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(1000);
        animator.start();
        //要开启无限加速度的模式需要注释掉该段代码start
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        //移动canvas坐标系到中心
        canvas.translate(width / 2, height / 2);
        carMatrix.reset();
        //无限加速度的代码
//        distanceRatio += sudo;//每次走
//        if ((distanceRatio >= 1)) {//走到头重新从0开始走 因为是圆就是转圈圈
//            distanceRatio = 0;
//        }
        //记录位置
        float[] pos = new float[2];
        //记录切点值xy 用来算车的角度
        float[] tan = new float[2];
        float distance = pathMeasure.getLength() * distanceRatio;
        pathMeasure.getPosTan(distance, pos, tan);
        //tan[0]代表 cos tan[1]代表sin
        float degree = (float) ((Math.atan2(tan[1], tan[0])) * 180 / Math.PI);//计算小车本身要旋转的角度
        carMatrix.postRotate(degree, carBitmap.getWidth()/2, carBitmap.getHeight()/2);//设置旋转角度和旋转中心
        //这里要将设置到小车的中心点
        carMatrix.postTranslate(pos[0] - carBitmap.getWidth() / 2, pos[1] - carBitmap.getHeight() / 2);
        canvas.drawPath(path, circlePaint);
        canvas.drawBitmap(carBitmap, carMatrix, carPaint);

        //无限加速度的代码
//        invalidate();
//        sudo += 0.0001f;
    }
}
