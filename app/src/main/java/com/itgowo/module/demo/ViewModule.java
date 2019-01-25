package com.itgowo.module.demo;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.itgowo.module.chat.ChatResource;
import com.itgowo.module.chat.ItemData;
import com.itgowo.module.chat.Messenger;
import com.itgowo.module.chat.ViewAction;
import com.itgowo.module.chat.onViewModuleListener;
import com.itgowo.module.view.gridpagerview.GridPagerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewModule implements onViewModuleListener {
    public Context context;
    public Messenger<ItemData<MsgEntity>> messenger;

    public Messenger<ItemData<MsgEntity>> getMessenger() {
        return messenger;
    }

    @Override
    public void onCreateActivity(AppCompatActivity activity, ChatResource resource) {
//            resource.setIconEmojiButtion(R.drawable.ic_launcher);
        this.context = activity;
        resource.setIconInputTypeButtionKeyboard1(R.drawable.followup_chat_keyboard1);
        resource.setIconInputTypeButtionKeyboard2(R.drawable.followup_chat_keyboard2);
        resource.setIconInputTypeButtionVoice(R.drawable.followup_chat_voice);
        resource.setIconOtherButtion(R.drawable.otherbtn);
        resource.setIconEmojiButtion(R.drawable.otherbtn);
        resource.setIconSendButtion(R.drawable.send_btn_bg);
        resource.setTextSendBtn(R.string.module_chat_sendBtn);
        resource.setBgFirstLine(R.drawable.bg);
        resource.setBgInputET(R.drawable.input_bg);
        resource.setBgActivity(R.color.chatActivityBackground);
        resource.setBgVoiceBtn(R.drawable.voicebtn_bg);
        resource.setTextVoiceBtn(R.string.module_chat_pressandtalk);
        TextView textView = new TextView(activity);
        textView.setText("偷梁换柱");
        resource.setViewVoiceBtn(textView);
    }

    @Override
    public void onCreateToolbar(final AppCompatActivity activity, Toolbar toolbar) {
        toolbar.setTitle("Title");
        toolbar.setTitleTextColor(Color.RED);
        toolbar.setLogo(R.drawable.ic_launcher);
        toolbar.setLogoDescription("LogoDescription");
        toolbar.setSubtitleTextColor(Color.BLUE);
        toolbar.setContentDescription("ContentDescription");
        toolbar.setNavigationContentDescription("NavigationContentDescription");
        toolbar.setNavigationIcon(R.drawable.collection_orange);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        toolbar.setSubtitle("Subtitle");
        toolbar.setBackgroundColor(Color.BLACK);
        toolbar.setOverflowIcon(activity.getResources().getDrawable(R.drawable.ic_launcher));

//            EditText editText = new EditText(activity);
//            toolbar.addView(editText);

    }


    @Override
    public boolean onCreateOptionsMenu(final AppCompatActivity activity, Toolbar toolbar, Menu menu) {
        toolbar.inflateMenu(R.menu.module_chat_toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Toast.makeText(activity, "我是大灰狼" + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return true;
    }

    @Override
    public Map<Integer, ViewAction> getViewTypeList() {
        Map<Integer, ViewAction> map = new HashMap<>();
        map.put(1, new TextViewAction());
        map.put(2, new TextViewAction2());
        map.put(3, new ImageViewAction());
        map.put(4, new TextViewAction2());
        return map;
    }

    @Override
    public void getMoreHistory(Messenger messenger) {
        // TODO: 2019/1/23 加载历史数据被触发

    }

    @Override
    public void onBind(final Messenger messenger, RecyclerView recyclerView) {
        this.messenger = messenger;


        List<GridPagerView.GridItemData> dataList1 = new ArrayList<>();
        for (int i = 0; i < 310; i++) {
            dataList1.add(new GridPagerView.GridItemData().setImage(R.drawable.ic_launcher).setText("emoji" + i));
        }
        messenger.setGridPageViewDataList1(dataList1, 5, 10, new GridPagerView.onGridPageViewListener() {
            @Override
            public void onBindGridItemView(GridPagerView.GridItemView itemView) {
                Glide.with(itemView.itemView).load(itemView.data.imageUrl).into(itemView.imageButton);
            }

            @Override
            public View getGridItemView(int position) {
                ImageView view=new ImageView(messenger.getContext());
                view.setImageResource(R.drawable.dynamic_praise);
                return view;
            }

            @Override
            public void onClickGridItem(View view, int position, GridPagerView.GridItemView gridItemView, GridPagerView.GridItemData data) {
                Toast.makeText(view.getContext(), "Emoji点击了" + data.text, Toast.LENGTH_SHORT).show();
            }
        });


        List<GridPagerView.GridItemData> dataList2 = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            dataList2.add(new GridPagerView.GridItemData().setImage(R.drawable.ic_launcher).setText("icon" + i));
        }
        messenger.setGridPageViewDataList2(dataList2, 2, 4, new GridPagerView.onGridPageViewListener() {
            @Override
            public void onBindGridItemView(GridPagerView.GridItemView itemView) {
                Glide.with(itemView.itemView).load(itemView.data.imageUrl).into(itemView.imageButton);
            }

            @Override
            public View getGridItemView(int position) {
                return null;
            }

            @Override
            public void onClickGridItem(View view, int position, GridPagerView.GridItemView gridItemView, GridPagerView.GridItemData data) {
                Toast.makeText(view.getContext(), "功能模块点击了" + data.text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onSendButtonClick(EditText editText, String msg) {
        Toast.makeText(editText.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

}