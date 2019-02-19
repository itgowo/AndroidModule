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
import com.itgowo.module.chat.Messenger;
import com.itgowo.module.chat.ViewAction;
import com.itgowo.module.demo.MsgEntity;
import com.itgowo.module.demo.R;

public class ImageViewAction extends ViewAction<ImageViewAction.ImageViewActionViewHolder, MsgEntity> {


    @Override
    public void initDataAndListener(Messenger<MsgEntity> messenger, ImageViewActionViewHolder viewHolder, final ItemData<MsgEntity> item, int position) {
        viewHolder.timeline.setText(item.getData().getTimeLine());
        viewHolder.img.setImageResource(R.drawable.ic_launcher);
        viewHolder.name.setText(item.getData().getName());
        viewHolder.head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "我是:" + item.getData().getName(), Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "我是没加放大预览功能" + item.getData().getName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public RecyclerView.ViewHolder initView(Context context,Messenger<MsgEntity> messenger, ViewGroup viewGroup) {
        RecyclerView.ViewHolder holder = new ImageViewActionViewHolder(LayoutInflater.from(context).inflate(R.layout.listitem_image, viewGroup, false));
        return holder;
    }

    public static class ImageViewActionViewHolder extends RecyclerView.ViewHolder {

        private TextView timeline;
        private TextView name;
        private ImageView img;
        private ImageView head;

        public ImageViewActionViewHolder(@NonNull View itemView) {
            super(itemView);
            timeline = itemView.findViewById(R.id.timeline);
            name = itemView.findViewById(R.id.name);
            img = itemView.findViewById(R.id.img);
            head = itemView.findViewById(R.id.head);
        }
    }
}
