package com.cb.sticky;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cb.sticky.library.R;

import java.lang.reflect.Field;

/**
 * 绘制divider
 * Created by cb on 2019/12/16.
 */
public class DividerDecoration extends RecyclerView.ItemDecoration {
    private Paint mPaint = new Paint();
    /**
     * 定义divider的高度
     */
    private int mDividerHeight = 3;
    /**
     * 底部视图的高度
     */
    private int mLoadEndHeight;
    private boolean mHasResetDecorInsets = true;
    private TextView mLoadEndView;
    private boolean mHasMeasure = false;

    public DividerDecoration(@NonNull Context context) {
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#333333"));
        mLoadEndHeight = context.getResources().getDimensionPixelOffset(R.dimen.load_end_layout_height);
        mLoadEndView = new TextView(context);
        mLoadEndView.setText("已经到底啦");
        mLoadEndView.setTextSize(20);
        mLoadEndView.setTextColor(Color.parseColor("#000000"));
        mLoadEndView.setGravity(Gravity.CENTER);
        mLoadEndView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (parent.getChildCount() <= 0) return;
        if (!mHasMeasure) {
            mHasMeasure = true;
            mLoadEndView.measure(View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(mLoadEndHeight, View.MeasureSpec.EXACTLY));
            mLoadEndView.layout(0, 0, parent.getWidth(), mLoadEndHeight);
        }
        for (int index = 0; index < parent.getChildCount(); index++) {
            View child = parent.getChildAt(index);
            int position = parent.getChildLayoutPosition(child);
            // 绘制分割线
            c.drawRect(new Rect(child.getLeft(),
                    child.getBottom(), child.getRight(),
                    child.getBottom() + mDividerHeight), mPaint);
            // 绘制底部视图
            if (isFilledAndLastChild(parent, position)) {
                int count = c.save();
                c.translate(0, child.getBottom() + mDividerHeight);
                mLoadEndView.draw(c);
                c.restoreToCount(count);
            } else if (position == (parent.getAdapter().getItemCount() - 1) && !mHasResetDecorInsets) {
                mHasResetDecorInsets = true;
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                // 在不满一屏的时候需要减去在getItemOffsets方法中设置的DecorInsetsBottom(mLoadEndHeight)
                setDecorInsetsBottom(params, mDividerHeight);
            }
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        int totalHeight = mDividerHeight;
        int position = parent.getChildLayoutPosition(view);
        if (position == (parent.getAdapter().getItemCount() - 1)) {
            totalHeight += mLoadEndHeight;
            mHasResetDecorInsets = false;
        }
        outRect.bottom = totalHeight;
    }

    /**
     * 反射重置DecorInsetsBottom，在子视图无法填充满RecyclerView时，也能正常展示底部视图
     *
     * @param param  子view的LayoutParams
     * @param bottom 重置的DecorInsetsBottom的值
     */
    private void setDecorInsetsBottom(RecyclerView.LayoutParams param, int bottom) {
        try {
            // 找到RecyclerView.LayoutParams中的mDecorInsets属性值
            Field filed = RecyclerView.LayoutParams.class.getDeclaredField("mDecorInsets");
            filed.setAccessible(true);
            Rect decorRect = (Rect) filed.get(param);
            decorRect.bottom = bottom;
        } catch (Exception e) {
        }
    }

    /**
     * 判断RecyclerView是否被填满&是否是最后一个子view
     *
     * @param parent        RecyclerView
     * @param childPosition 子view的位置
     * @return false/true
     */
    private boolean isFilledAndLastChild(RecyclerView parent, int childPosition) {
        int childCount = parent.getChildCount();
        if (childCount <= 0 || childPosition != (parent.getAdapter().getItemCount() - 1)) {
            return false;
        }
        View firstChild = parent.getChildAt(0);
        View lastChild = parent.getChildAt(childCount - 1);
        int firstPosition = parent.getChildLayoutPosition(firstChild);
        // 首个child pos大于0则认为是被填满的,否则判断高度
        if (firstPosition > 0 || (lastChild.getBottom() - firstChild.getTop()) >= parent.getHeight()) {
            return true;
        }
        return false;
    }
}
