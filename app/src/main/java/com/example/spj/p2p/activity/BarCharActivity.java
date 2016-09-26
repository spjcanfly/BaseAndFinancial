package com.example.spj.p2p.activity;

import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.spj.p2p.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class BarCharActivity extends BaseActivity {


    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.iv_top_settings)
    ImageView ivTopSettings;
    @Bind(R.id.chart)
    BarChart chart;

    private Typeface mTf;

    @Override
    protected void initData() {
        //初始化字体
        mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        // apply styling
        //图表的描述
        chart.setDescription("high哥一天发神经的次数");
        //设置网格背景
        chart.setDrawGridBackground(false);
        //是否设置阴影的显示
        chart.setDrawBarShadow(false);

        //获取x轴
        XAxis xAxis = chart.getXAxis();
        //设置x轴的显示位置
        xAxis.setPosition(XAxisPosition.BOTTOM);
        //设置x轴的字体
        xAxis.setTypeface(mTf);
        //是否绘制x轴网格线
        xAxis.setDrawGridLines(false);
        //是否绘制x轴轴线
        xAxis.setDrawAxisLine(true);

        //获取y轴
        YAxis leftAxis = chart.getAxisLeft();
        //设置y轴的字体
        leftAxis.setTypeface(mTf);
        //参数1：设置显示的区间的个数。参数2：是否均匀分布。fasle:均匀显示区间的端点值。
        leftAxis.setLabelCount(5, false);
        //设置最高的柱状图距离顶端的距离
        leftAxis.setSpaceTop(20f);

        YAxis rightAxis = chart.getAxisRight();
        //是否显示右边的y轴
        rightAxis.setEnabled(false);
        rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(5, false);
        rightAxis.setSpaceTop(20f);

        generateDataBar().setValueTypeface(mTf);

        // set data
        chart.setData(generateDataBar());

        // do not forget to refresh the chart
//        holder.chart.invalidate();
        chart.animateY(700);
    }

    @Override
    protected void initTitle() {
        ivTopBack.setVisibility(View.VISIBLE);
        ivTopSettings.setVisibility(View.INVISIBLE);
        tvTopTitle.setText("柱状图演示");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bar_char;
    }


    @OnClick(R.id.iv_top_back)
    public void onClick() {
        closeCurrentActivity();
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private BarData generateDataBar() {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (int i = 0; i < 12; i++) {
            entries.add(new BarEntry((int) (Math.random() * 70) + 30, i));
        }

        BarDataSet d = new BarDataSet(entries, "New DataSet ");
        d.setBarSpacePercent(20f);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setHighLightAlpha(255);

        BarData cd = new BarData(getMonths(), d);
        return cd;
    }

    private ArrayList<String> getMonths() {

        ArrayList<String> m = new ArrayList<String>();
        m.add("Jan");
        m.add("Feb");
        m.add("Mar");
        m.add("Apr");
        m.add("May");
        m.add("Jun");
        m.add("Jul");
        m.add("Aug");
        m.add("Sep");
        m.add("Okt");
        m.add("Nov");
        m.add("Dec");

        return m;
    }
}
