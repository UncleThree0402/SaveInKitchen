package adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import com.unclethree.saveinkitchen.AddPrepareRecipeIngredientActivity;
import com.unclethree.saveinkitchen.R;
import com.unclethree.saveinkitchen.RecipeActivity;
import formatters.NumberFormatter;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import models.*;
import util.VerticalSpacingItemDecorator;
import viewmodels.BuyFoodViewModel;
import viewmodels.CookDishIngredientViewModel;
import viewmodels.CookDishIngredientViewViewModel;

import java.util.ArrayList;
import java.util.List;

public class PrepareRecipeIngredientRecycleViewAdapter extends RecyclerView.Adapter<PrepareRecipeIngredientRecycleViewAdapter.ViewHolder>{
    private static final String TAG = "PrepareRecipeIngredient";

    private List<RecipeFood> mRecipeFood = new ArrayList<>();
    private ViewModelStoreOwner mViewModelStoreOwner;
    private LifecycleOwner mLifecycleOwner;
    private Context mContext;
    private int mDishId;
    private double mQuantity;
    private double mServing;

    public PrepareRecipeIngredientRecycleViewAdapter(List<RecipeFood> mRecipeFood, ViewModelStoreOwner mViewModelStoreOwner, LifecycleOwner mLifecycleOwner, Context mContext, int mDishId, double mQuantity, double mServing) {
        this.mRecipeFood = mRecipeFood;
        this.mViewModelStoreOwner = mViewModelStoreOwner;
        this.mLifecycleOwner = mLifecycleOwner;
        this.mContext = mContext;
        this.mDishId = mDishId;
        this.mQuantity = mQuantity;
        this.mServing = mServing;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prepare_recipe_ingerdients_recycle_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mFoodNameTextView.setText(mRecipeFood.get(position).getName());
        holder.mStatusTextView.setText(mRecipeFood.get(position).getStatus());
        String quantity = NumberFormatter.quantityFormatter(mRecipeFood.get(position).getQuantity() * (mQuantity / mServing)) + " " + mRecipeFood.get(position).getUnit();
        holder.mQuantityTextView.setText(quantity);
        holder.mDescriptionTextView.setText(mRecipeFood.get(position).getDescription());

        holder.mAddBuyListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuyFood buyFood = new BuyFood();
                buyFood.setFood_type_id(mRecipeFood.get(position).getFood_type_id());
                buyFood.setName(mRecipeFood.get(position).getName());
                buyFood.setStatus(mRecipeFood.get(position).getStatus());
                buyFood.setQuantity(mRecipeFood.get(position).getQuantity());
                buyFood.setUnit(mRecipeFood.get(position).getUnit());
                holder.mBuyFoodViewModel.insertBuyFood(buyFood);
            }
        });

        holder.mAddItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddPrepareRecipeIngredientActivity.class);
                intent.putExtra("DishId",mDishId);
                intent.putExtra("RecipeId",mRecipeFood.get(position).getRecipe_id());
                mContext.startActivity(intent);
            }
        });

        holder.mCookDishIngredientViewModel.getSpecificCookDishIngredient(mRecipeFood.get(position).getRecipe_id(),mDishId).observe(mLifecycleOwner, new Observer<List<CookDishIngredient>>() {
            @Override
            public void onChanged(List<CookDishIngredient> cookDishIngredients) {
                if(holder.mCookDishIngredientViews.size() > 0){
                    holder.mCookDishIngredientViews.clear();
                }
                if(holder.mCookDishIngredientViews != null){
                    for (int i = 0; i < cookDishIngredients.size(); i++) {
                        holder.mCookDishIngredientViewViewModel.getCookDishIngredientView(cookDishIngredients.get(i).getCook_dish_id(),mRecipeFood.get(position).getRecipe_food_id(),cookDishIngredients.get(i).getFood_id()).observe(mLifecycleOwner, new Observer<List<CookDishIngredientView>>() {
                            @Override
                            public void onChanged(List<CookDishIngredientView> cookDishIngredientViews) {
                                holder.mCookDishIngredientViews.addAll(cookDishIngredientViews);
                                holder.mPrepareRecipeIngredientAddedRecycleViewAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }
        });

        holder.initRecipeListRecycleView();


    }

    @Override
    public int getItemCount() {
        return mRecipeFood.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView mFoodNameTextView;
        private final TextView mStatusTextView;
        private final TextView mQuantityTextView;
        private final TextView mDescriptionTextView;
        private RecyclerView mIngredientsAddRecycleView;
        private Button mAddItemButton;
        private Button mAddBuyListButton;

        private BuyFoodViewModel mBuyFoodViewModel;
        private CookDishIngredientViewViewModel mCookDishIngredientViewViewModel;
        private PrepareRecipeIngredientAddedRecycleViewAdapter mPrepareRecipeIngredientAddedRecycleViewAdapter;
        private CookDishIngredientViewModel mCookDishIngredientViewModel;
        private ArrayList<CookDishIngredientView> mCookDishIngredientViews = new ArrayList<>();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mFoodNameTextView = itemView.findViewById(R.id.prepare_recipe_ingredients_recycle_view_type);
            mStatusTextView = itemView.findViewById(R.id.prepare_recipe_ingredients_recycle_view_status);
            mQuantityTextView = itemView.findViewById(R.id.prepare_recipe_ingredients_recycle_view_quantity);
            mDescriptionTextView = itemView.findViewById(R.id.prepare_recipe_ingredients_recycle_view_description);
            mIngredientsAddRecycleView = itemView.findViewById(R.id.prepare_recipe_ingredients_use_recycle_view);
            mAddItemButton = itemView.findViewById(R.id.prepare_recipe_ingredients_add_button);
            mAddBuyListButton = itemView.findViewById(R.id.prepare_recipe_ingredients_add_button_buy_list);
            mBuyFoodViewModel = new ViewModelProvider(mViewModelStoreOwner).get(BuyFoodViewModel.class);
            mCookDishIngredientViewModel = new ViewModelProvider(mViewModelStoreOwner).get(CookDishIngredientViewModel.class);
            mCookDishIngredientViewViewModel = new ViewModelProvider(mViewModelStoreOwner).get(CookDishIngredientViewViewModel.class);
        }

        private void initRecipeListRecycleView() {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            mIngredientsAddRecycleView.setLayoutManager(linearLayoutManager);
            mPrepareRecipeIngredientAddedRecycleViewAdapter = new PrepareRecipeIngredientAddedRecycleViewAdapter(mCookDishIngredientViews);
            VerticalSpacingItemDecorator verticalSpacingItemDecorator = new VerticalSpacingItemDecorator(8);
            new ItemTouchHelper(cookDishIngredientViewsSimpleCallback).attachToRecyclerView(mIngredientsAddRecycleView);
            mIngredientsAddRecycleView.addItemDecoration(verticalSpacingItemDecorator);
            mIngredientsAddRecycleView.setAdapter(mPrepareRecipeIngredientAddedRecycleViewAdapter);
        }

        private final ItemTouchHelper.SimpleCallback cookDishIngredientViewsSimpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                switch (direction) {
                    case ItemTouchHelper.RIGHT:
                        mCookDishIngredientViewModel.getSpecificCookDishIngredientById(mCookDishIngredientViews.get(viewHolder.getAbsoluteAdapterPosition()).getCook_dish_ingredient_id()).observe(mLifecycleOwner, new Observer<CookDishIngredient>() {
                            @Override
                            public void onChanged(CookDishIngredient cookDishIngredient) {
                                if(cookDishIngredient!= null) {
                                    mCookDishIngredientViewModel.deleteCookDishIngredient(cookDishIngredient);
                                }
                            }
                        });
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



}
