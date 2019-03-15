package com.hdnz.inanming.ui.activity.home;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hdnz.inanming.R;
import com.tsienlibrary.bean.CommonBean;
import com.hdnz.inanming.mvp.view.MVPActivity;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.tsienlibrary.statusbar.StatusBarUtil;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * <pre>
 *     author : Tsien
 *     e-mail : 974490643@qq.com
 *     time   : 2018/11/14
 *     desc   :
 * </pre>
 */
public class SearchActivity extends MVPActivity<MVPContract.View, MVPPresenter> {

    @BindView(R.id.et_search_content)
    EditText mEtSearchContent;
    @BindView(R.id.tv_search)
    TextView mTvSearch;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
    }

    @OnClick({R.id.iv_go_back, R.id.tv_search})
    void onClicks(View view) {
        switch (view.getId()) {
            case R.id.iv_go_back:
                this.finish();
                break;
            case R.id.tv_search:

                break;
        }
    }

    @Override
    protected void request() {
        super.request();
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
