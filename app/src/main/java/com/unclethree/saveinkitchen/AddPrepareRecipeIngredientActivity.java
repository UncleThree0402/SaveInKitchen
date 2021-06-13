package com.unclethree.saveinkitchen;

import adapters.BuyHistoryRecycleViewAdapter;
import adapters.PrepareRecipeAddIngredientRecycleViewAdapter;
import android.content.Intent;
import android.graphics.Canvas;
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
import com.google.android.material.snackbar.Snackbar;
import dialogs.PrepareRecipeAddIngredientDialog;
import dialogs.StockDialog;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import models.BuyHistory;
import models.Food;
import util.VerticalSpacingItemDecorator;
import viewmodels.BuyHistoryViewModel;
import viewmodels.FoodViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddPrepareRecipeIngredientActivity extends AppCompatActivity implements PrepareRecipeAddIngredientRecycleViewAdapter.OnIngredientClickListener {

    //UI
    private RecyclerView mIngredientRecyclerView;
    private TextView mIngredientRecyclerViewTextView;
    private SearchView mSearchView;

    //Var
    private PrepareRecipeAddIngredientRecycleViewAdapter mPrepareRecipeAddIngredientRecycleViewAdapter;
    private ArrayList<Food> mFood = new ArrayList<>();
    private FoodViewModel mFoodViewModel;
    private LifecycleOwner mLifecycleOwner = this;
    private int mDishId;
    private int mRecipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prepare_recipe_ingredient);

        mFoodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);

        Intent intent = getIntent();
        mDishId = intent.getIntExtra("DishId",0 );
        mRecipeId = intent.getIntExtra("RecipeId", 0);

        mIngredientRecyclerView = findViewById(R.id.add_prepare_recipe_ingredient_recycle_view);
        mIngredientRecyclerViewTextView = findViewById(R.id.add_prepare_recipe_ingredient_recycle_view_text);
        mSearchView = findViewById(R.id.add_prepare_recipe_ingredient_search_view);

        initIngredientListRecycleView();

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mFoodViewModel.getSearchFood(newText).observe(mLifecycleOwner, new Observer<List<Food>>() {
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
                        mPrepareRecipeAddIngredientRecycleViewAdapter.notifyDataSetChanged();
                    }
                });
                return false;
            }
        });

        mFoodViewModel.getFood().observe(mLifecycleOwner, new Observer<List<Food>>() {
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
                mPrepareRecipeAddIngredientRecycleViewAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initIngredientListRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mIngredientRecyclerView.setLayoutManager(linearLayoutManager);
        mPrepareRecipeAddIngredientRecycleViewAdapter = new PrepareRecipeAddIngredientRecycleViewAdapter(mFood,this);
        VerticalSpacingItemDecorator verticalSpacingItemDecorator = new VerticalSpacingItemDecorator(8);
        mIngredientRecyclerView.addItemDecoration(verticalSpacingItemDecorator);
        mIngredientRecyclerView.setAdapter(mPrepareRecipeAddIngredientRecycleViewAdapter);
    }


    @Override
    public void OnIngredientClick(int position) {
        PrepareRecipeAddIngredientDialog prepareRecipeAddIngredientDialog = new PrepareRecipeAddIngredientDialog(mFood.get(position),mDishId,mRecipeId);
        prepareRecipeAddIngredientDialog.show(getSupportFragmentManager(), "prepareRecipeAddIngredientDialog");
    }
}