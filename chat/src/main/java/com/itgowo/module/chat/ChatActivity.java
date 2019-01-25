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
import android.widget.LinearLayout;

import com.itgowo.module.view.gridpagerview.GridPagerView;

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
    private Button moduleChatInputTypeSwitch;
    private Button moduleChatEmojiBtn;
    private Button moduleChatOtherBtn;
    private EditText moduleChatInputEt;
    private View moduleChatVoiceBtn;
    private FrameLayout moduleChatBottomLayoutGrid;
    private KeyboardLayout keyboardLayout;
    private int keyboardHeight;
    private boolean isFirst = true;
    private GridPagerView moduleChatBottomLayoutGridview2, moduleChatBottomLayoutGridview1;
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
        rootLayout.setBackgroundResource(resource.getBgActivity());
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
            view.requestFocus();
            im.showSoftInput(view, 0);
            keyboardLayout.setmIsKeyboardActive(true);
        }
    }

    private void init() {
        moduleChatInputEt = (EditText) findViewById(R.id.module_chat_input_et);
        moduleChatBottomLayout = (LinearLayout) findViewById(R.id.module_chat_bottomLayout);
        moduleChatBottomLayoutFirstline = (LinearLayout) findViewById(R.id.module_chat_bottomLayout_firstline);
        moduleChatInputTypeSwitch = findViewById(R.id.module_chat_input_type_switch);
        moduleChatEmojiBtn = findViewById(R.id.module_chat_emoji);
        moduleChatOtherBtn = findViewById(R.id.module_chat_other);
        moduleChatVoiceBtn = findViewById(R.id.module_chat_voice_btn);
        moduleChatBottomLayoutGrid = findViewById(R.id.module_chat_bottomLayout_grid);
        recyclerView = findViewById(R.id.module_chat_recyclerView);
        swipeRefreshLayout = findViewById(R.id.module_chat_swipeRefreshLayout);
        moduleChatBottomLayoutGridview2 = findViewById(R.id.module_chat_bottomLayout_gridview2);
        moduleChatBottomLayoutGridview1 = findViewById(R.id.module_chat_bottomLayout_gridview1);
        keyboard();

        initResoure();


        viewActionType.putAll(viewListener.getViewTypeList());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BaseAdapter(listItems, viewActionType, this);
        recyclerView.setAdapter(adapter);
        messenger = new Messenger(listItems, swipeRefreshLayout, this, adapter, moduleChatBottomLayoutGridview1, moduleChatBottomLayoutGridview2, viewListener);
        viewListener.onBind(messenger, recyclerView);

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
                        moduleChatOtherBtn.setSelected(false);
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
                    moduleChatOtherBtn.setBackgroundResource(resource.getIconOtherButtion());
                    moduleChatOtherBtn.setText("");
                } else {
                    moduleChatOtherBtn.setBackgroundResource(resource.getIconSendButtion());
                    moduleChatOtherBtn.setText(resource.getTextSendBtn());
                }
            }
        });
        moduleChatOtherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchBottomLayout(v, false);
            }
        });
        moduleChatEmojiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchBottomLayout(v, true);
            }
        });

    }

    private void switchBottomLayout(View v, boolean isEmoji) {
        if (moduleChatInputEt.getText().toString().trim().equals("")) {
            if (keyboardLayout.isKeyboardActive()) { // 输入法打开状态下
                if (isEmoji) {
                    moduleChatEmojiBtn.setSelected(true);
                    moduleChatOtherBtn.setSelected(false);
                    moduleChatBottomLayoutGridview1.setVisibility(View.VISIBLE);
                    moduleChatBottomLayoutGridview2.setVisibility(View.GONE);
                } else {
                    moduleChatEmojiBtn.setSelected(false);
                    moduleChatOtherBtn.setSelected(true);
                    moduleChatBottomLayoutGridview1.setVisibility(View.GONE);
                    moduleChatBottomLayoutGridview2.setVisibility(View.VISIBLE);
                }
                if (moduleChatOtherBtn.isSelected() || moduleChatEmojiBtn.isSelected()) { // 打开表情
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING); //  不改变布局，隐藏键盘，emojiView弹出
                    hideSoftKeyboard(v);
                    moduleChatBottomLayoutGrid.setVisibility(View.VISIBLE);
                } else {
                    moduleChatBottomLayoutGrid.setVisibility(View.GONE);
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    hideSoftKeyboard(v);
                }
            } else { //  输入法关闭状态下
                if (isEmoji) {
                    if (moduleChatEmojiBtn.isSelected()) {
                        moduleChatEmojiBtn.setSelected(false);
                        moduleChatOtherBtn.setSelected(false);
                        moduleChatBottomLayoutGridview1.setVisibility(View.GONE);
                        moduleChatBottomLayoutGridview2.setVisibility(View.GONE);
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                        moduleChatBottomLayoutGrid.setVisibility(View.GONE);
                    } else {
                        moduleChatEmojiBtn.setSelected(true);
                        moduleChatOtherBtn.setSelected(false);
                        moduleChatBottomLayoutGridview1.setVisibility(View.VISIBLE);
                        moduleChatBottomLayoutGridview2.setVisibility(View.GONE);
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
                        moduleChatBottomLayoutGrid.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (moduleChatOtherBtn.isSelected()) {
                        moduleChatEmojiBtn.setSelected(false);
                        moduleChatOtherBtn.setSelected(false);
                        moduleChatBottomLayoutGridview1.setVisibility(View.GONE);
                        moduleChatBottomLayoutGridview2.setVisibility(View.GONE);
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                        moduleChatBottomLayoutGrid.setVisibility(View.GONE);
                    } else {
                        moduleChatEmojiBtn.setSelected(false);
                        moduleChatOtherBtn.setSelected(true);
                        moduleChatBottomLayoutGridview1.setVisibility(View.GONE);
                        moduleChatBottomLayoutGridview2.setVisibility(View.VISIBLE);
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
                        moduleChatBottomLayoutGrid.setVisibility(View.VISIBLE);
                    }
                }
            }
        } else {
            viewListener.onSendButtonClick(moduleChatInputEt, moduleChatInputEt.getText().toString().trim());
            moduleChatInputEt.setText("");
        }
    }

    @Override
    protected void onDestroy() {
        if (messenger != null) {
            messenger.finish();
        }
        super.onDestroy();
    }

    // 设置表情栏的高度
    private void initBottomLayout() {
        ViewGroup.LayoutParams layoutParams = moduleChatBottomLayoutGrid.getLayoutParams();
        layoutParams.height = keyboardHeight;
        moduleChatBottomLayoutGrid.setLayoutParams(layoutParams);
    }

    private void keyboard() {
        keyboardHeight = getInputKeyboardHeight();
        initBottomLayout();
        keyboardLayout = new KeyboardLayout(this);
        keyboardLayout.setWindow(getWindow());
        keyboardLayout.setKeyboardListener(new KeyboardLayout.KeyboardLayoutListener() {
            @Override
            public void onKeyboardStateChanged(boolean isActive, int keyboardHeight1) {

                if (isActive) { // 输入法打开
                    if (keyboardHeight != keyboardHeight1 && keyboardHeight1 > 0) { // 键盘发生改变时才设置emojiView的高度，因为会触发onGlobalLayoutChanged，导致onKeyboardStateChanged再次被调用
                        keyboardHeight = keyboardHeight1;
                        saveInputKeyboardHeight(keyboardHeight1);
                        initBottomLayout(); // 每次输入法弹起时，设置emojiView的高度为键盘的高度，以便下次emojiView弹出时刚好等于键盘高度
                    }
                    if (moduleChatOtherBtn.isSelected()) { // 表情打开状态下
                        moduleChatBottomLayoutGrid.setVisibility(View.GONE);
                        moduleChatOtherBtn.setSelected(false);
                    }
                }
            }
        });
        moduleChatBottomLayoutGrid.addView(keyboardLayout);
    }

    private void initResoure() {
        if (resource.getBgFirstLine() != 0) {
            moduleChatBottomLayoutFirstline.setBackgroundResource(resource.getBgFirstLine());
        }
        if (resource.getIconEmojiButtion() != 0) {
            moduleChatEmojiBtn.setVisibility(View.VISIBLE);
            moduleChatEmojiBtn.setBackgroundResource(resource.getIconEmojiButtion());
        } else {
            moduleChatEmojiBtn.setVisibility(View.GONE);
            moduleChatBottomLayoutGridview1.setVisibility(View.GONE);
        }

        if (resource.getIconInputTypeButtionKeyboard1() != 0) {
            moduleChatInputTypeSwitch.setVisibility(View.VISIBLE);
            moduleChatInputTypeSwitch.setBackgroundResource(resource.getIconInputTypeButtionVoice());
            moduleChatInputTypeSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (moduleChatInputTypeSwitch.isSelected()) {
                        moduleChatInputTypeSwitch.setSelected(false);
                        moduleChatInputTypeSwitch.setBackgroundResource(resource.getIconInputTypeButtionVoice());
                        moduleChatInputEt.setVisibility(View.VISIBLE);
                        moduleChatVoiceBtn.setVisibility(View.GONE);
                        if (moduleChatBottomLayoutGrid.getVisibility()==View.VISIBLE) {
                            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
                        } else {
                            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                        }

                        showSoftKeyboard(moduleChatInputEt);
                    } else {
                        moduleChatInputTypeSwitch.setSelected(true);
                        moduleChatInputTypeSwitch.setBackgroundResource(resource.getIconInputTypeButtionKeyboard1());
                        moduleChatInputEt.setVisibility(View.GONE);
                        moduleChatVoiceBtn.setVisibility(View.VISIBLE);
                        moduleChatEmojiBtn.setSelected(false);
                        moduleChatOtherBtn.setSelected(false);
                        hideSoftKeyboard(v);
                        moduleChatBottomLayoutGrid.setVisibility(View.GONE);

                    }
                }
            });
        } else {
            moduleChatInputTypeSwitch.setVisibility(View.GONE);
        }


        if (resource.getIconOtherButtion() != 0) {
            moduleChatOtherBtn.setVisibility(View.VISIBLE);
            moduleChatOtherBtn.setBackgroundResource(resource.getIconOtherButtion());
        } else {
            moduleChatOtherBtn.setVisibility(View.GONE);
            moduleChatBottomLayoutGridview2.setVisibility(View.GONE);
        }

        if (resource.getBgInputET() != 0) {
            moduleChatInputEt.setBackgroundResource(resource.getBgInputET());
        }

        if (resource.getViewVoiceBtn() != null) {
            LinearLayout linearLayout = (LinearLayout) moduleChatVoiceBtn.getParent();
            int index = linearLayout.indexOfChild(moduleChatVoiceBtn);
            resource.getViewVoiceBtn().setLayoutParams(moduleChatVoiceBtn.getLayoutParams());
            linearLayout.removeViewAt(index);
            linearLayout.addView(resource.getViewVoiceBtn(), index);
            moduleChatVoiceBtn = resource.getViewVoiceBtn();
        } else {
            if (resource.getBgVoiceBtn() != 0) {
                moduleChatVoiceBtn.setBackgroundResource(resource.getBgVoiceBtn());
            }
            if (resource.getTextVoiceBtn() != 0) {
                ((Button) moduleChatVoiceBtn).setText(resource.getTextVoiceBtn());
            }
        }
        moduleChatVoiceBtn.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return viewListener.onCreateOptionsMenu(this, toolbar, menu);
    }

    public void saveInputKeyboardHeight(int keyboardHeight) {
        getSharedPreferences("AndroidModule", Context.MODE_PRIVATE).edit().putInt("InputKeyboardHeight", keyboardHeight).apply();
    }

    public int getInputKeyboardHeight() {
        return getSharedPreferences("AndroidModule", Context.MODE_PRIVATE).getInt("InputKeyboardHeight", 400);
    }
}
