package com.itgowo.module.chat;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

import com.itgowo.module.view.gridpagerview.GridPagerView;

import java.lang.ref.WeakReference;
import java.util.List;

public class Messenger<ItemType> {
    WeakReference<List<ItemData<ItemType>>> dataList;
    WeakReference<SwipeRefreshLayout> swipeRefreshLayout;
    WeakReference<Context> context;
    WeakReference<BaseAdapter> adapter;
    WeakReference<GridPagerView> gridPageView1;
    WeakReference<GridPagerView> gridPageView2;
    WeakReference<onViewModuleListener> onViewModuleListener;

    public Context getContext() {
        return context.get();
    }

    public Messenger(List<ItemData<ItemType>> dataList, SwipeRefreshLayout swipeRefreshLayout, Context context, BaseAdapter adapter, GridPagerView gridPageView1, GridPagerView gridPageView2, onViewModuleListener listener) {
        this.dataList = new WeakReference<>(dataList);
        this.swipeRefreshLayout = new WeakReference<>(swipeRefreshLayout);
        this.context = new WeakReference<>(context);
        this.adapter = new WeakReference<>(adapter);
        this.gridPageView1 = new WeakReference<>(gridPageView1);
        this.gridPageView2 = new WeakReference<>(gridPageView2);
        this.onViewModuleListener = new WeakReference<>(listener);
    }

    protected void finish() {
        dataList.clear();
        swipeRefreshLayout.clear();
        context.clear();
        adapter.clear();
        gridPageView1.clear();
        gridPageView2.clear();
        onViewModuleListener.clear();
    }

    public void setData(List<ItemData<ItemType>> dataList) {
        if (dataList != null) {
            if (this.dataList.get() == null || adapter.get() == null) {
                return;
            }
            this.dataList.get().clear();
            this.dataList.get().addAll(dataList);
            adapter.get().notifyDataSetChanged();

        }
        if (swipeRefreshLayout.get() != null) {
            swipeRefreshLayout.get().setRefreshing(false);
        }
    }


    public void addFirstDataList(List<ItemData<ItemType>> dataList) {
        if (dataList != null) {
            if (this.dataList.get() == null || adapter.get() == null) {
                return;
            }
            this.dataList.get().addAll(dataList);
            adapter.get().notifyDataSetChanged();
        }
        if (swipeRefreshLayout.get() != null) {
            swipeRefreshLayout.get().setRefreshing(false);
        }
    }

    public void addLastDataList(List<ItemData<ItemType>> dataList) {
        if (dataList != null) {
            if (this.dataList.get() == null || adapter.get() == null) {
                return;
            }
            this.dataList.get().addAll(dataList);
            adapter.get().notifyDataSetChanged();
        }
        if (swipeRefreshLayout.get() != null) {
            swipeRefreshLayout.get().setRefreshing(false);
        }
    }

    public void setGridPageViewDataList1(List<GridPagerView.GridItemData> gridPageViewDataList, int row, int coloumn, GridPagerView.onGridPageViewListener listener) {
        if (gridPageViewDataList != null) {
            if (gridPageView1.get() == null) {
                return;
            }
            gridPageView1.get().init(row, coloumn, listener);
            gridPageView1.get().setData(gridPageViewDataList);
        }
    }

    public void setGridPageViewDataList2(List<GridPagerView.GridItemData> gridPageViewDataList, int row, int coloumn, GridPagerView.onGridPageViewListener listener) {
        if (gridPageViewDataList != null) {
            if (gridPageView2.get() == null) {
                return;
            }
            gridPageView2.get().init(row, coloumn, listener);
            gridPageView2.get().setData(gridPageViewDataList);

        }
    }
}
