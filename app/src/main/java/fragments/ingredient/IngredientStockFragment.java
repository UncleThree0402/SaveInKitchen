package fragments.ingredient;

import adapters.IngredientRecycleViewAdapter;
import adapters.StockRecycleViewAdapter;
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
import dialogs.DeleteFoodTypeWarningDialog;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import models.Food;
import models.FoodType;
import util.VerticalSpacingItemDecorator;
import viewmodels.FoodTypeViewModel;
import viewmodels.FoodViewModel;

import java.util.ArrayList;
import java.util.List;

public class IngredientStockFragment extends Fragment {

    //UI
    private RecyclerView mIngredientRecyclerView;
    private TextView mIngredientRecyclerViewTextView;
    private SearchView mSearchView;

    //Var
    private StockRecycleViewAdapter mStockRecycleViewAdapter;
    private ArrayList<FoodType> mFoodType = new ArrayList<>();
    private FoodTypeViewModel mFoodTypeViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ingredient_page_content_fragment, container, false);

        if (savedInstanceState != null) {
            mFoodType = savedInstanceState.getParcelableArrayList("FoodType");
        }

        mFoodTypeViewModel = new ViewModelProvider(this).get(FoodTypeViewModel.class);

        mIngredientRecyclerView = view.findViewById(R.id.ingredient_recycle_view);
        mIngredientRecyclerViewTextView = view.findViewById(R.id.ingredient_recycle_view_text);
        mSearchView = view.findViewById(R.id.ingredient_search_view);

        initIngredientRecycleView();

        setIngredientList();

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("FoodType", mFoodType);
    }

    private void setIngredientList() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mFoodTypeViewModel.getSearchFoodType(newText).observe(getViewLifecycleOwner(), new Observer<List<FoodType>>() {
                    @Override
                    public void onChanged(List<FoodType> foodTypes) {
                        if (mFoodType.size() > 0) {
                            mFoodType.clear();
                        }
                        if (mFoodType != null) {
                            mFoodType.addAll(foodTypes);
                        }
                        if (mFoodType.size() == 0) {
                            mIngredientRecyclerView.setVisibility(View.GONE);
                            mIngredientRecyclerViewTextView.setVisibility(View.VISIBLE);
                        } else {
                            mIngredientRecyclerView.setVisibility(View.VISIBLE);
                            mIngredientRecyclerViewTextView.setVisibility(View.GONE);
                        }
                        mStockRecycleViewAdapter.notifyDataSetChanged();
                    }
                });
                return false;
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
                if (mFoodType.size() == 0) {
                    mIngredientRecyclerView.setVisibility(View.GONE);
                    mIngredientRecyclerViewTextView.setVisibility(View.VISIBLE);
                } else {
                    mIngredientRecyclerView.setVisibility(View.VISIBLE);
                    mIngredientRecyclerViewTextView.setVisibility(View.GONE);
                }
                mStockRecycleViewAdapter.notifyDataSetChanged();
            }
        });
    }


    private void initIngredientRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mIngredientRecyclerView.setLayoutManager(linearLayoutManager);
        mStockRecycleViewAdapter = new StockRecycleViewAdapter(mFoodType, this, this, getContext());
        VerticalSpacingItemDecorator verticalSpacingItemDecorator = new VerticalSpacingItemDecorator(8);
        mIngredientRecyclerView.addItemDecoration(verticalSpacingItemDecorator);
        new ItemTouchHelper(stockListSimpleCallback).attachToRecyclerView(mIngredientRecyclerView);
        mIngredientRecyclerView.setAdapter(mStockRecycleViewAdapter);
    }

    private final ItemTouchHelper.SimpleCallback stockListSimpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
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
}
