package fragments.cooking.preparing;

import adapters.CookDishRecycleViewAdapter;
import adapters.DishesRecycleViewAdapter;
import android.content.Intent;
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
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.unclethree.saveinkitchen.CookDishRecipeActivity;
import com.unclethree.saveinkitchen.PrepareRecipeActivity;
import com.unclethree.saveinkitchen.R;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import models.CookDish;
import models.Dishes;
import models.Recipe;
import util.VerticalSpacingItemDecorator;
import viewmodels.CookDishViewModel;
import viewmodels.DishesViewModel;
import viewmodels.RecipeViewModel;

import java.util.ArrayList;
import java.util.List;

public class PrepareFragment extends Fragment implements CookDishRecycleViewAdapter.OnCookDishClickListener {
    private static final String TAG = "PrepareFragment";

    //UI
    private RelativeLayout mRelativeLayout;
    private RecyclerView mCookDishRecyclerView;
    private TextView mCookDishRecyclerViewTextView;
    private SearchView mSearchView;
    private ExtendedFloatingActionButton mAddItemButton;

    //Var
    private CookDishRecycleViewAdapter mCookDishRecycleViewAdapter;
    private final ArrayList<CookDish> mCookDish = new ArrayList<>();
    private CookDishViewModel mCookDishViewModel;
    private RecipeViewModel mRecipeViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.prepare_fragment,container,false);

        mCookDishViewModel = new ViewModelProvider(this).get(CookDishViewModel.class);
        mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        mRelativeLayout = view.findViewById(R.id.prepare_fragment_rl);
        mCookDishRecyclerView = view.findViewById(R.id.prepare_recycle_view);
        mCookDishRecyclerViewTextView = view.findViewById(R.id.prepare_recycle_view_text);
        mSearchView = view.findViewById(R.id.prepare_search_view);
        mAddItemButton = view.findViewById(R.id.prepare_add_button);

        initCookDishRecycleView();

        mAddItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CookDishRecipeActivity.class);
                startActivity(intent);
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mCookDishViewModel.getSearchCookDish(newText).observe(getViewLifecycleOwner(), new Observer<List<CookDish>>() {
                    @Override
                    public void onChanged(List<CookDish> cookDishes) {
                        if (mCookDish.size() > 0) {
                            mCookDish.clear();
                        }
                        if (mCookDish != null) {
                            mCookDish.addAll(cookDishes);
                        }
                        if (cookDishes.size() == 0) {
                            mCookDishRecyclerView.setVisibility(View.GONE);
                            mCookDishRecyclerViewTextView.setVisibility(View.VISIBLE);
                        } else {
                            mCookDishRecyclerView.setVisibility(View.VISIBLE);
                            mCookDishRecyclerViewTextView.setVisibility(View.GONE);
                        }
                        mCookDishRecycleViewAdapter.notifyDataSetChanged();
                    }
                });
                return false;
            }
        });

        mCookDishViewModel.getCookDish().observe(getViewLifecycleOwner(), new Observer<List<CookDish>>() {
            @Override
            public void onChanged(List<CookDish> cookDishes) {
                if (mCookDish.size() > 0) {
                    mCookDish.clear();
                }
                if (mCookDish != null) {
                    mCookDish.addAll(cookDishes);
                }
                if (cookDishes.size() == 0) {
                    mCookDishRecyclerView.setVisibility(View.GONE);
                    mCookDishRecyclerViewTextView.setVisibility(View.VISIBLE);
                } else {
                    mCookDishRecyclerView.setVisibility(View.VISIBLE);
                    mCookDishRecyclerViewTextView.setVisibility(View.GONE);
                }
                mCookDishRecycleViewAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }


    private void initCookDishRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mCookDishRecyclerView.setLayoutManager(linearLayoutManager);
        mCookDishRecycleViewAdapter = new CookDishRecycleViewAdapter(mCookDish,this);
        VerticalSpacingItemDecorator verticalSpacingItemDecorator = new VerticalSpacingItemDecorator(8);
        mCookDishRecyclerView.addItemDecoration(verticalSpacingItemDecorator);
        new ItemTouchHelper(dishesSimpleCallback).attachToRecyclerView(mCookDishRecyclerView);
        mCookDishRecyclerView.setAdapter(mCookDishRecycleViewAdapter);
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
                    CookDish deleteCookDish = mCookDish.get(viewHolder.getAbsoluteAdapterPosition());
                    mCookDishViewModel.deleteCookDish(mCookDish.get(viewHolder.getAbsoluteAdapterPosition()));
                    Snackbar.make(mRelativeLayout, deleteCookDish.getName() + " is Deleted.", Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mCookDishViewModel.insertCookDish(deleteCookDish);
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
    public void OnCookDishClick(int position) {
        final boolean[] update = {true};
        mRecipeViewModel.getIdRecipe(mCookDish.get(position).getRecipe_id()).observe(getViewLifecycleOwner(), new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                if(update[0]) {
                    if (recipe != null) {
                        Intent intent = new Intent(getContext(), PrepareRecipeActivity.class);
                        intent.putExtra("Recipe", recipe);
                        intent.putExtra("Quantity", mCookDish.get(position).getServing());
                        intent.putExtra("dishId",mCookDish.get(position).getCook_dish_id());
                        startActivity(intent);
                        update[0] = false;
                    }
                }
            }
        });
    }
}


