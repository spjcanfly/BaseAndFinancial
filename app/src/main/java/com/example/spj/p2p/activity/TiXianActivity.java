package com.example.spj.p2p.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spj.p2p.R;
import com.example.spj.p2p.utils.UIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TiXianActivity extends BaseActivity {

    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.iv_top_settings)
    ImageView ivTopSettings;
    @Bind(R.id.account_zhifubao)
    TextView accountZhifubao;
    @Bind(R.id.select_bank)
    RelativeLayout selectBank;
    @Bind(R.id.chongzhi_text)
    TextView chongzhiText;
    @Bind(R.id.view)
    View view;
    @Bind(R.id.input_money)
    EditText inputMoney;
    @Bind(R.id.chongzhi_text2)
    TextView chongzhiText2;
    @Bind(R.id.textView5)
    TextView textView5;
    @Bind(R.id.btn_tixian)
    Button btnTixian;

    @Override
    protected void initData() {
        //设置button的可点击性
        btnTixian.setClickable(false);
        //为页面的edittext设置监听
        inputMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String money = inputMoney.getText().toString().trim();
                if(!TextUtils.isEmpty(money)) {
                    btnTixian.setClickable(true);
                    btnTixian.setBackgroundResource(R.drawable.btn_01);
                }else {
                    btnTixian.setClickable(false);
                    btnTixian.setBackgroundResource(R.drawable.btn_023);
                }
            }
        });
    }

    @Override
    protected void initTitle() {
        ivTopBack.setVisibility(View.VISIBLE);
        ivTopSettings.setVisibility(View.INVISIBLE);
        tvTopTitle.setText("提现");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ti_xian;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.iv_top_back, R.id.btn_tixian})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_top_back:
                closeCurrentActivity();
                break;
            case R.id.btn_tixian:
                btnTixian.setClickable(false);
                Toast.makeText(TiXianActivity.this, "您的退款申请已经提交。商家在24小时以内会做出回复。如果退款成功，退款会相应的打到您的支付宝账号中", Toast.LENGTH_SHORT).show();

                UIUtils.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        closeCurrentActivity();
                    }
                },2000);
                break;
        }
    }

}
