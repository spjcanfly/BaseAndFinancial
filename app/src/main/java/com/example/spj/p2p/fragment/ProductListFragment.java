package com.example.spj.p2p.fragment;


import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.spj.p2p.R;
import com.example.spj.p2p.bean.Product;
import com.example.spj.p2p.common.AppNetConfig;
import com.example.spj.p2p.common.BaseFragment;
import com.example.spj.p2p.common.BaseHolder;
import com.example.spj.p2p.common.MyAdapter;
import com.example.spj.p2p.common.MyBaseAdapter;
import com.example.spj.p2p.ui.RoundProgress;
import com.loopj.android.http.RequestParams;

import java.util.List;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductListFragment extends BaseFragment {

    @Bind(R.id.lv_product_list)
    ListView lvProductList;
    private List<Product> products;

    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return AppNetConfig.PRODUCT;
    }

    @Override
    protected void initData(String content) {

        //解析json数据
        JSONObject jsonObject = JSON.parseObject(content);
        if (jsonObject.getBoolean("success")) {
            String data = jsonObject.getString("data");
            //得到了所有产品构成的集合

            products = JSON.parseArray(data, Product.class);
            Log.e("TAG", "products" + products);
//            方式一，显示listview
            MyAdapter adapter = new MyAdapter(products);
            lvProductList.setAdapter(adapter);
//            //方式二：抽取
//            ProductListAdapter adapter = new ProductListAdapter(products);
//            lvProductList.setAdapter(adapter);
        }
    }

    @Override
    protected void initTitle() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_product_list;
    }

    class ProductListAdapter extends MyBaseAdapter<Product> {

        public ProductListAdapter(List<Product> lists) {
            super(lists);
        }

        @Override
        protected BaseHolder getHolder() {
            return new MyHolder();
        }
    }

    class MyHolder extends BaseHolder<Product> {
        @Bind(R.id.p_name)
        TextView pName;
        @Bind(R.id.p_money)
        TextView pMoney;
        @Bind(R.id.p_yearlv)
        TextView pYearlv;
        @Bind(R.id.p_suodingdays)
        TextView pSuodingdays;
        @Bind(R.id.p_minzouzi)
        TextView pMinzouzi;
        @Bind(R.id.p_minnum)
        TextView pMinnum;
        @Bind(R.id.p_progresss)
        RoundProgress pProgresss;

        @Override
        protected View initView() {
            return View.inflate(getActivity(), R.layout.item_product_list, null);
        }

        @Override
        protected void refreshData(Product product) {
            pMinnum.setText(product.memberNum);
            pMinzouzi.setText(product.minTouMoney);
            pMoney.setText(product.money);
            pName.setText(product.name);
            pSuodingdays.setText(product.suodingDays);
            pYearlv.setText(product.yearLv);
            pProgresss.setProgress(Integer.parseInt(product.progress));
        }
    }
}
