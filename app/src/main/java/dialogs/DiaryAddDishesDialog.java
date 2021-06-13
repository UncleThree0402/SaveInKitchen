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
import models.Dishes;
import models.Food;
import viewmodels.DiaryHistoryViewModel;
import viewmodels.DishesViewModel;
import viewmodels.FoodViewModel;

public class DiaryAddDishesDialog extends DialogFragment {

    //Ui
    private TextView mNameTextView;
    private TextView mQuantityTextView;

    private TextInputLayout mQuantityInputLayout;

    private Button mConfirmButton;

    //Var
    private final Dishes mDishes;
    private DishesViewModel mDishesViewModel;
    private DiaryHistoryViewModel mDiaryHistoryViewModel;

    private final String mType;
    private final long mDatetime;

    public DiaryAddDishesDialog(Dishes mDishes, String mType, long mDatetime) {
        this.mDishes = mDishes;
        this.mType = mType;
        this.mDatetime = mDatetime;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diary_add_dishes_dialog, container, false);
        mDishesViewModel = new ViewModelProvider(this).get(DishesViewModel.class);
        mDiaryHistoryViewModel = new ViewModelProvider(this).get(DiaryHistoryViewModel.class);

        mNameTextView = view.findViewById(R.id.add_dishes_name);
        mQuantityTextView = view.findViewById(R.id.add_dishes_quantity);

        mQuantityInputLayout = view.findViewById(R.id.add_dishes_quantity_input_layout);

        mConfirmButton = view.findViewById(R.id.add_dishes_confirm_button);

        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInput();
            }
        });

        setText();

        return view;
    }

    private void checkInput() {
        String quantity = mQuantityInputLayout.getEditText().getText().toString();
        if (quantity.isEmpty()) {
            mQuantityInputLayout.setError("Field can't be empty");
        } else if (Double.parseDouble(quantity) > mDishes.getServings()) {
            mQuantityInputLayout.setError("Quantity can't greater than you have");
        } else {
            DiaryHistory diaryHistory = new DiaryHistory();

            diaryHistory.setFood_id(mDishes.getDishes_id());
            diaryHistory.setBeforeQuantity(mDishes.getServings());
            diaryHistory.setCostPerUnit(mDishes.getCostPerServing());
            diaryHistory.setBuyDate(mDishes.getDate());

            diaryHistory.setName(mDishes.getName());
            diaryHistory.setQuantity(Double.parseDouble(quantity));
            diaryHistory.setTime(mType);
            diaryHistory.setDate(mDatetime);
            diaryHistory.setCost(Double.parseDouble(quantity) * mDishes.getCostPerServing());

            if (Double.parseDouble(quantity) != mDishes.getDishes_id()) {
                mDishes.setServings(mDishes.getServings() - Double.parseDouble(quantity));
                mDishesViewModel.updateDishes(mDishes);
            } else {
                mDishesViewModel.deleteDishes(mDishes);
            }


            mDiaryHistoryViewModel.insertEatFoodHistory(diaryHistory);
            getActivity().finish();
            dismiss();

        }
    }

    private void setText() {
        mNameTextView.setText(mDishes.getName());
        String quantity = NumberFormatter.quantityFormatter(mDishes.getServings()) + " Servings";
        mQuantityTextView.setText(quantity);
        mQuantityInputLayout.setSuffixText("Servings");
    }
}
