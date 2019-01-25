package com.itgowo.module.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    private static final String INTENT_EXTRA_LISTENER = "listener";
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private onViewModuleListener viewListener;
    private List listItems = new ArrayList();
    private Map<Integer, ViewAction> viewActionType = new HashMap<>();
    private BaseAdapter adapter;
    private Messenger messenger;
    private ChatResource resource;
    private LinearLayout moduleChatBottomLayout, moduleChatBottomLayoutFirstline;
    private ImageView moduleChatInputTypeSwitch;
    private ImageView moduleChatEmoji;
    private Button moduleChatOther;
    private EditText moduleChatInputEt;
    private Button moduleChatVoiceBtn;
    private FrameLayout moduleChatBottomLayoutGrid;
    private KeyboardLayout keyboardLayout;
    private int keyboardHeight;
    private boolean isFirst = true;
    private GridPageView moduleChatBottomLayoutGridview;
    private ViewGroup rootLayout;


    public static void go(Context context, onViewModuleListener listener) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(INTENT_EXTRA_LISTENER, listener);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resource = new ChatResource();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        viewListener = (onViewModuleListener) getIntent().getSerializableExtra(INTENT_EXTRA_LISTENER);
        viewListener.onCreateActivity(this, resource);
        setContentView(R.layout.module_chat_activity_chat);
        rootLayout = findViewById(R.id.module_chat_rootlayout);
        rootLayout.setBackgroundResource(resource.getResourceBG());
        toolbar = findViewById(R.id.module_chat_toolbar);
        viewListener.onCreateToolbar(this, toolbar);
        setSupportActionBar(toolbar);
        init();

    }

    private void hideSoftKeyboard(View view) {
        InputMethodManager im = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null) {
            im.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void showSoftKeyboard(View view) {
        InputMethodManager im = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im != null) {
            im.showSoftInput(view, 0);
        }
    }

    private void init() {
        moduleChatInputEt = (EditText) findViewById(R.id.module_chat_input_et);
        moduleChatBottomLayout = (LinearLayout) findViewById(R.id.module_chat_bottomLayout);
        moduleChatBottomLayoutFirstline = (LinearLayout) findViewById(R.id.module_chat_bottomLayout_firstline);
        moduleChatInputTypeSwitch = (ImageView) findViewById(R.id.module_chat_input_type_switch);
        moduleChatEmoji = (ImageView) findViewById(R.id.module_chat_emoji);
        moduleChatOther = findViewById(R.id.module_chat_other);
        moduleChatVoiceBtn = findViewById(R.id.module_chat_voice_btn);
        moduleChatBottomLayoutGrid = findViewById(R.id.module_chat_bottomLayout_grid);
        recyclerView = findViewById(R.id.module_chat_recyclerView);
        swipeRefreshLayout = findViewById(R.id.module_chat_swipeRefreshLayout);
        moduleChatBottomLayoutGridview = findViewById(R.id.module_chat_bottomLayout_gridview);
        keyboard();

        initResoure();


        viewActionType.putAll(viewListener.getViewTypeList());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BaseAdapter(listItems, viewActionType, this);
        recyclerView.setAdapter(adapter);
        messenger = new Messenger(listItems, swipeRefreshLayout, this, adapter, moduleChatBottomLayoutGridview);
        viewListener.onBind(messenger,recyclerView);

        moduleChatBottomLayoutGridview.init(viewListener);
        viewListener.getGridItemList(messenger);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewListener.getMoreHistory(messenger);
            }
        });


        // 点击输入框
        moduleChatInputEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyboardLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() { // 输入法弹出之后，重新调整
                        moduleChatOther.setSelected(false);
                        moduleChatBottomLayoutGrid.setVisibility(View.GONE);
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    }
                }, 250); // 延迟一段时间，等待输入法完全弹出
            }
        });
        moduleChatInputEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (moduleChatInputEt.getText().toString().trim().equals("")) {
                    moduleChatOther.setBackgroundResource(resource.getResourceIcon_OtherButtion());
                    moduleChatOther.setText("");
                } else {
                    moduleChatOther.setBackgroundResource(resource.getResourceIcon_SendButtion());
                    moduleChatOther.setText(resource.getResourceText_SendBtn());
                }
            }
        });
        moduleChatOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moduleChatInputEt.getText().toString().trim().equals("")) {
                    moduleChatOther.setSelected(!moduleChatOther.isSelected());
                    if (keyboardLayout.isKeyboardActive()) { // 输入法打开状态下
                        if (moduleChatOther.isSelected()) { // 打开表情
                            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING); //  不改变布局，隐藏键盘，emojiView弹出
                            hideSoftKeyboard(v);
                            moduleChatBottomLayoutGrid.setVisibility(View.VISIBLE);
                        } else {
                            moduleChatBottomLayoutGrid.setVisibility(View.GONE);
                            hideSoftKeyboard(v);
                            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                        }
                    } else { //  输入法关闭状态下
                        if (moduleChatOther.isSelected()) {
                            // 设置为不会调整大小，以便输入弹起时布局不会改变。若不设置此属性，输入法弹起时布局会闪一下
                            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
                            moduleChatBottomLayoutGrid.setVisibility(View.VISIBLE);
                        } else {
                            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                            moduleChatBottomLayoutGrid.setVisibility(View.GONE);
                        }
                    }
                } else {
                    viewListener.onSendButtonClick(moduleChatInputEt, moduleChatInputEt.getText().toString().trim());
                    moduleChatInputEt.setText("");
                }
            }
        });

        keyboardLayout.setKeyboardListener(new KeyboardLayout.KeyboardLayoutListener() {
            @Override
            public void onKeyboardStateChanged(boolean isActive, int keyboardHeight1) {

                if (isActive) { // 输入法打开
                    if (keyboardHeight != keyboardHeight1) { // 键盘发生改变时才设置emojiView的高度，因为会触发onGlobalLayoutChanged，导致onKeyboardStateChanged再次被调用
                        keyboardHeight = keyboardHeight1;
                        initEmojiView(); // 每次输入法弹起时，设置emojiView的高度为键盘的高度，以便下次emojiView弹出时刚好等于键盘高度
                    }
                    if (moduleChatOther.isSelected()) { // 表情打开状态下
                        moduleChatBottomLayoutGrid.setVisibility(View.GONE);
                        moduleChatOther.setSelected(false);
                    }
                }
            }
        });
    }


    // 设置表情栏的高度
    private void initEmojiView() {
        ViewGroup.LayoutParams layoutParams = moduleChatBottomLayoutGrid.getLayoutParams();
        layoutParams.height = keyboardHeight;
        moduleChatBottomLayoutGrid.setLayoutParams(layoutParams);
    }

    private void keyboard() {
        keyboardLayout = new KeyboardLayout(this);
        keyboardLayout.setKeyboardListener(new KeyboardLayout.KeyboardLayoutListener() {
            @Override
            public void onKeyboardStateChanged(boolean isActive, int keyboardHeight1) {
                System.out.println(isActive + "  " + keyboardHeight1);
                if (isActive && moduleChatBottomLayoutGrid.getHeight() != keyboardLayout.getKeyboardHeight() && keyboardHeight1 > 0 && keyboardHeight == 0) {
                    System.out.println("ChatActivity.onKeyboardStateChanged  " + keyboardHeight1);
                    keyboardHeight = keyboardHeight1;
                    moduleChatBottomLayoutGrid.getLayoutParams().height = keyboardHeight1;
                }
            }
        });
        moduleChatBottomLayoutGrid.addView(keyboardLayout);
    }

    private void initResoure() {
        if (resource.getResourceBG_FirstLine() != 0) {
            moduleChatBottomLayoutFirstline.setBackgroundResource(resource.getResourceBG_FirstLine());
        }
        if (resource.getResourceIcon_EmojiButtion() != 0) {
            moduleChatEmoji.setVisibility(View.VISIBLE);
            moduleChatEmoji.setImageResource(resource.getResourceIcon_EmojiButtion());
        } else {
            moduleChatEmoji.setVisibility(View.GONE);
        }

        if (resource.getResourceIcon_InputTypeButtion_Keyboard1() != 0) {
            moduleChatInputTypeSwitch.setVisibility(View.VISIBLE);
            moduleChatInputTypeSwitch.setImageResource(resource.getResourceIcon_InputTypeButtion_Keyboard1());
            moduleChatInputTypeSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (moduleChatInputTypeSwitch.getTag() == null) {
                        moduleChatInputTypeSwitch.setTag("");
                        moduleChatInputTypeSwitch.setImageResource(resource.getResourceIcon_InputTypeButtion_Keyboard1());
                        moduleChatInputEt.setVisibility(View.GONE);
                        moduleChatVoiceBtn.setVisibility(View.VISIBLE);
                    } else {
                        moduleChatInputTypeSwitch.setTag(null);
                        moduleChatInputTypeSwitch.setImageResource(resource.getResourceIcon_InputTypeButtion_Voice());
                        moduleChatInputEt.setVisibility(View.VISIBLE);
                        moduleChatVoiceBtn.setVisibility(View.GONE);
                    }
                }
            });
        } else {
            moduleChatInputTypeSwitch.setVisibility(View.GONE);
        }


        if (resource.getResourceIcon_OtherButtion() != 0) {
            moduleChatOther.setVisibility(View.VISIBLE);
            moduleChatOther.setBackgroundResource(resource.getResourceIcon_OtherButtion());
        } else {
            moduleChatOther.setVisibility(View.GONE);
        }

        if (resource.getResourceBG_InputET() != 0) {
            moduleChatInputEt.setBackgroundResource(resource.getResourceBG_InputET());
        }
        if (resource.getResourceText_VoiceBtn() != 0) {
            moduleChatVoiceBtn.setText(resource.getResourceText_VoiceBtn());
        }
        if (resource.getResourceBG_VoiceBtn() != 0) {
            moduleChatVoiceBtn.setBackgroundResource(resource.getResourceBG_VoiceBtn());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return viewListener.onCreateOptionsMenu(this, toolbar, menu);
    }

}
