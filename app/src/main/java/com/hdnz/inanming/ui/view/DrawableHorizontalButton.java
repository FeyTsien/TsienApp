package com.hdnz.inanming.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Copyright (C), 2017-2018, 华电南自（贵州）科技有限公司
 * FileName:    DrawableHorizontalButton.java
 * Author:      肖昕
 * Email:       xiaox@huadiannanzi.com
 * Date:        2018-11-16 14:56
 * Description: 自定义图片和文字居中的按钮
 * Version:     V1.0.0
 * History:     历史信息
 */
@SuppressLint("AppCompatCustomView")
public class DrawableHorizontalButton extends Button {
    public DrawableHorizontalButton(Context context) {
        this(context, null);
    }

    public DrawableHorizontalButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        dealWithCanvas(canvas);
        super.onDraw(canvas);
    }

    /**
     * 这里我们可以通过重写Button的onDraw(Canvas canvas) 方法，把图片和文字一起居中
     *
     * @param canvas
     * @return
     */
    private Canvas dealWithCanvas(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        if (drawables == null) {
            return canvas;
        }
        // 左面的drawable
        Drawable drawable = drawables[0];
        if (drawable == null) {
            // 右面的drawable
            drawable = drawables[2];
        }

        // float textSize = getPaint().getTextSize(); // 使用这个会导致文字竖向排下来
        float textSize = getPaint().measureText(getText().toString());
        int drawWidth = drawable.getIntrinsicWidth();
        int drawPadding = getCompoundDrawablePadding();
        float contentWidth = textSize + drawWidth + drawPadding;
        int leftPadding = (int) (getWidth() - contentWidth);
        // 直接贴到左边
        setPadding(0, 0, leftPadding, 0);
        float dx = (getWidth() - contentWidth) / 2;
        // 往右移动
        canvas.translate(dx, 0);
        return canvas;
    }
}
