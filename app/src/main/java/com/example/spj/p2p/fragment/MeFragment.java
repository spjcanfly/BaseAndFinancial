package com.example.spj.p2p.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.spj.p2p.R;
import com.example.spj.p2p.activity.BarCharActivity;
import com.example.spj.p2p.activity.BaseActivity;
import com.example.spj.p2p.activity.ChongZhiActivity;
import com.example.spj.p2p.activity.LineCharActivity;
import com.example.spj.p2p.activity.LoginActivity;
import com.example.spj.p2p.activity.PieChartActivity;
import com.example.spj.p2p.activity.TiXianActivity;
import com.example.spj.p2p.activity.ToggleButtonActivity;
import com.example.spj.p2p.activity.UserInfoActivity;
import com.example.spj.p2p.bean.User;
import com.example.spj.p2p.common.BaseFragment;
import com.example.spj.p2p.utils.BitmapUtils;
import com.example.spj.p2p.utils.UIUtils;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends BaseFragment {


    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.iv_top_settings)
    ImageView ivTopSettings;
    @Bind(R.id.imageView1)
    ImageView imageView1;
    @Bind(R.id.icon_time)
    RelativeLayout iconTime;
    @Bind(R.id.textView11)
    TextView textView11;
    @Bind(R.id.relativeLayout1)
    RelativeLayout relativeLayout1;
    @Bind(R.id.recharge)
    ImageView recharge;
    @Bind(R.id.withdraw)
    ImageView withdraw;
    @Bind(R.id.ll_touzi)
    TextView llTouzi;
    @Bind(R.id.ll_touzi_zhiguan)
    TextView llTouziZhiguan;
    @Bind(R.id.ll_zichang)
    TextView llZichang;
    @Bind(R.id.ll_zhanquan)
    TextView llZhanquan;


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
        //验证用户是否已经登录
        isLogin();
    }

    private void isLogin() {
        //1.读取本地保存登录信息位置的文件，判断是否已经登录。
        SharedPreferences sp = this.getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String uf_acc = sp.getString("UF_ACC", "");
        if (TextUtils.isEmpty(uf_acc)) {
            //2.1如果没有找到文件中的登录信息：提供用户：必须登录
            login();
        } else {
            //2.2如果找到了登录信息，加载登录用户的信息
            doUser();
        }

    }

    //如果在本地找到了用户登录的信息，则将此信息读入内存中
    private void doUser() {
        User user = ((BaseActivity) this.getActivity()).readLogin();
        //设置用户名的显示
        textView11.setText(user.UF_ACC);

        //如果在本地存储了用户头像，则优先从本地获取
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File externalFilesDir = this.getActivity().getExternalFilesDir(null);
            File file = new File(externalFilesDir, "icon.png");
            if (file.exists()) {
                //将文件中的图片数据加载到内存中，生成一个Bitmap对象
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                imageView1.setImageBitmap(bitmap);
                return;
            }
        }

        //设置用户头像--联网获取
        Picasso.with(this.getActivity()).load(user.UF_AVATAR_URL).transform(new Transformation() {
            @Override
            public Bitmap transform(Bitmap source) {
                ////对Bitmap进行压缩处理
                Bitmap zoom = BitmapUtils.zoom(source, UIUtils.dp2px(62), UIUtils.dp2px(62));
                //对Bitmap进行圆形处理
                Bitmap circleBitmap = BitmapUtils.circleBitmap(zoom);

                //回收
                source.recycle();
                return circleBitmap;
            }

            @Override
            public String key() {
                return "";//此方法不能返回null.否则报异常
            }
        }).into(imageView1);
    }

    private void login() {
        new AlertDialog.Builder(this.getActivity())
                .setTitle("登录")
                .setMessage("用户必须先登录")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(MeFragment.this.getActivity(), "启动登录的界面", Toast.LENGTH_SHORT).show();
                        //启动登录操作的activity
                        ((BaseActivity) MeFragment.this.getActivity()).goToActivity(LoginActivity.class, null);
                    }
                })
                .setCancelable(false)
                .show();
    }

    @Override
    protected void initTitle() {
        ivTopBack.setVisibility(View.VISIBLE);
        ivTopSettings.setVisibility(View.VISIBLE);
        tvTopTitle.setText("我的资产");
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_me;
    }

    @OnClick({R.id.iv_top_settings, R.id.recharge, R.id.withdraw, R.id.ll_touzi, R.id.ll_touzi_zhiguan, R.id.ll_zichang, R.id.ll_zhanquan})
    public void onClick(View view) {
        switch (view.getId()) {
            //点击设置
            case R.id.iv_top_settings:
                ((BaseActivity) this.getActivity()).goToActivity(UserInfoActivity.class, null);
                break;
            //点击充值
            case R.id.recharge:
                ((BaseActivity) this.getActivity()).goToActivity(ChongZhiActivity.class, null);
                break;
            //点击提现
            case R.id.withdraw:
                ((BaseActivity) this.getActivity()).goToActivity(TiXianActivity.class, null);
                break;
            //投资管理
            case R.id.ll_touzi:
                ((BaseActivity) this.getActivity()).goToActivity(LineCharActivity.class, null);
                break;
            //投资管理（直观）
            case R.id.ll_touzi_zhiguan:
                ((BaseActivity) this.getActivity()).goToActivity(BarCharActivity.class, null);
                break;
            //资产管理
            case R.id.ll_zichang:
                ((BaseActivity)this.getActivity()).goToActivity(PieChartActivity.class, null);
                break;
            //账户安全
            case R.id.ll_zhanquan:
                ((BaseActivity)this.getActivity()).goToActivity(ToggleButtonActivity.class, null);
                break;
        }
    }
}
