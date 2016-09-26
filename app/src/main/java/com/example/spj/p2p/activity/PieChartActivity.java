package com.example.spj.p2p.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spj.p2p.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class PieChartActivity extends BaseActivity {

    @Bind(R.id.iv_top_back)
    ImageView ivTopBack;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.iv_top_settings)
    ImageView ivTopSettings;
    @Bind(R.id.chart)
    PieChart chart;

    private Typeface mTf;

    @Override
    protected void initData() {
        //初始化字体库
        mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        // apply styling
        chart.setDescription("终于要结束了！！！");
        //设置内部圆的半径
        chart.setHoleRadius(52f);
        //设置包裹内部圆的半径
        chart.setTransparentCircleRadius(57f);
        chart.setCenterText("MPChart\nAndroid");
        //设置中间显示的文本的字体
        chart.setCenterTextTypeface(mTf);
        chart.setCenterTextSize(18f);
        //显示的各个部分的占比和是否为100%
        chart.setUsePercentValues(true);
        PieData pieData = generateDataPie();

        //设置显示数据的格式
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTypeface(mTf);
        pieData.setValueTextSize(11f);
        //设置显示各个部分的文字的字体颜色
        pieData.setValueTextColor(Color.BLACK);
        // set data
        chart.setData(pieData);

        Legend l = chart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        //设置几项说明在Y轴方向的间距
        l.setYEntrySpace(10f);
        //设置第一项距离y轴顶部的间距
        l.setYOffset(10f);

        // do not forget to refresh the chart
        // holder.chart.invalidate();
        chart.animateXY(900, 900);
    }



    @Override
    protected void initTitle() {
        ivTopBack.setVisibility(View.VISIBLE);
        ivTopSettings.setVisibility(View.INVISIBLE);
        tvTopTitle.setText("饼状图演示");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pie_chart;
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
    private PieData generateDataPie() {

        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int i = 0; i < 4; i++) {
            entries.add(new Entry((int) (Math.random() * 70) + 30, i));
        }

        PieDataSet d = new PieDataSet(entries, "");

        // space between slices
        d.setSliceSpace(2f);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);

        PieData cd = new PieData(getQuarters(), d);
        return cd;
    }

    private ArrayList<String> getQuarters() {

        ArrayList<String> q = new ArrayList<String>();
        q.add("1st Quarter");
        q.add("2nd Quarter");
        q.add("3rd Quarter");
        q.add("4th Quarter");

        return q;
    }
}
