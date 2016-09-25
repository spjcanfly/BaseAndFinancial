package com.example.spj.p2p.fragment;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spj.p2p.R;
import com.example.spj.p2p.common.BaseFragment;
import com.example.spj.p2p.ui.randomLayout.StellarMap;
import com.example.spj.p2p.utils.UIUtils;
import com.loopj.android.http.RequestParams;

import java.util.Random;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductRecommondFragment extends BaseFragment {

    private String[] datas = new String[]{"超级新手计划", "乐享活系列90天计划", "钱包计划", "30天理财计划(加息2%)", "90天理财计划(加息5%)", "180天理财计划(加息10%)",
            "林业局投资商业经营", "中学老师购买车辆", "屌丝下海经商计划", "新西游影视拍摄投资", "Java培训老师自己周转", "养猪场扩大经营",
            "旅游公司扩大规模", "阿里巴巴洗钱计划", "铁路局回款计划", "高级白领赢取白富美投资计划"
    };

    //将上述的数据分为两组
    private String[] ones = new String[datas.length / 2];
    private String[] twos = new String[datas.length / 2];

    @Bind(R.id.stellar_map)
    StellarMap stellarMap;

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
        //初始化ones和twos两个数组
        for (int i = 0; i < datas.length / 2; i++) {
            ones[i] = datas[i];
        }

        for (int i = 0; i < datas.length / 2; i++) {
            twos[i] = datas[i+8];
        }

        random = new Random();
        MyRecommondAdapter adapter = new MyRecommondAdapter();
        stellarMap.setAdapter(adapter);

        //设置内边距
        int leftPadding = random.nextInt(10);
        int rightPadding = random.nextInt(10);
        int topPadding = random.nextInt(10);
        int bottomtPadding = random.nextInt(10);
        stellarMap.setInnerPadding(leftPadding,topPadding,rightPadding,bottomtPadding);

        //下面的两个方法步调用，不会在界面显示随机数据的效果
        stellarMap.setRegularity(8,8);
        stellarMap.setGroup(0,true);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_recommond;
    }

     class MyRecommondAdapter implements StellarMap.Adapter{
         @Override
         public int getGroupCount() {
             return 2;
         }

         @Override
         public int getCount(int group) {//每一组显示几个
             return 8;
         }

         @Override
         public View getView(int group, int position, View convertView) {
             final TextView tv = new TextView(getActivity());
             //设置属性
             if(group == 0) {
                 tv.setText(ones[position]);
             }else {
                 tv.setText(twos[position]);
             }
             //设置字体大小为8-18之间
             tv.setTextSize(UIUtils.dp2px(8)+random.nextInt(10));
             int red = random.nextInt(211);
             int green = random.nextInt(211);
             int blue = random.nextInt(211);
             //设置随机的颜色
             tv.setTextColor(Color.rgb(red, green, blue));

             //设置每一个textview的点击事件
             tv.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Toast.makeText(ProductRecommondFragment.this.getActivity(), tv.getText(), Toast.LENGTH_SHORT).show();
                 }
             });
             return tv;
         }

         @Override
         public int getNextGroupOnPan(int group, float degree) {
             return 0;
         }

         @Override
         public int getNextGroupOnZoom(int group, boolean isZoomIn) {
             if(group == 0) {
                 return 1;
             }else {
                 return 0;
             }
         }
     }
}
