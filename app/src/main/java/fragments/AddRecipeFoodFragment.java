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
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.textfield.TextInputLayout;
import com.unclethree.saveinkitchen.R;
import dialogs.AddFoodTypeDialog;
import models.FoodType;
import models.Recipe;
import models.RecipeFood;
import viewmodels.FoodTypeViewModel;
import viewmodels.RecipeFoodViewModel;
import viewmodels.RecipeViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddRecipeFoodFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "AddRecipeFoodFragment";

    //UI
    private TextInputLayout mAutoTextLayout;
    private AutoCompleteTextView mAutoCompleteTextView;
    private Button mConfirmButton;
    private Button mAddNewFoodTypeButton;


    //Var
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private RecipeViewModel mRecipeViewModel;
    private FoodTypeViewModel mFoodTypeViewModel;
    private boolean isNameExist;
    private FoodType mFoodType;
    private String mName;
    private final ArrayList<String> mFoodTypeName = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_recipe_food_fragment,container,false);
        mAutoTextLayout = view.findViewById(R.id.add_recipe_food_type_auto_input_layout);
        mAutoCompleteTextView = view.findViewById(R.id.add_recipe_food_auto_input);
        mConfirmButton = view.findViewById(R.id.add_recipe_food_confirm_button);
        mAddNewFoodTypeButton = view.findViewById(R.id.add_recipe_food_add_new_type_button);

        Bundle bundle = this.getArguments();
        mName = bundle.getString("Name");

        mConfirmButton.setOnClickListener(this);
        mAddNewFoodTypeButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
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

    private void validName(){
        String name = mAutoTextLayout.getEditText().getText().toString().trim();
        if(name.isEmpty()){
            mAutoTextLayout.setError("Field can't be empty");
        }
        else if(!isNameExist){
            mAutoTextLayout.setError("Not exist");
        }
        else{
            mAutoTextLayout.setError(null);
            mRecipeViewModel.getSpecificRecipe(mName).observe(getViewLifecycleOwner(), new Observer<Recipe>() {
                @Override
                public void onChanged(Recipe recipe) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("Food Type Id",mFoodType.getFood_type_id());
                    bundle.putInt("Recipe Id",recipe.getRecipe_id());
                    AddRecipeListAddFoodFragment addRecipeListAddFoodFragment = new AddRecipeListAddFoodFragment();
                    addRecipeListAddFoodFragment.setArguments(bundle);
                    fragmentManager = getParentFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.add_recipe_food_frame_layout, addRecipeListAddFoodFragment);
                    fragmentTransaction.commit();
                }
            });
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
        switch (v.getId()){
            case R.id.add_recipe_food_confirm_button:
                checkFoodTypeName();
                break;

            case R.id.add_recipe_food_add_new_type_button:
                AddFoodTypeDialog addFoodTypeDialog = new AddFoodTypeDialog();
                addFoodTypeDialog.show(getChildFragmentManager(), "Add food type dialog");
                break;
        }
    }


}
