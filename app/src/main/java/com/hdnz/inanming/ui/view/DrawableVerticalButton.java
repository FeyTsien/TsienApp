package com.hdnz.inanming.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Copyright (C), 2017-2018, 华电南自（贵州）科技有限公司
 * FileName:    DrawableVerticalButton.java
 * Author:      肖昕
 * Email:       xiaox@huadiannanzi.com
 * Date:        2018-11-19 9:18
 * Description: 自定义图片和文字居中的按钮(垂直居中)
 * Version:     V1.0.0
 * History:     历史信息
 */
@SuppressLint("AppCompatCustomView")
public class DrawableVerticalButton extends Button {
    public DrawableVerticalButton(Context context) {
        this(context, null);
    }

    public DrawableVerticalButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        dealWithCanvas(canvas);
        super.onDraw(canvas);
    }

    /**
     * 里我们可以通过重写Button的onDraw(Canvas canvas) 方法，把图片和文字一起居中
     *
     * @param canvas
     * @return
     */
    private Canvas dealWithCanvas(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        if (drawables == null) {
            return canvas;
        }
        // 上面的drawable
        Drawable drawable = drawables[1];
        if (drawable == null) {
            //下面的drawable
            drawable = drawables[3];
        }
        float textSize = getPaint().getTextSize();
        int drawHeight = drawable.getIntrinsicHeight();
        int drawPadding = getCompoundDrawablePadding();
        float contentHeight = textSize + drawHeight + drawPadding;
        int topPadding = (int) (getHeight() - contentHeight) - 10;
        setPadding(0, topPadding, 0, 0);
        float dy = (contentHeight - getHeight()) / 2;
        canvas.translate(0, dy);
        return canvas;
    }
}
