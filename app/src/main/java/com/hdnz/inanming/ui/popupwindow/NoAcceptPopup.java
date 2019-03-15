package com.hdnz.inanming.ui.popupwindow;

import android.content.Context;
import android.view.View;

import com.hdnz.inanming.R;

public class NoAcceptPopup extends MyBasePopupWindow {

    public NoAcceptPopup(Context context) {
        super(context);
    }

    @Override
    protected int getLayouId() {
        return R.layout.popup_no_accept;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View rootView) {

    }
}
