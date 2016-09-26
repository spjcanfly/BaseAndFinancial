package com.example.spj.p2p.activity;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.spj.p2p.R;

import butterknife.Bind;
import butterknife.OnClick;

public class ToggleButtonActivity extends BaseActivity {
    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.iv_top_settings)
    ImageView ivTopSettings;
    @Bind(R.id.toggle_me)
    ToggleButton toggleMe;

    @Override
    protected void initData() {
        toggleMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(ToggleButtonActivity.this, "开启密码保护", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ToggleButtonActivity.this, "关闭密码保护", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void initTitle() {
        ivTopBack.setVisibility(View.VISIBLE);
        ivTopSettings.setVisibility(View.INVISIBLE);
        tvTopTitle.setText("ToggleButton的使用");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_toggle_button;
    }


    @OnClick({R.id.iv_top_back, R.id.toggle_me})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_top_back:
                closeCurrentActivity();
                break;
        }
    }
}
