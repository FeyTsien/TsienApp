package com.hdnz.inanming.ui.activity.workbench;

import android.annotation.SuppressLint;

import com.hdnz.inanming.R;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPActivity;

public class TaskInfoActivity extends MVPActivity<MVPContract.View, MVPPresenter> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_info;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {

    }
}
