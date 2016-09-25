package com.example.spj.p2p.common;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by spj on 2016/9/23.
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter{

    private List<T> lists;

    public MyBaseAdapter(List<T> lists) {
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists==null?0:lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //解决两个问题，一。提供一个holder的实现，并在holder中关联convertview，同时提供item layout加载
    //二，将集合中的具体position位置的数据装配到holder的converview中
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder;
        T t = lists.get(position);
        if(convertView == null) {
            holder = getHolder();
        }else {
            holder= (BaseHolder) convertView.getTag();
        }
        //装配数据
        holder.setData(t);

        return holder.getRootView();
    }

    protected abstract BaseHolder getHolder();
}
