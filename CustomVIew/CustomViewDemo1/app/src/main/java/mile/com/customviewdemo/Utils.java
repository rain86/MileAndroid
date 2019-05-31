package mile.com.customviewdemo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

import java.util.Random;

/**
 * Created by mile on 2019/5/28.
 * 生成一张和原来view一样的图片
 * 动画执行 震动 缩小透明
 */

public class Utils {
    public static final Random RANDOM = new Random(System.currentTimeMillis());
    private static final Canvas CANVAS = new Canvas();

    public static Bitmap createBitmapFromView(View view){
        view.clearFocus();//使view恢复原样
        Bitmap bitmap = createBitmapSafely(view.getWidth(),view.getHeight(),Bitmap.Config.ARGB_8888,1);
        if (bitmap != null){
            synchronized (CANVAS){
                CANVAS.setBitmap(bitmap);
                view.draw(CANVAS);
                CANVAS.setBitmap(null);
            }
        }
        return bitmap;
    }

    /**
     * @param width
     * @param height
     * @param config
     * @param retryCount 重试次数，发生内存溢出时回收重试
     * @return
     */
    private static Bitmap createBitmapSafely(int width,int height,Bitmap.Config config,int retryCount){
        try {
            return Bitmap.createBitmap(width,height,config);
        }catch (OutOfMemoryError e){
            e.printStackTrace();
            if (retryCount >0){
                System.gc();
                return createBitmapSafely(width,height,config,0);
            }
        }
        return null;
    }

}
