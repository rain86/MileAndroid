package com.yulore.customviewgroup.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.graphics.PathParser;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import com.yulore.customviewgroup.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class ChinaMapView extends View {

    private int[] colorArray = new int[]{0xFF239BD7, 0xFF30A9E5, 0xFF80CBF1, 0xFFFFFFFF};//各省地图显示的颜色
    private Context context;//上下文
    private List<ProviceItem> itemList;//各省地图列表 各省地图颜色 与路径
    private Paint paint;    //初始化画笔
    private ProviceItem select; //选中的省份
    private RectF totalRect;//中国地图的矩形范围
    private float scale = 1.0f;//中国地图的缩放比例

    public ChinaMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public ChinaMapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context) {
        this.context = context;
        paint = new Paint();
        paint.setAntiAlias(true);
        itemList = new ArrayList<>();
        loadThread.start();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取当前控件的高度 让地图宽高适配当前控件
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (totalRect != null) {
            double mapWidth = totalRect.width();
            scale = (float) (width / mapWidth); //获取控件高度为了让地图能缩放到和控件宽高适配
        }
        setMeasuredDimension(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }

    private Thread loadThread = new Thread() {
        @Override
        public void run() {
            final InputStream inputStream = context.getResources().openRawResource(R.raw.china);//读取地图svg
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); //获取DocumentBuilderFactory实例
            DocumentBuilder builder = null;
            try {
                builder = factory.newDocumentBuilder();
                Document doc = builder.parse(inputStream);//解析svg的输入流
                Element rootElement = doc.getDocumentElement();
                NodeList items = rootElement.getElementsByTagName("path");
                //获取地图的整个上下左右位置，
                float left = -1;
                float right = -1;
                float top = -1;
                float bottom = -1;
                List<ProviceItem> list = new ArrayList<>();
                for (int i = 0; i < items.getLength(); i++) {
                    Element element = (Element) items.item(i);
                    String pathData = element.getAttribute("android:pathData");
                    @SuppressLint("RestrictedApi")
                    Path path = PathParser.createPathFromPathData(pathData);
                    ProviceItem proviceItem = new ProviceItem(path);//设置路径
                    proviceItem.setDrawColor(colorArray[i % 4]);//设置颜色
                    //取每个省的上下左右 最后拿出最小或者最大的来充当 总地图的上下左右
                    RectF rect = new RectF();
                    path.computeBounds(rect, true);
                    left = left == -1 ? rect.left : Math.min(left, rect.left);
                    right = right == -1 ? rect.right : Math.max(right, rect.right);
                    top = top == -1 ? rect.top : Math.min(top, rect.top);
                    bottom = bottom == -1 ? rect.bottom : Math.max(bottom, rect.bottom);
                    list.add(proviceItem);
                }
                itemList = list;
                totalRect = new RectF(left, top, right, bottom);//设置地图的上下左右位置

                //加载完以后刷新界面
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        requestLayout();
                        invalidate();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        handleTouch(event.getX() / scale, event.getY() / scale);
        return super.onTouchEvent(event);
    }

    private void handleTouch(float x, float y) {
        if (itemList == null){
            return;
        }
        ProviceItem selectItem = null;
        for (ProviceItem proviceItem : itemList){
            if (proviceItem.isTouch(x,y)){
                selectItem = proviceItem;
            }
        }
        if (selectItem != null){
            select = selectItem;
            postInvalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (itemList != null){
            canvas.save();
            canvas.scale(scale,scale);//把画布缩放匹配到本控件的宽高
            for (ProviceItem proviceItem : itemList){
                if (proviceItem != select){
                    proviceItem.drawItem(canvas,paint,false);
                }else {
                    proviceItem.drawItem(canvas,paint,true);
                }
            }
        }
    }
}
