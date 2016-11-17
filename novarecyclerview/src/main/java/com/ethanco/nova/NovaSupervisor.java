package com.ethanco.nova;

import android.os.Handler;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.ethanco.nova.base.MRecyclerView;
import com.ethanco.nova.inter.LoadMoreListener;
import com.ethanco.nova.inter.RefreshStateListener;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.github.jdsjlzx.util.LuRecyclerViewStateUtils;
import com.github.jdsjlzx.util.LuRecyclerViewUtils;
import com.github.jdsjlzx.view.LoadingFooter;

import java.lang.ref.WeakReference;
import java.util.Collection;

import static android.content.ContentValues.TAG;

/**
 * @Description Recyclerview 监管者
 * Created by EthanCo on 2016/9/28.
 */

public class NovaSupervisor {

    //每一页展示多少条数据
    private int listPageSize = 10;
    //是否 没有更多了
    private boolean isLoadEnd = false;

    private LoadMoreListener loadmoreListener;
    private View.OnClickListener errorClickListener;
    private RefreshStateListener refreshStateListener;
    private WeakReference<NovaRecyclerView> recyclerViewRef;

    public NovaSupervisor(NovaRecyclerView recyclerView) {
        this.recyclerViewRef = new WeakReference<>(recyclerView);
    }

    //打开下拉刷新
    public void openRefresh(final SwipeRefreshLayout swipeRefreshLayout) {
        setRefreshStateListener(new RefreshStateListener() {
            @Override
            public boolean isRefreshing() {
                return swipeRefreshLayout.isRefreshing();
            }
        });
    }

    //打开加载更多功能
    public void openLoadMore() {
        openLoadMore(null);
    }

    public void openLoadMore(LoadMoreListener _Listener) {
        NovaRecyclerView recyclerView = recyclerViewRef.get();
        if (recyclerView == null) return;

        this.loadmoreListener = _Listener;
        recyclerView.addOnScrollBottomListener(new MRecyclerView.OnScrollBottomListener() {
            @Override
            public void onBottom() {
                loadMore();
            }
        });
        setFootViewIfIsNull(recyclerView);
    }

    //加载更多
    public void loadMore() {
        RecyclerView recyclerView = recyclerViewRef.get();
        if (recyclerView == null) return;

        LoadingFooter.State state = LuRecyclerViewStateUtils.getFooterViewState(recyclerView);
        if (state == LoadingFooter.State.Loading) {
            Log.d(TAG, "the state is Loading, just wait..");
            return;
        }

        int currCount = recyclerView.getAdapter().getItemCount();
        if (!isLoadEnd) {
            if (isRefreshing()) return;
            if (loadmoreListener == null) return;

            //加载更多
            setFooterViewState(listPageSize, LoadingFooter.State.Loading, null);
            loadmoreListener.loadMore(currCount / listPageSize, listPageSize);
        } else {
            //没有更多了
            setFooterViewState(listPageSize, LoadingFooter.State.TheEnd, null);
        }
    }

    private boolean isRefreshing() {
        if (refreshStateListener == null) return false;

        return refreshStateListener.isRefreshing();
    }

    //============================= Z-对外暴露 设置UI ==============================/

    //如果没有FootView 设置一个FootVIew
    private void setFootViewIfIsNull(NovaRecyclerView recyclerView) {
        if (recyclerView.getAdapter() instanceof AdapterWrap) {
            AdapterWrap adapterWrap = (AdapterWrap) recyclerView.getAdapter();
            View footerView = adapterWrap.getFooterView();
            if (footerView == null) {
                footerView = new LoadingFooter(recyclerView.getContext());
                //footerView.setVisibility(GONE);
                setFooterView(footerView);
            }
        }
    }

    private void setFooterViewState(int pageSize, LoadingFooter.State state, View.OnClickListener errorListener) {
        NovaRecyclerView recyclerView = recyclerViewRef.get();
        if (recyclerView == null) return;

        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && outerAdapter instanceof LuRecyclerViewAdapter) {
            LuRecyclerViewAdapter LuRecyclerViewAdapter = (LuRecyclerViewAdapter) outerAdapter;
            if (LuRecyclerViewAdapter.getInnerAdapter().getItemCount() >= pageSize) {
                if (LuRecyclerViewAdapter.getFooterViewsCount() > 0) {
                    LoadingFooter footerView = (LoadingFooter) LuRecyclerViewAdapter.getFooterView();
                    footerView.setState(state);
                    footerView.setVisibility(View.VISIBLE);
                    if (state == LoadingFooter.State.NetWorkError) {
                        footerView.setOnClickListener(errorListener);
                    }
                }
            }
        }
    }

    public LoadingFooter.State getFooterViewState() {
        NovaRecyclerView recyclerView = recyclerViewRef.get();
        if (recyclerView == null) return LoadingFooter.State.TheEnd;

        return LuRecyclerViewStateUtils.getFooterViewState(recyclerView);
    }

    public void setFooterViewState(LoadingFooter.State state) {
        NovaRecyclerView recyclerView = recyclerViewRef.get();
        if (recyclerView == null) return;

        if (state == LoadingFooter.State.NetWorkError && errorClickListener != null) {
            setFooterViewState(listPageSize, state, errorClickListener);
        } else {
            LuRecyclerViewStateUtils.setFooterViewState(recyclerView, state);
        }
    }

    public void setHeaderView(View view) {
        NovaRecyclerView recyclerView = recyclerViewRef.get();
        if (recyclerView == null) return;

        LuRecyclerViewUtils.setHeaderView(recyclerView, view);
    }

    public void setFooterView(View view) {
        NovaRecyclerView recyclerView = recyclerViewRef.get();
        if (recyclerView == null) return;

        LuRecyclerViewUtils.setFooterView(recyclerView, view);
    }

    public void removeFooterView() {
        NovaRecyclerView recyclerView = recyclerViewRef.get();
        if (recyclerView == null) return;

        LuRecyclerViewUtils.removeFooterView(recyclerView);
    }

    public void removeHeaderView() {
        NovaRecyclerView recyclerView = recyclerViewRef.get();
        if (recyclerView == null) return;

        LuRecyclerViewUtils.removeHeaderView(recyclerView);
    }

    public void onRefreshSuccess(Collection collection) {
        NovaRecyclerView recyclerView = recyclerViewRef.get();
        if (recyclerView == null) return;

        AdapterWrap adapterWrap = (AdapterWrap) recyclerView.getAdapter();
        adapterWrap.getAdapter().setNewData(collection);
        setFooterViewState(LoadingFooter.State.Normal);
    }

    public void onRefreshFailed(String error) {
        setFooterViewState(LoadingFooter.State.Normal);
    }

    public void onLoadMoreSuccess(Collection collection) {
        NovaRecyclerView recyclerView = recyclerViewRef.get();
        if (recyclerView == null) return;

        AdapterWrap adapterWrap = (AdapterWrap) recyclerView.getAdapter();
        adapterWrap.getAdapter().addAll(collection);
        setFooterViewState(LoadingFooter.State.Normal);
    }

    public void onLoadMoreFailed(String error) {
        setFooterViewState(LoadingFooter.State.NetWorkError);
    }

    //============================= Z-监听回调 ==============================/

    private void setRefreshStateListener(RefreshStateListener listener) {
        this.refreshStateListener = listener;
    }

    public void setErrorClickListener(View.OnClickListener errorClickListener) {
        this.errorClickListener = errorClickListener;
    }

    public void setLoadMoreListener(LoadMoreListener Listener) {
        this.loadmoreListener = Listener;
    }

    //============================= Z-Get Set ==============================/
    public boolean isLoadEnd() {
        return isLoadEnd;
    }

    //设置PageSize 一页加载几个
    public void setPageSize(int pageSize) {
        this.listPageSize = pageSize;
    }

    @UiThread
    public void setLoadEnd(final boolean loadEnd) {
        isLoadEnd = loadEnd;
        if (loadEnd) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setFooterViewState(listPageSize, LoadingFooter.State.TheEnd, null);
                }
            }, 500);
        } else {
            //加载更多
            setFooterViewState(listPageSize, LoadingFooter.State.Loading, null);
        }
    }
}
