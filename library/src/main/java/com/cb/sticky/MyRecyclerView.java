package com.cb.sticky;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 自定义MyRecyclerView
 * Created by cb on 2019/12/26.
 */
public class MyRecyclerView extends RecyclerView {
    private Rect mInterceptRect;
    private boolean isIntercept;

    public MyRecyclerView(@NonNull Context context) {
        super(context);
    }

    public MyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void interceptTouchEvent(Rect rect) {
        mInterceptRect = rect;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (mInterceptRect != null && mInterceptRect.contains((int) (e.getX() + 0.5f), (int) (e.getY() + 0.5f))) {
            isIntercept = true;
            return true;
        }
        isIntercept = false;
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (isIntercept) {
            return true;
        }
        return super.onTouchEvent(e);
    }
}
