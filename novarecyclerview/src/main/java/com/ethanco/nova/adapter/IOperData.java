package com.ethanco.nova.adapter;

import java.util.Collection;
import java.util.List;

/**
 * 操作数据
 *
 * @author EthanCo
 * @since 2016/12/5
 */

public interface IOperData<T> {
    int getItemCount();

    List<T> getDataList();

    void setDataList(Collection<T> list);

    //添加数据
    void add(T t);

    //添加数据到指定位置
    void add(T t, int position);


    //添加数据 (适用于上拉加载)
    public void addAll(Collection<T> list);

    //设置新的数据 (适用于下拉刷新)
    void setNewData(Collection<T> list);

    //移除数据
    void remove(int position);

    //移除数据
    boolean remove(T t);

    void clear();

    //根据Position获取数据
    T getData(int position);
}
