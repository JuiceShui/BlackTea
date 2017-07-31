/*
 * Created by lsy on 2017/7/21.
 * Copyright (c) 2017 yeeyuntech. All rights reserved.
 */

package com.shui.blacktea.widget.recycleAdapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: recycle 的adapter
 * Created by lsy on 2017/5/5.
 * <p>
 * Description: 代码规范
 * Modify by Juice_ on 2017/5/22 上午10:36
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<Holder> {

    protected List<T> mData = new ArrayList<>();
    protected Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private LayoutInflater mInflater;

    public BaseAdapter(List<T> data, Context context) {
        if (data != null) {
            this.mData = data;
        }
        this.mData = data;
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public Context getContext() {
        return mContext;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layId = setLayId(parent, viewType);
        if (layId == 0) {
            try {
                throw new Exception("warning , please set layout first!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(mInflater, layId, parent, false);
        return new Holder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        // 设置点击事件
        final int index = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClickListener(holder.itemView, index);
                }
            }
        });
        // 这下面继续写
        setViews(holder.getBinding(), position);
        // 处理消息刷新时 item 闪烁的问题
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * 点击事件
     */
    public interface OnItemClickListener {
        void onItemClickListener(View view, int position);
    }

    /**
     * 设置layout
     *
     * @param parent   父容器
     * @param viewType view的类别
     * @return
     */
    public abstract int setLayId(ViewGroup parent, int viewType);

    /**
     * 渲染view
     *
     * @param viewDataBinding  binding对象
     * @param position 对应位置
     */
    public abstract void setViews(ViewDataBinding viewDataBinding, int position);

    /**
     * 设置点击事件
     *
     * @param onItemClickListener listener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
