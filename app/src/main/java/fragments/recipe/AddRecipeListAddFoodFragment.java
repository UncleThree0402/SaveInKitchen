package fragments.recipe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.textfield.TextInputLayout;
import com.unclethree.saveinkitchen.R;
import models.RecipeFood;
import viewmodels.RecipeFoodViewModel;

public class AddRecipeListAddFoodFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "AddRecipeListAddFoodFra";

    //UI
    private TextInputLayout mFoodNameTextInputLayout;
    private TextInputLayout mStatusTextInputLayout;
    private AutoCompleteTextView mStatusTextEdit;
    private TextInputLayout mQuantityTextInputLayout;
    private TextInputLayout mUnitTextInputLayout;
    private TextInputLayout mDescriptionTextInputLayout;
    private Button mConfirmButton;

    //Var
    private String mName;
    private int mFoodTypeId;
    private int mRecipeId;
    private RecipeFood mRecipeFood;
    private final static String[] STATUS = new String[]{"Fresh", "Frozen", "Dry Goods", "Condiment"};
    private RecipeFoodViewModel mRecipeFoodViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_add_ingerdient_information_fragment, container, false);

        mFoodNameTextInputLayout = view.findViewById(R.id.add_recipe_food_name_input_layout);
        mStatusTextInputLayout = view.findViewById(R.id.add_recipe_food_status_input_layout);
        mStatusTextEdit = view.findViewById(R.id.add_recipe_food_status_input);
        mQuantityTextInputLayout = view.findViewById(R.id.add_recipe_food_quantity_input_layout);
        mUnitTextInputLayout = view.findViewById(R.id.add_recipe_food_unit_input_layout);
        mDescriptionTextInputLayout = view.findViewById(R.id.add_recipe_food_description_input_layout);
        mConfirmButton = view.findViewById(R.id.add_recipe_food_confirm_button);

        mConfirmButton.setOnClickListener(this);

        mRecipeFood = new RecipeFood();
        Bundle bundle = this.getArguments();
        mFoodTypeId = bundle.getInt("Food Type Id");
        mRecipeId = bundle.getInt("Recipe Id");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.list_item, STATUS);
        mStatusTextEdit.setAdapter(adapter);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecipeFoodViewModel = new ViewModelProvider(this).get(RecipeFoodViewModel.class);

    }

    private void checkInput() {
        if (!validFoodName() | !validStatus() | !validQuantity() | !validUnit() |!validDescription()) {
            return;
        } else {
            mRecipeFood.setRecipe_id(mRecipeId);
            mRecipeFood.setFood_type_id(mFoodTypeId);
            Log.d(TAG, "checkInput: " + mRecipeFood.getRecipe_food_id() + " " +mRecipeFood.getRecipe_id());
            mRecipeFoodViewModel.insertRecipeFood(mRecipeFood);
            ((DialogFragment) getParentFragment()).dismiss();
        }
    }

    private boolean validFoodName() {
        String brand = mFoodNameTextInputLayout.getEditText().getText().toString().trim();
        if (brand.isEmpty()) {
            mFoodNameTextInputLayout.setError("Field can't be empty");
            return false;
        } else {
            mFoodNameTextInputLayout.setError(null);
            mRecipeFood.setName(mFoodNameTextInputLayout.getEditText().getText().toString());
            return true;
        }
    }

    private boolean validStatus() {
        String brand = mStatusTextInputLayout.getEditText().getText().toString().trim();
        if (brand.isEmpty()) {
            mStatusTextInputLayout.setError("Field can't be empty");
            return false;
        } else {
            mStatusTextInputLayout.setError(null);
            mRecipeFood.setStatus(mStatusTextInputLayout.getEditText().getText().toString());
            return true;
        }
    }

    private boolean validQuantity() {
        String quantity = mQuantityTextInputLayout.getEditText().getText().toString().trim();
        if (quantity.isEmpty()) {
            mQuantityTextInputLayout.setError("Field can't be empty");
            return false;
        } else {
            mQuantityTextInputLayout.setError(null);
            mRecipeFood.setQuantity(Double.parseDouble(mQuantityTextInputLayout.getEditText().getText().toString()));
            return true;
        }
    }

    private boolean validUnit() {
        String brand = mUnitTextInputLayout.getEditText().getText().toString().trim();
        if (brand.isEmpty()) {
            mUnitTextInputLayout.setError("Field can't be empty");
            return false;
        } else {
            mUnitTextInputLayout.setError(null);
            mRecipeFood.setUnit(mUnitTextInputLayout.getEditText().getText().toString());
            return true;
        }
    }

    private boolean validDescription() {
        String description = mDescriptionTextInputLayout.getEditText().getText().toString().trim();
        if (description.isEmpty()) {
            mRecipeFood.setDescription(null);
            return false;
        } else {
            mRecipeFood.setDescription(mDescriptionTextInputLayout.getEditText().getText().toString());
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_recipe_food_confirm_button:
                checkInput();
                break;
        }
    }

}
