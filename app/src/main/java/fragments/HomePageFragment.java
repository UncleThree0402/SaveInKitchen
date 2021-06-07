package fragments;

import adapters.BuyListRecycleViewAdapter;
import adapters.StockListRecycleViewAdapter;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.snackbar.Snackbar;
import com.unclethree.saveinkitchen.R;
import dialogs.AddBuyListDialog;
import dialogs.AddStockDialog;
import dialogs.DeleteFoodTypeWarningDialog;
import formatters.NumberFormatter;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import models.BuyFood;
import models.BuyHistory;
import models.FoodType;
import util.VerticalSpacingItemDecorator;
import viewmodels.BuyFoodViewModel;
import viewmodels.BuyHistoryViewModel;
import viewmodels.FoodTypeViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomePageFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "HomePageFragment";

    //UI
    private RelativeLayout mRelativeLayout;
    private TextView mExpenseValueTextView;
    private LineChart mLineChart;
    private RecyclerView mBuyListRecycleView;
    private TextView mBuyListRecycleViewText;
    private RecyclerView mStockListRecycleView;
    private TextView mStockListRecycleViewText;
    private ImageView mAddStockListIcon;
    private ImageView mAddBuyListIcon;

    //Var
    private BuyHistoryViewModel mBuyHistoryViewModel;
    private BuyFoodViewModel mBuyFoodViewModel;
    private FoodTypeViewModel mFoodTypeViewModel;
    private BuyListRecycleViewAdapter mBuyListRecycleViewAdapter;
    private StockListRecycleViewAdapter mStockListRecycleViewAdapter;
    private ArrayList<BuyFood> mBuyFood = new ArrayList<>();
    private ArrayList<FoodType> mFoodType = new ArrayList<>();
    private ArrayList<BuyHistory> mBuyHistory = new ArrayList<>();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_page_fragment, container, false);

        mRelativeLayout = view.findViewById(R.id.home_page_rl);
        mExpenseValueTextView = view.findViewById(R.id.expense_value);
        mLineChart = view.findViewById(R.id.home_page_line_chart);
        mBuyListRecycleView = view.findViewById(R.id.buy_list_recycle_view);
        mBuyListRecycleViewText = view.findViewById(R.id.buy_list_recycle_view_text);
        mStockListRecycleView = view.findViewById(R.id.stock_recycle_view);
        mStockListRecycleViewText = view.findViewById(R.id.stock_recycle_view_text);
        mAddStockListIcon = view.findViewById(R.id.add_stock_icon);
        mAddBuyListIcon = view.findViewById(R.id.add_buy_list_icon);

        mBuyHistoryViewModel = new ViewModelProvider(this).get(BuyHistoryViewModel.class);
        mBuyFoodViewModel = new ViewModelProvider(this).get(BuyFoodViewModel.class);
        mFoodTypeViewModel = new ViewModelProvider(this).get(FoodTypeViewModel.class);

        mAddStockListIcon.setOnClickListener(this);
        mAddBuyListIcon.setOnClickListener(this);

        setExpenseValueTextView((dayTime() - 86400 * 30) * 1000);
        initLineChart();

        mBuyFoodViewModel.getBuyFood().observe(getViewLifecycleOwner(), new Observer<List<BuyFood>>() {
            @Override
            public void onChanged(List<BuyFood> buyFoods) {
                if (mBuyFood.size() > 0) {
                    mBuyFood.clear();
                }
                if (mBuyFood != null) {
                    mBuyFood.addAll(buyFoods);
                }
                if (buyFoods.size() == 0) {
                    mBuyListRecycleView.setVisibility(View.GONE);
                    mBuyListRecycleViewText.setVisibility(View.VISIBLE);
                } else {
                    mBuyListRecycleView.setVisibility(View.VISIBLE);
                    mBuyListRecycleViewText.setVisibility(View.GONE);
                }
                mBuyListRecycleViewAdapter.notifyDataSetChanged();
            }
        });

        mFoodTypeViewModel.getFoodType().observe(getViewLifecycleOwner(), new Observer<List<FoodType>>() {
            @Override
            public void onChanged(List<FoodType> foodTypes) {
                if (mFoodType.size() > 0) {
                    mFoodType.clear();
                }
                if (mFoodType != null) {
                    mFoodType.addAll(foodTypes);
                }
                if (foodTypes.size() == 0) {
                    mStockListRecycleView.setVisibility(View.GONE);
                    mStockListRecycleViewText.setVisibility(View.VISIBLE);
                } else {
                    mStockListRecycleView.setVisibility(View.VISIBLE);
                    mStockListRecycleViewText.setVisibility(View.GONE);
                }
                mStockListRecycleViewAdapter.notifyDataSetChanged();
            }
        });


        initBuyListRecycleView();
        initStockListRecycleView();

        return view;
    }

    private void initBuyListRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mBuyListRecycleView.setLayoutManager(linearLayoutManager);
        mBuyListRecycleViewAdapter = new BuyListRecycleViewAdapter(mBuyFood);
        VerticalSpacingItemDecorator verticalSpacingItemDecorator = new VerticalSpacingItemDecorator(8);
        mBuyListRecycleView.addItemDecoration(verticalSpacingItemDecorator);
        new ItemTouchHelper(buyListSimpleCallback).attachToRecyclerView(mBuyListRecycleView);
        mBuyListRecycleView.setAdapter(mBuyListRecycleViewAdapter);
    }

    private void initStockListRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mStockListRecycleView.setLayoutManager(linearLayoutManager);
        mStockListRecycleViewAdapter = new StockListRecycleViewAdapter(mFoodType, this, this, getContext());
        VerticalSpacingItemDecorator verticalSpacingItemDecorator = new VerticalSpacingItemDecorator(8);
        mStockListRecycleView.addItemDecoration(verticalSpacingItemDecorator);
        new ItemTouchHelper(stockListSimpleCallback).attachToRecyclerView(mStockListRecycleView);
        mStockListRecycleView.setAdapter(mStockListRecycleViewAdapter);
    }

    private ItemTouchHelper.SimpleCallback buyListSimpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            switch (direction) {
                case ItemTouchHelper.RIGHT:
                    BuyFood deleteBuyFood = mBuyFood.get(viewHolder.getAbsoluteAdapterPosition());
                    mBuyFoodViewModel.deleteBuyFood(mBuyFood.get(viewHolder.getAbsoluteAdapterPosition()));
                    Snackbar.make(mRelativeLayout, deleteBuyFood.getName() + " is Deleted.", Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mBuyFoodViewModel.insertBuyFood(deleteBuyFood);
                                }
                            }).show();
                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_delete_24)
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_edit_24)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };



    private ItemTouchHelper.SimpleCallback stockListSimpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            switch (direction) {
                case ItemTouchHelper.RIGHT:

                    Bundle bundle = new Bundle();
                    bundle.putParcelable("Food Type", mFoodType.get(viewHolder.getAbsoluteAdapterPosition()));
                    DeleteFoodTypeWarningDialog deleteFoodTypeWarningDialog = new DeleteFoodTypeWarningDialog(bundle);
                    deleteFoodTypeWarningDialog.show(getChildFragmentManager(), "Add stock dialog");
                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_delete_24)
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_edit_24)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };


    private void setExpenseValueTextView(long date) {
        mBuyHistoryViewModel.getBuyHistory30DaysCost(date).observe(getViewLifecycleOwner(), new Observer<Double>() {

            @Override
            public void onChanged(Double aDouble) {
                if (aDouble == null) {
                    mExpenseValueTextView.setText(NumberFormatter.moneyFormatter(0d));
                } else {
                    mExpenseValueTextView.setText(NumberFormatter.moneyFormatter(aDouble));
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
                return simpleDateFormat.format(date);
            }
        });

        mLineChart.getLegend().setEnabled(false);
        mLineChart.getDescription().setEnabled(false);
        mLineChart.setAutoScaleMinMaxEnabled(true);
        initDate();
        mLineChart.invalidate();

        mBuyHistoryViewModel.getBuyHistory30Days((dayTime() - 86400 * 30) * 1000).observe(getViewLifecycleOwner(), new Observer<List<BuyHistory>>() {
            @Override
            public void onChanged(List<BuyHistory> buyHistories) {
                if (mBuyHistory.size() > 0) {
                    mBuyHistory.clear();
                }
                if (mBuyHistory != null) {
                    mBuyHistory.addAll(buyHistories);
                }
                setDate();
            }
        });

    }

    private void setDate() {
        ArrayList<Entry> entries = new ArrayList<>();
        long currentDayEnd = dayTime();
        for (int i = 1; i < 31; i++) {
            long time = (currentDayEnd - 86400 * (30 - i));
            float value = 0f;
            for (int j = 0; j < mBuyHistory.size(); j++) {
                if (mBuyHistory.get(j).getBuyDate() >= (currentDayEnd - 86400 * (30 - i)) * 1000 && mBuyHistory.get(j).getBuyDate() <= (currentDayEnd - 86400 * (30 - i)) * 1000) {
                    value += mBuyHistory.get(j).getCost();
                    mBuyHistory.remove(j);
                    j--;
                }
            }
            entries.add(new Entry(time, value));
        }
        LineDataSet lineDataSet = new LineDataSet(entries, "7 Days Cost");
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
        LineDataSet lineDataSet = new LineDataSet(entries, "7 Days Cost");
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
        return nextOneDaySecond;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_stock_icon:
                AddStockDialog addStockDialog = new AddStockDialog();
                addStockDialog.show(getChildFragmentManager(), "Add stock dialog");
                break;
            case R.id.add_buy_list_icon:
                AddBuyListDialog addBuyListDialog = new AddBuyListDialog();
                addBuyListDialog.show(getChildFragmentManager(), "Add buy list dialog");
                break;
        }
    }
}
