package com.hdnz.inanming.ui.fragment.home.govaffairsoffice.govavffairinfo.Itemdetails;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.text.TextUtils;
import android.widget.TextView;

import com.hdnz.inanming.R;
import com.hdnz.inanming.bean.result.ItemDetailsBean;
import com.hdnz.inanming.mvp.contract.MVPContract;
import com.hdnz.inanming.mvp.presenter.MVPPresenter;
import com.hdnz.inanming.mvp.view.MVPFragment;
import com.hdnz.inanming.ui.adapter.RecyclerViewAdapter;
import com.hdnz.inanming.webview.WebViewActivity;
import com.tsienlibrary.ui.widget.MultiItemDivider;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class AdmissionDocumentFragment extends MVPFragment<MVPContract.View, MVPPresenter> {

    private static List<ItemDetailsBean.DatumListBean> mDatumListList;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_admission_document;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        //添加分割线
        MultiItemDivider itemDivider = new MultiItemDivider(getActivity(), MultiItemDivider.VERTICAL_LIST, R.drawable.divider_horizontal);
//        itemDivider.setDividerMode(MultiItemDivider.INSIDE);//最后一个item下没有分割线
         itemDivider.setDividerMode(MultiItemDivider.END);//最后一个item下有分割线
        mRecyclerView.addItemDecoration(itemDivider);

        RecyclerViewAdapter mAdapter = new RecyclerViewAdapter<ItemDetailsBean.DatumListBean>(mDatumListList, R.layout.item_admission_document) {
            @Override
            public void bindView(MyViewHolder holder, int position) {
                holder.setTextView(R.id.tv_title, mDatumListList.get(position).getName());
                TextView tvContent = holder.getView(R.id.tv_content);
                if (TextUtils.equals(mDatumListList.get(position).getType(), "1")) {
                    tvContent.setTextColor(Color.BLUE);
                    tvContent.setText("点击查看");
                } else {
                    tvContent.setTextColor(getResources().getColor(R.color.colorTextTitle));
                    tvContent.setText(mDatumListList.get(position).getClaims());
                }
            }
        };

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (TextUtils.equals(mDatumListList.get(position).getType(), "1")) {
                    //查看地址
                    WebViewActivity.goToWebView(getActivity(), mDatumListList.get(position).getPath());
                } else {
                    //材料原件
                }
            }
        });
    }


    /**
     * fragment静态传值
     */
    public static AdmissionDocumentFragment newInstance(List<ItemDetailsBean.DatumListBean> datumListList) {
        AdmissionDocumentFragment fragment = new AdmissionDocumentFragment();
        mDatumListList = datumListList;
        return fragment;
    }

}
