package com.hdnz.inanming.ui.popupwindow;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;

import com.hdnz.inanming.R;
import com.hdnz.inanming.ui.adapter.RecyclerViewAdapter;
import com.hdnz.inanming.utils.UrlUtils;
import com.tsienlibrary.ui.widget.MultiItemDivider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ServerListPopup extends MyBasePopupWindow {

    public static final String KEY_SELECT_URL = "select_url";

    private List<BaseUrlBean> mBaseUrlList;

    public static ServerListPopup getInstance(Context context, TsienPopupWindowCallback tsienPopupWindowCallback) {
        mTsienPopupWindowCallback = tsienPopupWindowCallback;
        return new ServerListPopup(context);
    }

    private ServerListPopup(Context context) {
        super(context);
    }

    @Override
    protected int getLayouId() {
        return R.layout.popup_server_list;
    }

    @Override
    protected void initData() {
        mBaseUrlList = new ArrayList<>();
        mBaseUrlList.add(new BaseUrlBean("默认", UrlUtils.BASEURL));
        mBaseUrlList.add(new BaseUrlBean("汭汎", UrlUtils.BASEURL_HRF));
        mBaseUrlList.add(new BaseUrlBean("海洋", UrlUtils.BASEURL_HY));
        mBaseUrlList.add(new BaseUrlBean("电商", UrlUtils.BASEURL_STORE));
    }

    @Override
    protected void initView(View rootView) {
        setAllowDismissWhenTouchOutside(false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter<BaseUrlBean>(mBaseUrlList, R.layout.item_server) {
            @Override
            public void bindView(MyViewHolder holder, int position) {
                holder.setTextView(R.id.tv_name, mBaseUrlList.get(position).getName());
                holder.setTextView(R.id.tv_url, mBaseUrlList.get(position).getUrl());
            }
        };
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        MultiItemDivider itemDivider = new MultiItemDivider(getContext(), MultiItemDivider.VERTICAL_LIST, R.drawable.divider_horizontal);
        itemDivider.setDividerMode(MultiItemDivider.INSIDE);//最后一个item下没有分割线
        // itemDivider.setDividerMode(MultiItemDivider.END);//最后一个item下有分割线
        recyclerView.addItemDecoration(itemDivider);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(pos -> {
            Map map = new HashMap();
            map.put(KEY_SELECT_URL, mBaseUrlList.get(pos).getUrl());
            sendCallback(map);
        });
    }

    // 以下为可选代码（非必须实现）
    // 返回作用于PopupWindow的show和dismiss动画，本库提供了默认的几款动画，这里可以自由实现
    @Override
    protected Animation onCreateShowAnimation() {
//        return getDefaultScaleAnimation(true);
//        return getDefaultAlphaAnimation(true);
        return getTranslateVerticalAnimation(-1500, 0, 500);
    }

    @Override
    protected Animation onCreateDismissAnimation() {
//        return getDefaultScaleAnimation(false);
//        return getDefaultAlphaAnimation(false);
        return getTranslateVerticalAnimation(0, -1500, 500);
    }

    private static class BaseUrlBean {
        // 成员变量
        private String name;
        private String url;

        BaseUrlBean(String name, String url) {
            this.name = name;
            this.url = url;
        }

        // get set 方法
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
