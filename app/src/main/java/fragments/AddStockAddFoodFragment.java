package fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.textfield.TextInputLayout;
import com.unclethree.saveinkitchen.R;
import models.Food;
import models.FoodType;

public class AddStockAddFoodFragment extends Fragment {

    //UI
    private TextInputLayout mFoodNameTextInputLayout;
    private TextInputLayout mStatusTextInputLayout;
    private TextInputLayout mQuantityTextInputLayout;
    private TextInputLayout mUnitTextInputLayout;
    private TextInputLayout mCostTextInputLayout;
    private Button mConfirmButton;

    //Var
    private FoodType mFoodType;
    private Food mFood;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_stock_add_food_info_fragment, container, false);
        mFood = new Food();
        Bundle bundle = this.getArguments();
        mFoodType = bundle.getParcelable("Food Type");
        mFood.setFood_type_id(mFoodType.getFood_type_id());
        return view;
    }

    private boolean validFoodName() {
        String brand = mFoodNameTextInputLayout.getEditText().getText().toString().trim();
        if (brand.isEmpty()) {
            mFoodNameTextInputLayout.setError("Field can't be empty");
            return false;
        } else {
            mFoodNameTextInputLayout.setError(null);
            mFood.setName(mFoodNameTextInputLayout.getEditText().getText().toString());
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
            mFood.setStatus(mStatusTextInputLayout.getEditText().getText().toString());
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
            mFood.setQuantity(Double.parseDouble(mQuantityTextInputLayout.getEditText().getText().toString()));
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
            mFood.setUnit(mUnitTextInputLayout.getEditText().getText().toString());
            return true;
        }
    }

    private boolean validCost() {
        String cost = mCostTextInputLayout.getEditText().getText().toString().trim();
        if (cost.isEmpty()) {
            mCostTextInputLayout.setError("Field can't be empty");
            return false;
        } else {
            mFood.setCost(Double.parseDouble(mCostTextInputLayout.getEditText().getText().toString()));
            return true;
        }
    }

    private void checkInput() {
        if (!validFoodName() | !validStatus() | !validQuantity() | !validUnit() | !validCost()) {
            return;
        } else {
            Bundle bundle = new Bundle();
            bundle.putParcelable("Food",mFood);
        }
    }


}
