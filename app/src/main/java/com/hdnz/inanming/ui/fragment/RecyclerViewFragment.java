package com.hdnz.inanming.ui.fragment;

import android.annotation.SuppressLint;

import com.hdnz.inanming.R;
import com.hdnz.inanming.ui.adapter.RecyclerViewAdapter;
import com.tsienlibrary.ui.fragment.BaseFragment;
import com.tsienlibrary.ui.widget.MultiItemDivider;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.Unbinder;

/**
 * <pre>
 *     author : Tsien
 *     e-mail : 974490643@qq.com
 *     time   : 2018/11/21
 *     desc   :
 * </pre>
 */
@SuppressLint("ValidFragment")
public class RecyclerViewFragment extends BaseFragment {

    protected Unbinder unbinder;

    //是否添加分割线
    private static boolean mIsItemDivider;
    private RecyclerViewAdapter mAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    public RecyclerViewFragment() {
    }

    public RecyclerViewFragment(RecyclerViewAdapter adapter) {
        this.mAdapter = adapter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recyclerview;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        if (mIsItemDivider) {
            //添加分割线
            MultiItemDivider itemDivider = new MultiItemDivider(getActivity(), MultiItemDivider.VERTICAL_LIST, R.drawable.divider_horizontal);
            itemDivider.setDividerMode(MultiItemDivider.INSIDE);//最后一个item下没有分割线
            // itemDivider.setDividerMode(MultiItemDivider.END);//最后一个item下有分割线
            mRecyclerView.addItemDecoration(itemDivider);
        }
        mRecyclerView.setAdapter(mAdapter);
    }


    /**
     * fragment静态传值
     */
    public static RecyclerViewFragment newInstance(RecyclerViewAdapter adapter, boolean isItemDivider) {
        RecyclerViewFragment fragment = new RecyclerViewFragment(adapter);
        mIsItemDivider = isItemDivider;
        return fragment;
    }


}
