package com.example.spj.p2p.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spj.p2p.R;
import com.example.spj.p2p.common.BaseFragment;
import com.example.spj.p2p.utils.UIUtils;
import com.loopj.android.http.RequestParams;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * 投资页面
 */
public class InvestFragment extends BaseFragment {


    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.iv_top_settings)
    ImageView ivTopSettings;
    @Bind(R.id.tab_page_indicator)
    TabPageIndicator tabPageIndicator;
    @Bind(R.id.vp_invest)
    ViewPager vpInvest;


    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return null;
    }

    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void initData(String content) {
        initFragments();
        MyAdapter adapter = new MyAdapter(getFragmentManager());
        vpInvest.setAdapter(adapter);
        //viewpager和indicator相关联
        tabPageIndicator.setViewPager(vpInvest);
        //注意以后监听页面的变化，TabPageIndicator监听页面的变化
    }

    private void initFragments() {

        ProductListFragment productListFragment = new ProductListFragment();
        ProductRecommondFragment productRecommondFragment = new ProductRecommondFragment();
        ProductHotFragment productHotFragment = new ProductHotFragment();

        fragments.add(productListFragment);
        fragments.add(productRecommondFragment);
        fragments.add(productHotFragment);
    }


    @Override
    protected void initTitle() {
        ivTopBack.setVisibility(View.GONE);
        ivTopSettings.setVisibility(View.GONE);
        tvTopTitle.setText("投资");
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_invest;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

     class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments == null ? 0 : fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //从strings.xml中读取String构成的数组
            return UIUtils.getStringArray(R.array.invest_tab)[position];
        }
    }
}
