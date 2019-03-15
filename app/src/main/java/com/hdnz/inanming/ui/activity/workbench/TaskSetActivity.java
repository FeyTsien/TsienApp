package com.hdnz.inanming.ui.activity.workbench;


import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import com.hdnz.inanming.R;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPActivity;
import com.tsienlibrary.bean.CommonBean;

import butterknife.BindView;

public class TaskSetActivity extends MVPActivity<MVPContract.View, MVPPresenter> {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_set;
    }


    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {
        setToolBar(mToolbar, mTvTitle, R.string.task_set);
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {

    }

    @Override
    public void requestFail(String requestUrl,String msg,int code) {

    }
}