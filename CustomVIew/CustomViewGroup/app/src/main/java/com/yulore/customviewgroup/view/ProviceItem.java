package com.yulore.customviewgroup.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;

public class ProviceItem {
    private Path path;

    /**
     * 绘制颜色
     * */
    private int drawColor;
    public void setDrawColor(int drawColor){
        this.drawColor = drawColor;
    }

    public ProviceItem(Path path) {
        this.path = path;
    }

    public void drawItem(Canvas canvas, Paint paint,boolean isSelect){
        if (isSelect){
            //绘制内部颜色
            paint.clearShadowLayer();
            paint.setStrokeWidth(1);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(0xffff0000);
            canvas.drawPath(path,paint);
            //绘制边界
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(0xFFD0E8F4);
            canvas.drawPath(path,paint);
        }else {
            paint.setStrokeWidth(2);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL);
            paint.setShadowLayer(8,0,0,0xffffff);
            canvas.drawPath(path,paint);
            //绘制边界
            paint.clearShadowLayer();
            paint.setColor(drawColor);
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(2);
            canvas.drawPath(path,paint);
        }
    }

    public boolean isTouch(float x,float y){
        RectF rectF = new RectF();
        path.computeBounds(rectF,true);
        Region region = new Region();
        region.setPath(path,new Region((int)rectF.left,(int)rectF.top,(int)rectF.right,(int) rectF.bottom));
        return  region.contains((int)x,(int)y);
    }
}
