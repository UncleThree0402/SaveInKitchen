package fragments.ingredient;

import adapters.BuyListRecycleViewAdapter;
import adapters.IngredientRecycleViewAdapter;
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
import models.Food;
import util.VerticalSpacingItemDecorator;
import viewmodels.BuyFoodViewModel;
import viewmodels.FoodViewModel;

import java.util.ArrayList;
import java.util.List;

public class IngredientContentFragment extends Fragment {

    //UI
    private RelativeLayout mRelativeLayout;
    private RecyclerView mIngredientRecyclerView;
    private TextView mIngredientRecyclerViewTextView;
    private SearchView mSearchView;

    //Var
    private IngredientRecycleViewAdapter mIngredientRecyclerAdapter;
    private ArrayList<Food> mFood = new ArrayList<>();
    private FoodViewModel mFoodViewModel;
    private String mStatus;

    public IngredientContentFragment(String mStatus) {
        this.mStatus = mStatus;
    }

    public IngredientContentFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ingredient_page_content_fragment, container, false);

        if (savedInstanceState != null) {
            mFood = savedInstanceState.getParcelableArrayList("Food");
            mStatus = savedInstanceState.getString("Status");
        }

        mFoodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);

        mRelativeLayout = view.findViewById(R.id.ingredient_content_rl);
        mIngredientRecyclerView = view.findViewById(R.id.ingredient_recycle_view);
        mIngredientRecyclerViewTextView = view.findViewById(R.id.ingredient_recycle_view_text);
        mSearchView = view.findViewById(R.id.ingredient_search_view);


        initIngredientRecycleView();

        setIngredientList(mStatus);

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("Food",mFood);
        outState.putString("Status" , mStatus);
    }

    private void setIngredientList(String mStatus) {
        if(mStatus != null){
            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    mFoodViewModel.getSpecificTypeFood(mStatus, newText).observe(getViewLifecycleOwner(), new Observer<List<Food>>() {
                        @Override
                        public void onChanged(List<Food> foods) {
                            if (mFood.size() > 0) {
                                mFood.clear();
                            }
                            if (mFood != null) {
                                mFood.addAll(foods);
                            }
                            if (foods.size() == 0) {
                                mIngredientRecyclerView.setVisibility(View.GONE);
                                mIngredientRecyclerViewTextView.setVisibility(View.VISIBLE);
                            } else {
                                mIngredientRecyclerView.setVisibility(View.VISIBLE);
                                mIngredientRecyclerViewTextView.setVisibility(View.GONE);
                            }
                            mIngredientRecyclerAdapter.notifyDataSetChanged();
                        }
                    });
                    return false;
                }
            });

            mFoodViewModel.getSpecificTypeFood(mStatus, "").observe(getViewLifecycleOwner(), new Observer<List<Food>>() {
                @Override
                public void onChanged(List<Food> foods) {
                    if (mFood.size() > 0) {
                        mFood.clear();
                    }
                    if (mFood != null) {
                        mFood.addAll(foods);
                    }
                    if (mFood.size() == 0) {
                        mIngredientRecyclerView.setVisibility(View.GONE);
                        mIngredientRecyclerViewTextView.setVisibility(View.VISIBLE);
                    } else {
                        mIngredientRecyclerView.setVisibility(View.VISIBLE);
                        mIngredientRecyclerViewTextView.setVisibility(View.GONE);
                    }
                    mIngredientRecyclerAdapter.notifyDataSetChanged();
                }
            });
        }else {
            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    mFoodViewModel.getSearchFood(newText).observe(getViewLifecycleOwner(), new Observer<List<Food>>() {
                        @Override
                        public void onChanged(List<Food> foods) {
                            if (mFood.size() > 0) {
                                mFood.clear();
                            }
                            if (mFood != null) {
                                mFood.addAll(foods);
                            }
                            if (foods.size() == 0) {
                                mIngredientRecyclerView.setVisibility(View.GONE);
                                mIngredientRecyclerViewTextView.setVisibility(View.VISIBLE);
                            } else {
                                mIngredientRecyclerView.setVisibility(View.VISIBLE);
                                mIngredientRecyclerViewTextView.setVisibility(View.GONE);
                            }
                            mIngredientRecyclerAdapter.notifyDataSetChanged();
                        }
                    });
                    return false;
                }
            });

            mFoodViewModel.getFood().observe(getViewLifecycleOwner(), new Observer<List<Food>>() {
                @Override
                public void onChanged(List<Food> foods) {
                    if (mFood.size() > 0) {
                        mFood.clear();
                    }
                    if (mFood != null) {
                        mFood.addAll(foods);
                    }
                    if (mFood.size() == 0) {
                        mIngredientRecyclerView.setVisibility(View.GONE);
                        mIngredientRecyclerViewTextView.setVisibility(View.VISIBLE);
                    } else {
                        mIngredientRecyclerView.setVisibility(View.VISIBLE);
                        mIngredientRecyclerViewTextView.setVisibility(View.GONE);
                    }
                    mIngredientRecyclerAdapter.notifyDataSetChanged();
                }
            });
        }

    }


    private void initIngredientRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mIngredientRecyclerView.setLayoutManager(linearLayoutManager);
        mIngredientRecyclerAdapter = new IngredientRecycleViewAdapter(mFood);
        VerticalSpacingItemDecorator verticalSpacingItemDecorator = new VerticalSpacingItemDecorator(8);
        mIngredientRecyclerView.addItemDecoration(verticalSpacingItemDecorator);
        new ItemTouchHelper(ingredientSimpleCallback).attachToRecyclerView(mIngredientRecyclerView);
        mIngredientRecyclerView.setAdapter(mIngredientRecyclerAdapter);
    }

    private final ItemTouchHelper.SimpleCallback ingredientSimpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            switch (direction) {
                case ItemTouchHelper.RIGHT:
                    Food deleteFood = mFood.get(viewHolder.getAbsoluteAdapterPosition());
                    mFoodViewModel.deleteFood(mFood.get(viewHolder.getAbsoluteAdapterPosition()));
                    Snackbar.make(mRelativeLayout, deleteFood.getName() + " is Deleted.", Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mFoodViewModel.insertFood(deleteFood);
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
