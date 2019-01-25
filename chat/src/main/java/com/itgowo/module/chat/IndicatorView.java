package com.itgowo.module.chat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class IndicatorView extends View {
    private int radius;
    private int number;
    private int position;
    private int defaultColor = Color.LTGRAY;
    private int checkedColor = Color.WHITE;
    private Rect[] cxs;
    private Paint defaultPatin;
    private Paint checkedPatin;
    private OnClickListener listener;


    public IndicatorView(Context context) {
        this(context, null);
    }

    public IndicatorView setRadius(int radius) {
        this.radius = radius;
        return this;
    }

    public IndicatorView setNumber(int number) {
        this.number = number;
        return this;
    }


    public IndicatorView setDefaultColor(int defaultColor) {
        this.defaultColor = defaultColor;
        return this;
    }

    public IndicatorView setCheckedColor(int checkedColor) {
        this.checkedColor = checkedColor;
        return this;
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(20, 6);
    }

    public void init(int radius, int number) {
        this.radius = radius;
        this.number = number;
        defaultPatin = new Paint();
        defaultPatin.setColor(defaultColor);
        defaultPatin.setAntiAlias(true);
        defaultPatin.setDither(true);
        checkedPatin = new Paint();
        checkedPatin.setColor(checkedColor);
        checkedPatin.setAntiAlias(true);
        checkedPatin.setDither(true);
    }

    public void setPosition(int positiion) {
        this.position = positiion;
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < number; i++) {
            canvas.drawCircle(cxs[i].centerX(), radius, radius, i == position ? checkedPatin : defaultPatin);
        }
    }

    public void setOnIndicatorClickListener(OnClickListener l) {
        if (!isClickable()) {
            setClickable(true);
        }
        listener = l;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (listener != null) {
                for (int i = 0; i < cxs.length; i++) {
                    if (cxs[i].contains((int) event.getX(), (int) event.getY())) {
                        position = i;
                        invalidate();
                        listener.onClick(this, i);
                        break;
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int center = MeasureSpec.getSize(widthMeasureSpec) / 2;
        cxs = new Rect[number];
        int length = radius * 3 * number;
        int centerx;
        for (int i = 0; i < number; i++) {
            centerx = center - length / 2 + radius * (i * 3 + 1);
            cxs[i] = new Rect(centerx - radius, 0, centerx + radius, radius * 2);
        }
        int height=radius*2;


    }

    public interface OnClickListener {
        public void onClick(View view, int position);
    }
}
