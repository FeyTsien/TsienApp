package com.hdnz.inanming.ui.adapter;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

//ViewPage的适配器
public class MyPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mList;
    private List<String> mListTitle;

    public MyPagerAdapter(FragmentManager fm, List<Fragment> list, List<String> listTitle) {
        super(fm);
        mList = list;
        mListTitle = listTitle;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mListTitle.get(position);
    }

}