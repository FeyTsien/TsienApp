package com.hdnz.inanming.ui.fragment.me.collect;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hdnz.inanming.R;
import com.hdnz.inanming.app.AppData;
import com.hdnz.inanming.app.GlideApp;
import com.hdnz.inanming.bean.result.HeadLineBean;
import com.hdnz.inanming.bean.request.RequestBean;
import com.hdnz.inanming.bean.result.ShopBean;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPFragment;
import com.hdnz.inanming.ui.adapter.RecyclerViewAdapter;
import com.hdnz.inanming.utils.UrlUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.loadsir.callback.NotDataCallback;
import com.tsienlibrary.ui.widget.MultiItemDivider;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * date:2017/6/7
 */


@SuppressLint("ValidFragment")
public class CollectShopsFragment extends MVPFragment<MVPContract.View, MVPPresenter> {

    //当前指定页
    private int pageIndex = 1;

    private List<ShopBean.RowsBean> mShopsList;
    private RecyclerViewAdapter mAdapter;

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_content;
    }

    @Override
    protected void initData() {
        mShopsList = new ArrayList<>();
    }

    @Override
    protected void initView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        //添加分割线
        MultiItemDivider itemDivider = new MultiItemDivider(getActivity(), MultiItemDivider.VERTICAL_LIST, R.drawable.divider_horizontal);
        itemDivider.setDividerMode(MultiItemDivider.INSIDE);//最后一个item下没有分割线
        // itemDivider.setDividerMode(MultiItemDivider.END);//最后一个item下有分割线
//        mRecyclerView.addItemDecoration(itemDivider);

        mAdapter = new RecyclerViewAdapter<ShopBean.RowsBean>(mShopsList, R.layout.item_shop) {
            @Override
            public void bindView(RecyclerViewAdapter.MyViewHolder holder, int position) {
                //logo图
                GlideApp.with(getActivity())
                        .load(mShopsList.get(position).getPic())
                        .placeholder(R.drawable.test)
                        .error(R.drawable.empty)
                        .into((ImageView) holder.getView(R.id.iv_pic));
                //名称
                holder.setTextView(R.id.tv_shop_name, mShopsList.get(position).getName());
                //人均消费
                TextView tvConsumption = holder.getView(R.id.tv_consumption);
                if (TextUtils.isEmpty(mShopsList.get(position).getConsumerPer())) {
                    tvConsumption.setVisibility(View.GONE);
                } else {
                    tvConsumption.setText("人均" + mShopsList.get(position).getConsumerPer() + "元");
                    tvConsumption.setVisibility(View.VISIBLE);
                }
                //店铺类型
                TextView tvShopType = holder.getView(R.id.tv_shop_type);
                if (TextUtils.isEmpty(mShopsList.get(position).getStorecategoryName())) {
                    tvShopType.setVisibility(View.GONE);
                } else {
                    tvShopType.setText(mShopsList.get(position).getStorecategoryName());
                    tvShopType.setVisibility(View.VISIBLE);
                }
                //地址
                TextView tvAddress = holder.getView(R.id.tv_address);
                if (TextUtils.isEmpty(mShopsList.get(position).getAddress())) {
                    tvAddress.setVisibility(View.GONE);
                } else {
                    tvAddress.setText(mShopsList.get(position).getAddress());
                    tvAddress.setVisibility(View.VISIBLE);
                }
                //距离
                TextView tvDistance = holder.getView(R.id.tv_distance);
                if (TextUtils.isEmpty(mShopsList.get(position).getStoreDistance())) {
                    tvDistance.setVisibility(View.GONE);
                } else {
                    tvDistance.setText(mShopsList.get(position).getStoreDistance());
                    tvDistance.setVisibility(View.VISIBLE);
                }

                //排名
                TextView tvRank = holder.getView(R.id.tv_rank);
                if (TextUtils.isEmpty(mShopsList.get(position).getStorecategoryrankName()) && TextUtils.isEmpty(mShopsList.get(position).getStorecategoryrankOrders())) {
                    tvRank.setVisibility(View.GONE);
                } else {
                    tvRank.setVisibility(View.VISIBLE);
                    tvRank.setText(mShopsList.get(position).getStorecategoryrankName() + " 第" + mShopsList.get(position).getStorecategoryrankOrders() + "名");
                }
                //折扣
                LinearLayout llDiscount = holder.getView(R.id.ll_discount);
                if (mShopsList.get(position).getPromotions().size() > 0) {
                    llDiscount.setVisibility(View.VISIBLE);
                    holder.setTextView(R.id.tv_discount, "买单" + (mShopsList.get(position).getPromotions().get(0).getDiscounvalue() / 10) + "折");
                } else {
                    llDiscount.setVisibility(View.GONE);
                }
                //价目表
                holder.getView(R.id.ll_price_list).setVisibility(TextUtils.isEmpty(mShopsList.get(position).getPricepics()) ? View.GONE : View.VISIBLE);
                //店长推荐
                holder.getView(R.id.ll_recommend).setVisibility(TextUtils.isEmpty(mShopsList.get(position).getRecommendpics()) ? View.GONE : View.VISIBLE);

            }
        };

        mRecyclerView.setAdapter(mAdapter);


        //下拉刷新
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //上拉加载更多
                pageIndex++;
                request();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //下拉刷新
                pageIndex = 1;
                request();
            }
        });

        request();
    }


    @Override
    protected boolean isLoadSir() {
        return true;
    }

    @Override
    protected void request() {
        RequestBean requestBean = new RequestBean();
        RequestBean.PageBean pageBean = new RequestBean.PageBean();
        pageBean.setPageIndex(pageIndex);
        pageBean.setPageSize(10);
        requestBean.setPage(pageBean);
        RequestBean.ParamsBean paramsBean = new RequestBean.ParamsBean();
        paramsBean.setMemberId("1");
        paramsBean.setCurrentPosition(AppData.getValueStr(AppData.KEY_LOCATION_LONGITUDE) + "," + AppData.getValueStr(AppData.KEY_LOCATION_LATITUDE));
        requestBean.setParams(paramsBean);
        Gson gson = new Gson();
        String jsonData = gson.toJson(requestBean);
        mPresenter.request(UrlUtils.COLLECT_SHOPS, jsonData, ShopBean.class);
        super.request();
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        super.requestSuccess(requestUrl, commonBean);
        //获取到指定list，发送给WokebenchFragment,进行刷新
        ShopBean shopBean = (ShopBean) commonBean.getData();
        if (pageIndex <= 1) {
            mSmartRefreshLayout.finishRefresh();
            if (shopBean.getRows().size() == 0) {
                //没有数据
                mBaseLoadService.showCallback(NotDataCallback.class);
                return;
            } else {
                mShopsList.clear();
                mShopsList.addAll(shopBean.getRows());
            }
        } else {
            if (pageIndex >= shopBean.getPages()) {
                //当前页和总页数相同
                //完成加载并标记没有更多数据
                mSmartRefreshLayout.finishLoadMoreWithNoMoreData();
                pageIndex = shopBean.getPages();
            } else {
                //完成加载
                mSmartRefreshLayout.finishLoadMore();
            }
            mShopsList.addAll(shopBean.getRows());
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestFail(String requestUrl, String msg, int code) {
        super.requestFail(requestUrl, msg, code);
    }

    /**
     * fragment静态传值
     */
    public static CollectShopsFragment newInstance() {
        CollectShopsFragment fragment = new CollectShopsFragment();
        return fragment;
    }


    /**
     * 启用懒加载
     *
     * @return
     */
    @Override
    protected boolean isLazyLoad() {
        return true;
    }

}
