package com.hdnz.inanming.ui.activity.message;

import android.widget.FrameLayout;
import android.widget.TextView;

import com.hdnz.inanming.R;
import com.hdnz.inanming.bean.AppBean;
import com.hdnz.inanming.bean.request.RequestBean;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPActivity;
import com.hdnz.inanming.ui.adapter.RecyclerViewAdapter;
import com.hdnz.inanming.ui.fragment.RecyclerViewFragment;
import com.hdnz.inanming.utils.UrlUtils;
import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.mvp.GsonManger;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;

/**
 * <pre>
 *     author : Tsien
 *     e-mail : 974490643@qq.com
 *     time   : 2018/11/12
 *     desc   :
 * </pre>
 */
public class SystemMessagesActivity extends MVPActivity<MVPContract.View, MVPPresenter> {

    private List<AppBean.DataBean> mAppDataList;

    private RecyclerViewAdapter mAdapter;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.fragment)
    FrameLayout mLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_common_listview;
    }

    @Override
    protected void initData() {
        mAppDataList = new ArrayList<>();
        mAppDataList.add(new AppBean.DataBean());
        mAppDataList.add(new AppBean.DataBean());
        mAppDataList.add(new AppBean.DataBean());
        mAppDataList.add(new AppBean.DataBean());
        mAppDataList.add(new AppBean.DataBean());
        mAppDataList.add(new AppBean.DataBean());
        request();
    }

    @Override
    protected void initView() {
        setToolBar(mToolbar, mTvTitle, R.string.system_messages);

        mAdapter = new RecyclerViewAdapter<AppBean.DataBean>(mAppDataList, R.layout.item_headline) {
            @Override
            public void bindView(RecyclerViewAdapter.MyViewHolder holder, int position) {
//                holder.setTextView(R.id.text_view, mAppDataList.get(position).getMsg());
            }
        };

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment, RecyclerViewFragment.newInstance(mAdapter, true));
        transaction.commit();
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
    protected void request() {
        super.request();
        RequestBean requestBean = new RequestBean();
        requestBean.setParams(new RequestBean.ParamsBean());
        String jsonData = GsonManger.getGsonManger().toJson(requestBean);
        mPresenter.request(UrlUtils.GET_MESSAGE_LIST, jsonData, AppBean.class);
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {
        super.requestSuccess(requestUrl, commonBean);
    }

    @Override
    public void requestFail(String requestUrl, String msg, int code) {
        super.requestFail(requestUrl, msg, code);
    }
}
