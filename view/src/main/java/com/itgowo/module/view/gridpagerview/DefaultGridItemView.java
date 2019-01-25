package com.itgowo.module.view.gridpagerview;

import android.content.Context;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.itgowo.module.view.R;

public class DefaultGridItemView extends LinearLayout {
    public AppCompatImageButton imageButton;
    public AppCompatTextView textView;

    public DefaultGridItemView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        imageButton = new AppCompatImageButton(getContext());
        LinearLayout.LayoutParams l = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        l.weight = 1;
        imageButton.setLayoutParams(l);
        imageButton.setClickable(false);
        imageButton.setId(R.id.module_view_GridPagerView_item_imagebutton);
        textView = new AppCompatTextView(getContext());
        textView.setGravity(Gravity.CENTER);
        textView.setId(R.id.module_view_GridPagerView_item_textview);
        addView(imageButton);
        addView(textView);
    }
}
