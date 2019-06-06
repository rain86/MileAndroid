package com.yulore.customviewgroup;

import android.app.Activity;
import android.os.Bundle;

import com.yulore.customviewgroup.view.DragBubbleView;

/**
 * @author mile
 * @title: QQBubbleActivity
 * @projectName CustomViewGroup
 * @description: 模仿QQ聊天拖拽气泡效果
 * @date 2019-06-0617:07
 */
public class QQBubbleActivity extends Activity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qq_bubble);
    }
}
