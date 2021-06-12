package dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.textfield.TextInputLayout;
import com.unclethree.saveinkitchen.R;
import formatters.NumberFormatter;
import models.DiaryHistory;
import models.Food;
import viewmodels.DiaryHistoryViewModel;
import viewmodels.FoodViewModel;

public class PrepareAddRecipeDialog extends DialogFragment {

    //Ui
    private TextView mNameTextView;
    private TextView mStatusTextView;
    private TextView mQuantityTextView;

    private TextInputLayout mQuantityInputLayout;

    private Button mConfirmButton;

    //Var
    private final Food mFood;
    private FoodViewModel mFoodViewModel;
    private DiaryHistoryViewModel mDiaryHistoryViewModel;

    private final String mType;
    private final long mDatetime;

    public PrepareAddRecipeDialog(Food mFood, String mType, long mDatetime) {
        this.mFood = mFood;
        this.mType = mType;
        this.mDatetime = mDatetime;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.prepare_add_reipce_dialog, container, false);
        mFoodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        mDiaryHistoryViewModel = new ViewModelProvider(this).get(DiaryHistoryViewModel.class);

        mNameTextView = view.findViewById(R.id.add_food_stock_name);
        mStatusTextView = view.findViewById(R.id.add_food_stock_status);
        mQuantityTextView = view.findViewById(R.id.add_food_stock_quantity);

        mQuantityInputLayout = view.findViewById(R.id.add_food_stock_quantity_input_layout);

        mConfirmButton = view.findViewById(R.id.add_food_stock_confirm_button);

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
        String quantity = mQuantityInputLayout.getEditText().getText().toString();
        if(quantity.isEmpty()){
            mQuantityInputLayout.setError("Field can't be empty");
        }else if(Double.parseDouble(quantity) > mFood.getQuantity()){
            mQuantityInputLayout.setError("Quantity can't greater than you have");
        }else{
            DiaryHistory diaryHistory = new DiaryHistory();

            diaryHistory.setFood_id(mFood.getFood_id());
            diaryHistory.setFood_type_id(mFood.getFood_type_id());
            diaryHistory.setStatus(mFood.getStatus());
            diaryHistory.setBeforeQuantity(mFood.getQuantity());
            diaryHistory.setCostPerUnit(mFood.getCostPerUnit());
            diaryHistory.setBuyDate(mFood.getBuyDate());
            diaryHistory.setExpireDate(mFood.getExpireDate());

            if(Double.parseDouble(quantity) != mFood.getQuantity()) {
                mFood.setQuantity(mFood.getQuantity() - Double.parseDouble(quantity));
                mFoodViewModel.updateFood(mFood);
            }else {
                mFoodViewModel.deleteFood(mFood);
            }

            diaryHistory.setName(mFood.getName());
            diaryHistory.setQuantity(Double.parseDouble(quantity));
            diaryHistory.setUnit(mFood.getUnit());
            diaryHistory.setTime(mType);
            diaryHistory.setDate(mDatetime);
            diaryHistory.setCost(Double.parseDouble(quantity) * mFood.getCostPerUnit());



            mDiaryHistoryViewModel.insertEatFoodHistory(diaryHistory);
            getActivity().finish();
            dismiss();

        }
    }

    private void setText(){
        mNameTextView.setText(mFood.getName());
        mStatusTextView.setText(mFood.getStatus());
        String quantity = NumberFormatter.quantityFormatter(mFood.getQuantity()) + " " + mFood.getUnit();
        mQuantityTextView.setText(quantity);
        mQuantityInputLayout.setSuffixText(mFood.getUnit());
    }
}
