package com.itgowo.module.demo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itgowo.module.chat.ItemData;
import com.itgowo.module.chat.ViewAction;

public class TextViewAction extends ViewAction<TextViewAction.TextViewActionViewHolder, MsgEntity> {


    @Override
    public void initDataAndListener(TextViewActionViewHolder viewHolder, ItemData<MsgEntity> item, int position) {
        viewHolder.timeline.setText(item.getData().getTimeLine());
        viewHolder.content.setText(item.getData().getContent());
        viewHolder.name.setText(item.getData().getName());
    }

    @Override
    public RecyclerView.ViewHolder initView(Context context, ViewGroup viewGroup) {
        RecyclerView.ViewHolder holder = new TextViewActionViewHolder(LayoutInflater.from(context).inflate(R.layout.listitem_text, viewGroup, false));
        return holder;
    }

    public static class TextViewActionViewHolder extends RecyclerView.ViewHolder {
        private TextView timeline;
        private TextView name;
        private TextView content;
        private ImageView head;


        public TextViewActionViewHolder(@NonNull View itemView) {
            super(itemView);
            timeline = (TextView) itemView.findViewById(R.id.timeline);
            name = (TextView) itemView.findViewById(R.id.name);
            content = (TextView) itemView.findViewById(R.id.content);
            head = (ImageView) itemView.findViewById(R.id.head);
        }
    }
}
