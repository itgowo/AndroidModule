package com.itgowo.module.chat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

public class BaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ItemData> listItems;
    private Map<Integer, ViewAction> viewActionType;
    private Context context;
    private Messenger messenger;

    public Messenger getMessenger() {
        return messenger;
    }

    public BaseAdapter setMessenger(Messenger messenger) {
        this.messenger = messenger;
        return this;
    }

    public BaseAdapter(List<ItemData> listItems, Map<Integer, ViewAction> viewActionType, Context context) {
        this.listItems = listItems;
        this.viewActionType = viewActionType;
        this.context = context;
    }

    public BaseAdapter setListItems(List<ItemData> listItems) {
        this.listItems = listItems;
        return this;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        ViewAction viewAction = viewActionType.get(viewType);
        return viewAction.init(context,messenger, viewGroup);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ViewAction viewAction = (ViewAction) viewHolder.itemView.getTag();
        viewAction.refreshData(messenger,viewHolder, listItems.get(position), position);
    }

    @Override
    public int getItemCount() {
        return listItems == null ? 0 : listItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return listItems.get(position).getType();
    }
}
