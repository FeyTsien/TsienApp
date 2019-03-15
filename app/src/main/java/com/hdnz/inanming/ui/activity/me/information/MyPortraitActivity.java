package com.hdnz.inanming.ui.activity.me.information;

import androidx.appcompat.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdnz.inanming.R;
import com.hdnz.inanming.app.GlideApp;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPActivity;

import butterknife.BindView;

/**
 * <pre>
 *     author : Tsien
 *     e-mail : 974490643@qq.com
 *     time   : 2018/12/26
 *     desc   : 【我的头像】
 * </pre>
 */
public class MyPortraitActivity extends MVPActivity<MVPContract.View, MVPPresenter> {

    public static final String KEY_PORTRAIT_URL = "portrait_url";

    private String mPortraitUrl;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_menu)
    TextView tvRightMenu;
    @BindView(R.id.iv_portrait)
    ImageView ivPortrait;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_portrait;
    }

    @Override
    protected void initData() {
        mPortraitUrl = getIntent().getStringExtra(KEY_PORTRAIT_URL);
    }

    @Override
    protected void initView() {

        setToolBar(mToolbar, tvTitle, getResources().getString(R.string.portrait));

        GlideApp.with(this)
                .load(mPortraitUrl)
                .placeholder(R.drawable.test)
                .error(R.drawable.empty)
                .into(ivPortrait);
    }
}
