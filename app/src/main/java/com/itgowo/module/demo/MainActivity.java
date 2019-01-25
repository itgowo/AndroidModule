package com.itgowo.module.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.itgowo.module.chat.ChatActivity;
import com.itgowo.module.chat.ChatResource;
import com.itgowo.module.chat.GridPageView;
import com.itgowo.module.chat.IndicatorView;
import com.itgowo.module.chat.ItemData;
import com.itgowo.module.chat.Messenger;
import com.itgowo.module.chat.ViewAction;
import com.itgowo.module.chat.onViewModuleListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static List<ItemData<MsgEntity>> dataList = new ArrayList<>();
    public static ViewModule viewModule = new ViewModule();
    private IndicatorView indicatorView;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        indicatorView = findViewById(R.id.iv);


        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatActivity.go(v.getContext(), new FollowUpViewModule());
            }
        });




        handler.sendEmptyMessageDelayed(1,3000);

    }



}
