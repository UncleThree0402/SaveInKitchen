package fragments;

import adapters.EatFoodHistoryListRecycleViewAdapter;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import com.unclethree.saveinkitchen.AddFoodActivity;
import com.unclethree.saveinkitchen.R;
import formatters.DateFormatter;
import formatters.NumberFormatter;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import models.DiaryHistory;
import util.VerticalSpacingItemDecorator;
import viewmodels.DiaryHistoryViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DiaryHistoryContentFragment extends Fragment implements View.OnClickListener {

    //UI
    private RelativeLayout mRelativeLayout;

    private TextView mExpendDateTextView;
    private TextView mExpendValueTextView;

    private RecyclerView mBreakfastRecyclerView;
    private RecyclerView mLunchRecyclerView;
    private RecyclerView mDinnerRecyclerView;
    private RecyclerView mSnacksRecyclerView;

    private ImageView mAddBreakfastImageView;
    private ImageView mAddLunchImageView;
    private ImageView mAddDinnerImageView;
    private ImageView mAddSnacksImageView;

    //Var
    private EatFoodHistoryListRecycleViewAdapter mBreakfastRecycleViewAdapter;
    private EatFoodHistoryListRecycleViewAdapter mLunchRecycleViewAdapter;
    private EatFoodHistoryListRecycleViewAdapter mDinnerRecycleViewAdapter;
    private EatFoodHistoryListRecycleViewAdapter mSnacksRecycleViewAdapter;

    private ArrayList<DiaryHistory> mBreakfastHistory = new ArrayList<>();
    private ArrayList<DiaryHistory> mLunchHistory = new ArrayList<>();
    private ArrayList<DiaryHistory> mDinnerHistory = new ArrayList<>();
    private ArrayList<DiaryHistory> mSnacksHistory = new ArrayList<>();

    private DiaryHistoryViewModel mDiaryHistoryViewModel;
    private long mDateTime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diary_page_content_fragment, container, false);

        mDiaryHistoryViewModel = new ViewModelProvider(this).get(DiaryHistoryViewModel.class);

        mRelativeLayout = view.findViewById(R.id.eat_food_history_rl);

        mExpendDateTextView = view.findViewById(R.id.total_expend_date);
        mExpendValueTextView = view.findViewById(R.id.total_expend_value);

        mBreakfastRecyclerView = view.findViewById(R.id.eat_page_breakfast_recycle_view);
        mLunchRecyclerView = view.findViewById(R.id.eat_page_lunch_recycle_view);
        mDinnerRecyclerView = view.findViewById(R.id.eat_page_dinner_recycle_view);
        mSnacksRecyclerView = view.findViewById(R.id.eat_page_snacks_recycle_view);

        mAddBreakfastImageView = view.findViewById(R.id.eat_page_breakfast_add_icon);
        mAddLunchImageView = view.findViewById(R.id.eat_page_lunch_add_icon);
        mAddDinnerImageView = view.findViewById(R.id.eat_page_dinner_add_icon);
        mAddSnacksImageView = view.findViewById(R.id.eat_page_snacks_add_icon);

        mAddBreakfastImageView.setOnClickListener(this);
        mAddLunchImageView.setOnClickListener(this);
        mAddDinnerImageView.setOnClickListener(this);
        mAddSnacksImageView.setOnClickListener(this);

        Bundle bundle = this.getArguments();
        mDateTime = bundle.getLong("dateTime");

        setExpendDateTextView();
        setExpendValueTextView();
        initRecyclerView();
        updateRecycleView();

        return view;
    }

    private void initRecyclerView(){
        initBreakfastRecycleView();
        initLunchRecycleView();
        initDinnerRecycleView();
        initSnacksRecycleView();
    }

    private void updateRecycleView(){
        updateBreakfastList();
        updateLunchList();
        updateDinnerList();
        updateSnacksList();
    }

    private void setExpendDateTextView(){
        if(mDateTime == dayTime(System.currentTimeMillis())){
            mExpendDateTextView.setText("Total Expenditure Today");
        }else {
            mExpendDateTextView.setText("Total Expenditure On " + DateFormatter.dayMonthYearFormatter(mDateTime- 86400*1000));
        }
    }

    private void setExpendValueTextView(){
        mDiaryHistoryViewModel.getTotalCostEatFoodHistory(mDateTime - 86400*1000,mDateTime).observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                if(aDouble != null) {
                    mExpendValueTextView.setText(NumberFormatter.moneyFormatter(aDouble));
                }else{
                    mExpendValueTextView.setText(NumberFormatter.moneyFormatter(0d));
                }
            }
        });
    }

    private long dayTime(long time) {

        long setTime;
        Date currentDate;
        long nextOneDaySecond = 86400;

        setTime = time;
        currentDate = new Date(setTime);
        long currentSecond = time;

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
        return nextOneDaySecond * 1000;
    }

    private void initBreakfastRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mBreakfastRecyclerView.setLayoutManager(linearLayoutManager);
        mBreakfastRecycleViewAdapter = new EatFoodHistoryListRecycleViewAdapter(mBreakfastHistory);
        VerticalSpacingItemDecorator verticalSpacingItemDecorator = new VerticalSpacingItemDecorator(8);
        mBreakfastRecyclerView.addItemDecoration(verticalSpacingItemDecorator);
        new ItemTouchHelper(breakfastSimpleCallback).attachToRecyclerView(mBreakfastRecyclerView);
        mBreakfastRecyclerView.setAdapter(mBreakfastRecycleViewAdapter);
    }

    private final ItemTouchHelper.SimpleCallback breakfastSimpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            switch (direction) {
                case ItemTouchHelper.RIGHT:
                    DiaryHistory deleteDiaryHistory = mBreakfastHistory.get(viewHolder.getAbsoluteAdapterPosition());
                    mDiaryHistoryViewModel.deleteEatFoodHistory(mBreakfastHistory.get(viewHolder.getAbsoluteAdapterPosition()));
                    Snackbar.make(mRelativeLayout, deleteDiaryHistory.getName() + " is Deleted.", Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mDiaryHistoryViewModel.insertEatFoodHistory(deleteDiaryHistory);
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

    private void initLunchRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mLunchRecyclerView.setLayoutManager(linearLayoutManager);
        mLunchRecycleViewAdapter = new EatFoodHistoryListRecycleViewAdapter(mLunchHistory);
        VerticalSpacingItemDecorator verticalSpacingItemDecorator = new VerticalSpacingItemDecorator(8);
        mLunchRecyclerView.addItemDecoration(verticalSpacingItemDecorator);
        new ItemTouchHelper(lunchSimpleCallback).attachToRecyclerView(mLunchRecyclerView);
        mLunchRecyclerView.setAdapter(mLunchRecycleViewAdapter);
    }

    private final ItemTouchHelper.SimpleCallback lunchSimpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            switch (direction) {
                case ItemTouchHelper.RIGHT:
                    DiaryHistory deleteDiaryHistory = mLunchHistory.get(viewHolder.getAbsoluteAdapterPosition());
                    mDiaryHistoryViewModel.deleteEatFoodHistory(mLunchHistory.get(viewHolder.getAbsoluteAdapterPosition()));
                    Snackbar.make(mRelativeLayout, deleteDiaryHistory.getName() + " is Deleted.", Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mDiaryHistoryViewModel.insertEatFoodHistory(deleteDiaryHistory);
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

    private void initDinnerRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mDinnerRecyclerView.setLayoutManager(linearLayoutManager);
        mDinnerRecycleViewAdapter = new EatFoodHistoryListRecycleViewAdapter(mDinnerHistory);
        VerticalSpacingItemDecorator verticalSpacingItemDecorator = new VerticalSpacingItemDecorator(8);
        mDinnerRecyclerView.addItemDecoration(verticalSpacingItemDecorator);
        new ItemTouchHelper(dinnerSimpleCallback).attachToRecyclerView(mDinnerRecyclerView);
        mDinnerRecyclerView.setAdapter(mDinnerRecycleViewAdapter);
    }

    private final ItemTouchHelper.SimpleCallback dinnerSimpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            switch (direction) {
                case ItemTouchHelper.RIGHT:
                    DiaryHistory deleteDiaryHistory = mDinnerHistory.get(viewHolder.getAbsoluteAdapterPosition());
                    mDiaryHistoryViewModel.deleteEatFoodHistory(mDinnerHistory.get(viewHolder.getAbsoluteAdapterPosition()));
                    Snackbar.make(mRelativeLayout, deleteDiaryHistory.getName() + " is Deleted.", Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mDiaryHistoryViewModel.insertEatFoodHistory(deleteDiaryHistory);
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

    private void initSnacksRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mSnacksRecyclerView.setLayoutManager(linearLayoutManager);
        mSnacksRecycleViewAdapter = new EatFoodHistoryListRecycleViewAdapter(mSnacksHistory);
        VerticalSpacingItemDecorator verticalSpacingItemDecorator = new VerticalSpacingItemDecorator(8);
        mSnacksRecyclerView.addItemDecoration(verticalSpacingItemDecorator);
        new ItemTouchHelper(snacksSimpleCallback).attachToRecyclerView(mSnacksRecyclerView);
        mSnacksRecyclerView.setAdapter(mSnacksRecycleViewAdapter);
    }

    private final ItemTouchHelper.SimpleCallback snacksSimpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            switch (direction) {
                case ItemTouchHelper.RIGHT:
                    DiaryHistory deleteDiaryHistory = mSnacksHistory.get(viewHolder.getAbsoluteAdapterPosition());
                    mDiaryHistoryViewModel.deleteEatFoodHistory(mSnacksHistory.get(viewHolder.getAbsoluteAdapterPosition()));
                    Snackbar.make(mRelativeLayout, deleteDiaryHistory.getName() + " is Deleted.", Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mDiaryHistoryViewModel.insertEatFoodHistory(deleteDiaryHistory);
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

    private void updateBreakfastList(){
        mDiaryHistoryViewModel.getSpecificTypeEatFoodHistory("breakfast",mDateTime - 86400*1000, mDateTime ).observe(getViewLifecycleOwner(), new Observer<List<DiaryHistory>>() {
            @Override
            public void onChanged(List<DiaryHistory> eatFoodHistories) {
                if(mBreakfastHistory.size() > 0){
                    mBreakfastHistory.clear();
                }
                if(mBreakfastHistory != null){
                    mBreakfastHistory.addAll(eatFoodHistories);
                }
                mBreakfastRecycleViewAdapter.notifyDataSetChanged();
            }
        });
    }

    private void updateLunchList(){
        mDiaryHistoryViewModel.getSpecificTypeEatFoodHistory("lunch",mDateTime - 86400*1000, mDateTime ).observe(getViewLifecycleOwner(), new Observer<List<DiaryHistory>>() {
            @Override
            public void onChanged(List<DiaryHistory> eatFoodHistories) {
                if(mLunchHistory.size() > 0){
                    mLunchHistory.clear();
                }
                if(mLunchHistory != null){
                    mLunchHistory.addAll(eatFoodHistories);
                }
                mLunchRecycleViewAdapter.notifyDataSetChanged();
            }
        });
    }

    private void updateDinnerList(){
        mDiaryHistoryViewModel.getSpecificTypeEatFoodHistory("dinner",mDateTime - 86400*1000, mDateTime ).observe(getViewLifecycleOwner(), new Observer<List<DiaryHistory>>() {
            @Override
            public void onChanged(List<DiaryHistory> eatFoodHistories) {
                if(mDinnerHistory.size() > 0){
                    mDinnerHistory.clear();
                }
                if(mDinnerHistory != null){
                    mDinnerHistory.addAll(eatFoodHistories);
                }
                mDinnerRecycleViewAdapter.notifyDataSetChanged();
            }
        });
    }

    private void updateSnacksList(){
        mDiaryHistoryViewModel.getSpecificTypeEatFoodHistory("snacks",mDateTime - 86400*1000, mDateTime ).observe(getViewLifecycleOwner(), new Observer<List<DiaryHistory>>() {
            @Override
            public void onChanged(List<DiaryHistory> eatFoodHistories) {
                if(mSnacksHistory.size() > 0){
                    mSnacksHistory.clear();
                }
                if(mSnacksHistory != null){
                    mSnacksHistory.addAll(eatFoodHistories);
                }
                mSnacksRecycleViewAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.eat_page_breakfast_add_icon:
                intentAddFood("breakfast");
                break;
            case R.id.eat_page_lunch_add_icon:
                intentAddFood("lunch");
                break;
            case R.id.eat_page_dinner_add_icon:
                intentAddFood("dinner");
                break;
            case R.id.eat_page_snacks_add_icon:
                intentAddFood("snacks");
                break;
        }
    }

    private void intentAddFood(String type){
        Intent intent = new Intent(getActivity(), AddFoodActivity.class);
        intent.putExtra("Type", type);
        intent.putExtra("Time",mDateTime - 8600);
        startActivity(intent);
    }
}
