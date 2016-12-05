package com.ethanco.nova;

import android.view.View;

import com.ethanco.nova.adapter.BaseAdapter;
import com.ethanco.nova.adapter.IOperData;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Description AdapterWrap
 * Created by EthanCo on 2016/9/28.
 */

public class AdapterWrap<T> extends LuRecyclerViewAdapter implements IOperData<T> {
    private BaseAdapter<T> adapter;

    public BaseAdapter<T> getAdapter() {
        return adapter;
    }

    public AdapterWrap(BaseAdapter<T> innerAdapter) {
        super(innerAdapter);

        this.adapter = innerAdapter;

        super.setOnItemClickListener(new com.github.jdsjlzx.interfaces.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                for (OnItemClickListener itemClickListener : itemClickListeners) {
                    itemClickListener.onItemClick(view, i);
                }
            }

            @Override
            public void onItemLongClick(View view, int i) {
                for (OnItemLongClickListener itemLongClickListener : itemLongClickListeners) {
                    itemLongClickListener.onLongItemClick(view, i);
                }
            }
        });
    }

    public interface OnItemLongClickListener {
        void onLongItemClick(View v, int position);
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    protected List<OnItemClickListener> itemClickListeners = new ArrayList<>();

    protected List<OnItemLongClickListener> itemLongClickListeners = new ArrayList<>();

    public void addOnItemClickListener(OnItemClickListener listener) {
        if (!itemClickListeners.contains(listener))
            itemClickListeners.add(listener);
    }

    public void addOnItemLongClickListener(OnItemLongClickListener listener) {
        if (!itemLongClickListeners.contains(listener))
            itemLongClickListeners.add(listener);
    }

    @Override
    @Deprecated
    public void setOnItemClickListener(com.github.jdsjlzx.interfaces.OnItemClickListener mOnItemClickListener) {
        throw new IllegalStateException("please use addItemClickListener or addLongClickListener");
    }

    //============================= Z-IPerData ==============================/
    @Override
    public List<T> getDataList() {
        return adapter.getDataList();
    }

    @Override
    public void setDataList(Collection<T> list) {
        adapter.setDataList(list);
    }

    @Override
    public void add(T t) {
        adapter.add(t);
    }

    @Override
    public void add(T t, int position) {
        adapter.add(t, position);
    }

    @Override
    public void addAll(Collection<T> list) {
        adapter.addAll(list);
    }

    @Override
    public void setNewData(Collection<T> list) {
        adapter.setNewData(list);
    }

    @Override
    public void remove(int position) {
        adapter.remove(position);
    }

    @Override
    public boolean remove(T t) {
        return adapter.remove(t);
    }

    @Override
    public void clear() {
        adapter.clear();
    }

    @Override
    public T getData(int position) {
        return adapter.getData(position);
    }
}
