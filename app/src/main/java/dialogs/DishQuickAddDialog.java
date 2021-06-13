package dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.textfield.TextInputLayout;
import com.unclethree.saveinkitchen.R;
import models.DiaryHistory;
import models.Dishes;
import viewmodels.DiaryHistoryViewModel;
import viewmodels.DishesViewModel;

public class DishQuickAddDialog extends DialogFragment {

    //Ui
    private TextInputLayout mNameInputLayout;
    private TextInputLayout mQuantityInputLayout;
    private TextInputLayout mCostInputLayout;

    private Button mConfirmButton;

    //Var
    private DishesViewModel mDishesViewModel;
    private Dishes dishes = new Dishes();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dish_quick_add_dialog, container, false);
        mDishesViewModel = new ViewModelProvider(this).get(DishesViewModel.class);

        mNameInputLayout = view.findViewById(R.id.dish_quick_add_name_input_layout);
        mQuantityInputLayout = view.findViewById(R.id.dish_quick_add_quantity_input_layout);
        mCostInputLayout = view.findViewById(R.id.dish_quick_add_cost_input_layout);

        mConfirmButton = view.findViewById(R.id.dish_quick_add_confirm_button);

        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInput();
            }
        });


        return view;
    }

    private void checkInput() {
        if(!validName() | !validQuantity() |  !validCost()){
            return;
        }else{
            dishes.setCostPerServing(dishes.getCost()/dishes.getServings());
            mDishesViewModel.insertDishes(dishes);
            dismiss();
        }
    }

    private boolean validName() {
        String name = mNameInputLayout.getEditText().getText().toString().trim();
        if (name.isEmpty()) {
            mNameInputLayout.setError("Field can't be empty");
            return false;
        } else {
            mNameInputLayout.setError(null);
            dishes.setName(mNameInputLayout.getEditText().getText().toString());
            return true;
        }
    }

    private boolean validQuantity() {
        String quantity = mQuantityInputLayout.getEditText().getText().toString().trim();
        if (quantity.isEmpty()) {
            mQuantityInputLayout.setError("Field can't be empty");
            return false;
        } else {
            mQuantityInputLayout.setError(null);
            dishes.setServings(Double.parseDouble(mQuantityInputLayout.getEditText().getText().toString()));
            return true;
        }
    }


    private boolean validCost() {
        String cost = mCostInputLayout.getEditText().getText().toString().trim();
        if (cost.isEmpty()) {
            mCostInputLayout.setError("Field can't be empty");
            return false;
        } else {
            dishes.setCost(Double.parseDouble(mCostInputLayout.getEditText().getText().toString()));
            return true;
        }
    }

}
