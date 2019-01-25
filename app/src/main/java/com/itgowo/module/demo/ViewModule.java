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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.itgowo.module.chat.ChatResource;
import com.itgowo.module.chat.GridPageView;
import com.itgowo.module.chat.ItemData;
import com.itgowo.module.chat.Messenger;
import com.itgowo.module.chat.ViewAction;
import com.itgowo.module.chat.onViewModuleListener;

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
//            resource.setResourceIcon_EmojiButtion(R.drawable.ic_launcher);
        this.context=activity;
        resource.setResourceIcon_InputTypeButtion_Keyboard1(R.drawable.followup_chat_keyboard1);
        resource.setResourceIcon_InputTypeButtion_Keyboard2(R.drawable.followup_chat_keyboard2);
        resource.setResourceIcon_InputTypeButtion_Voice(R.drawable.followup_chat_voice);
        resource.setResourceIcon_OtherButtion(R.drawable.otherbtn);
        resource.setResourceIcon_SendButtion(R.drawable.send_btn_bg);
        resource.setResourceText_SendBtn(R.string.module_chat_sendBtn);
        resource.setResourceBG_FirstLine(R.drawable.bg);
        resource.setResourceBG_InputET(R.drawable.input_bg);
        resource.setResourceBG(R.color.chatActivityBackground);
        resource.setResourceBG_VoiceBtn(R.drawable.voicebtn_bg);
        resource.setResourceText_VoiceBtn(R.string.module_chat_pressandtalk);
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
    public void getListItems(Messenger messenger) {
        // TODO: 2019/1/23 刷新界面，IM聊天目前没有,第一次加载会触发

    }

    @Override
    public void getMoreHistory(Messenger messenger) {
        // TODO: 2019/1/23 加载历史数据被触发

    }

    @Override
    public void onBind(Messenger messenger, RecyclerView recyclerView) {
        this.messenger = messenger;
    }

    @Override
    public void onBindGridItemView(GridPageView.GridItemView itemView) {
        Glide.with(itemView.itemView).load(itemView.data.imageUrl).into(itemView.imageButton);
    }

    @Override
    public void getGridItemList(Messenger messenger) {
        List<GridPageView.GridItemData> dataList = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            dataList.add(new GridPageView.GridItemData().setImage(R.drawable.ic_launcher).setText("icon" + i));
        }
        messenger.setGridData(dataList);
    }

    @Override
    public View getGridItemView(int position) {
        return null;
    }

    @Override
    public void onClickGridItem(View view, int position, GridPageView.GridItemView gridItemView, GridPageView.GridItemData data) {
        Toast.makeText(view.getContext(), "功能模块点击了" + data.text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSendButtonClick(EditText editText, String msg) {
        Toast.makeText(editText.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

}