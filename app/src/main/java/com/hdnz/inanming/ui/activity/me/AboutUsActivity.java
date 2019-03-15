package com.hdnz.inanming.ui.activity.me;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import androidx.appcompat.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdnz.inanming.R;
import com.tsienlibrary.ui.activity.BaseActivity;

import butterknife.BindView;

public class AboutUsActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_right_menu)
    TextView tvRightMenu;
    @BindView(R.id.iv_right_menu)
    ImageView ivRightMenu;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initData() {

    }
    /**
     * 初始化
     */
    @Override
    protected  void initView() {
        //设置title
        setToolBar(mToolbar, tvTitle, getResources().getString(R.string.mine_address));
    }

    /**
     * 获取版本名称
     *
     * @return
     */
    private String getVersionName() {
        PackageManager manager = getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
