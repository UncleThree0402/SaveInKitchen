package fragments;

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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.textfield.TextInputLayout;
import com.unclethree.saveinkitchen.R;
import dialogs.AddFoodTypeDialog;
import models.FoodType;
import viewmodels.FoodTypeViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddBuyListFoodTypeFragment extends Fragment implements View.OnClickListener{

    //UI
    private TextInputLayout mAutoTextLayout;
    private AutoCompleteTextView mAutoCompleteTextView;
    private Button mConfirmButton;
    private Button mAddNewFoodTypeButton;

    //Var
    private FoodTypeViewModel mFoodTypeViewModel;
    private final ArrayList<String> mFoodTypeName = new ArrayList<>();
    private boolean isNameExist;
    private FoodType mFoodType;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_stock_food_type_fragment, container, false);
        mAutoTextLayout = view.findViewById(R.id.choose_food_type_auto_input_layout);
        mAutoCompleteTextView = view.findViewById(R.id.choose_food_type_auto_input);
        mConfirmButton = view.findViewById(R.id.choose_food_confirm_button);
        mAddNewFoodTypeButton = view.findViewById(R.id.choose_food_add_new_type_button);

        mConfirmButton.setOnClickListener(this);
        mAddNewFoodTypeButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFoodTypeViewModel = new ViewModelProvider(this).get(FoodTypeViewModel.class);
        mFoodTypeViewModel.getFoodTypeName().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                if (mFoodTypeName.size() > 0) {
                    mFoodTypeName.clear();
                }
                if (mFoodTypeName != null) {
                    mFoodTypeName.addAll(strings);
                }
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.list_item, mFoodTypeName);
        mAutoCompleteTextView.setAdapter(adapter);
    }

    private void validName() {
        String name = mAutoCompleteTextView.getText().toString().trim();
        if (name.isEmpty()) {
            mAutoTextLayout.setError("Field can't be empty");
        } else if (!isNameExist) {
            mAutoTextLayout.setError("Category not exist");
        } else {
            Bundle bundle = new Bundle();
            bundle.putParcelable("Food Type",mFoodType);
            AddBuyListAddFoodFragment addStockAddFoodFragment = new AddBuyListAddFoodFragment();
            addStockAddFoodFragment.setArguments(bundle);
            fragmentManager = getParentFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.add_buy_list_frame_layout, addStockAddFoodFragment);
            fragmentTransaction.commit();
        }
    }

    private void checkFoodTypeName() {
        mFoodTypeViewModel.getSpecificFoodType(mAutoCompleteTextView.getText().toString()).observe(getViewLifecycleOwner(), new Observer<FoodType>() {
            @Override
            public void onChanged(FoodType foodType) {
                if (foodType != null) {
                    isNameExist = true;
                    mFoodType = foodType;
                } else {
                    isNameExist = false;
                }
                validName();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_food_confirm_button:
                checkFoodTypeName();
                break;
            case R.id.choose_food_add_new_type_button:
                AddFoodTypeDialog addFoodTypeDialog = new AddFoodTypeDialog();
                addFoodTypeDialog.show(getChildFragmentManager(), "Add food type dialog");
                break;
        }
    }
}
