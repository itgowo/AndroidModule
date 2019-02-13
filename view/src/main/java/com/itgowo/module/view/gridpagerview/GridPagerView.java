package com.itgowo.module.view.gridpagerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itgowo.module.view.IndicatorView;
import com.itgowo.module.view.R;

import java.util.ArrayList;
import java.util.List;

public class GridPagerView extends RelativeLayout {
    private ViewPager moduleViewGridpageViewpager;
    private IndicatorView moduleViewGridpageIndicator;

    private onGridPageViewListener onGridPageViewListener;

    private int columnMax = 4;
    private int rowMax = 2;

    public GridPagerView.onGridPageViewListener getOnGridPageViewListener() {
        return onGridPageViewListener;
    }

    public GridPagerView setOnGridPageViewListener(GridPagerView.onGridPageViewListener onGridPageViewListener) {
        this.onGridPageViewListener = onGridPageViewListener;
        return this;
    }

    public GridPagerView(Context context) {
        super(context);
        setView();
    }

    public GridPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setView();
    }

    /**
     * 代码初始化viewPager和indicatorView
     */
    private void setView() {
        removeAllViews();
        moduleViewGridpageViewpager = new ViewPager(getContext());
        moduleViewGridpageIndicator = new IndicatorView(getContext());
        moduleViewGridpageIndicator.setId(R.id.module_view_indicatorView);
        moduleViewGridpageViewpager.setId(R.id.module_view_GridPagerView);
        RelativeLayout.LayoutParams rl1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rl1.addRule(RelativeLayout.ABOVE, R.id.module_view_indicatorView);
        moduleViewGridpageViewpager.setLayoutParams(rl1);
        RelativeLayout.LayoutParams rl2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.module_view_GridPagerView_indicatorViewHeight));
        rl2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, TRUE);
        rl2.bottomMargin = getResources().getDimensionPixelSize(R.dimen.module_view_GridPagerView_indicatorViewBottomMargin);
        moduleViewGridpageIndicator.setLayoutParams(rl2);
        moduleViewGridpageIndicator.setPadding(0, getResources().getDimensionPixelSize(R.dimen.module_view_GridPagerView_indicatorViewPaddingTop), 0, 0);
        addView(moduleViewGridpageViewpager);
        addView(moduleViewGridpageIndicator);
        moduleViewGridpageIndicator.setOnIndicatorClickListener(new IndicatorView.OnClickListener() {
            @Override
            public void onClick(View view, int position) {
                moduleViewGridpageViewpager.setCurrentItem(position);
            }
        });
    }

    public void init(int rowMax, int columnMax, onGridPageViewListener listener) {
        this.rowMax = rowMax;
        this.columnMax = columnMax;
        this.onGridPageViewListener = listener;
    }

    public void setData(final List<GridItemData> itemDataList) {
        List<View> viewList = new ArrayList<>();
        List<GridItemView> itemViews = new ArrayList<>();
        for (int i = 0; i < itemDataList.size(); i++) {
            final GridItemView gridItemView = new GridItemView(itemDataList.get(i), onGridPageViewListener.getGridItemView(i), getContext());
            itemViews.add(gridItemView);
            final int finalI = i;
            gridItemView.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onGridPageViewListener.onClickGridItem(gridItemView.itemView, finalI, gridItemView, itemDataList.get(finalI));
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
            moduleViewGridpageIndicator.init(12, viewList.size());

        }
        moduleViewGridpageViewpager.setAdapter(new PageAdapter(viewList));
        moduleViewGridpageViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                moduleViewGridpageIndicator.setPosition(i);
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
                this.itemView = new DefaultGridItemView(context);
                this.imageButton = ((DefaultGridItemView) this.itemView).imageButton;
                this.textView = ((DefaultGridItemView) this.itemView).textView;
                this.textView.setText(data.text);
                if (data.image != 0) {
                    this.imageButton.setImageResource(data.image);
                } else {
                    onGridPageViewListener.onBindGridItemView(this);
                }
            } else {
                this.itemView = itemView;
            }
        }
    }

    public interface onGridPageViewListener {
        /**
         * 如果getGridItemView返回View，则此方法不需要实现，否则必须实现
         *
         * @param itemView
         */
        public void onBindGridItemView(GridPagerView.GridItemView itemView);

        /**
         * 返回扩展功能自定义View，如果为Null使用默认布局
         *
         * @param position
         * @return
         */
        public View getGridItemView(int position);

        /**
         * 功能区Item点击事件
         *
         * @param view
         * @param position
         * @param gridItemView
         * @param data
         */
        public void onClickGridItem(View view, int position, GridPagerView.GridItemView gridItemView, GridPagerView.GridItemData data);
    }
}
