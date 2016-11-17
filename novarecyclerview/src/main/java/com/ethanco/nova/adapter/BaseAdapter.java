package com.ethanco.nova.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.Collection;
import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter {
    protected final LayoutInflater mInflater;
    protected final Context mContext;
    protected final List<T> mDataList;

    public BaseAdapter(Context context, List<T> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mDataList = list;
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public List<T> getDataList() {
        return mDataList;
    }

    public void setDataList(Collection<T> list) {
        this.mDataList.clear();
        this.mDataList.addAll(list);
        notifyDataSetChanged();
    }

    //添加数据 (适用于上拉加载)
    public void addAll(Collection<T> list) {
        int lastIndex = this.mDataList.size();
        if (this.mDataList.addAll(list)) {
            notifyItemRangeInserted(lastIndex, list.size());
        }
    }

    //设置新的数据 (适用于下拉刷新)
    public void setNewData(Collection<T> list) {
        this.mDataList.clear();
        this.mDataList.addAll(list);
        notifyDataSetChanged();

    }

    public void remove(int position) {
        if (this.mDataList.size() > 0) {
            mDataList.remove(position);
            notifyItemRemoved(position);
        }

    }

    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }
}
