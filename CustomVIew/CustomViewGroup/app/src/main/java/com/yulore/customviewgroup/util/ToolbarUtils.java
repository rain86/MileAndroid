package com.yulore.customviewgroup.util;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;

import java.lang.reflect.Field;

public class ToolbarUtils {
    public static TextView getToolbarTitleView(Toolbar toolbar) {

        try {
            Field field = toolbar.getClass().getDeclaredField("mTitleTextView");
            field.setAccessible(true);

            Object object = field.get(toolbar);
            if (object != null) {
                TextView mTitleTextView = (TextView) object;
                return mTitleTextView;
            }
        } catch (IllegalAccessException e) {
        } catch (NoSuchFieldException e) {
        } catch (Exception e) {
        }
        return null;
    }

    public static void setMarqueeForToolbarTitleView(final Toolbar toolbar) {
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                TextView mTitleTextView = getToolbarTitleView(toolbar);
                if (mTitleTextView == null) {
                    return;
                }
                mTitleTextView.setHorizontallyScrolling(true);
                mTitleTextView.setMarqueeRepeatLimit(-1);
                mTitleTextView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                mTitleTextView.setSelected(true);
            }
        });
    }
}
