package dialogs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.textfield.TextInputLayout;
import com.unclethree.saveinkitchen.R;
import formatters.NumberFormatter;
import models.CookDishIngredient;
import models.DiaryHistory;
import models.Food;
import viewmodels.CookDishIngredientViewModel;
import viewmodels.DiaryHistoryViewModel;
import viewmodels.FoodViewModel;

public class PrepareRecipeAddIngredientDialog extends DialogFragment {
    private static final String TAG = "PrepareRecipeAddIngredi";

    //Ui
    private TextView mNameTextView;
    private TextView mStatusTextView;
    private TextView mUnplannedQuantityTextView;
    private TextView mPlannedQuantityTextView;

    private TextInputLayout mQuantityInputLayout;

    private Button mConfirmButton;

    //Var
    private CookDishIngredientViewModel mCookDishIngredientViewModel;
    private Food mFood;
    private int mDishId;
    private int mRecipeId;

    public PrepareRecipeAddIngredientDialog(Food mFood, int mDishId, int mRecipeId) {
        this.mFood = mFood;
        this.mDishId = mDishId;
        this.mRecipeId = mRecipeId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.prepare_recipe_add_ingredient_dialog, container, false);

        mCookDishIngredientViewModel = new ViewModelProvider(this).get(CookDishIngredientViewModel.class);

        mNameTextView = view.findViewById(R.id.prepare_recipe_add_ingredient_dialog_name);
        mStatusTextView = view.findViewById(R.id.prepare_recipe_add_ingredient_dialog_status);
        mUnplannedQuantityTextView = view.findViewById(R.id.prepare_recipe_add_ingredient_dialog_quantity_unplanned);
        mPlannedQuantityTextView = view.findViewById(R.id.prepare_recipe_add_ingredient_dialog_quantity_planned);

        mQuantityInputLayout = view.findViewById(R.id.prepare_recipe_add_ingredient_dialog_quantity_input_layout);

        mConfirmButton = view.findViewById(R.id.prepare_recipe_add_ingredient_dialog_confirm_button);

        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInput();
            }
        });

        setText();

        return view;
    }

    private void checkInput(){
        mCookDishIngredientViewModel.getSumOfSpecificCookDishIngredient(mFood.getFood_id()).observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                if (aDouble!=null) {
                    String quantity = mQuantityInputLayout.getEditText().getText().toString();
                    if (quantity.isEmpty()) {
                        mQuantityInputLayout.setError("Field can't be empty");
                    } else if (Double.parseDouble(quantity) > aDouble) {
                        mQuantityInputLayout.setError("Quantity can't greater than you planned to use");
                    } else {
                        insertAndFinish(quantity);
                    }
                }else{
                    String quantity = mQuantityInputLayout.getEditText().getText().toString();
                    if (quantity.isEmpty()) {
                        mQuantityInputLayout.setError("Field can't be empty");
                    } else if (Double.parseDouble(quantity) > mFood.getQuantity()) {
                        mQuantityInputLayout.setError("Quantity can't greater than you have");
                    } else {
                        insertAndFinish(quantity);
                    }
                }
            }
        });
    }

    private void insertAndFinish(String quantity){
        CookDishIngredient cookDishIngredient = new CookDishIngredient();
        cookDishIngredient.setCook_dish_id(mDishId);
        cookDishIngredient.setRecipe_food_id(mRecipeId);
        cookDishIngredient.setFood_id(mFood.getFood_id());
        cookDishIngredient.setQuantity(Double.parseDouble(quantity));
        cookDishIngredient.setCost(mFood.getCostPerUnit() * Double.parseDouble(quantity));
        mCookDishIngredientViewModel.insertCookDishIngredient(cookDishIngredient);
        getActivity().finish();
        dismiss();
    }

    private void setText(){
        mNameTextView.setText(mFood.getName());
        mStatusTextView.setText(mFood.getStatus());
        String unplannedQuantity = "Unplanned : " + NumberFormatter.quantityFormatter(mFood.getQuantity()) + " " + mFood.getUnit();
        mUnplannedQuantityTextView.setText(unplannedQuantity);
        mCookDishIngredientViewModel.getSumOfSpecificCookDishIngredient(mFood.getFood_id()).observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                if(aDouble != null) {
                    String plannedQuantity = "Planned : " + NumberFormatter.quantityFormatter(mFood.getQuantity() - aDouble) + " " + mFood.getUnit();
                    mPlannedQuantityTextView.setText(plannedQuantity);
                }else {
                    String plannedQuantity = "Planned : " + NumberFormatter.quantityFormatter(mFood.getQuantity()) + " " + mFood.getUnit();
                    mPlannedQuantityTextView.setText(plannedQuantity);
                }
            }
        });
        mQuantityInputLayout.setSuffixText(mFood.getUnit());
    }
}
