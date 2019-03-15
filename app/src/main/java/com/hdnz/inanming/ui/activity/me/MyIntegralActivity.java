package com.hdnz.inanming.ui.activity.me;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.TimeUtils;
import com.hdnz.inanming.R;
import com.hdnz.inanming.bean.request.RequestBean;
import com.hdnz.inanming.bean.result.IntegralBean;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPActivity;
import com.hdnz.inanming.ui.adapter.RecyclerViewAdapter;
import com.hdnz.inanming.ui.fragment.RecyclerViewFragment;
import com.hdnz.inanming.utils.UrlUtils;
import com.ms.banner.Banner;
import com.ms.banner.BannerConfig;
import com.ms.banner.holder.BannerViewHolder;
import com.ms.banner.holder.HolderCreator;
import com.ms.banner.listener.OnBannerClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.mvp.GsonManger;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

public class MyIntegralActivity extends MVPActivity<MVPContract.View, MVPPresenter> {

    private int pageIndex;
    private String profile;

    private List<IntegralBean.TransferDataBean> sumIntegralList;
    private List<IntegralBean.TransferDataBean> integralList;
    RecyclerViewAdapter mAdapter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.banner1)
    Banner banner;
//    @BindView(R.id.recycler_view)
//    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_integral;
    }

    @Override
    protected void initData() {
        sumIntegralList = new ArrayList<>();
        integralList = new ArrayList<>();
        pageIndex = 1;
    }

    @Override
    protected void initView() {
        setToolBar(mToolbar, mTvTitle, R.string.my_integral);

        initBanner();
        initRecyclerView();
        refreshLayout.setEnableRefresh(false);//是否启用下拉刷新功能
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageIndex++;
                getIntegral(profile, pageIndex);
            }
        });
        request();
    }

    /**
     * 初始化轮播控件（总积分）
     */
    private void initBanner() {

        banner.setAutoPlay(false)//是否自动轮播
                .setPages(sumIntegralList, new HolderCreator<BannerViewHolder>() {
                    @Override
                    public BannerViewHolder createViewHolder() {
                        return new MyBannerViewHolder();
                    }
                })
                .setOnBannerClickListener(new OnBannerClickListener() {
                    @Override
                    public void onBannerClick(int position) {
                        Toast.makeText(MyIntegralActivity.this, "你点击了：" + position, Toast.LENGTH_SHORT).show();
                    }
                })
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
//                .setBannerAnimation(Transformer.Scale)
                .start();
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (sumIntegralList != null && sumIntegralList.size() > 0) {
                    showProgressDialog("请稍等");
                    profile = sumIntegralList.get(position).getProfile();
                    pageIndex = 1;
                    getIntegral(profile, pageIndex);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化RecyclerView（积分列表）
     */
    private void initRecyclerView() {
        mAdapter = new RecyclerViewAdapter<IntegralBean.TransferDataBean>(integralList, R.layout.item_integral) {
            @Override
            public void bindView(RecyclerViewAdapter.MyViewHolder holder, int position) {
                holder.setTextView(R.id.tv_title, integralList.get(position).getName());
                holder.setTextView(R.id.tv_subhead, integralList.get(position).getName());
                holder.setTextView(R.id.tv_value, "+" + integralList.get(position).getValue());
                holder.setTextView(R.id.tv_time, TimeUtils.millis2String(integralList.get(position).getChangeTime()));
            }
        };


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment, RecyclerViewFragment.newInstance(mAdapter, true));
        transaction.commit();

//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(RecyclerView.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);
//        //添加分割线
//        MultiItemDivider itemDivider = new MultiItemDivider(this, MultiItemDivider.VERTICAL_LIST, R.drawable.divider_horizontal);
//        itemDivider.setDividerMode(MultiItemDivider.INSIDE);//最后一个item下没有分割线
//        recyclerView.addItemDecoration(itemDivider);
//        recyclerView.setAdapter(integralAdapter);
    }


    @Override
    protected boolean isLoadSir() {
        return true;
    }

    @Override
    protected void initLoadSir(Object target) {
        super.initLoadSir(banner);
    }

    @Override
    protected void request() {
        super.request();
        //获得三种积分的总数
        mPresenter.request(UrlUtils.GET_SUM_INTEGRAL_LIST, "", IntegralBean.class);
    }

    /**
     * 获取每种积分的列表
     *
     * @param pageIndex
     */
    private void getIntegral(String profile, int pageIndex) {

        RequestBean request = new RequestBean();
        RequestBean.ParamsBean params = new RequestBean.ParamsBean();
        RequestBean.PageBean page = new RequestBean.PageBean();
        params.setProfile(profile);
        page.setPageSize(10);
        page.setPageIndex(pageIndex);
        request.setParams(params);
        request.setPage(page);
        String jsonData = GsonManger.getGsonManger().toJson(request);
        mPresenter.request(UrlUtils.GET_INTEGRAL_LIST, jsonData, IntegralBean.class);
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        super.requestSuccess(requestUrl, commonBean);
        if (TextUtils.equals(requestUrl, UrlUtils.GET_SUM_INTEGRAL_LIST)) {
            IntegralBean integralBean = (IntegralBean) commonBean.getData();
            banner.update(integralBean.getTransferData());//此处要传入新的list，不可使用sumIntegralList（原因是，源代码会清楚初始化传入的list）
//            sumIntegralList.addAll(commonBeanList.getData());//必须写在banner.update()之后，update()clear该list
        } else if (TextUtils.equals(requestUrl, UrlUtils.GET_INTEGRAL_LIST)) {
            IntegralBean integralBean = (IntegralBean) commonBean.getData();
            if (pageIndex <= 1) {
                integralList.clear();
                integralList.addAll(integralBean.getTransferData());
            } else {
                if(integralBean.getTransferData().size()>0){
                    integralList.addAll(integralBean.getTransferData());
                    refreshLayout.finishLoadMore();
                }else {
                    refreshLayout.finishLoadMore();
                    pageIndex--;
                }
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void requestFail(String requestUrl, String msg, int code) {
        super.requestFail(requestUrl, msg, code);
    }


    private class MyBannerViewHolder implements BannerViewHolder<IntegralBean.TransferDataBean> {

        private RelativeLayout mRlSumIntegral;
        private TextView mTvTitle;
        private TextView mTvSum;

        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_sum_integral, null);
            mRlSumIntegral = view.findViewById(R.id.rl_sum_integral);
            mTvTitle = view.findViewById(R.id.tv_title);
            mTvSum = view.findViewById(R.id.tv_sum);
            return view;
        }

        @Override
        public void onBind(Context context, int position, IntegralBean.TransferDataBean integral) {
            // 数据绑定
            mTvTitle.setText(integral.getName());
            mTvSum.setText(integral.getCount());
            if(TextUtils.equals(integral.getProfile(),"gold")){
                mRlSumIntegral.setBackgroundResource(R.drawable.bg_sum_integral_yellow);
            }else if(TextUtils.equals(integral.getProfile(),"government")){
                mRlSumIntegral.setBackgroundResource(R.drawable.bg_sum_integral_bule);
            }else if(TextUtils.equals(integral.getProfile(),"purchase")){
                mRlSumIntegral.setBackgroundResource(R.drawable.bg_sum_integral_red);
            }
        }
    }

}
