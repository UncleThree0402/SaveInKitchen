package fragments.buylist;

import adapters.BuyHistoryRecycleViewAdapter;
import adapters.BuyListRecycleViewAdapter;
import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import models.BuyFood;
import models.BuyHistory;
import util.VerticalSpacingItemDecorator;
import viewmodels.BuyFoodViewModel;
import viewmodels.BuyHistoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class BuyListContentFragment extends Fragment {

    //UI
    private RelativeLayout mRelativeLayout;
    private RecyclerView mBuyListRecyclerView;
    private TextView mBuyListRecyclerViewTextView;
    private SearchView mSearchView;

    //Var
    private BuyListRecycleViewAdapter mBuyListRecyclerAdapter;
    private ArrayList<BuyFood> mBuyFood = new ArrayList<>();
    private BuyFoodViewModel mBuyFoodViewModel;
    private String mStatus;

    public BuyListContentFragment(String mStatus) {
        this.mStatus = mStatus;
    }

    public BuyListContentFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.buy_list_content_fragment, container, false);

        if (savedInstanceState != null) {
            mBuyFood = savedInstanceState.getParcelableArrayList("BuyFood");
            mStatus = savedInstanceState.getString("Status");
        }

        mBuyFoodViewModel = new ViewModelProvider(this).get(BuyFoodViewModel.class);

        mRelativeLayout = getActivity().findViewById(R.id.buy_list_page_rl);
        mBuyListRecyclerView = view.findViewById(R.id.buy_list_recycle_view);
        mBuyListRecyclerViewTextView = view.findViewById(R.id.buy_list_recycle_view_text);
        mSearchView = view.findViewById(R.id.buy_list_search_view);


        initBuyListRecycleView();

        setBuyList(mStatus);


        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("BuyFood",mBuyFood);
        outState.putString("Status" , mStatus);
    }

    private void setBuyList(String mStatus) {
        if(mStatus != null){
            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    mBuyFoodViewModel.getSpecificTypeBuyFood(mStatus, newText).observe(getViewLifecycleOwner(), new Observer<List<BuyFood>>() {
                        @Override
                        public void onChanged(List<BuyFood> buyFoods) {
                            if (mBuyFood.size() > 0) {
                                mBuyFood.clear();
                            }
                            if (mBuyFood != null) {
                                mBuyFood.addAll(buyFoods);
                            }
                            if (buyFoods.size() == 0) {
                                mBuyListRecyclerView.setVisibility(View.GONE);
                                mBuyListRecyclerViewTextView.setVisibility(View.VISIBLE);
                            } else {
                                mBuyListRecyclerView.setVisibility(View.VISIBLE);
                                mBuyListRecyclerViewTextView.setVisibility(View.GONE);
                            }
                            mBuyListRecyclerAdapter.notifyDataSetChanged();
                        }
                    });
                    return false;
                }
            });

            mBuyFoodViewModel.getSpecificTypeBuyFood(mStatus, "").observe(getViewLifecycleOwner(), new Observer<List<BuyFood>>() {
                @Override
                public void onChanged(List<BuyFood> buyFoods) {
                    if (mBuyFood.size() > 0) {
                        mBuyFood.clear();
                    }
                    if (mBuyFood != null) {
                        mBuyFood.addAll(buyFoods);
                    }
                    if (buyFoods.size() == 0) {
                        mBuyListRecyclerView.setVisibility(View.GONE);
                        mBuyListRecyclerViewTextView.setVisibility(View.VISIBLE);
                    } else {
                        mBuyListRecyclerView.setVisibility(View.VISIBLE);
                        mBuyListRecyclerViewTextView.setVisibility(View.GONE);
                    }
                    mBuyListRecyclerAdapter.notifyDataSetChanged();
                }
            });
        }else{
            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    mBuyFoodViewModel.getSpecificNameBuyFood(newText).observe(getViewLifecycleOwner(), new Observer<List<BuyFood>>() {
                        @Override
                        public void onChanged(List<BuyFood> buyFoods) {
                            if (mBuyFood.size() > 0) {
                                mBuyFood.clear();
                            }
                            if (mBuyFood != null) {
                                mBuyFood.addAll(buyFoods);
                            }
                            if (buyFoods.size() == 0) {
                                mBuyListRecyclerView.setVisibility(View.GONE);
                                mBuyListRecyclerViewTextView.setVisibility(View.VISIBLE);
                            } else {
                                mBuyListRecyclerView.setVisibility(View.VISIBLE);
                                mBuyListRecyclerViewTextView.setVisibility(View.GONE);
                            }
                            mBuyListRecyclerAdapter.notifyDataSetChanged();
                        }
                    });
                    return false;
                }
            });

            mBuyFoodViewModel.getSpecificNameBuyFood("").observe(getViewLifecycleOwner(), new Observer<List<BuyFood>>() {
                @Override
                public void onChanged(List<BuyFood> buyFoods) {
                    if (mBuyFood.size() > 0) {
                        mBuyFood.clear();
                    }
                    if (mBuyFood != null) {
                        mBuyFood.addAll(buyFoods);
                    }
                    if (buyFoods.size() == 0) {
                        mBuyListRecyclerView.setVisibility(View.GONE);
                        mBuyListRecyclerViewTextView.setVisibility(View.VISIBLE);
                    } else {
                        mBuyListRecyclerView.setVisibility(View.VISIBLE);
                        mBuyListRecyclerViewTextView.setVisibility(View.GONE);
                    }
                    mBuyListRecyclerAdapter.notifyDataSetChanged();
                }
            });
        }


    }


    private void initBuyListRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mBuyListRecyclerView.setLayoutManager(linearLayoutManager);
        mBuyListRecyclerAdapter = new BuyListRecycleViewAdapter(mBuyFood);
        VerticalSpacingItemDecorator verticalSpacingItemDecorator = new VerticalSpacingItemDecorator(8);
        mBuyListRecyclerView.addItemDecoration(verticalSpacingItemDecorator);
        new ItemTouchHelper(buyListSimpleCallback).attachToRecyclerView(mBuyListRecyclerView);
        mBuyListRecyclerView.setAdapter(mBuyListRecyclerAdapter);
    }

    private final ItemTouchHelper.SimpleCallback buyListSimpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
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

}
