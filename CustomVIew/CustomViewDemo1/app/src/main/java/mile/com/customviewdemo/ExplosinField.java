package mile.com.customviewdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mile on 2019/5/28.
 */

public class ExplosinField extends View {
    private static final String TAG = "ExplosinField";
    private ArrayList<ExplosionAnimator> explosionAnimators;
    private HashMap<View,ExplosionAnimator> explosionAnimatorHashMap;
    private ParticleFactory mParticleFactory;
    private OnClickListener onClickListener;

    public ExplosinField(Context context,ParticleFactory particleFactory) {
        super(context);
        init(particleFactory);
    }

    private void init(ParticleFactory particleFactory) {
        explosionAnimators = new ArrayList<>();
        explosionAnimatorHashMap = new HashMap<>();
        mParticleFactory = particleFactory;
        attach2Activity((Activity)getContext());
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (ExplosionAnimator animator : explosionAnimators){
            animator.draw(canvas);
        }
    }

    /*
        * 分裂
        * @param view 当前点击的view
        * */
    public void explode(final View view){
        //防止重复点击
        Log.d(TAG,"explode come in");

        if (explosionAnimatorHashMap.get(view) != null && explosionAnimatorHashMap.get(view).isStarted()){
            return;
        }
        if (view.getVisibility() != View.VISIBLE || view.getAlpha() == 0){
            return;
        }
        Log.d(TAG,"explode come in 2");
        final Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);//得到view相对于整个屏幕的坐标
        int contentTop = ((ViewGroup)getParent()).getTop();
        Rect frame = new Rect();
        ((Activity)getContext()).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHight = frame.top;
        rect.offset(0,-contentTop);
        if (rect.width() == 0 || rect.height() == 0){
            return;
        }
        Log.d(TAG,"explode come in 3");
        //震动动画
        ValueAnimator animator = ValueAnimator.ofFloat(0f,1f).setDuration(150);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setTranslationX((Utils.RANDOM.nextFloat() -0.5f)*view.getWidth() * 0.05f);
                view.setTranslationY((Utils.RANDOM.nextFloat() -0.5f)*view.getWidth() * 0.05f);
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                explode(view,rect);
            }


        });
        animator.start();
    }

    private void explode(final View view, Rect rect) {
        Log.d(TAG,"explode come");
        final ExplosionAnimator animator = new ExplosionAnimator(this,Utils.createBitmapFromView(view),rect,mParticleFactory);
        explosionAnimators.add(animator);
        explosionAnimatorHashMap.put(view,animator);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setClickable(false);
                //缩小,透明动画
                view.animate().setDuration(150).scaleX(0f).scaleY(0f).alpha(0f).start();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(150).start();
                view.setClickable(true);
                //动画结束时从动画集中移除
                explosionAnimators.remove(animation);
                explosionAnimatorHashMap.remove(view);
                animation = null;
            }
        });
        animator.start();
    }
    private OnClickListener getOnClickLitener(){

        if (onClickListener == null){
            onClickListener = new OnClickListener() {
                @Override
                public void onClick(View view) {
                    ExplosinField.this.explode(view);
                    if (clickCallback != null){
                        clickCallback.onClick(view);
                    }
                }
            };
        }
        return onClickListener;
    }





    /**
     * 给Activity加上全屏覆盖的ExplosionField
     * */
    private void attach2Activity(Activity activity){
        ViewGroup rootView = activity.findViewById(Window.ID_ANDROID_CONTENT);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        rootView.addView(this,params);
    }

    /**
     * 添加监听希望谁有破碎效果加上监听
     *
     * @param view 可以是ViewGroup
     * */
    public void addListener(View view){
        if(view instanceof ViewGroup){
            ViewGroup viewGroup = (ViewGroup) view;
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++){
                addListener(viewGroup.getChildAt(i));
            }
        }else {
            view.setClickable(true);
            view.setOnClickListener(getOnClickLitener());
        }
    }


    private ClickCallback clickCallback;

    public void setClickCallback(ClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }
}
