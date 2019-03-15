package com.hdnz.inanming.ui.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public abstract class RecyclerViewAdapter2<T> extends RecyclerView.Adapter<RecyclerViewAdapter2.MyViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private List<T> mList;
    private int mResId;

    private OnItemClickListener mOnItemClickListener;

    public RecyclerViewAdapter2(List<T> mList, int mResId) {
        this.mList = mList;
        this.mResId = mResId;
    }

    public abstract void bindView(MyViewHolder holder, int position);

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LogUtils.i(TAG, "onCreateViewHolder: ");
        return MyViewHolder.getHolder(mResId, parent, viewType);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, final int position) {
        LogUtils.i(TAG, "onBindViewHolder: ");
        bindView(viewHolder, position);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private SparseArray<View> views = new SparseArray<>();

        public static MyViewHolder getHolder(int mResId, ViewGroup parent, int viewType) {

            MyViewHolder holder;
            View view = LayoutInflater.from(parent.getContext()).inflate(mResId, parent, false);
            holder = new MyViewHolder(view);

            return holder;
        }

        private MyViewHolder(View itemView) {
            super(itemView);
        }


        private <T extends View> T getView(int id) {
            T t = (T) views.get(id);
            if (t == null) {
                t = this.itemView.findViewById(id);
                views.put(id, t);
            }
            return t;
        }

        //TextView初始化，赋值
        public MyViewHolder setTextView(int id, String text) {
            TextView textView = getView(id);
            textView.setText(text);
            return this;
        }

        //ImageView初始化，赋值
        public MyViewHolder setCheckBox(int id, boolean isChecked) {
            CheckBox checkBox = getView(id);
            checkBox.setChecked(isChecked);
            return this;
        }

        //ImageView初始化，赋值
        public MyViewHolder setImageView(int id, int resId) {
            ImageView imageView = getView(id);
            imageView.setImageResource(resId);
            return this;
        }
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }
}