package com.hdnz.inanming.ui.activity.transaction.checkProgress;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hdnz.inanming.R;
import com.hdnz.inanming.bean.ProgressDetailBean;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPActivity;
import com.hdnz.inanming.ui.activity.transaction.MineTransactionContract;
import com.hdnz.inanming.ui.adapter.RecyclerViewAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import io.reactivex.annotations.NonNull;

public class CheckProgressDetailActivity extends MVPActivity<MVPContract.View, MVPPresenter> {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right_menu)
    TextView tvRightMenu;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.btn_refresh)
    Button btnRefresh;
    @BindView(R.id.rl_error_page)
    RelativeLayout rlErrorPage;
    @BindView(R.id.rl_empty_page)
    RelativeLayout rlEmptyPage;
    @BindView(R.id.rv_progress_list)
    RecyclerView rvProgressList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_check_progress_detail;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        //设置title
        setToolBar(mToolbar, tvTitle, getResources().getString(R.string.progress_details));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvProgressList.setLayoutManager(linearLayoutManager);
        refreshLayout.autoRefresh();
        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                requestData();
            }
        });
    }

    /**
     * 数据请求
     */
    private void requestData() {
    }
//
//    @Override
//    public void requestSuccess(ProgressDetailBean progressDetailBean) {
//        rlErrorPage.setVisibility(View.GONE);
//        if (null != progressDetailBean) {
//            List<ProgressDetailBean.DataBean> datas = progressDetailBean.getData();
//            if (null != datas && datas.size() > 0) {
//                rvProgressList.setVisibility(View.VISIBLE);
//                rlEmptyPage.setVisibility(View.GONE);
//                setData(datas);
//            }else {
//                rvProgressList.setVisibility(View.GONE);
//                rlEmptyPage.setVisibility(View.VISIBLE);
//            }
//        } else {
//            rvProgressList.setVisibility(View.GONE);
//            rlEmptyPage.setVisibility(View.VISIBLE);
//        }
//        refreshLayout.finishRefresh(200);
//    }

    /**
     * 为列表设置数据
     * @param datas
     */
    private void setData(List<ProgressDetailBean.DataBean> datas) {
        RecyclerViewAdapter<ProgressDetailBean.DataBean> recyclerViewAdapter = new RecyclerViewAdapter(datas, R.layout.item_check_progress_time_line) {
            @Override
            public void bindView(MyViewHolder holder, int position) {
                ProgressDetailBean.DataBean dataBean = datas.get(position);
                if (dataBean.isCheck()) {
                    holder.setImageView(R.id.iv_check_progress_img, R.drawable.check_normal);
                }else {
                    holder.setImageView(R.id.iv_check_progress_img, R.drawable.check_default);
                }
                if (position == datas.size() - 1) {
                    holder.getView(R.id.v_time_line).setVisibility(View.GONE);
                }else {
                    holder.getView(R.id.v_time_line).setVisibility(View.VISIBLE);
                }
                holder.setTextView(R.id.tv_date, dataBean.getDate());
                holder.setTextView(R.id.tv_time, dataBean.getTime());
                holder.setTextView(R.id.tv_check_status, dataBean.getStatus());
                holder.setTextView(R.id.tv_check_desc, dataBean.getDescription());
            }
        };
        rvProgressList.setAdapter(recyclerViewAdapter);
    }

//    @Override
//    public void requestFail(String msg) {
//        rlErrorPage.setVisibility(View.VISIBLE);
//        rvProgressList.setVisibility(View.GONE);
//        rlEmptyPage.setVisibility(View.GONE);
//        refreshLayout.finishRefresh(200);
//    }
}
