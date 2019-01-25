package com.itgowo.module.chat;


import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import java.io.Serializable;
import java.util.Map;

public interface onViewModuleListener<ItemType, GridItemType> extends Serializable {
    public void onCreateActivity(final AppCompatActivity activity, ChatResource resource);

    public void onCreateToolbar(final AppCompatActivity activity, final Toolbar toolbar);

    public boolean onCreateOptionsMenu(final AppCompatActivity activity, final Toolbar toolbar, Menu menu);

    public Map<Integer, ViewAction> getViewTypeList();

    public void getListItems(Messenger<ItemType> messenger);

    public void getMoreHistory(Messenger<ItemType> messenger);

    public void onBind(Messenger<ItemType> messenger, RecyclerView recyclerView);

    public void onBindGridItemView(GridPageView.GridItemView itemView);

    public void getGridItemList(Messenger<ItemType> messenger);

    /**
     * 返回扩展功能自定义View，如果为Null使用默认布局
     * @return
     * @param position
     */
    public View getGridItemView(int position);

    /**
     * 功能区Item点击事件
     * @param view
     * @param position
     * @param gridItemView
     * @param data
     */
    public void onClickGridItem(View view, int position, GridPageView.GridItemView gridItemView, GridPageView.GridItemData data);
    public void onSendButtonClick(EditText editText, String msg);
}
