package com.example.spj.p2p.fragment;


import android.support.v4.app.Fragment;

import com.example.spj.p2p.R;
import com.example.spj.p2p.common.BaseFragment;
import com.example.spj.p2p.ui.FlowLayout;
import com.loopj.android.http.RequestParams;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductHotFragment extends BaseFragment {


    @Bind(R.id.flow_layout)
    FlowLayout flowLayout;

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

    }

    @Override
    protected void initTitle() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_hot;
    }

}
