package fragments;

import adapters.BuyListRecycleViewAdapter;
import adapters.StockListRecycleViewAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.unclethree.saveinkitchen.R;
import dialogs.AddStockDialog;
import formatters.NumberFormatter;
import models.BuyHistory;
import models.BuyListItem;
import models.FoodType;
import viewmodels.BuyFoodViewModel;
import viewmodels.BuyHistoryViewModel;
import viewmodels.FoodTypeViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomePageFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "HomePageFragment";

    //UI
    private TextView mExpenseValueTextView;
    private LineChart mLineChart;
    private RecyclerView mBuyListRecycleView;
    private RecyclerView mStockListRecycleView;
    private ImageView mAddStockListIcon;

    //Var
    private BuyHistoryViewModel mBuyHistoryViewModel;
    private BuyFoodViewModel mBuyFoodViewModel;
    private FoodTypeViewModel mFoodTypeViewModel;
    private BuyListRecycleViewAdapter mBuyListRecycleViewAdapter;
    private StockListRecycleViewAdapter mStockListRecycleViewAdapter;
    private ArrayList<BuyListItem> mBuyList = new ArrayList<>();
    private ArrayList<FoodType> mFoodType = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_page_fragment,container,false);

        mExpenseValueTextView = view.findViewById(R.id.expense_value);
        mLineChart = view.findViewById(R.id.home_page_line_chart);
        mBuyListRecycleView = view.findViewById(R.id.buy_list_recycle_view);
        mStockListRecycleView = view.findViewById(R.id.stock_recycle_view);
        mAddStockListIcon = view.findViewById(R.id.add_stock_icon);

        mBuyHistoryViewModel = new ViewModelProvider(this).get(BuyHistoryViewModel.class);
        mBuyFoodViewModel = new ViewModelProvider(this).get(BuyFoodViewModel.class);
        mFoodTypeViewModel = new ViewModelProvider(this).get(FoodTypeViewModel.class);

        mAddStockListIcon.setOnClickListener(this);

        setExpenseValueTextView(System.currentTimeMillis());
        initLineChart();

        mBuyFoodViewModel.getBuyFoodList().observe(getViewLifecycleOwner(), new Observer<List<BuyListItem>>() {
            @Override
            public void onChanged(List<BuyListItem> buyListItems) {
                if(mBuyList.size()>0){
                    mBuyList.clear();
                }
                if(mBuyList != null){
                    mBuyList.addAll(buyListItems);
                }
                mBuyListRecycleViewAdapter.notifyDataSetChanged();
            }
        });

        mFoodTypeViewModel.getFoodType().observe(getViewLifecycleOwner(), new Observer<List<FoodType>>() {
            @Override
            public void onChanged(List<FoodType> foodTypes) {
                if(mFoodType.size()>0){
                    mFoodType.clear();
                }
                if(mFoodType != null){
                    mFoodType.addAll(foodTypes);
                }
                mStockListRecycleViewAdapter.notifyDataSetChanged();
            }
        });

        initBuyListRecycleView();
        initStockListRecycleView();

        return view;
    }

    private void initBuyListRecycleView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mBuyListRecycleView.setLayoutManager(linearLayoutManager);
        mBuyListRecycleViewAdapter = new BuyListRecycleViewAdapter(mBuyList);
        mBuyListRecycleView.setAdapter(mBuyListRecycleViewAdapter);
    }

    private void initStockListRecycleView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mStockListRecycleView.setLayoutManager(linearLayoutManager);
        mStockListRecycleViewAdapter = new StockListRecycleViewAdapter(mFoodType,this,this,getContext());
        mStockListRecycleView.setAdapter(mStockListRecycleViewAdapter);
    }

    private void setExpenseValueTextView(long date){
        mBuyHistoryViewModel.getBuyHistory30DaysCost(date).observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                if(aDouble == null){
                    mExpenseValueTextView.setText(NumberFormatter.moneyFormatter(0d));
                    Log.d(TAG, "onChanged: called");
                }else{
                    mExpenseValueTextView.setText(NumberFormatter.moneyFormatter(aDouble));
                }

            }
        });
    }

    private void initLineChart(){
        List<Entry> entries = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            entries.add(new Entry(i,i));
        }

        LineDataSet lineDataSet = new LineDataSet(entries, "7 Days Cost");

        lineDataSet.setColor(R.color.black);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillColor(R.color.black);
        lineDataSet.setFillAlpha(0);

        LineData lineData = new LineData(lineDataSet);

        mLineChart.setHighlightPerTapEnabled(false);
        mLineChart.setHighlightPerDragEnabled(false);

        XAxis xAxis = mLineChart.getXAxis();
        YAxis yAxisLeft = mLineChart.getAxisLeft();
        YAxis yAxisRight = mLineChart.getAxisRight();
        xAxis.setDrawGridLines(false);
        yAxisLeft.setDrawGridLines(false);
        yAxisRight.setEnabled(false);
        xAxis.setLabelCount(7);
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        mLineChart.getLegend().setEnabled(false);
        mLineChart.getDescription().setEnabled(false);
        mLineChart.setAutoScaleMinMaxEnabled(true);

        mLineChart.setData(lineData);
        mLineChart.invalidate();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_stock_icon:
                AddStockDialog addStockDialog = new AddStockDialog();
                addStockDialog.show(getChildFragmentManager(), "Add stock dialog");
                break;
        }
    }
}
