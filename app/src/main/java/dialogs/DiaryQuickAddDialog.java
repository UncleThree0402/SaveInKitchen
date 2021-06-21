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
import viewmodels.DiaryHistoryViewModel;

public class DiaryQuickAddDialog extends DialogFragment {

    //Ui
    private TextInputLayout mNameInputLayout;
    private TextInputLayout mQuantityInputLayout;
    private TextInputLayout mUnitInputLayout;
    private TextInputLayout mCostInputLayout;

    private Button mConfirmButton;

    //Var
    private DiaryHistoryViewModel mDiaryHistoryViewModel;
    private final DiaryHistory mDiaryHistory = new DiaryHistory();
    private final String mType;
    private final long mDatetime;

    public DiaryQuickAddDialog(String mType, long mDatetime) {
        this.mType = mType;
        this.mDatetime = mDatetime;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diary_quick_add_dialog, container, false);
        mDiaryHistoryViewModel = new ViewModelProvider(this).get(DiaryHistoryViewModel.class);

        mNameInputLayout = view.findViewById(R.id.diary_quick_add_name_input_layout);
        mQuantityInputLayout = view.findViewById(R.id.diary_quick_add_quantity_input_layout);
        mUnitInputLayout = view.findViewById(R.id.diary_quick_add_unit_input_layout);
        mCostInputLayout = view.findViewById(R.id.diary_quick_add_cost_input_layout);

        mConfirmButton = view.findViewById(R.id.diary_quick_add_confirm_button);

        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInput();
            }
        });


        return view;
    }

    private void checkInput() {
        if(!validName() | !validQuantity() | !validUnit() | !validCost()){
            return;
        }else{
            mDiaryHistory.setDate(mDatetime);
            mDiaryHistory.setTime(mType);
            mDiaryHistory.setBuyDate(2);
            mDiaryHistoryViewModel.insertEatFoodHistory(mDiaryHistory);
            getActivity().finish();
        }
    }

    private boolean validName() {
        String name = mNameInputLayout.getEditText().getText().toString().trim();
        if (name.isEmpty()) {
            mNameInputLayout.setError("Field can't be empty");
            return false;
        } else {
            mNameInputLayout.setError(null);
            mDiaryHistory.setName(mNameInputLayout.getEditText().getText().toString());
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
            mDiaryHistory.setQuantity(Double.parseDouble(mQuantityInputLayout.getEditText().getText().toString()));
            return true;
        }
    }

    private boolean validUnit() {
        String unit = mUnitInputLayout.getEditText().getText().toString().trim();
        if (unit.isEmpty()) {
            mUnitInputLayout.setError("Field can't be empty");
            return false;
        } else {
            mUnitInputLayout.setError(null);
            mDiaryHistory.setUnit(mUnitInputLayout.getEditText().getText().toString());
            return true;
        }
    }

    private boolean validCost() {
        String cost = mCostInputLayout.getEditText().getText().toString().trim();
        if (cost.isEmpty()) {
            mCostInputLayout.setError("Field can't be empty");
            return false;
        } else {
            mDiaryHistory.setCost(Double.parseDouble(mCostInputLayout.getEditText().getText().toString()));
            return true;
        }
    }

}
