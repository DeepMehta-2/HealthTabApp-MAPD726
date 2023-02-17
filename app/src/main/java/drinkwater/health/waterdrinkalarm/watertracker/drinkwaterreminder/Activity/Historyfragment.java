package drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Activity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.model.GradientColor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Helper.AppPref;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.R;

@SuppressLint({"NewApi"})
public class Historyfragment extends Fragment {
    Button btnMonth;
    Button btnYear;
    Calendar cal = Calendar.getInstance();
    DatabaseHelper db;
    protected BarChart mChart;
    String ma;
    SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
    TextView textviewyear;
    TextView txtAvgCompResult;
    TextView txtdrinkFrenqResult;
    TextView txtmonthlyAvgResult;
    TextView txtweekResult;
    String year;
    SimpleDateFormat year_data = new SimpleDateFormat("MMMM - yyyy");

    private class SetReport extends AsyncTask<String, Void, String> {
        int count;
        String reportType;

        public SetReport(int i, String str) {
            this.count = i;
            this.reportType = str;
        }

        /* access modifiers changed from: protected|varargs */
        public String doInBackground(String... strArr) {
            BarDataSet barDataSet;
            int startColor1 = ContextCompat.getColor(getActivity(), R.color.white);
            int startColor2 = ContextCompat.getColor(getActivity(), R.color.white);
            int startColor3 = ContextCompat.getColor(getActivity(), R.color.white);
            int startColor4 = ContextCompat.getColor(getActivity(), R.color.white);
            int endColor1 = ContextCompat.getColor(getActivity(), R.color.white);
            int endColor2 = ContextCompat.getColor(getActivity(), R.color.white);
            int endColor3 = ContextCompat.getColor(getActivity(), R.color.white);
            int endColor4 = ContextCompat.getColor(getActivity(), R.color.white);
            List<GradientColor> gradientColors = new ArrayList<>();
            gradientColors.add(new GradientColor(startColor1, endColor1));
            gradientColors.add(new GradientColor(startColor2, endColor2));
            gradientColors.add(new GradientColor(startColor3, endColor3));
            gradientColors.add(new GradientColor(startColor4, endColor4));
            ArrayList arrayList = new ArrayList();
            HashMap monthlyReport = this.reportType == "month" ? Historyfragment.this.db.monthlyReport() : Historyfragment.this.db.yealyReport();
            int i = 1;
            while (true) {
                float f = (float) i;
                if (f >= (((float) this.count) + 1.0f) + 1.0f) {
                    break;
                }
                if (!monthlyReport.containsKey(Integer.valueOf(i))) {
                    arrayList.add(new BarEntry(f, 0.0f));
                } else if (((Integer) monthlyReport.get(Integer.valueOf(i))).intValue() >= 100) {
                    arrayList.add(new BarEntry(f, (float) ((Integer) monthlyReport.get(Integer.valueOf(i))).intValue(), Historyfragment.this.getResources().getDrawable(R.drawable.star)));
                } else {
                    arrayList.add(new BarEntry(f, (float) ((Integer) monthlyReport.get(Integer.valueOf(i))).intValue()));
                }
                i++;
            }
            if (Historyfragment.this.mChart.getData() == null || ((BarData) Historyfragment.this.mChart.getData()).getDataSetCount() <= 0) {
                barDataSet = new BarDataSet(arrayList, "This year 2019");
                barDataSet.setDrawIcons(false);
                List arrayList2 = new ArrayList();
                arrayList2.add(barDataSet);
                BarData barData = new BarData(arrayList2);
                barData.setValueTextSize(15.0f);
                barData.setBarWidth(0.9f);
                barData.setValueTextColor(R.color.darkblue);
                barDataSet.setGradientColors(gradientColors);
                Historyfragment.this.mChart.setData(barData);
            } else {
                barDataSet = (BarDataSet) ((BarData) Historyfragment.this.mChart.getData()).getDataSetByIndex(0);
                barDataSet.setValues(arrayList);
                ((BarData) Historyfragment.this.mChart.getData()).notifyDataChanged();
                Historyfragment.this.mChart.notifyDataSetChanged();
            }
            barDataSet.setDrawValues(false);
            barDataSet.setValueTextColor(R.color.darkblue);
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(String str) {
            super.onPostExecute(str);
            Historyfragment.this.mChart.invalidate();
        }
    }

    public void setUserVisibleHint(boolean z) {
        if (z) {
            refreshreport();
            new SetReport(31, "month").execute(new String[]{""});
        }
        super.setUserVisibleHint(z);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        try {
            View inflate = layoutInflater.inflate(R.layout.fragment_historyfragment, viewGroup, false);
            try {
                this.mChart = (BarChart) inflate.findViewById(R.id.chart);
                this.db = new DatabaseHelper(getContext());
                this.mChart.setDrawBarShadow(false);
                this.mChart.setDrawValueAboveBar(true);
                this.mChart.setPinchZoom(false);
                this.mChart.getDefaultValueFormatter();
                this.mChart.setDrawGridBackground(false);
                this.mChart.setFitBars(true);
                this.mChart.setTouchEnabled(false);
                this.mChart.setDrawBarShadow(false);
                this.mChart.setDrawGridBackground(false);
                this.btnMonth = (Button) inflate.findViewById(R.id.btnMonth);
                this.btnYear = (Button) inflate.findViewById(R.id.btnYear);
                this.txtAvgCompResult = (TextView) inflate.findViewById(R.id.txtAvgCompResult);
                this.txtdrinkFrenqResult = (TextView) inflate.findViewById(R.id.txtdrinkFrenqResult);
                this.txtmonthlyAvgResult = (TextView) inflate.findViewById(R.id.txtmonthlyAvgResult);
                this.textviewyear = (TextView) inflate.findViewById(R.id.textviewyear);
                this.txtweekResult = (TextView) inflate.findViewById(R.id.txtweekResult);
                this.ma = this.month_date.format(this.cal.getTime());
                this.year = this.year_data.format(this.cal.getTime());
                setBtnBack();
                this.btnMonth.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        new SetReport(31, "month").execute(new String[]{""});
                        AppPref.setmonth(Historyfragment.this.getActivity(), true);
                        Historyfragment.this.setBtnBack();
                        Historyfragment.this.textviewyear.setText(Historyfragment.this.year);
                    }
                });
                this.btnYear.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        new SetReport(12, "year").execute(new String[]{""});
                        AppPref.setmonth(Historyfragment.this.getActivity(), false);
                        Historyfragment.this.setBtnBack();
                        Historyfragment.this.textviewyear.setText(new SimpleDateFormat("yyyy").format(Historyfragment.this.cal.getTime()));
                    }
                });
                this.textviewyear.setText(this.year);
                return inflate;
            } catch (Exception e3) {
                e3.printStackTrace();
                return inflate;
            }
        } catch (Exception e4) {
            e4.printStackTrace();
            return null;
        }
    }

    public void onStart() {
        super.onStart();
    }

    /* access modifiers changed from: private */
    @SuppressLint({"NewApi"})
    public void setBtnBack() {
        this.btnMonth.setBackground(!AppPref.ismonth(getActivity()) ? getResources().getDrawable(R.drawable.his_right) : getResources().getDrawable(R.drawable.his_left));
        this.btnMonth.setTextColor(!AppPref.ismonth(getActivity()) ? getResources().getColor(R.color.white) : getResources().getColor(R.color.colorAccent));
        this.btnYear.setTextColor(AppPref.ismonth(getActivity()) ? getResources().getColor(R.color.white) : getResources().getColor(R.color.colorAccent));
        this.btnYear.setBackground(AppPref.ismonth(getActivity()) ? getResources().getDrawable(R.drawable.his_right) : getResources().getDrawable(R.drawable.his_left));
    }

    public void onResume() {
        refreshreport();
        super.onResume();
    }

    public void refreshreport() {
        TextView textView = this.txtAvgCompResult;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.db.getAveragePer());
        stringBuilder.append(" %");
        textView.setText(stringBuilder.toString());
        textView = this.txtdrinkFrenqResult;
        stringBuilder = new StringBuilder();
        stringBuilder.append(this.db.getDailyFreq());
        stringBuilder.append(" times/day");
        textView.setText(stringBuilder.toString());
        if (Homefragment.getkgstaus()) {
            textView = this.txtweekResult;
            stringBuilder = new StringBuilder();
            stringBuilder.append(this.db.getWeeklyAverage());
            stringBuilder.append(" ml/day");
            textView.setText(stringBuilder.toString());
            textView = this.txtmonthlyAvgResult;
            stringBuilder = new StringBuilder();
            stringBuilder.append(this.db.getMonthlyAverage());
            stringBuilder.append(" ml/day");
            textView.setText(stringBuilder.toString());
            return;
        }
        textView = this.txtweekResult;
        stringBuilder = new StringBuilder();
        stringBuilder.append(Homefragment.returnfloz(this.db.getWeeklyAverage()));
        stringBuilder.append(" fl oz/day");
        textView.setText(stringBuilder.toString());
        textView = this.txtmonthlyAvgResult;
        stringBuilder = new StringBuilder();
        stringBuilder.append(Homefragment.returnfloz(this.db.getMonthlyAverage()));
        stringBuilder.append(" fl oz/day");
        textView.setText(stringBuilder.toString());
    }

}
