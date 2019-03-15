package com.hdnz.inanming.ui.fragment.transaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.hdnz.inanming.R;
import com.hdnz.inanming.ui.activity.transaction.checkProgress.CheckProgressDetailActivity;
import com.hdnz.inanming.ui.activity.transaction.detail.TransactionDetailActivity;
import com.hdnz.inanming.ui.adapter.RecyclerViewAdapter;
import com.hdnz.inanming.ui.base.MVPBaseLazyFragment;
import com.hdnz.inanming.ui.view.DrawableHorizontalButton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;

import static android.app.Activity.RESULT_OK;

/**
 * Copyright (C), 2017-2018, 华电南自（贵州）科技有限公司
 * FileName:    TransactionFragment.java
 * Author:      肖昕
 * Email:       xiaox@huadiannanzi.com
 * Date:        2018-11-14 18:53
 * Description:
 * Version:     V1.0.0
 * History:     历史信息
 */
@SuppressLint("ValidFragment")
public class TransactionFragment extends MVPBaseLazyFragment<TransactionContract.View, TransactionPresenter>
        implements TransactionContract.View, BGASortableNinePhotoLayout.Delegate {
    @BindView(R.id.rv_transactions)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    /**
     * @field 上下文
     */
    private Context mContext;
    /**
     * @field 请求链接
     */
    private String mUrl;
    /**
     * @field 类型
     */
    private String mType;
    /**
     * @field 黄油刀
     */
    private Unbinder unbinder;
    private List<String> datas = new ArrayList<>();
    private RecyclerViewAdapter<String> adapter;
    /**
     * @field 是否第一次懒加载数据
     */
    private boolean isFirstLoad;
    /**
     * @field 权限
     */
    private RxPermissions rxPermissions;
    /**
     * @field 选择图片RESULT_CODE
     */
    private static final int RC_CHOOSE_PHOTO = 1;
    /**
     * @field 预览图片RESULT_CODE
     */
    private static final int RC_PHOTO_PREVIEW = 2;
    /**
     * @field 九宫格控件，继承RecyclerView
     */
    private BGASortableNinePhotoLayout mPhotosSnpl;

    public TransactionFragment() {

    }

    public TransactionFragment(String url) {
        this.mUrl = url;
    }

    public TransactionFragment(String url, String type) {
        this.mUrl = url;
        this.mType = type;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    /**
     * fragment静态传值
     */
    public static TransactionFragment newInstance() {
        return new TransactionFragment();
    }

    /**
     * fragment静态传值url
     *
     * @param url
     * @return
     */
    public static TransactionFragment newInstance(String url) {
        return new TransactionFragment(url);
    }

    /**
     * fragment静态传
     *
     * @param url
     * @param type
     * @return
     */
    public static TransactionFragment newInstance(String url, String type) {
        return new TransactionFragment(url, type);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_transaction;
    }

    @Override
    protected void initData() {

        rxPermissions = new RxPermissions(this);
    }

    /**
     * 初始化
     */
    @Override
    protected void initView() {
        if (isFirstLoad) {
            refreshLayout.autoRefresh();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(mContext, R.color.white), 2, 16));
        }

        //下拉加载
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                LogUtils.e("xiaoxin", "onRefresh:  " + "mType: " + mUrl + "isFirstLoad: " + isFirstLoad);
                if (!isFirstLoad) {
                    getData(0);
                }

                adapter = new RecyclerViewAdapter<String>(datas, R.layout.item_transaction) {
                    @Override
                    public void bindView(MyViewHolder holder, int position) {
                        ((TextView) holder.getView(R.id.btn_grid_personnel)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(mContext, "网格员", Toast.LENGTH_SHORT).show();
                                //请求网络
                                openPopupWindow(v, R.layout.pop_grid_personal, position);
                            }
                        });
                        ((TextView) holder.getView(R.id.btn_view_detail)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(mContext, "查看详情", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(mContext, TransactionDetailActivity.class);
//                                Intent intent = new Intent(mContext, TransactionDetail2Activity.class);
                                ActivityUtils.startActivity(intent);
                            }
                        });
                        ((TextView) holder.getView(R.id.btn_evaluation_service)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(mContext, "评价服务", Toast.LENGTH_SHORT).show();
                                mPresenter.setPermissions(rxPermissions, v, position, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE);
                            }
                        });
                        ((RelativeLayout) holder.getView(R.id.rl_one_cont)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(mContext, "状态详情..", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(mContext, CheckProgressDetailActivity.class);
                                ActivityUtils.startActivity(intent);
                            }
                        });
                    }
                };
                //更改第一次加载状态
                isFirstLoad = false;
                mRecyclerView.setAdapter(adapter);
                refreshLayout.finishRefresh(200);
            }
        });

        //上拉加载
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                getData(1);
                adapter.notifyDataSetChanged();
                refreshLayout.finishLoadMore(200);
            }
        });
    }

    @Override
    public void requestSuccess(Object o, Boolean isFirstLoad) {

    }

    @Override
    public void requestFail(String msg) {

    }

    @Override
    public void permissionsAreGranted(View attachView, int position) {
        openPopupWindow(attachView, R.layout.pop_evaluate_service, position);
    }

    @Override
    public void goToSettings() {
        AppUtils.launchAppDetailsSettings();
    }

    @Override
    protected void findPopupView(View popView, View attachView, int position) {
        super.findPopupView(popView, attachView, position);
        if (null != popView && null != attachView) {
            switch (attachView.getId()) {
                case R.id.btn_grid_personnel:
                    Toast.makeText(mContext, "网格员1..", Toast.LENGTH_SHORT).show();
                    findGridPersionalView(popView, position);
                    break;
                case R.id.btn_evaluation_service:
                    Toast.makeText(mContext, "评价1..", Toast.LENGTH_SHORT).show();
                    findEvaluateView(popView, position);
                    break;
            }
        }
    }

    @Override
    protected void lazyLoadData() {
        //LogUtils.e("xiaoxin", "lazyLoadData:  " + "mType: " + mUrl);
        //此处预加载数据
        getData(0);
        isFirstLoad = true;
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        LogUtils.e("xiaoxin", "onFragmentVisibleChange:  " + isVisible, "mType: " + mUrl);
        if (isVisible) {
            if (rootView != null) {
                unbinder = ButterKnife.bind(this, rootView);
                initView();
            }
        }
    }

    @Override
    public void onDestroyView() {
        LogUtils.e("xiaoxin onDestroyView: mType: " + mUrl + "    unbinder:" + unbinder);
        //解绑黄油刀
        if (unbinder != null) {
            unbinder.unbind();
            //此处置空很坑的
            unbinder = null;
        }
        super.onDestroyView();
    }

    private void getData(int type) {
        if (type == 0) {
            datas.clear();
            datas.add("1");
            datas.add("2");
            datas.add("3");
            datas.add("4");
            datas.add("5");
        } else {
            for (int i = 0; i < 5; i++) {
                datas.add("" + i);
            }
        }
    }

    /**
     * 查找popwindow相关控件——网格员，然后设置值
     *
     * @param view
     * @param position
     */
    private void findGridPersionalView(View view, int position) {
        TextView gridInfo = (TextView) view.findViewById(R.id.tv_grid_info);
        ImageView gridClose = (ImageView) view.findViewById(R.id.iv_grid_close);
        ImageView gridHeader = (ImageView) view.findViewById(R.id.iv_grid_header);
        TextView gridAddress = (TextView) view.findViewById(R.id.tv_grid_address);
        DrawableHorizontalButton gridDial = (DrawableHorizontalButton) view.findViewById(R.id.btn_grid_dial);
        //关闭弹窗
        gridClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePopWindow();
            }
        });
        //拨打电话
        gridDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhone(gridDial.getText().toString());
            }
        });
    }

    /**
     * 查找popwindow相关控件——评论服务，然后设置值
     *
     * @param view
     * @param position
     */
    private void findEvaluateView(View view, int position) {
        mPhotosSnpl = (BGASortableNinePhotoLayout) view.findViewById(R.id.bnp_moment_add_photos);
        //设置图片选择最大数
        mPhotosSnpl.setMaxItemCount(12);
        //是否编辑
        mPhotosSnpl.setEditable(true);
        //是否打开新增图片
        mPhotosSnpl.setPlusEnable(true);
        //是否拖拽
        mPhotosSnpl.setSortable(true);
        // 设置拖拽排序控件的代理
        mPhotosSnpl.setDelegate(TransactionFragment.this);
        ImageView evaluateClose = (ImageView) view.findViewById(R.id.iv_evaluate_close);
        RatingBar commentStar = (RatingBar) view.findViewById(R.id.rb_comment_star);
        EditText commentEdit = (EditText) view.findViewById(R.id.et_comment);
        Button submit = (Button) view.findViewById(R.id.btn_submit);

        //关闭弹窗
        evaluateClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePopWindow();
            }
        });

        //评分控件监听
        commentStar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(mContext, "rating:" + String.valueOf(rating),
                        Toast.LENGTH_LONG).show();
            }
        });

        //编辑评论控件获取值
        setEditTextHintSize(commentEdit, getResources().getString(R.string.please_input_comment), 14);
        //提交评论按钮
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "提交评论..." + mPhotosSnpl.getData().size(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    private void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    @Override
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        //点击新增图片按钮
        choicePhotoWrapper(sortableNinePhotoLayout);
    }

    @Override
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        //点击图片上的删除按钮
        sortableNinePhotoLayout.removeItem(position);
    }

    @Override
    public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        //点击图片item，进行图片预览
        Intent photoPickerPreviewIntent = new BGAPhotoPickerPreviewActivity.IntentBuilder(mContext)
                // 当前预览的图片路径集合
                .previewPhotos(models)
                // 当前已选中的图片路径集合
                .selectedPhotos(models)
                // 图片选择张数的最大值
                .maxChooseCount(sortableNinePhotoLayout.getMaxItemCount())
                // 当前预览图片的索引
                .currentPosition(position)
                // 是否是拍完照后跳转过来
                .isFromTakePhoto(false)
                .build();
        startActivityForResult(photoPickerPreviewIntent, RC_PHOTO_PREVIEW);
    }

    @Override
    public void onNinePhotoItemExchanged(BGASortableNinePhotoLayout sortableNinePhotoLayout, int fromPosition, int toPosition, ArrayList<String> models) {
        //拖拽重新排序的监听
        Toast.makeText(mContext, "排序发生变化", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //判断是否是单选图片，这里直接是false(多选)
        if (resultCode == RESULT_OK && requestCode == RC_CHOOSE_PHOTO) {
            if (false) {
                mPhotosSnpl.setData(BGAPhotoPickerActivity.getSelectedPhotos(data));
            } else {
                mPhotosSnpl.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));
            }
        } else if (requestCode == RC_PHOTO_PREVIEW) {
            mPhotosSnpl.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
        }
    }

    /**
     * 选择图片
     *
     * @param mPhotosSnpl
     */
    private void choicePhotoWrapper(BGASortableNinePhotoLayout mPhotosSnpl) {
        // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
        File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerTakePhoto");

        Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(mContext)
                // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
                .cameraFileDir(takePhotoDir)
                // 图片选择张数的最大值
                .maxChooseCount(mPhotosSnpl.getMaxItemCount() - mPhotosSnpl.getItemCount())
                // 当前已选中的图片路径集合
                .selectedPhotos(null)
                // 滚动列表时是否暂停加载图片
                .pauseOnScroll(false)
                .build();
        startActivityForResult(photoPickerIntent, RC_CHOOSE_PHOTO);
    }
}
