package dialogs;

import android.content.Intent;
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
import com.unclethree.saveinkitchen.PrepareRecipeActivity;
import com.unclethree.saveinkitchen.R;
import formatters.NumberFormatter;
import models.CookDish;
import models.DiaryHistory;
import models.Food;
import models.Recipe;
import viewmodels.*;

import java.util.ArrayList;

public class PrepareAddRecipeDialog extends DialogFragment {

    //Ui
    private TextView mNameTextView;

    private TextInputLayout mQuantityInputLayout;

    private Button mConfirmButton;

    //Var
    private Recipe mRecipe;
    private CookDishViewModel mCookDishViewModel;
    private CookDishIngredientViewModel mCookDishIngredientViewModel;

    public PrepareAddRecipeDialog(Recipe mRecipe) {
        this.mRecipe = mRecipe;
    }

    public PrepareAddRecipeDialog() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.prepare_add_reipce_dialog, container, false);

        mCookDishViewModel = new ViewModelProvider(this).get(CookDishViewModel.class);

        mNameTextView = view.findViewById(R.id.prepare_add_recipe_dialog_name);

        mQuantityInputLayout = view.findViewById(R.id.prepare_add_recipe_dialog_input_layout);

        mConfirmButton = view.findViewById(R.id.prepare_add_recipe_dialog_confirm_button);

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
        }else{
            CookDish cookDish = new CookDish();
            cookDish.setName(mRecipe.getName());
            cookDish.setRecipe_id(mRecipe.getRecipe_id());
            cookDish.setServing(Double.parseDouble(quantity));
            mCookDishViewModel.insertCookDish(cookDish);
            getActivity().finish();
            dismiss();

        }
    }

    private void setText(){
        mNameTextView.setText(mRecipe.getName());
        mQuantityInputLayout.setSuffixText("Servings");
    }
}
