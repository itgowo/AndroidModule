package com.itgowo.module.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.itgowo.module.chat.ChatActivity;
import com.itgowo.module.demo.viewModule.FollowUpViewModule;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatActivity.go(v.getContext(), new FollowUpViewModule());
            }
        });
    }
}
