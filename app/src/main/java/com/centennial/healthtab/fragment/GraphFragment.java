package com.centennial.healthtab.fragment;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.centennial.healthtab.R;
import com.centennial.healthtab.object.Chart_Data;
import com.centennial.healthtab.utils.CircularProgressBar;
import com.centennial.healthtab.utils.Constant;
import com.centennial.healthtab.utils.Pref_Utils;
import com.centennial.healthtab.utils.Utils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment;
import com.github.mikephil.charting.components.Legend.LegendOrientation;
import com.github.mikephil.charting.components.Legend.LegendVerticalAlignment;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GraphFragment extends Fragment {
    public static final int[] MATERIAL_COLORS = new int[]{ColorTemplate.rgb("#E84234"), ColorTemplate.rgb("#F9BB08"), ColorTemplate.rgb("#32A852"), ColorTemplate.rgb("#4284F4")};
    String[] DrinkWater;
    float[] HighestYValue;
    String[] TotalWater;
    ArrayList<String> Xdata;
    ArrayList<Chart_Data> chartDataArrayList;
    int count = 0;
    private ArrayList<Entry> e1;
    private ArrayList<Entry> e2;
    private BarChart mChart;
    protected String[] mMonths = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"};
    Typeface mTfLight;
    CircularProgressBar pbLastMonth;
    CircularProgressBar pbLastWeek;
    TextView tvwater30Day;
    TextView tvwater7Day;
    Type type = new TypeToken<List<Chart_Data>>() {
    }.getType();
    View view;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.view = layoutInflater.inflate(R.layout.fragment_graph, viewGroup, false);
        init();
        return this.view;
    }

    public void init() {
        int i;
        this.mTfLight = Typeface.createFromAsset(getActivity().getAssets(), getResources().getString(R.string.font_regular));
        this.mChart = this.view.findViewById(R.id.chart1);
        this.mChart.setDrawGridBackground(false);
        this.mChart.getDescription().setEnabled(false);
        this.mChart.setDragEnabled(true);
        this.mChart.setScaleEnabled(false);
        this.mChart.setPinchZoom(false);
        if (Pref_Utils.getDefaultChartListInfo(getActivity()).equals("")) {
            this.chartDataArrayList = new ArrayList();
        } else {
            this.chartDataArrayList = new Gson().fromJson(Pref_Utils.getDefaultChartListInfo(getActivity()), this.type);
        }
        Chart_Data chartData = new Chart_Data();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(Utils.getIntDefaults(getActivity(), Constant.DAILY_WATER_DRINK));
        chartData.setTotalWater(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(Utils.getWaterDrunkFromDefaults(getActivity(), Constant.PARAM_VALID_DRUNK_WATER));
        chartData.setDrinkWater(stringBuilder.toString());
        Calendar instance = Calendar.getInstance();
        Date time = instance.getTime();
        @SuppressLint("WrongConstant") int i2 = instance.get(2);
        @SuppressLint("WrongConstant") int i3 = instance.get(5);
        chartData.setDate(new SimpleDateFormat("dd/MM/yyyy").format(time));
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("");
        stringBuilder2.append(i3);
        stringBuilder2.append(" ");
        stringBuilder2.append(this.mMonths[i2]);
        chartData.setChartDate(stringBuilder2.toString());
        this.chartDataArrayList.add(chartData);
        this.DrinkWater = new String[this.chartDataArrayList.size()];
        this.TotalWater = new String[this.chartDataArrayList.size()];
        this.HighestYValue = new float[2];
        this.Xdata = new ArrayList();
        for (i = 0; i < this.chartDataArrayList.size(); i++) {
            this.Xdata.add(this.chartDataArrayList.get(i).getChartDate());
            this.DrinkWater[i] = this.chartDataArrayList.get(i).getDrinkWater();
            this.TotalWater[i] = this.chartDataArrayList.get(i).getTotalWater();
        }
        Arrays.sort(this.DrinkWater);
        Arrays.sort(this.TotalWater);
        this.HighestYValue[0] = Float.parseFloat(this.DrinkWater[this.DrinkWater.length - 1]);
        this.HighestYValue[1] = Float.parseFloat(this.TotalWater[this.TotalWater.length - 1]);
        Arrays.sort(this.HighestYValue);
        IAxisValueFormatter anonymousClass3 = new IAxisValueFormatter() {
            public String getFormattedValue(float f, AxisBase axisBase) {
                int i = (int) f;
                return (i < 0 || i >= GraphFragment.this.Xdata.size()) ? "" : GraphFragment.this.Xdata.get(i);
            }
        };
        XAxis xAxis = this.mChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setTypeface(this.mTfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1.0f);
        xAxis.setLabelCount(7);
        xAxis.setDrawLabels(true);
        xAxis.setValueFormatter(anonymousClass3);
        YAxis axisLeft = this.mChart.getAxisLeft();
        axisLeft.setTypeface(this.mTfLight);
        axisLeft.setLabelCount(5, false);
        axisLeft.setPosition(YAxisLabelPosition.OUTSIDE_CHART);
        float f = 0.0f;
        axisLeft.setAxisMinimum(0.0f);
        axisLeft.setAxisMaximum(this.HighestYValue[this.HighestYValue.length - 1]);
        this.mChart.getAxisRight().setEnabled(false);
        setData(this.Xdata.size(), 10.0f);
        this.mChart.animateX(2500);
        this.mChart.setVisibleXRangeMaximum(7.0f);
        this.mChart.moveViewToX((float) (this.Xdata.size() - 2));
        Legend legend = this.mChart.getLegend();
        legend.setVerticalAlignment(LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(LegendHorizontalAlignment.LEFT);
        legend.setOrientation(LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setForm(LegendForm.SQUARE);
        legend.setFormSize(9.0f);
        legend.setTextSize(11.0f);
        legend.setXEntrySpace(4.0f);
        legend.setForm(LegendForm.LINE);
        this.pbLastWeek = this.view.findViewById(R.id.pbLastWeek);
        this.pbLastMonth = this.view.findViewById(R.id.pbLastMonth);
        this.tvwater7Day = this.view.findViewById(R.id.tvwater7Day);
        this.tvwater30Day = this.view.findViewById(R.id.tvwater30Day);
        int i4 = 0;
        float f2 = 0.0f;
        float f3 = 0.0f;
        for (i = this.chartDataArrayList.size() - 1; i >= 0; i--) {
            if (i4 < 7) {
                f2 += Float.parseFloat(this.chartDataArrayList.get(i).getDrinkWater());
                f3 += Float.parseFloat(this.chartDataArrayList.get(i).getTotalWater());
                i4++;
            } else {
                i4 = 0;
            }
        }
        int i5 = 0;
        float f4 = 0.0f;
        for (i = this.chartDataArrayList.size() - 1; i >= 0; i--) {
            if (i5 < 30) {
                f += Float.parseFloat(this.chartDataArrayList.get(i).getDrinkWater());
                f4 += Float.parseFloat(this.chartDataArrayList.get(i).getTotalWater());
                i5++;
            } else {
                i5 = 0;
            }
        }
        float f5 = (f2 * 100.0f) / f3;
        float f6 = (100.0f * f) / f4;
        this.pbLastWeek.setTitle("0%");
        this.pbLastMonth.setTitle("0%");
        this.pbLastWeek.animateProgressTo(0, (int) f5, new CircularProgressBar.ProgressAnimationListener() {
            public void onAnimationStart() {
            }

            public void onAnimationProgress(int i) {
                CircularProgressBar circularProgressBar = GraphFragment.this.pbLastWeek;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(i);
                stringBuilder.append("%");
                circularProgressBar.setTitle(stringBuilder.toString());
            }

            public void onAnimationFinish() {
                GraphFragment.this.pbLastWeek.setSubTitle("7 days");
            }
        });
        this.pbLastMonth.animateProgressTo(0, (int) f6, new CircularProgressBar.ProgressAnimationListener() {
            public void onAnimationStart() {
            }

            public void onAnimationProgress(int i) {
                CircularProgressBar circularProgressBar = GraphFragment.this.pbLastMonth;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(i);
                stringBuilder.append("%");
                circularProgressBar.setTitle(stringBuilder.toString());
            }

            public void onAnimationFinish() {
                GraphFragment.this.pbLastMonth.setSubTitle("30 days");
            }
        });
        TextView textView;
        if (Utils.getFromUserDefaults(getActivity(), Constant.PARAM_VALID_WATER_TYPE).equals("ml")) {
            i = (int) f2;
            int i6 = (int) f;
            TextView textView2 = this.tvwater7Day;
            stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(Math.round((float) i));
            stringBuilder.append(" ");
            stringBuilder.append(Utils.getFromUserDefaults(getActivity(), Constant.PARAM_VALID_WATER_TYPE));
            textView2.setText(stringBuilder.toString());
            textView = this.tvwater30Day;
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append("");
            stringBuilder3.append(Math.round((float) i6));
            stringBuilder3.append(" ");
            stringBuilder3.append(Utils.getFromUserDefaults(getActivity(), Constant.PARAM_VALID_WATER_TYPE));
            textView.setText(stringBuilder3.toString());
            return;
        }
        double d = f2;
        double d2 = Constant.oz;
        Double.isNaN(d);
        d *= d2;
        double d3 = f;
        d2 = Constant.oz;
        Double.isNaN(d3);
        d3 *= d2;
        TextView textView3 = this.tvwater7Day;
        StringBuilder stringBuilder4 = new StringBuilder();
        stringBuilder4.append("");
        stringBuilder4.append(Math.round(d));
        stringBuilder4.append(" ");
        stringBuilder4.append(Utils.getFromUserDefaults(getActivity(), Constant.PARAM_VALID_WATER_TYPE));
        textView3.setText(stringBuilder4.toString());
        textView = this.tvwater30Day;
        StringBuilder stringBuilder5 = new StringBuilder();
        stringBuilder5.append("");
        stringBuilder5.append(Math.round(d3));
        stringBuilder5.append(" ");
        stringBuilder5.append(Utils.getFromUserDefaults(getActivity(), Constant.PARAM_VALID_WATER_TYPE));
        textView.setText(stringBuilder5.toString());
    }

    private void setData(int i, float f) {
        List arrayList = new ArrayList();
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add(new BarEntry((float) i2, Float.parseFloat(this.chartDataArrayList.get(i2).getDrinkWater()), getResources().getDrawable(R.drawable.iv_graph_dot)));
        }
        if (this.mChart.getData() == null || this.mChart.getData().getDataSetCount() <= 0) {
            BarDataSet barDataSet = new BarDataSet(arrayList, "Drunk water report");
            barDataSet.setDrawIcons(false);
            barDataSet.setColors(MATERIAL_COLORS);
            barDataSet.setDrawValues(true);
            barDataSet.setValueTextSize(0.5f);
            barDataSet.setValueTypeface(this.mTfLight);
            arrayList = new ArrayList();
            arrayList.add(barDataSet);
            BarData barData = new BarData(arrayList);
            barData.setValueTypeface(this.mTfLight);
            barData.setValueTextSize(9.0f);
            barData.setBarWidth(0.9f);
            this.mChart.setData(barData);
            return;
        }
        ((BarDataSet) this.mChart.getData().getDataSetByIndex(0)).setValues(arrayList);
        this.mChart.getData().notifyDataChanged();
        this.mChart.notifyDataSetChanged();
    }
}
