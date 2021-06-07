package fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;
import com.unclethree.saveinkitchen.R;
import models.BuyHistory;
import models.Food;
import models.FoodType;
import viewmodels.BuyHistoryViewModel;
import viewmodels.FoodTypeViewModel;
import viewmodels.FoodViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddStockAddFoodFragment extends Fragment implements View.OnClickListener, MaterialPickerOnPositiveButtonClickListener {
    private static final String TAG = "AddStockAddFoodFragment";

    //UI
    private TextInputLayout mFoodNameTextInputLayout;
    private TextInputLayout mStatusTextInputLayout;
    private AutoCompleteTextView mStatusTextEdit;
    private TextInputLayout mQuantityTextInputLayout;
    private TextInputLayout mUnitTextInputLayout;
    private TextInputLayout mCostTextInputLayout;
    private TextInputLayout mBuyDateTextInputLayout;
    private TextInputLayout mExpireDateTextInputLayout;
    private Button mConfirmButton;
    private MaterialDatePicker mMaterialDatePicker;

    //Var
    private FoodType mFoodType;
    private Food mFood;
    private final static String[] STATUS = new String[]{"Fresh", "Frozen", "Dry Goods", "Condiment"};
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private int datepickerId = 0;
    private FoodViewModel mFoodViewModel;
    private BuyHistoryViewModel mBuyHistoryViewModel;
    private FoodTypeViewModel mFoodTypeViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_stock_add_food_info_fragment, container, false);

        mFoodNameTextInputLayout = view.findViewById(R.id.add_stock_food_type_input_layout);
        mStatusTextInputLayout = view.findViewById(R.id.add_stock_food_status_input_layout);
        mStatusTextEdit = view.findViewById(R.id.add_stock_food_status_input);
        mQuantityTextInputLayout = view.findViewById(R.id.add_stock_food_quantity_input_layout);
        mUnitTextInputLayout = view.findViewById(R.id.add_stock_food_unit_input_layout);
        mCostTextInputLayout = view.findViewById(R.id.add_stock_food_cost_input_layout);
        mBuyDateTextInputLayout = view.findViewById(R.id.add_stock_food_buy_date_input_layout);
        mExpireDateTextInputLayout = view.findViewById(R.id.add_stock_food_expire_date_input_layout);
        mConfirmButton = view.findViewById(R.id.add_stock_food_confirm_button);


        mFood = new Food();
        Bundle bundle = this.getArguments();
        mFoodType = bundle.getParcelable("Food Type");
        mFood.setFood_type_id(mFoodType.getFood_type_id());

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("SELECT A DATE");
        mMaterialDatePicker = builder.build();
        mMaterialDatePicker.addOnPositiveButtonClickListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.list_item, STATUS);
        mStatusTextEdit.setAdapter(adapter);

        mBuyDateTextInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepickerId = 1;
                mMaterialDatePicker.show(getChildFragmentManager(), "Date Picker");
            }
        });
        mExpireDateTextInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepickerId = 2;
                mMaterialDatePicker.show(getChildFragmentManager(), "Date Picker");
            }
        });
        mConfirmButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFoodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        mBuyHistoryViewModel = new ViewModelProvider(this).get(BuyHistoryViewModel.class);
        mFoodTypeViewModel = new ViewModelProvider(this).get(FoodTypeViewModel.class);
    }

    private void checkInput() {
        if (!validFoodName() | !validStatus() | !validQuantity() | !validUnit() | !validCost() | !validBuyDate() | !validExpireDate()) {
            return;
        } else {
            mFood.setCostPerUnit(mFood.getCost() / mFood.getQuantity());
            mFoodViewModel.insertFood(mFood);
            mBuyHistoryViewModel.insertBuyHistory(new BuyHistory(mFood.getName(),mFood.getStatus(),mFood.getQuantity(),mFood.getUnit(),mFood.getCost(), mFood.getBuyDate(), mFood.getExpireDate()));
            mFoodViewModel.getFoodTypeCount(mFood.getFood_type_id()).observe(getViewLifecycleOwner(), new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    if(integer == null){
                        mFoodType.setInStock(false);
                        mFoodTypeViewModel.updateFoodType(mFoodType);
                    }else{
                        mFoodType.setInStock(true);
                        mFoodTypeViewModel.updateFoodType(mFoodType);
                    }
                }
            });
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

    private boolean validBuyDate() {
        String dateText = mBuyDateTextInputLayout.getEditText().getText().toString();
        if (dateText.isEmpty()) {
            mBuyDateTextInputLayout.setError("Field can't be empty");
            return false;
        } else {
            try {
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateText);
                if (date.getTime() > System.currentTimeMillis()) {
                    mBuyDateTextInputLayout.setError("You are not in future");
                    return false;
                } else {
                    mUnitTextInputLayout.setError(null);
                    mFood.setBuyDate(date.getTime());
                    return true;
                }
            } catch (ParseException e) {
                mBuyDateTextInputLayout.setError("Wrong Format");
                return false;
            }
        }
    }

    private boolean validExpireDate() {
        String dateText = mExpireDateTextInputLayout.getEditText().getText().toString();
        if(!dateText.isEmpty()){
            try {
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateText);
                if (date.getTime() < System.currentTimeMillis()) {
                    mExpireDateTextInputLayout.setError("This already expired");
                    return false;
                } else {
                    mExpireDateTextInputLayout.setError(null);
                    mFood.setExpireDate(date.getTime());
                    return true;
                }
            } catch (ParseException e) {
                mExpireDateTextInputLayout.setError("Wrong Format");
                return false;
            }
        }
        return true;
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_stock_food_confirm_button:
                checkInput();
                break;
        }
    }

    @Override
    public void onPositiveButtonClick(Object selection) {
        if (datepickerId == 1) {
            mBuyDateTextInputLayout.getEditText().setText(simpleDateFormat.format(selection));
        } else if (datepickerId == 2) {
            mExpireDateTextInputLayout.getEditText().setText(simpleDateFormat.format(selection));
        }
        datepickerId = 0;
    }
}
