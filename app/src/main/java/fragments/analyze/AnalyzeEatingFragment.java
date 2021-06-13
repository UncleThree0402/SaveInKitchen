package fragments.analyze;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.unclethree.saveinkitchen.R;
import formatters.DateFormatter;
import formatters.NumberFormatter;
import models.BuyHistory;
import models.DiaryHistory;
import viewmodels.BuyHistoryViewModel;
import viewmodels.DiaryHistoryViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnalyzeEatingFragment extends Fragment {
    private static final String TAG = "AnalyzeEatingFragment";

    //Ui
    private TextView mTotalValueTextView;
    private TextView mAverageValueTextView;
    private TextView mMostExpensiveValueTextView;

    private LineChart mLineChart;

    //Var
    private DiaryHistoryViewModel mDiaryHistoryViewModel;
    private final ArrayList<DiaryHistory> mDiaryhistory = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.analyze_buying_fragment,container,false);

        mDiaryHistoryViewModel = new ViewModelProvider(this).get(DiaryHistoryViewModel.class);

        mTotalValueTextView = view.findViewById(R.id.analyze_expense_value);
        mAverageValueTextView = view.findViewById(R.id.ave_exp_day_value);
        mMostExpensiveValueTextView = view.findViewById(R.id.most_expensive_value);

        mLineChart = view.findViewById(R.id.analyze_line_chart);

        setExpenseValueTextView((dayTime() - 86400 * 30) * 1000);
        setAverageTextView((dayTime() - 86400 * 30) * 1000);
        setMostExpensiveValueTextViewTextView((dayTime() - 86400 * 30) * 1000);
        initLineChart();

        return view;
    }

    private void setExpenseValueTextView(long date) {
        mDiaryHistoryViewModel.getDiaryHistory30DaysCost(date).observe(getViewLifecycleOwner(), new Observer<Double>() {

            @Override
            public void onChanged(Double aDouble) {
                if (aDouble == null) {
                    mTotalValueTextView.setText(NumberFormatter.moneyFormatter(0d));
                } else {
                    mTotalValueTextView.setText(NumberFormatter.moneyFormatter(aDouble));
                }

            }
        });
    }

    private void setAverageTextView(long date) {
        mDiaryHistoryViewModel.getDiaryHistory30DaysAverage(date).observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                if (aDouble == null) {
                    mAverageValueTextView.setText(NumberFormatter.moneyFormatter(0d));
                } else {
                    mAverageValueTextView.setText(NumberFormatter.moneyFormatter(aDouble));
                }

            }
        });
    }

    private void setMostExpensiveValueTextViewTextView(long date) {
        mDiaryHistoryViewModel.getDiaryHistoryMostExpensive(date).observe(getViewLifecycleOwner(), new Observer<DiaryHistory>() {
            @Override
            public void onChanged(DiaryHistory diaryHistory) {
                if (diaryHistory == null) {
                    mMostExpensiveValueTextView.setText("Nothing");
                } else {
                    String text = diaryHistory.getName() + " ( " + NumberFormatter.moneyFormatter(diaryHistory.getCost()) + " ) ";
                    mMostExpensiveValueTextView.setText(text);
                }
            }
        });
    }
    private void initLineChart() {

        mLineChart.setHighlightPerTapEnabled(false);
        mLineChart.setHighlightPerDragEnabled(false);

        XAxis xAxis = mLineChart.getXAxis();
        YAxis yAxisLeft = mLineChart.getAxisLeft();
        YAxis yAxisRight = mLineChart.getAxisRight();
        xAxis.setDrawGridLines(false);
        yAxisLeft.setDrawGridLines(false);
        yAxisRight.setEnabled(false);
        yAxisLeft.setLabelCount(5, false);
        xAxis.setLabelCount(5);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        yAxisLeft.setAxisMinimum(0);

        yAxisLeft.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return NumberFormatter.moneyFormatter(value);
            }
        });

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                Date date = new Date((long) (value * 1000));
                return DateFormatter.dayMonthFormatter(date);
            }
        });

        mLineChart.getLegend().setEnabled(false);
        mLineChart.getDescription().setEnabled(false);
        mLineChart.setAutoScaleMinMaxEnabled(true);
        initDate();
        mLineChart.invalidate();

        mDiaryHistoryViewModel.getDiaryHistory30Days((dayTime() - 86400 * 30) * 1000).observe(getViewLifecycleOwner(), new Observer<List<DiaryHistory>>() {
            @Override
            public void onChanged(List<DiaryHistory> diaryHistories) {
                if (mDiaryhistory.size() > 0) {
                    mDiaryhistory.clear();
                }
                if (mDiaryhistory != null) {
                    mDiaryhistory.addAll(diaryHistories);
                }
                set30DaysCost();
            }
        });

    }


    private void set30DaysCost() {
        ArrayList<Entry> entries = new ArrayList<>();
        long currentDayEnd = dayTime();
        for (int i = 1; i < 31; i++) {
            long time = (currentDayEnd - 86400 * (30 - i));
            float value = 0f;
            for (int j = 0; j < mDiaryhistory.size(); j++) {
                if (mDiaryhistory.get(j).getDate() >= (currentDayEnd - 86400 * (31 - i)) * 1000 && mDiaryhistory.get(j).getDate() <= (currentDayEnd - 86400 * (30 - i)) * 1000){
                    value += mDiaryhistory.get(j).getCost();
                    mDiaryhistory.remove(j);
                    j--;
                }
            }
            entries.add(new Entry(time, value));
        }
        LineDataSet lineDataSet = new LineDataSet(entries, "30 Days Cost");
        lineDataSet.setMode(LineDataSet.Mode.LINEAR);
        lineDataSet.setColor(R.color.black);
        lineDataSet.setCubicIntensity(0.2f);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillColor(R.color.black);
        lineDataSet.setFillAlpha(99999);
        LineData lineData = new LineData(lineDataSet);
        mLineChart.setData(lineData);
        mLineChart.invalidate();

    }

    private void initDate() {
        ArrayList<Entry> entries = new ArrayList<>();
        long currentDayEnd = dayTime();
        for (int i = 1; i < 31; i++) {
            long time = (currentDayEnd - 86400 * (30 - i));
            float value = 0f;
            entries.add(new Entry(time, value));
        }
        LineDataSet lineDataSet = new LineDataSet(entries, "30 Days Cost");
        lineDataSet.setMode(LineDataSet.Mode.LINEAR);
        lineDataSet.setColor(R.color.black);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setCubicIntensity(0.2f);
        lineDataSet.setFillColor(R.color.black);
        LineData lineData = new LineData(lineDataSet);
        mLineChart.setData(lineData);

    }

    private long dayTime() {

        long setTime;
        Date currentDate;
        long nextOneDaySecond = 86400;

        setTime = System.currentTimeMillis();
        currentDate = new Date(setTime);
        long currentSecond = System.currentTimeMillis();

        SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");
        String minuteString = minuteFormat.format(currentDate);
        long minuteLong = Long.parseLong(minuteString);

        SimpleDateFormat secondFormat = new SimpleDateFormat("ss");
        String secondString = secondFormat.format(currentDate);
        long secondLong = Long.parseLong(secondString);

        SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
        String hourString = hourFormat.format(currentDate);
        long hourLong = Long.parseLong(hourString);

        nextOneDaySecond += ((currentSecond / 1000) - (minuteLong * 60) - (hourLong * 3600) - secondLong);
        return nextOneDaySecond ;
    }
}
