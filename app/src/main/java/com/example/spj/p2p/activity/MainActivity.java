package com.example.spj.p2p.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.spj.p2p.R;
import com.example.spj.p2p.common.AppManager;
import com.example.spj.p2p.common.BaseFragment;
import com.example.spj.p2p.fragment.HomeFragment;
import com.example.spj.p2p.fragment.InvestFragment;
import com.example.spj.p2p.fragment.MeFragment;
import com.example.spj.p2p.fragment.MoreFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {

    @Bind(R.id.rg_main)
    RadioGroup rgMain;

    private ArrayList<BaseFragment> fragments;
    private Fragment fragment;
    private int position;
    private boolean isExit = true;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                isExit = true;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initFragment();

        setListener();

        //将当前的activity添加到栈管理中
        AppManager.getInstance().addActivity(this);

    }

    //监听fragment的切换
    private void setListener() {
        Log.e("TAG", "1111111111111");
        Log.e("TAG", "rgmain" + rgMain);
        rgMain.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //默认选择第一个界面
        rgMain.check(R.id.rb_bottom_home);
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new InvestFragment());
        fragments.add(new MeFragment());
        fragments.add(new MoreFragment());
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            if (isExit) {
                Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                isExit =false;
                handler.sendEmptyMessageDelayed(0, 2000);
                return true;
            }

        }
        return super.onKeyDown(keyCode,event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        handler.removeCallbacksAndMessages(null);
        handler = null;
    }

    private class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {


        private Fragment toFragment;

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            switch (checkedId) {
                case R.id.rb_bottom_home:
                    position = 0;
                    break;
                case R.id.rb_bottom_invest:
                    position = 1;
                    break;
                case R.id.rb_bottom_me:
                    position = 2;
                    break;
                case R.id.rb_bottom_settings:
                    position = 3;
                    break;

            }
            //根据位置从集合中取出对应的fragment
            toFragment = getFragment(position);
            //把对应的Fragment绑定到Activity中
            switchFragment(fragment, toFragment);


        }
    }

    //隐藏刚刚显示的，显示将要显示的
    private void switchFragment(Fragment fromFragment, Fragment toFragment) {
        if (fromFragment != toFragment) {
            fragment = toFragment;
            if (toFragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (toFragment.isAdded()) {
                    //添加了，则显示
                    transaction.show(toFragment).commit();
                } else {
                    //没有添加，添加了，提交
                    transaction.add(R.id.fl_main_content, toFragment).commit();
                }
                //这是一个坑
                if (fromFragment != null) {
                    //隐藏之前的fragment
                    transaction.hide(fromFragment);
                }

            }
        }
    }

    private Fragment getFragment(int position) {
        return fragments.get(position);
    }
}
