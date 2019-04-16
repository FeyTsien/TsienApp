package com.tsien.ui.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tsienlibrary.utils.TextViewUitls;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private List<T> mList;
    private int mResId;

    private OnItemClickListener mOnItemClickListener;
    private OnItemCheckListener mOnItemCheckListener;

    public RecyclerViewAdapter(List<T> list, int mResId) {
        this.mList = list;
        this.mResId = mResId;
    }

    public abstract void bindView(MyViewHolder holder, int position);

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return MyViewHolder.getHolder(mResId, parent, viewType);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, final int position) {
        bindView(viewHolder, position);
        if (mOnItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(position);
                }
            });

        }

//        if (viewHolder.checkBox != null) {
        if (mOnItemCheckListener != null) {
            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemCheckListener.onItemCheck(position);
                }
            });
        }
//        }
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 新增下面方法提供给页面刷新和加载时调用
     *
     * @param addList
     */
    public void refresh(List<T> addList) {
        //增加数据
        int position = mList.size();
        mList.addAll(position, addList);
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private SparseArray<View> views = new SparseArray<>();
        TextView textView;
        ImageView imageView;
        CheckBox checkBox;

        public static MyViewHolder getHolder(int mResId, ViewGroup parent, int viewType) {

            MyViewHolder holder;
            View view = LayoutInflater.from(parent.getContext()).inflate(mResId, parent, false);
            holder = new MyViewHolder(view);

            return holder;
        }

        private MyViewHolder(View itemView) {
            super(itemView);
        }

        /**
         * 相当于findviewById
         *
         * @param id
         * @param <T>
         * @return
         */
        public <T extends View> T getView(int id) {
            T t = (T) views.get(id);
            if (t == null) {
                t = this.itemView.findViewById(id);
                views.put(id, t);
            }
            return t;
        }

        //TextView初始化，赋值
        public MyViewHolder setTextView(int id, String text) {
            textView = getView(id);
            TextViewUitls.setText(textView,text);
            return this;
        }

        //ImageView初始化，赋值
        public MyViewHolder setCheckBox(int id, boolean isChecked) {
            checkBox = getView(id);
            checkBox.setChecked(isChecked);
            return this;
        }

        //ImageView初始化，赋值
        public MyViewHolder setImageView(int id, int resId) {
            imageView = getView(id);
            imageView.setImageResource(resId);
            return this;
        }

        //TextView初始化，赋值颜色
        public MyViewHolder setTextViewColor(int id, int color) {
            textView = getView(id);
            textView.setTextColor(color);
            return this;
        }

        //ImageView初始化，加载网络图片
        public MyViewHolder setImageView(int id, String url) {
            imageView = getView(id);
            Glide.with(imageView).load(url).into(imageView);
            return this;
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    public void setOnItemCheckListener(OnItemCheckListener onItemCheckListener) {
        this.mOnItemCheckListener = onItemCheckListener;
    }

    /**
     * 复选框监听回调接口
     */
    public interface OnItemCheckListener {
        void onItemCheck(int pos);
    }
}