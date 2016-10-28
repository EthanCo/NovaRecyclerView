package com.ethanco.sample.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import com.ethanco.nova.NovaSupervisor;
import com.ethanco.sample.view.IListView;
import com.lib.frame.view.BaseActivity;
import com.lib.frame.viewmodel.BaseViewModel;

import java.util.Collection;

/**
 * @Description Recyclerview列表 Adapter
 * Created by EthanCo on 2016/10/8.
 */

public abstract class BaseListActivity<B, T extends BaseViewModel<IListView<B>>> extends BaseActivity<IListView<B>, T> implements IListView<B> {
    protected NovaSupervisor supervisor;

    @Override
    protected void midfield() {
        super.midfield();
        supervisor = createSupervisor();
    }

    protected abstract NovaSupervisor createSupervisor();

    protected abstract SwipeRefreshLayout getSwipeRefreshLayout();


    @Override
    public void onRefreshSuccess(Collection<B> collection) {
        supervisor.onRefreshSuccess(collection);
        setRefreshing(false);
    }

    @Override
    public void onRefreshFailed(String error) {
        supervisor.onRefreshFailed(error);
        setRefreshing(false);

        Toast.makeText(BaseListActivity.this, "刷新错误:" + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadMoreSuccess(Collection<B> collection) {
        supervisor.onLoadMoreSuccess(collection);
    }

    @Override
    public void onLoadMoreFailed(String error) {
        supervisor.onLoadMoreFailed(error);

        Toast.makeText(BaseListActivity.this, "加载错误:" + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadEnd() {
        supervisor.setLoadEnd(true);
    }

    protected void setRefreshing(boolean refreshing) {
        SwipeRefreshLayout swipeRefreshLayout = getSwipeRefreshLayout();

        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(refreshing);
        }
    }
}
