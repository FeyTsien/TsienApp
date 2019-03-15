package com.hdnz.inanming.ui.activity.main;

import android.os.Bundle;
import android.view.MenuItem;

import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hdnz.inanming.R;
import com.tsienlibrary.ui.activity.BaseActivity;
import com.hdnz.inanming.ui.fragment.home.HomeFragment;
import com.hdnz.inanming.ui.fragment.me.MeFragment;
import com.hdnz.inanming.ui.fragment.message.MessageFragment;
import com.hdnz.inanming.ui.fragment.workbench.WorkbenchFragment;
import com.tsienlibrary.ui.fragment.fragmentBackHandler.BackHandlerHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class MainActivity1 extends BaseActivity {

    private static final String KEY_CURRENT_FRAGMENT = "current_fragment";
    private static final String KEY_HOME_FRAGMENT = "home_fragment";
    private static final String KEY_WOKEBENCH_FRAGMENT = "wokebench_fragment";
    private static final String KEY_MESSAGE_FRAGMENT = "message_fragment";
    private static final String KEY_ME_FRAGMENT = "me_fragment";

    private List<Fragment> mFragmentList;
    private Fragment mCurrentFragment;//当前Fragment
    private HomeFragment mHomeFragment;//首页
    private WorkbenchFragment mWorkbenchFragment;//工作台
    private MessageFragment mMessageFragment;//消息
    private MeFragment mMeFragment;//我的


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (mHomeFragment == null) {
                        mHomeFragment = HomeFragment.newInstance();//首页
                    }
                    addFragment(mHomeFragment);
                    showFragment(mHomeFragment);
                    return true;
                case R.id.navigation_workbench:
                    if (mWorkbenchFragment == null) {
                        mWorkbenchFragment = WorkbenchFragment.newInstance();//工作台
                    }
                    addFragment(mWorkbenchFragment);
                    showFragment(mWorkbenchFragment);
                    return true;
//                case R.id.navigation_center:
//                    //由 BottomNavigationView 上面的 imageview 处理点击事件
//                    return true;
                case R.id.navigation_message:
                    if (mMessageFragment == null) {
                        mMessageFragment = MessageFragment.newInstance();//消息
                    }
                    addFragment(mMessageFragment);
                    showFragment(mMessageFragment);
                    return true;
                case R.id.navigation_me:
                    if (mMeFragment == null) {
                        mMeFragment = MeFragment.newInstance();//我的
                    }
                    addFragment(mMeFragment);
                    showFragment(mMeFragment);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在当前activity onCreate中设置 取消padding,  因为这个padding 我们用代码实现了
        //可以将其他控件沉浸到状态栏下面
//        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        mFragmentList = new ArrayList<>();
        initView();
        if (savedInstanceState == null) {
            initFragment();
        } else {
            /*获取保存的fragment  没有的话返回null*/
            mCurrentFragment = getSupportFragmentManager().getFragment(savedInstanceState, KEY_CURRENT_FRAGMENT);
            mHomeFragment = (HomeFragment) getSupportFragmentManager().getFragment(savedInstanceState, KEY_HOME_FRAGMENT);
            mWorkbenchFragment = (WorkbenchFragment) getSupportFragmentManager().getFragment(savedInstanceState, KEY_WOKEBENCH_FRAGMENT);
            mMessageFragment = (MessageFragment) getSupportFragmentManager().getFragment(savedInstanceState, KEY_MESSAGE_FRAGMENT);
            mMeFragment = (MeFragment) getSupportFragmentManager().getFragment(savedInstanceState, KEY_ME_FRAGMENT);

            addToList(mHomeFragment);
            addToList(mWorkbenchFragment);
            addToList(mMessageFragment);
            addToList(mMeFragment);
//            showFragment(mCurrentFragment); //如果用这个显示，就不用BaseFragment做隐藏，显示。
        }
    }

    @Override
    protected void initView() {
        BottomNavigationView navigation = findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        ImageView imageView = (ImageView) findViewById(R.id.navigation_center_image);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "Center", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void initFragment() {
        mHomeFragment = HomeFragment.newInstance();
        addFragment(mHomeFragment);
        showFragment(mHomeFragment);
    }

    private void addToList(Fragment fragment) {
        if (fragment != null) {
            mFragmentList.add(fragment);
        }

    }

    /*添加fragment*/
    private void addFragment(Fragment fragment) {

        /*判断该fragment是否已经被添加过  如果没有被添加  则添加*/
        if (!fragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment, fragment).commit();
            /*添加到 fragmentList*/
            mFragmentList.add(fragment);
        }
    }

    /*显示fragment*/
    private void showFragment(Fragment fragment) {
        mCurrentFragment = fragment;
        for (Fragment frag : mFragmentList) {
            if (frag != fragment) {
                /*先隐藏其他fragment*/
                getSupportFragmentManager().beginTransaction().hide(frag).commit();
            }
        }
        getSupportFragmentManager().beginTransaction().show(fragment).commit();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        /*fragment不为空时 保存*/
        if (mCurrentFragment != null) {
            getSupportFragmentManager().putFragment(outState, KEY_CURRENT_FRAGMENT, mCurrentFragment);
        }
        if (mHomeFragment != null) {
            getSupportFragmentManager().putFragment(outState, KEY_HOME_FRAGMENT, mHomeFragment);
        }
        if (mWorkbenchFragment != null) {
            getSupportFragmentManager().putFragment(outState, KEY_WOKEBENCH_FRAGMENT, mWorkbenchFragment);
        }
        if (mMessageFragment != null) {
            getSupportFragmentManager().putFragment(outState, KEY_MESSAGE_FRAGMENT, mMessageFragment);
        }
        if (mMeFragment != null) {
            getSupportFragmentManager().putFragment(outState, KEY_ME_FRAGMENT, mMeFragment);
        }


        super.onSaveInstanceState(outState);
    }

    private long lastBackPress;

    @Override
    public void onBackPressed() {

        if (!BackHandlerHelper.handleBackPress(this)) {
            if (System.currentTimeMillis() - lastBackPress < 1000) {
                super.onBackPressed();
            } else {
                lastBackPress = System.currentTimeMillis();
                ToastUtils.showShort("再按一次退出");
            }
        }

    }
}
