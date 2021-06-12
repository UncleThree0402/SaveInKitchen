package fragments.buylist;

import android.os.Bundle;
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
import models.BuyFood;
import models.FoodType;
import viewmodels.BuyFoodViewModel;

public class BuyListAddFoodFragment extends Fragment implements View.OnClickListener{

    //UI
    private TextInputLayout mFoodNameTextInputLayout;
    private TextInputLayout mStatusTextInputLayout;
    private AutoCompleteTextView mStatusTextEdit;
    private TextInputLayout mQuantityTextInputLayout;
    private TextInputLayout mUnitTextInputLayout;
    private Button mConfirmButton;

    //Var
    private FoodType mFoodType;
    private BuyFood mBuyFood;
    private final static String[] STATUS = new String[]{"Fresh", "Frozen", "Dry Goods", "Condiment"};
    private BuyFoodViewModel mBuyFoodViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.buy_list_add_ingredient_fragment, container, false);

        mFoodNameTextInputLayout = view.findViewById(R.id.add_buy_list_name_input_layout);
        mStatusTextInputLayout = view.findViewById(R.id.add_buy_list_status_input_layout);
        mStatusTextEdit = view.findViewById(R.id.add_buy_list_status_input);
        mQuantityTextInputLayout = view.findViewById(R.id.add_buy_list_quantity_input_layout);
        mUnitTextInputLayout = view.findViewById(R.id.add_buy_list_unit_input_layout);
        mConfirmButton = view.findViewById(R.id.add_buy_list_confirm_button);

        mConfirmButton.setOnClickListener(this);

        mBuyFood = new BuyFood();
        Bundle bundle = this.getArguments();
        mFoodType = bundle.getParcelable("Food Type");
        mBuyFood.setFood_type_id(mFoodType.getFood_type_id());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.list_item, STATUS);
        mStatusTextEdit.setAdapter(adapter);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBuyFoodViewModel = new ViewModelProvider(this).get(BuyFoodViewModel.class);
    }

    private void checkInput() {
        if (!validFoodName() | !validStatus() | !validQuantity() | !validUnit()) {
            return;
        } else {
            mBuyFoodViewModel.insertBuyFood(mBuyFood);
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
            mBuyFood.setName(mFoodNameTextInputLayout.getEditText().getText().toString());
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
            mBuyFood.setStatus(mStatusTextInputLayout.getEditText().getText().toString());
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
            mBuyFood.setQuantity(Double.parseDouble(mQuantityTextInputLayout.getEditText().getText().toString()));
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
            mBuyFood.setUnit(mUnitTextInputLayout.getEditText().getText().toString());
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_buy_list_confirm_button:
                checkInput();
                break;
        }
    }


}
