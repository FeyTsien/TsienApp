package com.hdnz.inanming.ui.activity.me.information;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hdnz.inanming.R;
import com.hdnz.inanming.bean.request.RequestBean;
import com.hdnz.inanming.bean.result.AddressBean;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPActivity;
import com.hdnz.inanming.ui.activity.me.information.address.MyAddressActivity;
import com.hdnz.inanming.ui.adapter.RecyclerViewAdapter;
import com.hdnz.inanming.utils.UrlUtils;
import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.eventbus.Event;
import com.tsienlibrary.eventbus.EventCode;
import com.tsienlibrary.loadsir.callback.NotDataCallback;
import com.tsienlibrary.mvp.GsonManger;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Copyright (C), 2017-2018, 华电南自（贵州）科技有限公司
 * FileName:    SettingActivity.java
 * Author:      肖昕
 * Email:       xiaox@huadiannanzi.com
 * Date:        2018-11-06 14:09
 * Description: 用户地址列表activity
 * Version:     V1.0.0
 * History:     历史信息
 */
public class MyAddressListActivity extends MVPActivity<MVPContract.View, MVPPresenter> {

    private String defaultAddressId;
    private int defaultAddressPos;
    private List<AddressBean.ListBean> addressBeanList;
    private RecyclerViewAdapter recyclerViewAdapter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_menu)
    TextView tvRightMenu;
    @BindView(R.id.layout)
    FrameLayout mLayout;
    @BindView(R.id.rv_list)
    SwipeMenuRecyclerView rvList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_list;
    }

    @Override
    protected void initData() {
        addressBeanList = new ArrayList<>();
    }

    @Override
    protected void initView() {
        //设置title
        setToolBar(mToolbar, tvTitle, getResources().getString(R.string.mine_address));
        tvRightMenu.setVisibility(View.VISIBLE);
        //设置save文本，为“完成”
        tvRightMenu.setText(R.string.add);

        //设置列表
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rvList.setLayoutManager(layoutManager);
        //rvList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvList.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(this, R.color.me_divider), 1, 1));

        // 策划删除，默认关闭，需打开，此处bug 需设置false
        rvList.setItemViewSwipeEnabled(false);
        //recyclerview点击item 事件
        rvList.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Intent intent = new Intent(MyAddressListActivity.this, MyAddressActivity.class);
                intent.putExtra(MyAddressActivity.KEY_ADDRESS_ITEM, (Serializable) addressBeanList.get(position));
                ActivityUtils.startActivity(intent);
            }
        });

        // 创建侧滑菜单：
        rvList.setSwipeMenuCreator(new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
                int width = getResources().getDimensionPixelSize(R.dimen.dp_90);
                // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
                // 2. 指定具体的高，比如80;
                // 3. WRAP_CONTENT，自身高度，不推荐;
//                int height =  getResources().getDimensionPixelSize(R.dimen.dp_80);
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                SwipeMenuItem deleteItem = new SwipeMenuItem(MyAddressListActivity.this)
                        .setBackgroundColor(ContextCompat.getColor(MyAddressListActivity.this, R.color.red))
                        .setText("删除")
                        .setTextColor(ContextCompat.getColor(MyAddressListActivity.this, R.color.white))
                        .setTextSize(16)
                        .setHeight(height)
                        .setWidth(width);
                // 在Item右侧添加一个菜单。
                swipeRightMenu.addMenuItem(deleteItem);
            }
        });

        //菜单监听
        rvList.setSwipeMenuItemClickListener(new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge) {
                // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
                menuBridge.closeMenu();
                int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
                int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
                int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
                switch (menuPosition) {
                    case 0:
                        deleteAddress(addressBeanList.get(adapterPosition).getId());
                        break;
                    case 1:
                        break;
                }
            }
        });
        //绑定adapter
        recyclerViewAdapter = new RecyclerViewAdapter<AddressBean.ListBean>(addressBeanList, R.layout.mine_address_item) {
            @Override
            public void bindView(RecyclerViewAdapter.MyViewHolder holder, int position) {
                holder.setTextView(R.id.tv_name, addressBeanList.get(position).getName());
                holder.setTextView(R.id.tv_phone, addressBeanList.get(position).getPhoneNumber());
                String address = addressBeanList.get(position).getProvince() + " "//省
                        + addressBeanList.get(position).getCity() + " "//市
                        + addressBeanList.get(position).getCounty() + " "//区
                        + addressBeanList.get(position).getLdName() + " "//楼栋
                        + addressBeanList.get(position).getDyName() + " "//单元
                        + addressBeanList.get(position).getLcName() + " "//楼层
                        + addressBeanList.get(position).getMphName();//房间号
                holder.setTextView(R.id.tv_address, address);
                if (TextUtils.equals(addressBeanList.get(position).getStatus(), "1")) {
                    holder.setCheckBox(R.id.cb_toggle, true);
                    defaultAddressPos = position;
                } else {
                    holder.setCheckBox(R.id.cb_toggle, false);
                }
            }
        };
        rvList.setAdapter(recyclerViewAdapter);

        recyclerViewAdapter.setOnItemCheckListener(pos -> {
            addressBeanList.get(defaultAddressPos).setStatus("0");
            addressBeanList.get(pos).setStatus("1");
            defaultAddressId = addressBeanList.get(pos).getId();
            rvList.post(new Runnable() {
                @Override
                public void run() {
                    recyclerViewAdapter.notifyDataSetChanged();
                }
            });

        });
        request();
    }

    @Override
    protected boolean isLoadSir() {
        return true;
    }

    @Override
    protected void initLoadSir(Object target) {
        super.initLoadSir(mLayout);
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @Override
    protected void receiveEvent(Event event) {
        super.receiveEvent(event);

        switch (event.getCode()) {
            case EventCode.ME_ADDRESS_B:
                request();
                break;
        }
    }

    /**
     * 设置默认地址
     *
     * @param id
     */
    private void setDefaultAddress(String id) {
        RequestBean request = new RequestBean();
        RequestBean.ParamsBean params = new RequestBean.ParamsBean();
        params.setId(id);
        request.setParams(params);
        String jsonData = GsonManger.getGsonManger().toJson(request);
        mPresenter.request(UrlUtils.SET_DEFAULT_ADDRESS, jsonData, AddressBean.class);
    }

    /**
     * 删除地址
     *
     * @param id
     */
    private void deleteAddress(String id) {
        showProgressDialog("删除");
        RequestBean request = new RequestBean();
        RequestBean.ParamsBean params = new RequestBean.ParamsBean();
        params.setId(id);
        request.setParams(params);
        String jsonData = GsonManger.getGsonManger().toJson(request);
        mPresenter.request(UrlUtils.DEL_ADDRESS, jsonData, AddressBean.class);
    }

    /**
     * 获取地址列表
     */
    @Override
    protected void request() {
        super.request();
        mPresenter.request(UrlUtils.GET_ADDRESS_LIST, "", AddressBean.class);
    }


    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        super.requestSuccess(requestUrl, commonBean);
        if (TextUtils.equals(requestUrl, UrlUtils.GET_ADDRESS_LIST) || TextUtils.equals(requestUrl, UrlUtils.DEL_ADDRESS)) {
            AddressBean addressBean = (AddressBean) commonBean.getData();
            if (addressBean.getList().size() > 0) {
                addressBeanList.clear();
                addressBeanList.addAll(addressBean.getList());
                recyclerViewAdapter.notifyDataSetChanged();
            } else {
                mBaseLoadService.showCallback(NotDataCallback.class);
            }
        }
    }

    @Override
    public void requestFail(String requestUrl, String msg, int code) {
        super.requestFail(requestUrl, msg, code);
    }

    @OnClick(R.id.tv_right_menu)
    void addNewAddress() {
        Toast.makeText(MyAddressListActivity.this, "新增...", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MyAddressListActivity.this, MyAddressActivity.class);
        ActivityUtils.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //点击返回的时候在执行选择默认地址操作
        if (!TextUtils.isEmpty(defaultAddressId)) {
            setDefaultAddress(defaultAddressId);
        }
    }

}
