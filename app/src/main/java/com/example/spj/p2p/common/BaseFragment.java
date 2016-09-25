package com.example.spj.p2p.common;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spj.p2p.ui.LoadingPage;
import com.example.spj.p2p.utils.UIUtils;
import com.loopj.android.http.RequestParams;

import butterknife.ButterKnife;

/**
 * Created by spj on 2016/9/19.
 */
public abstract class BaseFragment extends Fragment {

    private Context mContext;
    private LoadingPage loadingPage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = UIUtils.getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        loadingPage = new LoadingPage(container.getContext()) {
            @Override
            public int layoutId() {
                return getLayoutId();
            }

            @Override
            protected RequestParams params() {
                return getParams();
            }

            @Override
            protected void onSuccess(ResultState resultState, View successView) {
                ButterKnife.bind(BaseFragment.this,successView);
                initTitle();
                initData(resultState.getContent());
            }

            @Override
            protected String url() {
                return getUrl();
            }
        };
        return loadingPage;
    }


    //oncreadView()方法调用之后，方法可使用showLoadingPage(),所以声名在如下的方法中
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
                showLoadingPage();
    }

    private void showLoadingPage() {
        loadingPage.show();
    }

    //提供请求的参数
    protected abstract RequestParams getParams();
    //提供请求的url
    protected abstract String getUrl();
    //初始化数据
    protected abstract void initData(String content);
    //初始化title
    protected abstract void initTitle();
    //提供对应的布局文件
    public abstract int getLayoutId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
