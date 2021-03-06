package com.example.spj.p2p.fragment;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spj.p2p.R;
import com.example.spj.p2p.common.BaseFragment;
import com.example.spj.p2p.ui.FlowLayout;
import com.example.spj.p2p.utils.DrawUtils;
import com.example.spj.p2p.utils.UIUtils;
import com.loopj.android.http.RequestParams;

import java.util.Random;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductHotFragment extends BaseFragment {


    @Bind(R.id.flow_layout)
    FlowLayout flowLayout;
    //提供页面要显示的数据
    private String[] datas = new String[]{"新手计划", "乐享活系列90天计划", "钱包", "30天理财计划(加息2%)",
            "林业局投资商业经营与大捞一笔", "中学老师购买车辆", "屌丝下海经商计划", "新西游影视拍",
            "Java培训老师自己周转", "HelloWorld", "C++-C-ObjectC-java", "Android vs ios", "算法与数据结构", "JNI与NDK", "team working"};
    private Random random;

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
        random = new Random();
        //1.动态的创建TextView
        for (int i = 0; i < datas.length; i++) {

            final TextView tv = new TextView(getActivity());

            //设置TextView的属性
            tv.setText(datas[i]);
            tv.setTextSize(UIUtils.dp2px(10));

            ViewGroup.MarginLayoutParams mp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mp.leftMargin = UIUtils.dp2px(8);
            mp.rightMargin = UIUtils.dp2px(8);
            mp.topMargin = UIUtils.dp2px(8);
            mp.bottomMargin = UIUtils.dp2px(8);
            tv.setLayoutParams(mp);

            //设置textView的背景
            int red = random.nextInt(211);
            int green = random.nextInt(211);
            int blue = random.nextInt(211);
            //测试一：
//            tv.setBackground(DrawUtils.getDrawable(Color.rgb(red,green,blue),UIUtils.dp2Px(5)));

            //测试二：
            tv.setBackground(DrawUtils.getSelector(DrawUtils.getDrawable(Color.rgb(red, green, blue), UIUtils.dp2px(5)), DrawUtils.getDrawable(Color.WHITE, UIUtils.dp2px(5))));
            //保存按下能显示selector的效果，需要设置一个如下的属性
//            tv.setClickable(true);

            //添加点击事件，也是实现显示selector的效果的一种方式
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ProductHotFragment.this.getActivity(), tv.getText(), Toast.LENGTH_SHORT).show();
                }
            });

            //设置内边距
            int padding = UIUtils.dp2px(5);
            tv.setPadding(padding, padding, padding, padding);

            // 2.添加到FlowLayout布局中
            flowLayout.addView(tv);
        }
    }

    @Override
    protected void initTitle() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_hot;
    }

}
