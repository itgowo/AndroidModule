package com.itgowo.module.chat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GridPageView extends RelativeLayout {
    private ViewPager moduleChatGridpageViewpage;
    private IndicatorView moduleChatGridpageIndicator;

    private onViewModuleListener listener;

    private int columnMax = 4;
    private int rowMax = 2;

    public void init(onViewModuleListener listener) {
        this.listener = listener;
        moduleChatGridpageViewpage = (ViewPager) findViewById(R.id.module_chat_gridpage_viewpage);
        moduleChatGridpageIndicator = (IndicatorView) findViewById(R.id.module_chat_gridpage_indicator);
    }

    public GridPageView(Context context) {
        super(context);
        View.inflate(context, R.layout.module_chat_gridpageview, this);
    }

    public GridPageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.module_chat_gridpageview, this);
    }


    public void setData(final List<GridItemData> itemDataList) {
        List<View> viewList = new ArrayList<>();
        List<GridItemView> itemViews = new ArrayList<>();
        for (int i = 0; i < itemDataList.size(); i++) {
            final GridItemView gridItemView = new GridItemView(itemDataList.get(i), listener.getGridItemView(i), getContext());
            itemViews.add(gridItemView);
            final int finalI = i;
            gridItemView.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickGridItem(gridItemView.itemView, finalI, gridItemView, itemDataList.get(finalI));
                }
            });
        }
        if (rowMax * columnMax < itemDataList.size()) {
            int page = (int) Math.ceil(itemDataList.size() * 1.0 / rowMax / columnMax);
            for (int i = 0; i < page; i++) {
                viewList.add(getGridLayout(itemViews, i, rowMax, columnMax));
            }
        } else {
            viewList.add(getGridLayout(itemViews, 0, rowMax, columnMax));
        }
        if (viewList.size() > 1) {
            moduleChatGridpageIndicator.init(9, viewList.size());

        }
        moduleChatGridpageViewpage.setAdapter(new PageAdapter(viewList));
        moduleChatGridpageViewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                moduleChatGridpageIndicator.setPosition(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    public GridLayout getGridLayout(List<GridItemView> itemDataList, int pageIndex, int row, int column) {
        GridLayout gridLayout = new GridLayout(getContext());
        gridLayout.setColumnCount(column);
        gridLayout.setRowCount(row);
        int size = 0;
        int index = pageIndex * row * column;
        int end = (pageIndex + 1) * row * column;
        if (end < itemDataList.size()) {
            size = row * column;
        } else {
            size = itemDataList.size() - index;
        }
        for (int i = 0; i < row * column; i++) {
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(GridLayout.spec(i / column, 1, 1), GridLayout.spec(i % column, 1, 1));
            layoutParams.height = 0;
            layoutParams.width = 0;
            View view = null;
            if (i < size) {
                view = itemDataList.get(index + i).itemView;
            } else {
                view = new View(getContext());
            }
            view.setLayoutParams(layoutParams);
            gridLayout.addView(view);
        }
        return gridLayout;
    }

    public class PageAdapter extends PagerAdapter {
        List<View> views;

        public PageAdapter(List<View> views) {
            this.views = views;
        }

        @Override
        public int getCount() {
            return views == null ? 0 : views.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(views.get(position));

        }
    }

    public static class GridItemData {
        public String imageUrl;
        public int image;
        public String text;
        public Object tag;

        public Object getTag() {
            return tag;
        }

        public GridItemData setTag(Object tag) {
            this.tag = tag;
            return this;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public GridItemData setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public int getImage() {
            return image;
        }

        public GridItemData setImage(int image) {
            this.image = image;
            return this;
        }

        public String getText() {
            return text;
        }

        public GridItemData setText(String text) {
            this.text = text;
            return this;
        }
    }

    public class GridItemView {
        public GridItemData data;
        public View itemView;
        public ImageButton imageButton;
        public TextView textView;

        public GridItemView(GridItemData data, View itemView, Context context) {
            this.data = data;
            if (itemView == null) {
                this.itemView = View.inflate(context, R.layout.module_chat_gridpageview_item, null);
                this.imageButton = this.itemView.findViewById(R.id.module_chat_gridpage_item_image);
                this.textView = this.itemView.findViewById(R.id.module_chat_gridpage_item_text);
                this.textView.setText(data.text);
                if (data.image != 0) {
                    this.imageButton.setImageResource(data.image);
                } else {
                    listener.onBindGridItemView(this);
                }
            } else {
                this.itemView = itemView;
            }
        }

    }
}
