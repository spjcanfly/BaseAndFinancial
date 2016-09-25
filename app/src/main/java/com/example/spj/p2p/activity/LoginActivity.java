package com.example.spj.p2p.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.spj.p2p.R;
import com.example.spj.p2p.bean.User;
import com.example.spj.p2p.common.AppManager;
import com.example.spj.p2p.common.AppNetConfig;
import com.example.spj.p2p.utils.MD5Utils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {


    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.iv_top_settings)
    ImageView ivTopSettings;
    @Bind(R.id.textView1)
    TextView textView1;
    @Bind(R.id.log_ed_mob)
    EditText logEdMob;
    @Bind(R.id.about_com)
    RelativeLayout aboutCom;
    @Bind(R.id.tv_2)
    TextView tv2;
    @Bind(R.id.log_ed_pad)
    EditText logEdPad;
    @Bind(R.id.log_log_btn)
    Button logLogBtn;

    @Override
    protected void initData() {
        ivTopBack.setVisibility(View.VISIBLE);
        ivTopSettings.setVisibility(View.INVISIBLE);
        tvTopTitle.setText("用户登录");
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }


    @OnClick({R.id.iv_top_back, R.id.log_log_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_top_back:
                AppManager.getInstance().removeAll();
                goToActivity(MainActivity.class,null);
                break;
            case R.id.log_log_btn:
                //获取用户的手机号码和密码
                String number = logEdMob.getText().toString().trim();
                String password = logEdPad.getText().toString().trim();
                if(!TextUtils.isEmpty(number) && !TextUtils.isEmpty(password)) {
                    //2.联网发送请求，将手机号和密码作为请求参数发送给服务器
                    RequestParams params = new RequestParams();
                    params.put("username",number);
                    params.put("password", MD5Utils.MD5(password));
                    client.post(AppNetConfig.LOGIN,params,new AsyncHttpResponseHandler(){
                        //联网成功，返回服务器的数据

                        @Override
                        public void onSuccess(String content) {
                            JSONObject jsonObject = JSON.parseObject(content);
                            Boolean isSuccess = jsonObject.getBoolean("success");
                            if(isSuccess) {
                                //登录成功
                                String data = jsonObject.getString("data");
                                User user = JSON.parseObject(data, User.class);
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                //保存用户登录的信息
                                saveLogin(user);
                                //返回操作
                                AppManager.getInstance().removeAll();
                                goToActivity(MainActivity.class,null);
                            }else {
                                //登陆不成功
                                Toast.makeText(LoginActivity.this, "用户名或者密码不匹配", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Throwable error, String content) {
                            super.onFailure(error, content);
                            Toast.makeText(LoginActivity.this, "联网失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Toast.makeText(LoginActivity.this, "手机号或者密码不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
