package fragments.cooking.dishes;

import adapters.BuyHistoryRecycleViewAdapter;
import adapters.DishesRecycleViewAdapter;
import android.graphics.Canvas;
import android.os.Bundle;
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
import models.BuyFood;
import models.BuyHistory;
import models.Dishes;
import util.VerticalSpacingItemDecorator;
import viewmodels.BuyHistoryViewModel;
import viewmodels.DishesViewModel;

import java.util.ArrayList;
import java.util.List;

public class DishesFragment extends Fragment implements DishesRecycleViewAdapter.OnDishClickListener {
    private static final String TAG = "DishesFragment";

    //UI
    private RelativeLayout mRelativeLayout;
    private RecyclerView mDishesRecyclerView;
    private TextView mDishesRecyclerViewTextView;
    private SearchView mSearchView;

    //Var
    private DishesRecycleViewAdapter mDishRecycleViewAdapter;
    private ArrayList<Dishes> mDishes = new ArrayList<>();
    private DishesViewModel mDishesViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dishes_fragment,container,false);

        mDishesViewModel = new ViewModelProvider(this).get(DishesViewModel.class);

        mRelativeLayout = view.findViewById(R.id.cook_page_rl);
        mDishesRecyclerView = view.findViewById(R.id.dish_recycle_view);
        mDishesRecyclerViewTextView = view.findViewById(R.id.dish_recycle_view_text);
        mSearchView = view.findViewById(R.id.dish_search_view);

        initDishRecycleView();

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mDishesViewModel.getSearchDish(newText).observe(getViewLifecycleOwner(), new Observer<List<Dishes>>() {
                    @Override
                    public void onChanged(List<Dishes> dishes) {
                        if (mDishes.size() > 0) {
                            mDishes.clear();
                        }
                        if (mDishes != null) {
                            mDishes.addAll(dishes);
                        }
                        if (dishes.size() == 0) {
                            mDishesRecyclerView.setVisibility(View.GONE);
                            mDishesRecyclerViewTextView.setVisibility(View.VISIBLE);
                        } else {
                            mDishesRecyclerView.setVisibility(View.VISIBLE);
                            mDishesRecyclerViewTextView.setVisibility(View.GONE);
                        }
                        mDishRecycleViewAdapter.notifyDataSetChanged();
                    }
                });
                return false;
            }
        });

        mDishesViewModel.getDishes().observe(getViewLifecycleOwner(), new Observer<List<Dishes>>() {
            @Override
            public void onChanged(List<Dishes> dishes) {
                if (mDishes.size() > 0) {
                    mDishes.clear();
                }
                if (mDishes != null) {
                    mDishes.addAll(dishes);
                }
                if (dishes.size() == 0) {
                    mDishesRecyclerView.setVisibility(View.GONE);
                    mDishesRecyclerViewTextView.setVisibility(View.VISIBLE);
                } else {
                    mDishesRecyclerView.setVisibility(View.VISIBLE);
                    mDishesRecyclerViewTextView.setVisibility(View.GONE);
                }
                mDishRecycleViewAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }


    private void initDishRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mDishesRecyclerView.setLayoutManager(linearLayoutManager);
        mDishRecycleViewAdapter = new DishesRecycleViewAdapter(mDishes,this);
        VerticalSpacingItemDecorator verticalSpacingItemDecorator = new VerticalSpacingItemDecorator(8);
        mDishesRecyclerView.addItemDecoration(verticalSpacingItemDecorator);
        new ItemTouchHelper(dishesSimpleCallback).attachToRecyclerView(mDishesRecyclerView);
        mDishesRecyclerView.setAdapter(mDishRecycleViewAdapter);
    }

    private final ItemTouchHelper.SimpleCallback dishesSimpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            switch (direction) {
                case ItemTouchHelper.RIGHT:
                    Dishes deleteDishes = mDishes.get(viewHolder.getAbsoluteAdapterPosition());
                    mDishesViewModel.deleteDishes(mDishes.get(viewHolder.getAbsoluteAdapterPosition()));
                    Snackbar.make(mRelativeLayout, deleteDishes.getName() + " is Deleted.", Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mDishesViewModel.insertDishes(deleteDishes);
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

    @Override
    public void OnDishClick(int position) {

    }
}


