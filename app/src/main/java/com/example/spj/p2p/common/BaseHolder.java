package com.example.spj.p2p.common;

import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by spj on 2016/9/23.
 */
public abstract class BaseHolder<T> {
    //要装配的数据
    private T data;
    //装配到的视图
    private View rootView;

    public BaseHolder() {
        rootView = initView();
        rootView.setTag(this);
        ButterKnife.bind(this,rootView);
    }

    protected abstract View initView();

    public void setData(T t){
        this.data = t;
        //如何装配
        refreshData(t);
    }

    public View getRootView(){
        return rootView;
    }

    protected abstract void refreshData(T t);
}
