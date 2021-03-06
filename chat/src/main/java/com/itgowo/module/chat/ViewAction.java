package com.itgowo.module.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public abstract class ViewAction<ViewHolderType extends RecyclerView.ViewHolder, ItemType> {

    public abstract void initDataAndListener(Messenger<ItemType> messenger, ViewHolderType viewHolder, ItemData<ItemType> item, int position);

    public abstract RecyclerView.ViewHolder initView(Context context, Messenger<ItemType> messenger, ViewGroup viewGroup);

    /**
     * Adapter里创建View调用
     *
     * @return
     */
    public RecyclerView.ViewHolder init(Context context, Messenger<ItemType> messenger, ViewGroup viewGroup) {
        RecyclerView.ViewHolder viewHolder = initView(context, messenger, viewGroup);
        viewHolder.itemView.setTag(this);
        return viewHolder;
    }

    public void refreshData(Messenger<ItemType> messenger, ViewHolderType itemView, ItemData item, int position) {
        initDataAndListener(messenger, itemView, item, position);
    }
}
