package com.example.spj.p2p.ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * Created by spj on 2016/9/21.
 */
public class MyScrollView extends ScrollView{


    private View childView;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //获取内部的子视图

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(getChildCount() > 0) {
            childView = getChildAt(0);
        }
    }

    private int lastY,lastX,downX,downY;
    private Rect normal = new Rect();//用户记录临界状态下的childview的上，左，下，右
    private boolean isFinishAnimation = true;//判断动画是否结束

    //子视图和父视图事件冲突，这个是父视图，所以考虑对子视图的拦截操作
    //true，拦截成功，false，拦截失败
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int evenY = (int) ev.getY();
        int evenX = (int) ev.getX();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN :
                downX = lastX=evenX;
                downY = lastY=evenY;
                Log.e("TAG", "lastY__________down"+lastY);
                break;
            case MotionEvent.ACTION_MOVE :
                int totalX = Math.abs(evenX-downX);
                int totalY = Math.abs(evenY-downY);
                if(totalY > totalX && totalY >10) {
                    return true;
                }
                Log.e("TAG", "lastY__________up"+lastY);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //没有子视图，不执行下面的方法，平移动画没有结束，不执行下面的方法
        if(childView == null || !isFinishAnimation) {
            return super.onTouchEvent(ev);
        }

        int eventY = (int) ev.getY();//获取相对于当前视图的y轴坐标
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN :
                lastY = eventY;
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = eventY - lastY;//获得移动的距离
                if(isNeedMove()) {
                    //记录在临界位置时候的childview的坐标
                    if(normal.isEmpty()) {
                        //未被赋值
                        normal.set(childView.getLeft(),childView.getTop(),childView.getRight(),childView.getBottom());
                    }
                    //给childView重新布局(dy/2为了不让移动的太多)
                    childView.layout(childView.getLeft(),childView.getTop()+dy/2,childView.getRight(),childView.getBottom()+dy/2);
                }
                lastY = eventY;
                break;
            case MotionEvent.ACTION_UP :
                if(isNeedAnimation()) {
                    TranslateAnimation animation = new TranslateAnimation(0,0,0,normal.bottom-childView.getBottom());
                    animation.setDuration(300);
                    childView.startAnimation(animation);
                    //设置动画的监听
                    animation.setAnimationListener(new Animation.AnimationListener() {

                        @Override
                        public void onAnimationStart(Animation animation) {
                            isFinishAnimation = false;
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            //动画结束清除动画
                            childView.clearAnimation();
                            //重新布局childview
                            childView.layout(normal.left,normal.top,normal.right,normal.bottom);
                            //置空normal
                            normal.setEmpty();
                            isFinishAnimation = true;
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }

                break;
        }
        return super.onTouchEvent(ev);
    }

    //判断up时候，是否需要平移动画
    private boolean isNeedAnimation() {
        return !normal.isEmpty();
    }

    //判断在何种情况下需要给scrollview重新布局
    private boolean isNeedMove() {
        //获取子视图的测量高度
        int measuredHeight = childView.getMeasuredHeight();
        int height = this.getHeight();//得到scrollview在屏幕上占据的空间的高度
        Log.e("TAG", "measuredHeight"+measuredHeight+",height"+height);
        //测量距离差
        int dHeight = measuredHeight - height;
        //获取子视图在y轴上的滚动量，特点上加下减
        int scrollY = childView.getScrollY();
        if(scrollY <= 0 || scrollY >= dHeight) {
            return true;
        }
        return false;
    }
}
