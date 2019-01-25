package com.itgowo.module.demo.viewAction;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.itgowo.module.chat.ItemData;
import com.itgowo.module.chat.ViewAction;
import com.itgowo.module.demo.MsgEntity;
import com.itgowo.module.demo.R;

public class TextViewAction2 extends ViewAction<TextViewAction2.TextViewActionViewHolder, MsgEntity> {


    @Override
    public void initDataAndListener(TextViewActionViewHolder viewHolder, final ItemData<MsgEntity> item, int position) {
        viewHolder.timeline.setText(item.getData().getTimeLine());
        viewHolder.content.setText(item.getData().getContent());
        viewHolder.name.setText(item.getData().getName());
        viewHolder.head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "我是:" + item.getData().getName(), Toast.LENGTH_SHORT).show();
                ;
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder initView(Context context, ViewGroup viewGroup) {
        RecyclerView.ViewHolder holder = new TextViewActionViewHolder(LayoutInflater.from(context).inflate(R.layout.listitem_text2, viewGroup, false));
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
