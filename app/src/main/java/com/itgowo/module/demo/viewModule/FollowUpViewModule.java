package com.itgowo.module.demo.viewModule;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.itgowo.module.chat.ItemData;
import com.itgowo.module.chat.Messenger;
import com.itgowo.module.demo.MsgEntity;

import java.util.ArrayList;
import java.util.List;

public class FollowUpViewModule extends ViewModule {
    private int size = 0;

    @Override
    public void onCreateToolbar(AppCompatActivity activity, Toolbar toolbar) {
        super.onCreateToolbar(activity, toolbar);
        toolbar.setTitle("hahaha");
    }

    @Override
    public void getMoreHistory(Messenger messenger) {
        List<ItemData<MsgEntity>> dataList = new ArrayList<>();
        ItemData<MsgEntity> itemData = new ItemData<>();
        itemData.setData(new MsgEntity().setName("大王").setContent("今天天气真好啊今天天气真好啊今天天气真好啊今天天气真好啊今天天气真好啊今天天气真好啊。" + size).setTimeLine("2019年1月1日").setUserid(10086)).setType(1);
        dataList.add(itemData);
//        size++;
//        ItemData<MsgEntity> itemData1 = new ItemData<>();
//        itemData1.setData(new MsgEntity().setName("小王").setContent("是呀是呀。" + size).setTimeLine("2019年1月1日").setUserid(10087)).setType(2);
//        dataList.add(itemData1);
//        size++;
//        ItemData<MsgEntity> itemData2 = new ItemData<>();
//        itemData2.setData(new MsgEntity().setName("大王").setContent("今天天气真好啊。" + size).setTimeLine("2019年1月1日").setUserid(10086)).setType(3);
//        dataList.add(itemData2);
//        ItemData<MsgEntity> itemData3 = new ItemData<>();
//        itemData3.setData(new MsgEntity().setName("小王").setContent("是呀是呀。" + size).setTimeLine("2019年1月1日").setUserid(10087)).setType(4);
//        dataList.add(itemData3);
        messenger.addFirstDataList(dataList);
    }
}
