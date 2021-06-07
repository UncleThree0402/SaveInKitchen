package fragments;

import adapters.BuyHistoryRecycleViewAdapter;
import adapters.RecipeListRecycleAdapter;
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
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import com.unclethree.saveinkitchen.R;
import dialogs.AddRecipeDialog;
import dialogs.AddStockDialog;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import models.BuyHistory;
import models.Recipe;
import util.VerticalSpacingItemDecorator;
import viewmodels.RecipeViewModel;

import java.util.ArrayList;
import java.util.List;

public class RecipePageFragment extends Fragment implements View.OnClickListener{

    //Ui
    private RelativeLayout mRelativeLayout;
    private RecyclerView mRecipeRecyclerView;
    private TextView mRecipeRecycleViewText;
    private SearchView mSearchView;
    private ImageView mAddRecipeButton;

    //Var
    private ArrayList<Recipe> mRecipe = new ArrayList<>();
    private RecipeListRecycleAdapter mRecipeRecyclerAdapter;
    private RecipeViewModel mRecipeViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_page_fragment,container,false);
        mRelativeLayout = view.findViewById(R.id.recipe_page_rl);
        mRecipeRecyclerView = view.findViewById(R.id.recipe_list_recycle_view);
        mRecipeRecycleViewText = view.findViewById(R.id.recipe_list_recycle_view_text);
        mSearchView = view.findViewById(R.id.recipe_search_view);
        mAddRecipeButton = view.findViewById(R.id.add_recipe_list_icon);

        mAddRecipeButton.setOnClickListener(this);

        mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        initRecipeListRecycleView();

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mRecipeViewModel.getSearchRecipe(newText).observe(getViewLifecycleOwner(), new Observer<List<Recipe>>() {
                    @Override
                    public void onChanged(List<Recipe> recipes) {
                        if (mRecipe.size() > 0) {
                            mRecipe.clear();
                        }
                        if (mRecipe != null) {
                            mRecipe.addAll(recipes);
                        }
                        if (recipes.size() == 0) {
                            mRecipeRecyclerView.setVisibility(View.GONE);
                            mRecipeRecycleViewText.setVisibility(View.VISIBLE);
                        } else {
                            mRecipeRecyclerView.setVisibility(View.VISIBLE);
                            mRecipeRecycleViewText.setVisibility(View.GONE);
                        }
                        mRecipeRecyclerAdapter.notifyDataSetChanged();
                    }
                });
                return false;
            }
        });

        mRecipeViewModel.getRecipe().observe(getViewLifecycleOwner(), new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                if (mRecipe.size() > 0) {
                    mRecipe.clear();
                }
                if (mRecipe != null) {
                    mRecipe.addAll(recipes);
                }
                if (recipes.size() == 0) {
                    mRecipeRecyclerView.setVisibility(View.GONE);
                    mRecipeRecycleViewText.setVisibility(View.VISIBLE);
                } else {
                    mRecipeRecyclerView.setVisibility(View.VISIBLE);
                    mRecipeRecycleViewText.setVisibility(View.GONE);
                }
                mRecipeRecyclerAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    private void initRecipeListRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecipeRecyclerView.setLayoutManager(linearLayoutManager);
        mRecipeRecyclerAdapter = new RecipeListRecycleAdapter(mRecipe);
        VerticalSpacingItemDecorator verticalSpacingItemDecorator = new VerticalSpacingItemDecorator(8);
        mRecipeRecyclerView.addItemDecoration(verticalSpacingItemDecorator);
        new ItemTouchHelper(recipeSimpleCallback).attachToRecyclerView(mRecipeRecyclerView);
        mRecipeRecyclerView.setAdapter(mRecipeRecyclerAdapter);
    }

    private ItemTouchHelper.SimpleCallback recipeSimpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            switch (direction) {
                case ItemTouchHelper.RIGHT:
                    Recipe deleteRecipe = mRecipe.get(viewHolder.getAbsoluteAdapterPosition());
                    mRecipeViewModel.deleteRecipe(mRecipe.get(viewHolder.getAbsoluteAdapterPosition()));
                    Snackbar.make(mRelativeLayout, deleteRecipe.getName() + " is Deleted.", Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mRecipeViewModel.insertRecipe(deleteRecipe);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_recipe_list_icon:
                AddRecipeDialog addRecipeDialog = new AddRecipeDialog();
                addRecipeDialog.show(getChildFragmentManager(), "Add recipe dialog");
                break;
        }
    }
}
