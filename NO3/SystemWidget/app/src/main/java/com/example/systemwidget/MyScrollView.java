package com.example.systemwidget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

/**
 * Created by 25400 on 2019/10/18.
 */

public class MyScrollView extends ViewGroup {

    private int mScreenHeight;
    private Scroller mScroller;
    private int mLastY;
    private int mStart;
    private int mEnd;

    public MyScrollView(Context context) {
        super(context);
        initView(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        //获取屏幕尺寸的方法
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        mScreenHeight = dm.heightPixels;//高度上像素点的个数
        mScroller = new Scroller(context);//Scroller用于View的滑动
    }

    @Override
    protected void onLayout(boolean changed,
                            int l, int t, int r, int b) {
        int childCount = getChildCount();//获得view的个数
        //设置ViewGroup的高度
        MarginLayoutParams mlp = (MarginLayoutParams) getLayoutParams();
        mlp.height = mScreenHeight * childCount;
        setLayoutParams(mlp);
        //设置每个子view的位置
        for (int i = 0; i < childCount; i++){
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE){
                // 每一个子控件在竖直方向上进行布局
                child.layout(1, i * mScreenHeight, r, (i + 1) * mScreenHeight);
            }
        }
    }

    /**
     * 通过遍历的方式对子View进行测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec,
                             int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        for (int i=0; i<count; ++i){
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);//每一个子控件测量大小
        }
    }

    /**
     * 为滑动添加响应时间
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();//点击的位置相对于控件本身的坐标
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                mStart = getScrollY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mScroller.isFinished()) {//滑动动画未结束，则终止动画
                    mScroller.abortAnimation();
                }
                int dy = mLastY - y;
                if (getScrollY() < 0) {//若已经到顶部且手指下滑，则不进行位移动画，即到顶不允许下拉动画
                    dy = 0;
                }
                if (getScrollY() > getHeight() - mScreenHeight) {//到底不允许上拉动画
                    dy = 0;
                }
                scrollBy(0, dy);
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
//                int dScrollY = checkAlignment();
                mEnd = getScrollY();
                int dScrollY = mEnd - mStart;
                if (dScrollY > 0) {
                    if (dScrollY < mScreenHeight / 3) {
                        mScroller.startScroll(
                                0, getScrollY(),
                                0, -dScrollY);
                    } else {
                        mScroller.startScroll(
                                0, getScrollY(),
                                0, mScreenHeight - dScrollY);
                    }
                } else {
                    if (-dScrollY < mScreenHeight / 3) {
                        mScroller.startScroll(
                                0, getScrollY(),
                                0, -dScrollY);
                    } else {
                        mScroller.startScroll(
                                0, getScrollY(),
                                0, -mScreenHeight - dScrollY);
                    }
                }
                break;
        }
        postInvalidate();
        return true;
    }
//    private int checkAlignment(){
//        int mEnd = getScrollY();
//        boolean isUp = ((mEnd - mStart) > 0) ? true: false;
//        int lastPrev = mEnd % mScreenHeight;
//        int lastNext = mScreenHeight - lastPrev;
//        if (isUp){
//            //向上的
//            return lastPrev;
//        }else {
//            return -lastNext;
//        }
//    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()){//判断scroll是否完成
            scrollTo(0, mScroller.getCurrY());//执行本段位移
            postInvalidate();//进行下段位移
        }
    }
}
