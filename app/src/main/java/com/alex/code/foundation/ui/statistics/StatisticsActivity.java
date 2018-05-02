package com.alex.code.foundation.ui.statistics;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.alex.code.foundation.R;
import com.alex.code.foundation.base.BaseMvpActivity;
import com.alex.code.foundation.bean.StatisticsBean;
import com.alex.code.foundation.utils.VLog;
import com.alex.code.foundation.view.CustomToolBar;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

/**
 * Created by dth
 * Des:
 * Date: 2017/12/18.
 */

@Route(path = "/foundation/statistics")
public class StatisticsActivity extends BaseMvpActivity<StatisticsView, StatisticsPresenter> implements StatisticsView,OnChartValueSelectedListener {
    @BindView(R.id.toolbar)
    CustomToolBar mToolbar;
    @BindView(R.id.lineChart)
    LineChart     mLineChart;
    @BindView(R.id.tv_goods_no1)
    TextView mTvGoodsNo1;
    @BindView(R.id.tv_price_no1)
    TextView mTvPriceNo1;
    @BindView(R.id.tv_goods_no2)
    TextView mTvGoodsNo2;
    @BindView(R.id.tv_price_no2)
    TextView mTvPriceNo2;
    @BindView(R.id.tv_goods_no3)
    TextView mTvGoodsNo3;
    @BindView(R.id.tv_price_no3)
    TextView mTvPriceNo3;
    @BindView(R.id.tv_total_price)
    TextView mTvTotalPrice;
    @BindView(R.id.tv_total_order)
    TextView mTvTotalOrder;
    @BindView(R.id.tv_date)
    TextView mTvDate;

    String[]     month   = {"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};
    List<Entry>  mValues = new ArrayList<>();
    private List<StatisticsBean.MonthListEntity> mMonth_list;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_statistics;
    }

    @Override
    protected void init() {

        mToolbar.setTitle("统计报表")
                .setLeftBackListener(this::finish);

        initChart(mLineChart);

        getPresenter().getConsumeStatistics("");


    }


    /**
     * 初始化图表
     *
     * @param lineChart 原始图表
     * @return 初始化后的图表
     */
    public LineChart initChart(LineChart lineChart) {
        //创建描述信息
        Description description = new Description();
        description.setText("测试图表");
        description.setTextColor(Color.RED);
        description.setTextSize(12);
        lineChart.setDescription(description);//设置图表描述信息
        lineChart.setNoDataText("没有数据");//没有数据时显示的文字
        lineChart.setNoDataTextColor(Color.BLUE);//没有数据时显示文字的颜色
        lineChart.setDrawBorders(false);//禁止绘制图表边框的线
        lineChart.setOnChartValueSelectedListener(this);
        // 不显示数据描述
        lineChart.getDescription().setEnabled(false);
        // 没有数据的时候，显示“暂无数据”
        //        lineChart.setNoDataText("暂无数据");
        // 不显示表格颜色
        lineChart.setDrawGridBackground(false);
        // 不可以缩放
        lineChart.setScaleEnabled(false);
        // 不显示y轴右边的值
        lineChart.getAxisRight().setEnabled(false);
        // 显示图例
        Legend legend = lineChart.getLegend();
        legend.setEnabled(true);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        //        legend.setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);
        // modify the legend ...
        legend.setForm(Legend.LegendForm.SQUARE);// 样式
        legend.setFormSize(4f);// 字体
        legend.setTextColor(getResources().getColor(R.color.white));// 颜色

        // 向左偏移15dp，抵消y轴向右偏移的30dp
        //        lineChart.setExtraLeftOffset(-15);

        //获取此图表的x轴
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setEnabled(true);//设置轴启用或禁用 如果禁用以下的设置全部不生效
        xAxis.setDrawAxisLine(true);//是否绘制轴线
        xAxis.setDrawGridLines(true);//设置x轴上每个点对应的线
        xAxis.setDrawLabels(true);//绘制标签  指x轴上的对应数值
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴的显示位置
        //xAxis.setTextSize(20f);//设置字体
        //xAxis.setTextColor(Color.BLACK);//设置字体颜色
        //设置竖线的显示样式为虚线
        //lineLength控制虚线段的长度
        //spaceLength控制线之间的空间
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setAxisMinimum(0f);//设置x轴的最小值
        //        xAxis.setAxisMaximum(10f);//设置最大值
        xAxis.setAvoidFirstLastClipping(true);//图表将避免第一个和最后一个标签条目被减掉在图表或屏幕的边缘
        xAxis.setLabelRotationAngle(10f);//设置x轴标签的旋转角度
        //        设置x轴显示标签数量  还有一个重载方法第二个参数为布尔值强制设置数量 如果启用会导致绘制点出现偏差
        xAxis.setLabelCount(11);
        xAxis.setTextColor(Color.WHITE);//设置轴标签的颜色
        //        xAxis.setTextSize(24f);//设置轴标签的大小
        //        xAxis.setGridLineWidth(10f);//设置竖线大小
        //        xAxis.setGridColor(Color.RED);//设置竖线颜色
        //        xAxis.setAxisLineColor(Color.GREEN);//设置x轴线颜色
        //        xAxis.setAxisLineWidth(5f);//设置x轴线宽度
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                VLog.d("value: " + value);
                return month[(int) value];
            }
        });//格式化x轴标签显示字符

        /**
         * Y轴默认显示左右两个轴线
         */
        //获取右边的轴线
        YAxis rightAxis = lineChart.getAxisRight();
        //设置图表右边的y轴禁用
        rightAxis.setEnabled(false);
        //获取左边的轴线
        YAxis leftAxis = lineChart.getAxisLeft();
        //设置网格线为虚线效果
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        //是否绘制0所在的网格线
        leftAxis.setDrawZeroLine(false);
        leftAxis.setAxisMinimum(0);
        leftAxis.setTextColor(Color.WHITE);

        //        YAxis yAxis = lineChart.getAxisLeft();
        //        // 不显示y轴
        //        yAxis.setDrawAxisLine(false);
        //        // 设置y轴数据的位置
        //        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        //        // 不从y轴发出横向直线
        //        yAxis.setDrawGridLines(false);
        //        yAxis.setTextColor(Color.WHITE);
        //        yAxis.setTextSize(12);
        //        // 设置y轴数据偏移量
        //        yAxis.setXOffset(30);
        //        yAxis.setYOffset(-3);
        //        yAxis.setAxisMinimum(0);

        lineChart.setTouchEnabled(true); // 设置是否可以触摸
        lineChart.setDragEnabled(true);// 是否可以拖拽
        lineChart.setScaleEnabled(false);// 是否可以缩放 x和y轴, 默认是true
        lineChart.setScaleXEnabled(true); //是否可以缩放 仅x轴
        lineChart.setScaleYEnabled(true); //是否可以缩放 仅y轴
        lineChart.setPinchZoom(true);  //设置x轴和y轴能否同时缩放。默认是否
        lineChart.setDoubleTapToZoomEnabled(true);//设置是否可以通过双击屏幕放大图表。默认是true
        lineChart.setHighlightPerDragEnabled(true);//能否拖拽高亮线(数据点与坐标的提示线)，默认是true
        lineChart.setDragDecelerationEnabled(true);//拖拽滚动时，手放开是否会持续滚动，默认是true（false是拖到哪是哪，true拖拽之后还会有缓冲）
        lineChart.setDragDecelerationFrictionCoef(0.99f);//与上面那个属性配合，持续滚动时的速度快慢，[0,1) 0代表立即停止。

        //Matrix matrix = new Matrix();
        // x轴缩放1.5倍
        //matrix.postScale(1.5f, 1f);
        // 在图表动画显示之前进行缩放
        //chart.getViewPortHandler().refresh(matrix, chart, false);
        // x轴执行动画
        //chart.animateX(2000);
        lineChart.invalidate();
        return lineChart;
    }


    /**
     * 设置图表数据
     *
     * @param lineChart 图表
     * @param values    数据
     */
    public void setChartData(LineChart lineChart, List<Entry> values) {
        /**
         * Entry 坐标点对象  构造函数 第一个参数为x点坐标 第二个为y点
         */
        //LineDataSet每一个对象就是一条连接线
        LineDataSet lineDataSet;

        //判断图表中原来是否有数据
        if (lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 0) {
            //获取数据1
            lineDataSet = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            lineDataSet.setValues(values);
            //刷新数据
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            //设置数据1  参数1：数据源 参数2：图例名称
            lineDataSet = new LineDataSet(values,"消费金额");
            lineDataSet.setColor(Color.WHITE);
            lineDataSet.setCircleColor(Color.WHITE);
            lineDataSet.setLineWidth(1f);//设置线宽
            lineDataSet.setCircleRadius(3f);//设置焦点圆心的大小
            lineDataSet.enableDashedHighlightLine(10f, 5f, 0f);//点击后的高亮线的显示样式
            lineDataSet.setHighlightLineWidth(1f);//设置点击交点后显示高亮线宽
            lineDataSet.setHighlightEnabled(true);//是否禁用点击高亮线
            lineDataSet.setHighLightColor(Color.WHITE);//设置点击交点后显示交高亮线的颜色
            lineDataSet.setValueTextSize(9f);//设置显示值的文字大小
            lineDataSet.setValueTextColor(Color.WHITE);
            lineDataSet.setDrawFilled(true);//设置禁用范围背景填充
            lineDataSet.setFillColor(Color.parseColor("#D08685"));
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER); //设置平滑曲线

            //格式化显示数据
            final DecimalFormat mFormat = new DecimalFormat("###,###,##0");
            lineDataSet.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return mFormat.format(value);
                }
            });
            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
                lineDataSet.setFillDrawable(drawable);//设置范围背景填充
            } else {
                lineDataSet.setFillColor(Color.BLACK);
            }


            //保存LineDataSet集合
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(lineDataSet); // add the datasets
            //创建LineData对象 属于LineChart折线图的数据集合
            LineData data = new LineData(dataSets);
            // 添加到图表中
            lineChart.setData(data);
            //绘制图表
            lineChart.invalidate();
        }
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (mMonth_list != null && mMonth_list.size() > 0) {
            StatisticsBean.MonthListEntity monthListEntity = mMonth_list.get((int) e.getX());
            List<StatisticsBean.MonthListEntity.GoodsNameEntity> goods_name_list = monthListEntity.getGoods_name_list();
            mTvDate.setText(Calendar.getInstance().get(Calendar.YEAR)+"."+monthListEntity.getMonth());
            mTvTotalPrice.setText(monthListEntity.getPrice() + "元");
            mTvTotalOrder.setText(monthListEntity.getOrder_number() + "笔");
            mTvPriceNo1.setVisibility(View.VISIBLE);
            mTvPriceNo2.setVisibility(View.VISIBLE);
            mTvPriceNo3.setVisibility(View.VISIBLE);
            mTvGoodsNo1.setVisibility(View.VISIBLE);
            mTvGoodsNo2.setVisibility(View.VISIBLE);
            mTvGoodsNo3.setVisibility(View.VISIBLE);
            if (goods_name_list != null && goods_name_list.size() == 3) {
                mTvGoodsNo1.setText(goods_name_list.get(0).getGoods_name());
                mTvPriceNo1.setText(goods_name_list.get(0).getOrder_price()+"元");
                mTvGoodsNo2.setText(goods_name_list.get(1).getGoods_name());
                mTvPriceNo2.setText(goods_name_list.get(1).getOrder_price()+"元");
                mTvGoodsNo3.setText(goods_name_list.get(2).getGoods_name());
                mTvPriceNo3.setText(goods_name_list.get(2).getOrder_price()+"元");
            } else if (goods_name_list != null && goods_name_list.size() == 2) {
                mTvGoodsNo1.setText(goods_name_list.get(0).getGoods_name());
                mTvPriceNo1.setText(goods_name_list.get(0).getOrder_price()+"元");
                mTvGoodsNo2.setText(goods_name_list.get(1).getGoods_name());
                mTvPriceNo2.setText(goods_name_list.get(1).getOrder_price()+"元");
                mTvGoodsNo3.setVisibility(View.GONE);
                mTvPriceNo3.setVisibility(View.GONE);
            } else if (goods_name_list != null && goods_name_list.size() == 1) {
                mTvGoodsNo1.setText(goods_name_list.get(0).getGoods_name());
                mTvPriceNo1.setText(goods_name_list.get(0).getOrder_price() + "元");
                mTvGoodsNo2.setVisibility(View.GONE);
                mTvPriceNo2.setVisibility(View.GONE);
                mTvGoodsNo3.setVisibility(View.GONE);
                mTvPriceNo3.setVisibility(View.GONE);
            } else {
                mTvGoodsNo1.setText("本月没有消费信息哟~");
                mTvGoodsNo2.setText("本月没有消费信息哟~");
                mTvGoodsNo3.setText("本月没有消费信息哟~");
                mTvPriceNo1.setVisibility(View.GONE);
                mTvPriceNo2.setVisibility(View.GONE);
                mTvPriceNo3.setVisibility(View.GONE);
            }
        }
        VLog.d("onValueSelected: " + e.getX());
    }

    @Override
    public void onNothingSelected() {
        VLog.d("onNothingSelected: ");
    }

    @Override
    public void onSuccess(StatisticsBean statisticsBean) {
        mValues.clear();
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        mMonth_list = statisticsBean.getMonth_list();
        if (mMonth_list != null && mMonth_list.size() > 0) {
            for (StatisticsBean.MonthListEntity monthListEntity : mMonth_list) {
                mValues.add(new Entry(monthListEntity.getMonth() - 1,monthListEntity.getPrice()));
                if (month == monthListEntity.getMonth() - 1) {
                    onValueSelected(mValues.get(month),null);
                }
            }

            setChartData(mLineChart, mValues);
        }
    }
}
