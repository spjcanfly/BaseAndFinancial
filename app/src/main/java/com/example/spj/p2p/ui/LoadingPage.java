package com.example.spj.p2p.ui;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.spj.p2p.R;
import com.example.spj.p2p.utils.UIUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by spj on 2016/9/21.
 * 提供不同的需要联网操作的Fragment的通用页面。
 * 联网页面可能出现的四种状态：
 * １．正在加载
 * ２.联网失败
 * ３.联网成功，但是数据返回为空
 * 4.联网成功，且正确的返回数据
 */
public abstract class LoadingPage extends FrameLayout{

    //联网操作的4种不同状态
    private static final int PAGE_STATE_LOADING = 1;
    private static final int PAGE_STATE_ERROR = 2;
    private static final int PAGE_STATE_EMPTY = 3;
    private static final int PAGE_STATE_SUCCESS = 4;

    //当前的状态
    private int page_state_current = PAGE_STATE_LOADING;

    //提供4种不同的View的展示
    private View loadingView;
    private View errorView;
    private View emptyView;
    private View successView;
    private LayoutParams params;

    private Context mContext;

    public LoadingPage(Context context) {
        this(context, null);
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        if(loadingView == null) {
            loadingView = UIUtils.getXmlView(R.layout.page_loading);
            addView(loadingView,params);
        }

        if(errorView == null) {
            errorView = UIUtils.getXmlView(R.layout.page_error);
            addView(errorView,params);
        }

        if(emptyView == null) {
            emptyView = UIUtils.getXmlView(R.layout.page_empty);
            addView(emptyView,params);
        }

        showSafePage();
    }

    //保证View的显示在主线程中执行
    private void showSafePage() {
        UIUtils.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                //显示具体的页面
                showPage();
            }
        });

    }

    //显示具体的页面
    private void showPage() {
        loadingView.setVisibility(page_state_current == PAGE_STATE_LOADING ?View.VISIBLE:View.GONE);
        errorView.setVisibility(page_state_current == PAGE_STATE_ERROR ?View.VISIBLE:View.GONE);
        emptyView.setVisibility(page_state_current == PAGE_STATE_EMPTY ? View.VISIBLE : View.GONE);

        if(successView == null) {
//            successView = UIUtils.getXmlView(layoutId()); 当前使用的是application的实例
            successView = View.inflate(mContext,layoutId(),null);//使用fragment依赖的mainactivity的实例
            addView(successView,params);
        }

        successView.setVisibility(page_state_current == PAGE_STATE_SUCCESS?VISIBLE:GONE);
    }

    public abstract int layoutId();

    private AsyncHttpClient client = new AsyncHttpClient();
    //声,明联网状态和数据的类的对象
    private ResultState resultState;
    /**
     *实现联网操作，根据联网的情况，修改当前的page_state_current
     */
    public void show(){
        String url = url();
        if(TextUtils.isEmpty(url)) {
            //只有是成功的，才能保证page_state_current是success的，进而调用onSuccess()
            resultState = ResultState.SUCCESS;
            resultState.setContent("");
            loadPage();
            return;
        }

        //哪里有网络的请求，就推迟延迟
        UIUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                client.get(url(), params(), new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String content) {
                        if (TextUtils.isEmpty(content)) {
                            resultState = ResultState.EMPTY;
                            resultState.setContent("");
                        } else {
                            resultState = ResultState.SUCCESS;
                            //封装响应的json数据
                            resultState.setContent(content);
                        }

                        loadPage();
                    }

                    @Override
                    public void onFailure(Throwable error, String content) {
                        resultState = ResultState.ERROR;
                        resultState.setContent("");
                        loadPage();
                    }
                });
            }
        }, 2000);

    }


    //根据当前的resultState的状态和封装的数据，更新page_state_current,以及传递数据
    private void loadPage() {
        switch (resultState) {
            case ERROR:
                page_state_current = PAGE_STATE_ERROR;
                break;
            case EMPTY:
                page_state_current = PAGE_STATE_EMPTY;
                break;
            case SUCCESS:
                page_state_current = PAGE_STATE_SUCCESS;
                break;
        }
        showSafePage();

        if(page_state_current == PAGE_STATE_SUCCESS) {
            onSuccess(resultState,successView);
        }
    }

    //提供请求参数
    protected abstract RequestParams params();

    protected abstract void onSuccess(ResultState resultState, View successView);

    //提供联网请求的url
    protected abstract String url();

    //封装联网状态和数据的枚举类
    public enum ResultState{
        ERROR(2),EMPTY(3),SUCCESS(4);//提供的3个枚举类对象

        private int state;//联网状态

        ResultState(int state) {
            this.state = state;
        }

        private String content;

        public String getContent(){
            return content;
        }

        public void setContent(String content){
            this.content = content;
        }
    }
}
