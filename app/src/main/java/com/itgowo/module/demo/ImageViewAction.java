package com.itgowo.module.demo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.itgowo.module.chat.ItemData;
import com.itgowo.module.chat.ViewAction;

public class ImageViewAction extends ViewAction<ImageViewAction.ImageViewActionViewHolder, MsgEntity> {


    @Override
    public void initDataAndListener(ImageViewActionViewHolder viewHolder, ItemData<MsgEntity> item, int position) {
        viewHolder.image.setImageResource(R.drawable.ic_launcher);

    }

    @Override
    public RecyclerView.ViewHolder initView(Context context, ViewGroup viewGroup) {
        RecyclerView.ViewHolder holder = new ImageViewActionViewHolder(LayoutInflater.from(context).inflate(R.layout.listitem_text2, viewGroup, false));
        return holder;
    }

    public static class ImageViewActionViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;


        public ImageViewActionViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }
    }
}
