package com.hdnz.inanming.ui.activity.main;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Field;

public class BottomNavigationViewHelper {
    /**
     * @param view
     * @param goneOptions 是需要隐藏的Item的下标数组
     */
    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view, int... goneOptions) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);


            for (int opt : goneOptions) {
                menuView.removeViewAt(opt);//去掉不需要展示的menu
            }

            for (int i = 0; i < menuView.getChildCount(); i++) {

                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setChecked(item.getItemData().isChecked());
            }

    }
}