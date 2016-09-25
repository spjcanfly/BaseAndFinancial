package com.example.spj.p2p.fragment;


import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.spj.p2p.R;
import com.example.spj.p2p.bean.Image;
import com.example.spj.p2p.bean.Index;
import com.example.spj.p2p.bean.Product;
import com.example.spj.p2p.common.AppNetConfig;
import com.example.spj.p2p.common.BaseFragment;
import com.example.spj.p2p.ui.RoundProgress;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 * 首页
 */
public class HomeFragment extends BaseFragment {


    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.iv_top_settings)
    ImageView ivTopSettings;
    @Bind(R.id.vp_home)
    ViewPager vpHome;
    @Bind(R.id.circle_home_progress)
    CirclePageIndicator circleHomeProgress;
    @Bind(R.id.tv_home_product)
    TextView tvHomeProduct;
    @Bind(R.id.tv_home_yearrate)
    TextView tvHomeYearrate;
    @Bind(R.id.btn_home_join)
    Button btnHomeJoin;
    @Bind(R.id.rp_home_progress)
    RoundProgress rpHomeProgress;


    private Index index;
    private MyAdapter adapter;
    private int currentProgress;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rpHomeProgress.setMax(100);
            rpHomeProgress.setProgress(0);

            for (int i = 0; i < currentProgress; i++) {
                rpHomeProgress.setProgress(rpHomeProgress.getProgress()+1);
                SystemClock.sleep(20);
                //每一次都调用ondraw方法
                rpHomeProgress.postInvalidate();
            }
        }
    };

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg.what == 1) {
                //切换viewpage的下一个页面
                int item = (vpHome.getCurrentItem()+1)%index.images.size();
                //设置初始显示的下标
                //平滑的切换到下一个页面
                vpHome.setCurrentItem(item,true);
                //发送切换的消息
                sendEmptyMessageDelayed(1, 3000);
            }

        }
    };


    @Override
    protected RequestParams getParams() {
        return new RequestParams();
    }

    @Override
    protected String getUrl() {
        return AppNetConfig.INDEX;
    }

    @Override
    protected void initData(String content) {
        if(!TextUtils.isEmpty(content)) {
            handler.removeCallbacksAndMessages(null);
            handler.sendEmptyMessageDelayed(1, 3000);
            Log.e("TAG", "success");

            //解析json数据
            JSONObject jsonObject = JSON.parseObject(content);
            //1.解析得到Product对象
            String proInfo = jsonObject.getString("proInfo");
            Product product = JSON.parseObject(proInfo, Product.class);
            //2.解析得到Image构成的集合
            String imageArr = jsonObject.getString("imageArr");
            List<Image> images = JSON.parseArray(imageArr, Image.class);

            //将得到的数据封装在index中
            index = new Index();
            index.product = product;
            index.images = images;

            adapter = new MyAdapter();
            //使用ViewPager加载显示数据
            vpHome.setAdapter(adapter);

            //显示对应的小圆圈
            circleHomeProgress.setViewPager(vpHome);

            //修改显示的产品的年利率
            tvHomeYearrate.setText(index.product.yearRate + "%");

            //初始化当前的进度
            currentProgress = Integer.parseInt(index.product.progress);

            //让当前的圆形进度条的进度动态的加载显示
            new Thread(runnable) {
            }.start();

            vpHome.addOnPageChangeListener(new MyOnPageChangeListener());
        }

    }

    @Override
    protected void initTitle() {
        ivTopBack.setVisibility(View.GONE);
        ivTopSettings.setVisibility(View.GONE);
        tvTopTitle.setText("首页");
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return index.images == null ? 0 : index.images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            //判断界面显示的视图数据是否来自于集合对象
            return view == object;
        }

        //销毁指定的图片数据
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        //装配图片数据并返回
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            String imaurl = index.images.get(position).IMAURL;
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);


            //根据图片的url，联网获取图片数据，装载到imagview对象上
            Picasso.with(getActivity()).load(imaurl).placeholder(R.drawable.ic_error_page).into(imageView);
            container.addView(imageView);

            //给图片设置监听，接触时候移除消息
//            imageView.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                        handler.removeCallbacksAndMessages(null);
//                    }
//                    if (event.getAction() == MotionEvent.ACTION_UP) {
//                        handler.sendEmptyMessageDelayed(1, 3000);
//                    }
//                    return true;
//                }
//            });


            return imageView;

        }



    }


    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if(state == ViewPager.SCROLL_STATE_DRAGGING) {
                //拖拽
                handler.removeCallbacksAndMessages(null);
            }else if(state == ViewPager.SCROLL_STATE_SETTLING){
                //惯性
                handler.removeCallbacksAndMessages(null);
                handler.sendEmptyMessageDelayed(1, 3000);
            }else if(state == ViewPager.SCROLL_STATE_IDLE) {
                //静止
                handler.removeCallbacksAndMessages(null);
                handler.sendEmptyMessageDelayed(1, 3000);
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("TAG", "33333333333333333333");
        handler.removeCallbacksAndMessages(null);
        handler=null;
    }
}
