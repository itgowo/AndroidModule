package com.itgowo.module.chat;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

import java.util.List;

public class Messenger<ItemType> {
    List<ItemData<ItemType>> dataList;
    SwipeRefreshLayout swipeRefreshLayout;
    Context context;
    BaseAdapter adapter;
    GridPageView gridPageView;

    public Messenger(List<ItemData<ItemType>> dataList, SwipeRefreshLayout swipeRefreshLayout, Context context, BaseAdapter adapter, GridPageView gridPageView) {
        this.dataList = dataList;
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.context = context;
        this.adapter = adapter;
        this.gridPageView = gridPageView;
    }

    public void setData(List<ItemData<ItemType>> dataList) {
        if (dataList != null) {
            this.dataList.clear();
            this.dataList.addAll(dataList);
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }
    }
    public void setGridData(List<GridPageView.GridItemData> dataList) {
        if (dataList != null) {
             gridPageView.setData(dataList);
        }
    }
    public void addFirstDataList(List<ItemData<ItemType>> dataList) {
        if (dataList != null) {
            this.dataList.addAll(dataList);
            adapter.notifyDataSetChanged();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    public void addLastDataList(List<ItemData<ItemType>> dataList) {
        if (dataList != null) {
            this.dataList.addAll(dataList);
            adapter.notifyDataSetChanged();
        }
        swipeRefreshLayout.setRefreshing(false);
    }
}
