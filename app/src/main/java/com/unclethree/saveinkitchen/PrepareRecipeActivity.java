package com.unclethree.saveinkitchen;

import adapters.PrepareRecipeIngredientRecycleViewAdapter;
import adapters.RecipeIngredientRecycleViewAdapter;
import android.content.Intent;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import formatters.NumberFormatter;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import models.*;
import util.VerticalSpacingItemDecorator;
import viewmodels.*;

import java.util.ArrayList;
import java.util.List;

public class PrepareRecipeActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "PrepareRecipeActivity";

    //UI
    private RecyclerView mIngredientRecyclerView;

    private TextView mNameTextView;
    private TextView mServingsTextView;
    private TextView mNoteTextView;
    private TextView mMethodsTextView;

    private ImageView mBackArrow;
    private ExtendedFloatingActionButton mDone;

    //Var
    private Recipe mRecipe;
    private Double mQuantity;
    private int mDishId;
    private RecipeFoodViewModel mRecipeFoodViewModel;
    private CookDishIngredientViewModel mCookDishIngredientViewModel;
    private DishesViewModel mDishesViewModel;
    private CookDishViewModel mCookDishViewModel;
    private FoodViewModel mFoodViewModel;
    private LifecycleOwner mLifecycleOwner = this;
    private PrepareRecipeIngredientRecycleViewAdapter mPrepareRecipeIngredientRecycleViewAdapter;
    private ArrayList<RecipeFood> mRecipeFood = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare_recipe);

        mRecipeFoodViewModel = new ViewModelProvider(this).get(RecipeFoodViewModel.class);
        mCookDishIngredientViewModel = new ViewModelProvider(this).get(CookDishIngredientViewModel.class);
        mDishesViewModel = new ViewModelProvider(this).get(DishesViewModel.class);
        mCookDishViewModel = new ViewModelProvider(this).get(CookDishViewModel.class);
        mFoodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);

        mNameTextView = findViewById(R.id.prepare_recipe_name_text);
        mServingsTextView = findViewById(R.id.prepare_recipe_servings_text);
        mNoteTextView = findViewById(R.id.prepare_recipe_note_text);
        mMethodsTextView = findViewById(R.id.prepare_recipe_methods_text);
        mBackArrow = findViewById(R.id.prepare_recipe_arrow);
        mIngredientRecyclerView = findViewById(R.id.prepare_recipe_ingredients_recycle_view);
        mDone = findViewById(R.id.prepare_recipe_icon);

        mBackArrow.setOnClickListener(this);
        mDone.setOnClickListener(this);

        Intent intent = getIntent();
        mRecipe = intent.getParcelableExtra("Recipe");
        mQuantity = intent.getDoubleExtra("Quantity", 0);
        mDishId = intent.getIntExtra("dishId",0);
        mNameTextView.setText(mRecipe.getName());
        mServingsTextView.setText(NumberFormatter.quantityFormatter(mQuantity) + " Servings");
        mNoteTextView.setText(mRecipe.getNote());
        mMethodsTextView.setText(mRecipe.getMethods());

        initRecipeFoodRecycleView();

        mRecipeFoodViewModel.getSpecificRecipeFood(mRecipe.getRecipe_id()).observe(this, new Observer<List<RecipeFood>>() {
            @Override
            public void onChanged(List<RecipeFood> recipeFoods) {
                if (mRecipeFood.size() > 0) {
                    mRecipeFood.clear();
                }
                if (mRecipeFood != null) {
                    mRecipeFood.addAll(recipeFoods);
                    mPrepareRecipeIngredientRecycleViewAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    private void initRecipeFoodRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mIngredientRecyclerView.setLayoutManager(linearLayoutManager);
        mPrepareRecipeIngredientRecycleViewAdapter = new PrepareRecipeIngredientRecycleViewAdapter(mRecipeFood,this,this,this,mDishId, mQuantity,mRecipe.getServings());
        VerticalSpacingItemDecorator verticalSpacingItemDecorator = new VerticalSpacingItemDecorator(8);
        mIngredientRecyclerView.addItemDecoration(verticalSpacingItemDecorator);
        mIngredientRecyclerView.setAdapter(mPrepareRecipeIngredientRecycleViewAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.prepare_recipe_arrow:
                finish();
                break;
            case R.id.prepare_recipe_icon:
                finishCook();
                break;
        }
    }

    private void finishCook(){
        final boolean[] update = {true};
        mCookDishIngredientViewModel.getCostOfDish(mDishId).observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                if(update[0]) {
                    Dishes dishes = new Dishes();
                    dishes.setName(mRecipe.getName());
                    dishes.setServings(mQuantity);
                    if (aDouble != null) {
                        dishes.setCost(aDouble);
                        dishes.setCostPerServing(aDouble / mQuantity);
                        mDishesViewModel.insertDishes(dishes);
                    } else {
                        dishes.setCost(0d);
                        dishes.setCostPerServing(0);
                        mDishesViewModel.insertDishes(dishes);
                    }
                    update[0] = false;
                }
            }
        });

        mCookDishIngredientViewModel.getSpecificCookDishIngByDish(mDishId).observe(this, new Observer<List<CookDishIngredient>>() {
            @Override
            public void onChanged(List<CookDishIngredient> cookDishIngredients) {
                for (int i = 0; i < cookDishIngredients.size(); i++) {
                    int finalI = i;
                    final boolean[] update = {true};
                    mFoodViewModel.getSpecificFoodById(cookDishIngredients.get(i).getFood_id()).observe(mLifecycleOwner, new Observer<Food>() {
                        @Override
                        public void onChanged(Food food) {
                            if(update[0]) {
                                if(food.getQuantity() != cookDishIngredients.get(finalI).getQuantity()) {
                                    food.setQuantity(food.getQuantity() - cookDishIngredients.get(finalI).getQuantity());
                                    mFoodViewModel.updateFood(food);
                                }else {
                                    mFoodViewModel.deleteFood(food);
                                }
                                update[0] = false;
                            }
                        }
                    });
                }
            }
        });

        mCookDishViewModel.getCookDishesById(mDishId).observe(this, new Observer<CookDish>() {
            @Override
            public void onChanged(CookDish cookDish) {
                if(cookDish != null) {
                    mCookDishViewModel.deleteCookDish(cookDish);
                }
            }
        });
        finish();
    }
}