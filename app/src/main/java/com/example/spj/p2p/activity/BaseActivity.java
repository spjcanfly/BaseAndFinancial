package com.example.spj.p2p.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.spj.p2p.bean.User;
import com.example.spj.p2p.common.AppManager;
import com.loopj.android.http.AsyncHttpClient;

import butterknife.ButterKnife;

/**
 * Created by spj on 2016/9/24.
 */
public abstract class BaseActivity extends FragmentActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        //将当前的activity添加到自己的栈空间中
        AppManager.getInstance().addActivity(this);

        //初始化操作
        initTitle();
        initData();
    }

    protected abstract void initData();

    protected abstract void initTitle();

    protected abstract int getLayoutId();

    //启动新的activity
    public void goToActivity(Class activity,Bundle bundle){
        Intent intent = new Intent(this, activity);
        //携带数据
        if(bundle != null) {
            intent.putExtra("data",bundle);
        }
        startActivity(intent);
    }

    //联网操作需要使用AsyncHttpClient
    public AsyncHttpClient client = new AsyncHttpClient();

    //保存用户登录信息
    public void saveLogin(User user){

        SharedPreferences sp = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        sp.edit().putString("UF_ACC",user.UF_ACC)
                .putString("UF_AVATAR_URL",user.UF_AVATAR_URL)
                .putString("UF_IS_CERT",user.UF_IS_CERT)
                .putString("UF_PHONE",user.UF_PHONE)
                .commit();
    }

    //读取用户的登录信息
    public User readLogin(){
        SharedPreferences sp = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        User user = new User();
        user.UF_ACC = sp.getString("UF_ACC","");
        user.UF_AVATAR_URL = sp.getString("UF_AVATAR_URL","");
        user.UF_IS_CERT = sp.getString("UF_IS_CERT","");
        user.UF_PHONE = sp.getString("UF_PHONE","");
        return user;
    }

    //关闭当前activity的显示
    public void closeCurrentActivity(){
        AppManager.getInstance().removeCurrent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
