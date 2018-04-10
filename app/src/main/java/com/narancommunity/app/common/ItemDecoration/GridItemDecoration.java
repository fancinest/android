package com.narancommunity.app.common.ItemDecoration;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.View;

/**
 * Writer：fancy on 2017/5/9 14:13
 * Email：120760202@qq.com
 * FileName : 有问题，后面遇到在处理
 */

public class GridItemDecoration extends RecyclerView.ItemDecoration {
    private int verticalSpace;
    private int horizontalSpace;
    private Paint mPaint;

    public GridItemDecoration(int horizontalSpace, int verticalSpace, int colour) {
        this.horizontalSpace = horizontalSpace;
        this.verticalSpace = verticalSpace;
        mPaint = new Paint();
        mPaint.setColor(colour);
    }


    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawHorizontal(c, parent);
        drawVertical(c, parent);
    }

    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final int top = child.getBottom();
            final int bottom = top + verticalSpace;
            int left = child.getLeft();
            int right = child.getRight();
            c.drawRect(left, top, right, bottom, mPaint);
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getTop();
            final int bottom = child.getBottom() + verticalSpace;
            final int left = child.getRight() + params.rightMargin;
            final int right = left + horizontalSpace;
            c.drawRect(left, top, right, bottom, mPaint);
        }
    }

    /**
     * @param parent     RecyclerView
     * @param pos        当前item的位置
     * @param spanCount  每行显示的item个数
     * @param childCount child个数
     */
    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int leftCount = childCount - childCount % spanCount;//3
            //leftCount:若childCount能被span整除为childCount否则为去掉最后一行的item总数
            if ((pos + 1) > leftCount) {
                return true;
            }
        }
        return false;
    }

    private boolean isLastColumn(RecyclerView parent, int pos, int spanCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if ((pos) % spanCount == 0)
                // 如果是最后一列，则不需要绘制右边
                return true;
        }
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        //判断总的数量是否可以整除
        int totalCount = layoutManager.getItemCount();
        int surplusCount = totalCount % layoutManager.getSpanCount();
        int childPosition = parent.getChildAdapterPosition(view);
        if (layoutManager.getOrientation() == GridLayoutManager.VERTICAL) {//竖直方向的
            if (surplusCount == 0 && childPosition > totalCount - layoutManager.getSpanCount() - 1) {
                //后面几项需要bottom
                outRect.bottom = verticalSpace;
            } else if (surplusCount != 0 && childPosition > totalCount - surplusCount - 1) {
                outRect.bottom = verticalSpace;
            }
            if ((childPosition + 1) % layoutManager.getSpanCount() == 0) {//被整除的需要右边
                outRect.right = horizontalSpace;
            }
            outRect.top = verticalSpace;
            outRect.left = horizontalSpace;
        } else {
            if (surplusCount == 0 && childPosition > totalCount - layoutManager.getSpanCount() - 1) {
                //后面几项需要右边
                outRect.right = horizontalSpace;
            } else if (surplusCount != 0 && childPosition > totalCount - surplusCount - 1) {
                outRect.right = horizontalSpace;
            }
            if ((childPosition + 1) % layoutManager.getSpanCount() == 0) {//被整除的需要下边
                outRect.bottom = verticalSpace;
            }
            outRect.top = verticalSpace;
            outRect.left = horizontalSpace;
        }
    }

//    @Override
//    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        super.getItemOffsets(outRect, view, parent, state);
//        int itemPosition = parent.getChildAdapterPosition(view);
//        int spanCount = getSpanCount(parent);
//        int childCount = parent.getAdapter().getItemCount();
//        if (!(parent.getLayoutManager() instanceof GridLayoutManager)) {
//            //LinearLayoutManager
//            if (itemPosition == (childCount - 2))
//                outRect.set(0, 0, 0, 0);
//            else
//                outRect.set(0, 0, 0, verticalSpace);
//        } else {
//            //GridLayoutManager
//            if (isLastRaw(parent, itemPosition, spanCount, childCount - 2)) {
//                //最后一行
//                if (isLastColumn(parent, itemPosition, spanCount)) {
//                    // 最后一行最后一列
//                    outRect.set(0, 0, 0, verticalSpace);
//                } else {
//                    // 最后一行不是最后一列
//                    outRect.set(0, 0, horizontalSpace, verticalSpace);
//                }
//            } else {
//                //最后一列
//                if (isLastColumn(parent, itemPosition, spanCount)) {
//                    // 最后一列最后一行
//                    outRect.set(0, 0, 0, verticalSpace);
//                } else {
//                    // 最后一列非最后一行
//                    outRect.set(0, 0, horizontalSpace, verticalSpace);
//                }
//            }
//        }
//    }

    public static class Builder {
        private Context mContext;
        private Resources mResources;
        private int mHorizontal;
        private int mVertical;


        private int mColour;

        public Builder(Context context) {
            mContext = context;
            mResources = context.getResources();
            mHorizontal = 0;
            mVertical = 0;
            mColour = Color.BLACK;
        }


        /**
         * Sets the divider colour
         *
         * @param resource the colour resource id
         * @return the current instance of the Builder
         */
        public GridItemDecoration.Builder setColorResource(@ColorRes int resource) {
            setColor(ContextCompat.getColor(mContext, resource));
            return this;
        }

        /**
         * Sets the divider colour
         *
         * @param color the colour
         * @return the current instance of the Builder
         */
        public GridItemDecoration.Builder setColor(@ColorInt int color) {
            mColour = color;
            return this;
        }


        //通过dp设置垂直间距
        public GridItemDecoration.Builder setVertical(@DimenRes int vertical) {
            this.mVertical = mResources.getDimensionPixelSize(vertical);
            return this;
        }

        //通过px设置垂直间距
        public GridItemDecoration.Builder setVertical(float mVertical) {
            this.mVertical = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, mVertical, mResources.getDisplayMetrics());
            return this;
        }

        //通过dp设置水平间距
        public GridItemDecoration.Builder setHorizontal(@DimenRes int horizontal) {
            this.mHorizontal = mResources.getDimensionPixelSize(horizontal);
            return this;
        }

        //通过px设置水平间距
        public GridItemDecoration.Builder setHorizontal(float horizontal) {
            this.mHorizontal = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, horizontal, mResources.getDisplayMetrics());
            return this;
        }

        /**
         * Instantiates a DividerDecoration with the specified parameters.
         *
         * @return a properly initialized DividerDecoration instance
         */
        public GridItemDecoration build() {
            return new GridItemDecoration(mHorizontal, mVertical, mColour);
        }
    }

}

