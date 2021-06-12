package com.unclethree.saveinkitchen;

import adapters.PrepareRecipeRecycleAdapter;
import adapters.RecipeRecycleAdapter;
import android.content.Intent;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import dialogs.RecipeDialog;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import models.Recipe;
import util.VerticalSpacingItemDecorator;
import viewmodels.RecipeViewModel;

import java.util.ArrayList;
import java.util.List;

public class CookDishRecipeActivity extends AppCompatActivity implements View.OnClickListener , PrepareRecipeRecycleAdapter.OnPrepareRecipeClickListener {
    private static final String TAG = "CookDishRecipeActivity";
    //Ui
    private RelativeLayout mRelativeLayout;
    private RecyclerView mRecipeRecyclerView;
    private TextView mRecipeRecycleViewText;
    private SearchView mSearchView;
    private ExtendedFloatingActionButton mAddRecipeButton;

    //Var
    private ArrayList<Recipe> mRecipe = new ArrayList<>();
    private PrepareRecipeRecycleAdapter mRecipeRecyclerAdapter;
    private RecipeViewModel mRecipeViewModel;
    private LifecycleOwner mLifecycleOwner = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_dish_recipe);

        mRelativeLayout = findViewById(R.id.prepare_recipe_rl);
        mRecipeRecyclerView = findViewById(R.id.prepare_recipe_list_recycle_view);
        mRecipeRecycleViewText = findViewById(R.id.prepare_recipe_list_recycle_view_text);
        mSearchView = findViewById(R.id.prepare_recipe_search_view);
        mAddRecipeButton = findViewById(R.id.prepare_recipe_add_button);

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
                mRecipeViewModel.getSearchRecipe(newText).observe(mLifecycleOwner, new Observer<List<Recipe>>() {
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

        mRecipeViewModel.getRecipe().observe(mLifecycleOwner, new Observer<List<Recipe>>() {
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
    }

    private void initRecipeListRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecipeRecyclerView.setLayoutManager(linearLayoutManager);
        mRecipeRecyclerAdapter = new PrepareRecipeRecycleAdapter(mRecipe,this);
        VerticalSpacingItemDecorator verticalSpacingItemDecorator = new VerticalSpacingItemDecorator(8);
        mRecipeRecyclerView.addItemDecoration(verticalSpacingItemDecorator);
        new ItemTouchHelper(recipeSimpleCallback).attachToRecyclerView(mRecipeRecyclerView);
        mRecipeRecyclerView.setAdapter(mRecipeRecyclerAdapter);
    }

    private final ItemTouchHelper.SimpleCallback recipeSimpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
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

                case ItemTouchHelper.LEFT:
                    Intent intent = new Intent(getApplicationContext() , RecipeActivity.class);
                    intent.putExtra("Recipe Name",mRecipe.get(viewHolder.getAbsoluteAdapterPosition()).getName());
                    intent.putExtra("Mode",true);
                    startActivity(intent);
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
            case R.id.prepare_recipe_add_button:
                RecipeDialog recipeDialog = new RecipeDialog();
                recipeDialog.show(getSupportFragmentManager(), "Add recipe dialog");
                break;
        }
    }

    @Override
    public void OnPrepareRecipeClick(int position) {

    }
}