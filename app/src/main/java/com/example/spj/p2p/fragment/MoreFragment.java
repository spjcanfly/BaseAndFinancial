package com.example.spj.p2p.fragment;


import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spj.p2p.R;
import com.example.spj.p2p.common.BaseFragment;
import com.loopj.android.http.RequestParams;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends BaseFragment {


    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.iv_top_settings)
    ImageView ivTopSettings;
    @Bind(R.id.tv_say)
    TextView tvSay;


    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void initData(String content) {
//        //实现跑马灯的效果一：
//        tvSay.setFocusable(true);
//        tvSay.setFocusableInTouchMode(true);
//        tvSay.requestFocus();
        //实现跑马灯的效果二：有利于多处使用跑马灯
//        自定义textview
    }

    @Override
    protected void initTitle() {
        ivTopBack.setVisibility(View.GONE);
        ivTopSettings.setVisibility(View.GONE);
        tvTopTitle.setText("更多");
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_more;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
