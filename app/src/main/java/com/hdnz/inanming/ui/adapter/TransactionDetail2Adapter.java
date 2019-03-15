package com.hdnz.inanming.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hdnz.inanming.R;
import com.hdnz.inanming.bean.TransactionDetail2Bean;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
public class TransactionDetail2Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /**
     * @field 上下文
     */
    private Context context;
    /**
     * @field 数据源
     */
    List<TransactionDetail2Bean.DataBean.MaterialsBean> materials;

    public TransactionDetail2Adapter() {
    }

    public TransactionDetail2Adapter(Context context, List<TransactionDetail2Bean.DataBean.MaterialsBean> materials) {
        this.context = context;
        this.materials = materials;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case 6:
                View view6 = LayoutInflater.from(context).inflate(R.layout.item_transaction_detail_idcard_material, parent, false);
                viewHolder = new Type6ViewHolder(view6);
                break;
            case 7:
                View view7 = LayoutInflater.from(context).inflate(R.layout.item_transaction_detail_bareheaded_photo_material, parent, false);
                viewHolder = new Type7ViewHolder(view7);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TransactionDetail2Bean.DataBean.MaterialsBean materialsBean = materials.get(position);
        switch (getItemViewType(position)) {
            case 6:
                Type6ViewHolder type6ViewHolder = (Type6ViewHolder) holder;
                break;
            case 7:
                Type7ViewHolder type7ViewHolder = (Type7ViewHolder) holder;
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return materials.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return null != materials ? materials.size() : 0;
    }


    /**
     * 布局类型6加载item_transaction_detail_idcard_material（办理材料-身份证布局）
     */
    public class Type6ViewHolder extends RecyclerView.ViewHolder {

        public Type6ViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 布局类型7加载item_transaction_detail_bareheaded_photo_material（办理材料-近期免冠照布局）
     */
    public class Type7ViewHolder extends RecyclerView.ViewHolder {

        public Type7ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
