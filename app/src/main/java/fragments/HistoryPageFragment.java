package fragments;

import adapters.BuyHistoryRecycleViewAdapter;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import com.unclethree.saveinkitchen.R;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import models.BuyHistory;
import util.VerticalSpacingItemDecorator;
import viewmodels.BuyHistoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class HistoryPageFragment extends Fragment {
    private static final String TAG = "HistoryPageFragment";

    //UI
    private RelativeLayout mRelativeLayout;
    private RecyclerView mBuyHistoryRecyclerView;
    private TextView mBuyHistoryRecyclerViewTextView;
    private SearchView mSearchView;

    //Var
    private BuyHistoryRecycleViewAdapter mBuyHistoryRecycleViewAdapter;
    private final ArrayList<BuyHistory> mBuyHistory = new ArrayList<>();
    private BuyHistoryViewModel mBuyHistoryViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.histort_page_fragment,container,false);

        mRelativeLayout = view.findViewById(R.id.history_page_rl);
        mBuyHistoryRecyclerView = view.findViewById(R.id.history_list_recycle_view);
        mBuyHistoryRecyclerViewTextView = view.findViewById(R.id.history_list_recycle_view_text);
        mSearchView = view.findViewById(R.id.history_search_view);

        initStockListRecycleView();

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mBuyHistoryViewModel.getSearchBuyHistory(newText).observe(getViewLifecycleOwner(), new Observer<List<BuyHistory>>() {
                    @Override
                    public void onChanged(List<BuyHistory> buyHistories) {
                        if (mBuyHistory.size() > 0) {
                            mBuyHistory.clear();
                        }
                        if (mBuyHistory != null) {
                            mBuyHistory.addAll(buyHistories);
                        }
                        if (buyHistories.size() == 0) {
                            mBuyHistoryRecyclerView.setVisibility(View.GONE);
                            mBuyHistoryRecyclerViewTextView.setVisibility(View.VISIBLE);
                        } else {
                            mBuyHistoryRecyclerView.setVisibility(View.VISIBLE);
                            mBuyHistoryRecyclerViewTextView.setVisibility(View.GONE);
                        }
                        mBuyHistoryRecycleViewAdapter.notifyDataSetChanged();
                    }
                });
                return false;
            }
        });

        mBuyHistoryViewModel = new ViewModelProvider(this).get(BuyHistoryViewModel.class);

        mBuyHistoryViewModel.getBuyHistory().observe(getViewLifecycleOwner(), new Observer<List<BuyHistory>>() {
            @Override
            public void onChanged(List<BuyHistory> buyHistories) {
                if (mBuyHistory.size() > 0) {
                    mBuyHistory.clear();
                }
                if (mBuyHistory != null) {
                    mBuyHistory.addAll(buyHistories);
                }
                if (buyHistories.size() == 0) {
                    mBuyHistoryRecyclerView.setVisibility(View.GONE);
                    mBuyHistoryRecyclerViewTextView.setVisibility(View.VISIBLE);
                } else {
                    mBuyHistoryRecyclerView.setVisibility(View.VISIBLE);
                    mBuyHistoryRecyclerViewTextView.setVisibility(View.GONE);
                }
                mBuyHistoryRecycleViewAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    private void initStockListRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mBuyHistoryRecyclerView.setLayoutManager(linearLayoutManager);
        mBuyHistoryRecycleViewAdapter = new BuyHistoryRecycleViewAdapter(mBuyHistory);
        VerticalSpacingItemDecorator verticalSpacingItemDecorator = new VerticalSpacingItemDecorator(8);
        mBuyHistoryRecyclerView.addItemDecoration(verticalSpacingItemDecorator);
        new ItemTouchHelper(buyHistorySimpleCallback).attachToRecyclerView(mBuyHistoryRecyclerView);
        mBuyHistoryRecyclerView.setAdapter(mBuyHistoryRecycleViewAdapter);
    }

    private final ItemTouchHelper.SimpleCallback buyHistorySimpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            switch (direction) {
                case ItemTouchHelper.RIGHT:
                    BuyHistory deleteBuyHistory = mBuyHistory.get(viewHolder.getAbsoluteAdapterPosition());
                    mBuyHistoryViewModel.deleteBuyHistory(mBuyHistory.get(viewHolder.getAbsoluteAdapterPosition()));
                    Snackbar.make(mRelativeLayout, deleteBuyHistory.getName() + " is Deleted.", Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mBuyHistoryViewModel.insertBuyHistory(deleteBuyHistory);
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
}


