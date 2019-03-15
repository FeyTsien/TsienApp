package com.hdnz.inanming.ui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.hdnz.inanming.R;
import com.hdnz.inanming.app.GlideApp;
import com.hdnz.inanming.bean.TransactionDetailBean;
import com.hdnz.inanming.ui.activity.transaction.detail.TransactionDetailActivity;
import com.hdnz.inanming.utils.PopupWindowUtils;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (C), 2017-2018, 华电南自（贵州）科技有限公司
 * FileName:    TransactionDetailAdapter.java
 * Author:      肖昕
 * Email:       xiaox@huadiannanzi.com
 * Date:        2018-11-20 14:21
 * Description: 办理证明详情adapter
 * Version:     V1.0.0
 * History:     历史信息
 */
public class TransactionDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /**
     * @field 上下文
     */
    private Context mContext;
    /**
     * @field 数据源
     */
    private List<TransactionDetailBean.DataBean> datas;

    public TransactionDetailAdapter() {
    }

    public TransactionDetailAdapter(Context context, List<TransactionDetailBean.DataBean> datas) {
        this.mContext = context;
        this.datas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case 1:
                View view1 = LayoutInflater.from(mContext).inflate(R.layout.item_transaction_detail_status_and_name, parent, false);
                viewHolder = new Type1ViewHolder(view1);
                break;
            case 2:
                View view2 = LayoutInflater.from(mContext).inflate(R.layout.item_transaction_detail_managed_object, parent, false);
                viewHolder = new Type2ViewHolder(view2);
                break;
            case 3:
                View view3 = LayoutInflater.from(mContext).inflate(R.layout.item_transaction_detail_verification_code, parent, false);
                viewHolder = new Type3ViewHolder(view3);
                break;
            case 4:
                View view4 = LayoutInflater.from(mContext).inflate(R.layout.item_transaction_detail_managed_person, parent, false);
                viewHolder = new Type4ViewHolder(view4);
                break;
            case 5:
                View view5 = LayoutInflater.from(mContext).inflate(R.layout.item_transaction_detail_cut_off_date, parent, false);
                viewHolder = new Type5ViewHolder(view5);
                break;
            case 6:
                View view6 = LayoutInflater.from(mContext).inflate(R.layout.item_transaction_detail_idcard_material, parent, false);
                viewHolder = new Type6ViewHolder(view6);
                break;
            case 7:
                View view7 = LayoutInflater.from(mContext).inflate(R.layout.item_transaction_detail_bareheaded_photo_material, parent, false);
                viewHolder = new Type7ViewHolder(view7);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TransactionDetailBean.DataBean dataBean = datas.get(position);
        switch (getItemViewType(position)) {
            case 1:
                Type1ViewHolder type1ViewHolder = (Type1ViewHolder) holder;
                type1ViewHolder.matterName.setText(dataBean.getStatus());
                type1ViewHolder.mTransactionStatus.setText(dataBean.getTransationName());
                type1ViewHolder.mProvetType.setText(dataBean.getType());
                break;
            case 2:
                break;
            case 3:
                Type3ViewHolder type3ViewHolder = (Type3ViewHolder) holder;
                TransactionDetailActivity activity = (TransactionDetailActivity) this.mContext;
                type3ViewHolder.mVerifyContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new PopupWindowUtils(activity).openPopupWindowByLayout(v, R.layout.pop_verification_code, new PopupWindowUtils.PopWindowListener() {
                            @Override
                            public void popWindowView(View popView, View attachView, PopupWindow popupWindow) {
                                ImageView verifyClose = (ImageView) popView.findViewById(R.id.iv_verify_close);
                                ImageView verifyImage = (ImageView) popView.findViewById(R.id.iv_verify_img);
                                TextView verifyId = (TextView) popView.findViewById(R.id.tv_verify_id);
                                //关闭
                                verifyClose.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        popupWindow.dismiss();
                                    }
                                });
                                //验证码图片
                                GlideApp.with(mContext)
                                        .load(dataBean.getVerifyCodeUrl())
                                        .placeholder(R.drawable.test)
                                        .error(R.drawable.empty).into(verifyImage);
                                //验证码编号文本
                                verifyId.setText(dataBean.getVerifyCode());
                            }
                        });
                    }
                });
                break;
            case 4:
                Type4ViewHolder type4ViewHolder = (Type4ViewHolder) holder;
                //联系他
                type4ViewHolder.mContactHim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle("提示");
                        builder.setMessage("您确定拨打电话：" + dataBean.getPhone() + "吗？");
                        builder.setPositiveButton("拨打", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                callPhone(dataBean.getPhone());
                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
//                        builder.setView(R.layout.common_dialog_layout);
                        builder.create().show();
                    }
                });
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (null == datas || datas.size() == 0) {
            return 99;
        }
        return datas.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return null != datas ? datas.size() : 0;
    }

    /**
     * 布局类型1加载item_transaction_detail_status_and_name（办理状态和证明名称、类型布局）
     */
    public class Type1ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_transaction_status)
        TextView mTransactionStatus;
        @BindView(R.id.btn_cancel_order)
        Button mOrder;
        @BindView(R.id.tv_matter_name)
        TextView matterName;
        @BindView(R.id.tv_prove_type)
        TextView mProvetType;

        public Type1ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 布局类型2加载item_transaction_detail_managed_object（办理对象布局）
     */
    public class Type2ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_header_img)
        ImageView mHeaderImg;
        @BindView(R.id.tv_name)
        TextView mName;
        @BindView(R.id.tv_sex_and_address)
        TextView mSexAndAddr;
        @BindView(R.id.tv_idCard_num)
        TextView mIdCard;
        @BindView(R.id.tv_phone_number)
        TextView mPhone;

        public Type2ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 布局类型3加载item_transaction_detail_verification_code（验证码布局）
     */
    public class Type3ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_verification_code)
        ImageView mVerifyImg;
        @BindView(R.id.tv_verification_code)
        TextView mVerifyCode;
        @BindView(R.id.rl_verify_cont)
        RelativeLayout mVerifyContent;

        public Type3ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 布局类型4加载item_transaction_detail_managed_person（办理人布局）
     */
    public class Type4ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_managed_grid_person)
        TextView mamanagedPerson;
        @BindView(R.id.btn_contact_with_him)
        Button mContactHim;

        public Type4ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 布局类型5加载item_transaction_detail_cut_off_date（截止日期布局）
     */
    public class Type5ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_cut_off_date)
        TextView mCutOffDate;

        public Type5ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 布局类型6加载item_transaction_detail_idcard_material（办理材料-身份证布局）
     */
    public class Type6ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_idCard_apply)
        TextView mIdCardApply;
        @BindView(R.id.tv_idCard_text)
        TextView mTypeIdCard;
        @BindView(R.id.tv_auth_status)
        TextView mAuthStatus;
        @BindView(R.id.iv_idCard_face)
        ImageView mIdCardFaceImg;
        @BindView(R.id.iv_idCard_back)
        ImageView mIdCardBackImg;
        @BindView(R.id.iv_idCard_hand)
        ImageView mIdCardHandleImg;

        public Type6ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 布局类型7加载item_transaction_detail_bareheaded_photo_material（办理材料-近期免冠照布局）
     */
    public class Type7ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_bareheaded_apply)
        TextView mBareheadedApply;
        @BindView(R.id.tv_bareheaded_photo_text)
        TextView mInchText;
        @BindView(R.id.tv_bareheaded_photo_status)
        TextView mInchStatus;
        @BindView(R.id.iv_bareheaded_photo)
        ImageView mInchImg;

        public Type7ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
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
        ActivityUtils.startActivity(intent);
    }
}