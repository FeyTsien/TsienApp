package com.tsien.ui.fragment.me;


import android.graphics.Color;

import com.blankj.utilcode.util.ToastUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;
import com.tsien.R;
import com.tsien.mvp.contract.MVPContract;
import com.tsien.mvp.presenter.MVPPresenter;
import com.tsien.mvp.view.MVPFragment;
import com.tsienlibrary.bean.CommonBean;
import com.tsienlibrary.eventbus.Event;
import com.tsien.eventbus.EventCode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * ================
 * ===== 我的 =====
 * ================
 */

public class MeFragment extends MVPFragment<MVPContract.View, MVPPresenter> implements
        OnChartValueSelectedListener {
    List<Entry> mValues1 = new ArrayList<>();
    List<Entry> mValues2 = new ArrayList<>();
    List<Entry> mValues3 = new ArrayList<>();

    @BindView(R.id.chart1)
    LineChart chart;

    public static MeFragment newInstance() {
        return new MeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }


    @Override
    protected void initData() {

        mValues1.add(new Entry(0, 10, "2019-01"));
        mValues1.add(new Entry(1, 15, ""));
        mValues1.add(new Entry(2, 25, ""));
        mValues1.add(new Entry(3, 19, ""));
        mValues1.add(new Entry(4, 25, "2019-05"));
        mValues1.add(new Entry(5, 16, ""));
        mValues1.add(new Entry(6, 40, ""));
        mValues1.add(new Entry(7, 24, ""));
        mValues1.add(new Entry(8, 27, "2019-09"));

        mValues2.add(new Entry(0, 50));
        mValues2.add(new Entry(1, 65));
        mValues2.add(new Entry(2, 75));
        mValues2.add(new Entry(3, 59));
        mValues2.add(new Entry(4, 45));
        mValues2.add(new Entry(5, 66));
        mValues2.add(new Entry(6, 30));
        mValues2.add(new Entry(7, 64));
        mValues2.add(new Entry(8, 97));
    }

    @Override
    protected void initView() {
        initLineChart();
        chart.animateX(1500);
        // get the legend (only possible after setting data)
        Legend legend = chart.getLegend();

        // 设置图例
        legend.setForm(Legend.LegendForm.LINE);
//        legend.setTypeface(tfLight);
        legend.setTextSize(11f);
        legend.setTextColor(Color.BLACK);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
//        legend.setYOffset(11f);

        //X轴设置
        XAxis xAxis = chart.getXAxis();
//        xAxis.setTypeface(tfLight);//设置字体样式
        xAxis.setTextSize(11f);//X轴字体大小
        xAxis.setTextColor(Color.BLACK);//字体颜色
//        xAxis.setAxisMinimum(0f);//设置x轴的最小值 //`
//        xAxis.setAxisMaximum(31f);//设置最大值 //
//        xAxis.setLabelCount(10);  //设置X轴的显示个数
        xAxis.setAxisLineColor(Color.BLACK);//设置X轴线颜色
        xAxis.setAxisLineWidth(0.5f);//设置x轴线宽度
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//X轴显示位置
        xAxis.setDrawAxisLine(true);//是否绘制轴线
        xAxis.setDrawGridLines(false);//设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setAvoidFirstLastClipping(false);//图表将避免第一个和最后一个标签条目被减掉在图表或屏幕的边缘
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return mValues1.get((int) value).getData() + "";
            }
        });

        //Y轴设置(左侧)
        YAxis leftAxis = chart.getAxisLeft();
//        leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setAxisMaximum(200f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);
//
//        leftAxis.setValueFormatter(new IAxisValueFormatter() {
//                                       @Override
//                                       public String getFormattedValue(float value, AxisBase axis) {
//                                           return "￥"+ value;
//                                       }


//
//        //Y轴设置(右侧)
//        YAxis rightAxis = chart.getAxisRight();
////        rightAxis.setTypeface(tfLight);
//        rightAxis.setTextColor(Color.RED);
//        rightAxis.setAxisMaximum(900);
//        rightAxis.setAxisMinimum(-200);
//        rightAxis.setDrawGridLines(false);
//        rightAxis.setDrawZeroLine(false);
//        rightAxis.setGranularityEnabled(false);
    }

    /**
     * 刷新用户信息
     */
    private void setUserInfo() {
    }

    @Override
    public void requestSuccess(String requestUrl, CommonBean commonBean) {

    }

    @Override
    public void requestFail(String requestUrl, String msg, int code) {

    }

    @Override
    protected boolean isRegisteredEventBus() {
        //订阅EventBus,返回true.
        return true;
    }

    @Override
    protected void receiveEvent(Event event) {
        switch (event.getCode()) {
            case EventCode.MAIN_ME_A:
                setUserInfo();
                break;
        }
    }

    /**
     * 初始化折线图控件属性
     */
    private void initLineChart() {
        chart.setNoDataText("没有数据");//没有数据时显示的文字
        chart.setNoDataTextColor(Color.WHITE);//没有数据时显示文字的颜色
        chart.setDrawGridBackground(false);//chart 绘图区后面的背景矩形将绘制
        chart.setDrawBorders(false);//是否禁止绘制图表边框的线
        chart.setOnChartValueSelectedListener(this);

        // 右下角的描述文本
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        chart.setDragDecelerationFrictionCoef(0.9f);

        //启用缩放和拖动
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setHighlightPerDragEnabled(true);

        // 如果禁用，可以分别在x轴和y轴上缩放
        chart.setPinchZoom(false);
        //不展示Y轴右侧刻度
        chart.getAxisRight().setEnabled(false);

        //设置整体控件背景颜色
        chart.setBackgroundColor(Color.WHITE);

        setData();
    }

    private void setData() {

        LineDataSet set1, set2;
        //判断图表中原来是否有数据
        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            //获取数据
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) chart.getData().getDataSetByIndex(1);
            set1.setValues(mValues1);
            set2.setValues(mValues2);
            //刷新数据
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(mValues1, "DataSet 1");
            set1.setAxisDependency(YAxis.AxisDependency.LEFT);//数值是以左侧Y轴为标准
            set1.setColor(ColorTemplate.getHoloBlue());//折线颜色
            set1.setLineWidth(2f);//折线宽度
            set1.setCircleColor(ColorTemplate.getHoloBlue());//折线顶点颜色
            set1.setCircleRadius(3f);//顶点半径
            set1.setDrawCircleHole(true);//是否显示空心的顶点
//            set1.setDrawFilled(true);//显示折线图之下填充色
            set1.setFillColor(Color.GREEN);//填充的颜色
            set1.setFillAlpha(65);//填充色的透明度
//            set1.setHighlightEnabled(true);//是否显示十字横纵轴
            set1.setHighLightColor(ColorTemplate.getHoloBlue());//十字轴颜色
            set1.setDrawValues(true);//是否绘制数值
            set1.setValueTextColor(ColorTemplate.getHoloBlue());
            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);

            // create a dataset and give it a type
            set2 = new LineDataSet(mValues2, "DataSet 2");
            set2.setAxisDependency(YAxis.AxisDependency.LEFT);
            set2.setColor(Color.RED);
            set2.setCircleColor(Color.RED);
            set2.setLineWidth(2f);
            set2.setCircleRadius(3f);
            set2.setFillAlpha(65);
            set2.setFillColor(Color.RED);
            set2.setDrawCircleHole(false);
            set2.setHighLightColor(Color.rgb(244, 117, 117));
            set2.setDrawValues(false);//是否显示数值
            //set2.setFillFormatter(new MyFillFormatter(900f));

            // create a data object with the data sets
            LineData data = new LineData(set1, set2);
//            data.setValueTextColor(Color.BLACK);//折线顶点字体颜色
            data.setDrawValues(false);//是否绘制数值
            data.setValueTextSize(9f);//数值字体大小
            data.setHighlightEnabled(true);//是否显示十字横纵轴

            // set data
            chart.setData(data);
        }
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        //根据点击的位置自动移动
        chart.centerViewToAnimated(e.getX(), e.getY(), chart.getData().getDataSetByIndex(h.getDataSetIndex())
                .getAxisDependency(), 500);
        refreshContent(e,h);
    }

    @Override
    public void onNothingSelected() {

    }

    /**
     * 点击提示的marker适配器
     * @param e
     * @param h
     */

    public void refreshContent(Entry e, Highlight h) {

        if (e instanceof CandleEntry) {

            CandleEntry ce = (CandleEntry) e;

            ToastUtils.showShort(Utils.formatNumber(ce.getHigh(), 0, true));
//            tvContent.setText("￥" + Utils.formatNumber(ce.getHigh(), 0, true));
        } else {

            ToastUtils.showShort( Utils.formatNumber(e.getY(), 0, true));
        }

    }


}
