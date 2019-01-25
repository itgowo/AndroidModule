package com.itgowo.module.chat;


import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.EditText;

import java.io.Serializable;
import java.util.Map;

public interface onViewModuleListener<ItemType, GridItemType> extends Serializable {
    public void onCreateActivity(final AppCompatActivity activity, ChatResource resource);

    public void onCreateToolbar(final AppCompatActivity activity, final Toolbar toolbar);

    public boolean onCreateOptionsMenu(final AppCompatActivity activity, final Toolbar toolbar, Menu menu);

    public Map<Integer, ViewAction> getViewTypeList();

    public void getMoreHistory(Messenger<ItemType> messenger);

    /**
     * 此处返回可以通过message对象操作底部表盘和recyclerView配置
     *
     * @param messenger
     * @param recyclerView
     */
    public void onBind(Messenger<ItemType> messenger, RecyclerView recyclerView);

    public void onSendButtonClick(EditText editText, String msg);
}
