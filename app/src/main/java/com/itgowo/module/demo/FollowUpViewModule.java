package com.itgowo.module.demo;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.itgowo.module.chat.ItemData;
import com.itgowo.module.chat.Messenger;

import java.util.ArrayList;

public class FollowUpViewModule extends ViewModule {
    @Override
    public void onCreateToolbar(AppCompatActivity activity, Toolbar toolbar) {
        super.onCreateToolbar(activity, toolbar);
        toolbar.setTitle("hahaha");
    }

    @Override
    public void getMoreHistory(Messenger messenger) {
        messenger.setData(new ArrayList<ItemData>());
    }

    @Override
    public View getGridItemView(int position) {
        TextView textView=new TextView(context);
        textView.setText("aaaaa"+position);
        return textView;
    }
}
