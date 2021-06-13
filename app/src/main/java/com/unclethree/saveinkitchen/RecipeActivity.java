package com.unclethree.saveinkitchen;

import adapters.RecipeIngredientRecycleViewAdapter;
import android.content.Intent;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import dialogs.RecipeIngredientDialog;
import formatters.NumberFormatter;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import models.Recipe;
import models.RecipeFood;
import util.VerticalSpacingItemDecorator;
import viewmodels.RecipeFoodViewModel;
import viewmodels.RecipeViewModel;

import java.util.ArrayList;
import java.util.List;


public class RecipeActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "RecipeActivity";

    //UI
    private RelativeLayout mRelativeLayout;

    private TextInputLayout mNameTextInputLayout;
    private TextInputLayout mServingsTextInputLayout;
    private TextInputLayout mNotesTextInputLayout;
    private TextInputLayout mMethodsTextInputLayout;

    private ImageView mCheck;
    private ImageView mEdit;
    private ImageView mBack;
    private ImageView mAddRecipeFood;

    private RecyclerView mIngredientsRecycleView;

    //Var
    private String mName;
    private RecipeViewModel mRecipeViewModel;
    private RecipeFoodViewModel mRecipeFoodViewModel;
    private boolean isEditMode;
    private final ArrayList<RecipeFood> mRecipeFood = new ArrayList<>();
    private RecipeIngredientRecycleViewAdapter mRecipeIngredientRecycleViewAdapter;
    private final LifecycleOwner mLifecycleOwner = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        mRelativeLayout = findViewById(R.id.recipe_rl);
        mNameTextInputLayout = findViewById(R.id.recipe_name_edit_text);
        mServingsTextInputLayout = findViewById(R.id.recipe_serving_edit_text);
        mNotesTextInputLayout = findViewById(R.id.recipe_note_edit_text);
        mMethodsTextInputLayout = findViewById(R.id.recipe_methods_edit_text);

        mCheck = findViewById(R.id.add_recipe_check);
        mEdit = findViewById(R.id.add_recipe_edit);
        mBack = findViewById(R.id.add_recipe_arrow);
        mAddRecipeFood = findViewById(R.id.add_recipe_add_food_icon);

        mIngredientsRecycleView = findViewById(R.id.add_recipe_ingredients_recycle_view);

        Intent intent = getIntent();
        mName = intent.getStringExtra("Recipe Name");
        isEditMode = intent.getBooleanExtra("Mode", false);
        if (isEditMode) {
            editMode();

        } else {
            viewMode();
        }

        mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        mRecipeFoodViewModel = new ViewModelProvider(this).get(RecipeFoodViewModel.class);

        setText(mName);

        mCheck.setOnClickListener(this);
        mEdit.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mAddRecipeFood.setOnClickListener(this);

        initRecipeFoodRecycleView();

        mRecipeViewModel.getSpecificRecipe(mName).observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                if(recipe != null) {
                    mRecipeFoodViewModel.getSpecificRecipeFood(recipe.getRecipe_id()).observe(mLifecycleOwner, new Observer<List<RecipeFood>>() {
                        @Override
                        public void onChanged(List<RecipeFood> recipeFoods) {
                            if (mRecipeFood.size() > 0) {
                                mRecipeFood.clear();
                            }
                            if (mRecipeFood != null) {
                                mRecipeFood.addAll(recipeFoods);
                                mRecipeIngredientRecycleViewAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }
        });

    }

    private void initRecipeFoodRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mIngredientsRecycleView.setLayoutManager(linearLayoutManager);
        mRecipeIngredientRecycleViewAdapter = new RecipeIngredientRecycleViewAdapter(mRecipeFood);
        VerticalSpacingItemDecorator verticalSpacingItemDecorator = new VerticalSpacingItemDecorator(8);
        mIngredientsRecycleView.addItemDecoration(verticalSpacingItemDecorator);
        new ItemTouchHelper(recipeFoodSimpleCallback).attachToRecyclerView(mIngredientsRecycleView);
        mIngredientsRecycleView.setAdapter(mRecipeIngredientRecycleViewAdapter);
    }

    private final ItemTouchHelper.SimpleCallback recipeFoodSimpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            switch (direction) {
                case ItemTouchHelper.RIGHT:
                    RecipeFood deleteRecipeFood = mRecipeFood.get(viewHolder.getAbsoluteAdapterPosition());
                    mRecipeFoodViewModel.deleteRecipeFood(mRecipeFood.get(viewHolder.getAbsoluteAdapterPosition()));
                    Snackbar.make(mRelativeLayout, deleteRecipeFood.getName() + " is Deleted.", Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mRecipeFoodViewModel.insertRecipeFood(deleteRecipeFood);
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

    private void editMode() {
        editTextEnable();

        mCheck.setVisibility(View.VISIBLE);
        mEdit.setVisibility(View.GONE);
    }

    private void viewMode() {
        editTextDisable();

        mCheck.setVisibility(View.GONE);
        mEdit.setVisibility(View.VISIBLE);
    }

    private void editTextDisable() {
        mNameTextInputLayout.getEditText().setKeyListener(null);
        mNameTextInputLayout.getEditText().setFocusable(false);
        mNameTextInputLayout.getEditText().setFocusableInTouchMode(false);
        mNameTextInputLayout.getEditText().setCursorVisible(false);
        mNameTextInputLayout.getEditText().clearFocus();

        mServingsTextInputLayout.getEditText().setKeyListener(null);
        mServingsTextInputLayout.getEditText().setFocusable(false);
        mServingsTextInputLayout.getEditText().setFocusableInTouchMode(false);
        mServingsTextInputLayout.getEditText().setCursorVisible(false);
        mServingsTextInputLayout.getEditText().clearFocus();

        mNotesTextInputLayout.getEditText().setKeyListener(null);
        mNotesTextInputLayout.getEditText().setFocusable(false);
        mNotesTextInputLayout.getEditText().setFocusableInTouchMode(false);
        mNotesTextInputLayout.getEditText().setCursorVisible(false);
        mNotesTextInputLayout.getEditText().clearFocus();

        mMethodsTextInputLayout.getEditText().setKeyListener(null);
        mMethodsTextInputLayout.getEditText().setFocusable(false);
        mMethodsTextInputLayout.getEditText().setFocusableInTouchMode(false);
        mMethodsTextInputLayout.getEditText().setCursorVisible(false);
        mMethodsTextInputLayout.getEditText().clearFocus();

    }

    private void editTextEnable() {
        mNameTextInputLayout.getEditText().setKeyListener(new EditText(this).getKeyListener());
        mNameTextInputLayout.getEditText().setFocusable(true);
        mNameTextInputLayout.getEditText().setFocusableInTouchMode(true);
        mNameTextInputLayout.getEditText().setCursorVisible(true);
        mNameTextInputLayout.getEditText().clearFocus();

        mServingsTextInputLayout.getEditText().setKeyListener(new EditText(this).getKeyListener());
        mServingsTextInputLayout.getEditText().setFocusable(true);
        mServingsTextInputLayout.getEditText().setFocusableInTouchMode(true);
        mServingsTextInputLayout.getEditText().setCursorVisible(true);
        mServingsTextInputLayout.getEditText().clearFocus();

        mNotesTextInputLayout.getEditText().setKeyListener(new EditText(this).getKeyListener());
        mNotesTextInputLayout.getEditText().setFocusable(true);
        mNotesTextInputLayout.getEditText().setFocusableInTouchMode(true);
        mNotesTextInputLayout.getEditText().setCursorVisible(true);
        mNotesTextInputLayout.getEditText().clearFocus();

        mMethodsTextInputLayout.getEditText().setKeyListener(new EditText(this).getKeyListener());
        mMethodsTextInputLayout.getEditText().setFocusable(true);
        mMethodsTextInputLayout.getEditText().setFocusableInTouchMode(true);
        mMethodsTextInputLayout.getEditText().setCursorVisible(true);
        mMethodsTextInputLayout.getEditText().clearFocus();

    }

    private void update() {
        mRecipeViewModel.getSpecificRecipe(mName).observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                if (recipe != null) {
                    recipe.setName(mNameTextInputLayout.getEditText().getText().toString());
                    recipe.setServings(Integer.parseInt(mServingsTextInputLayout.getEditText().getText().toString()));
                    recipe.setNote(mNotesTextInputLayout.getEditText().getText().toString());
                    recipe.setMethods(mMethodsTextInputLayout.getEditText().getText().toString());
                    mRecipeViewModel.updateRecipe(recipe);
                    mRecipeViewModel.getSpecificRecipe(mNameTextInputLayout.getEditText().getText().toString()).removeObserver(this);
                }
            }
        });
    }

    private void setText(String name) {

        mRecipeViewModel.getSpecificRecipe(name).observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                if (recipe != null) {
                    mNameTextInputLayout.getEditText().setText(recipe.getName());
                    mServingsTextInputLayout.getEditText().setText(NumberFormatter.quantityFormatter(recipe.getServings()));
                    mNotesTextInputLayout.getEditText().setText(recipe.getNote());
                    mMethodsTextInputLayout.getEditText().setText(recipe.getMethods());

                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_recipe_check:
                viewMode();
                isEditMode = false;
                break;
            case R.id.add_recipe_edit:
                editMode();
                isEditMode = true;
                break;
            case R.id.add_recipe_arrow:
                endActivity();
                break;

            case R.id.add_recipe_add_food_icon:
                addRecipeFood();
                break;
        }
    }

    private void addRecipeFood(){
        RecipeIngredientDialog recipeIngredientDialog = new RecipeIngredientDialog(mName);
        recipeIngredientDialog.show(getSupportFragmentManager(), "Add recipe food dialog");
    }

    private void endActivity() {
        update();
        finish();
    }

    @Override
    public void onBackPressed() {
        endActivity();
    }
}