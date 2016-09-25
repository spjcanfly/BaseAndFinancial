package com.example.spj.p2p.common;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.spj.p2p.R;
import com.example.spj.p2p.bean.Product;
import com.example.spj.p2p.ui.RoundProgress;
import com.example.spj.p2p.utils.UIUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by spj on 2016/9/23.
 */
public class MyAdapter extends BaseAdapter{

    private List<Product> lists;

    public MyAdapter(List<Product> products) {
        this.lists = products;
    }

    //多类型listview显示的条目数量增多，需要相加
    @Override
    public int getCount() {
        return lists==null?0:lists.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        if(type == 0) {
            //显示不同的item
            TextView tv = new TextView(parent.getContext());
            tv.setText("我想快点给祖国母亲过生日");
            tv.setTextColor(Color.RED);
            tv.setTextSize(UIUtils.dp2px(20));
            return tv;
        }else {
            if(position>2) {
                position-=1;
            }


            Product product = lists.get(position);
            ViewHolder viewHolder;
            if(convertView == null) {
                convertView = View.inflate(parent.getContext(), R.layout.item_product_list, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.pMinnum.setText(product.memberNum);
            viewHolder.pMinzouzi.setText(product.minTouMoney);
            viewHolder.pMoney.setText(product.money);
            viewHolder.pName.setText(product.name);
            viewHolder.pSuodingdays.setText(product.suodingDays);
            viewHolder.pYearlv.setText(product.yearLv);
            viewHolder.pProgresss.setProgress(Integer.parseInt(product.progress));
            return convertView;
        }

    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 2) {
            return 0;
        }else {
            return 1;
        }

    }

    class ViewHolder {
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

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
