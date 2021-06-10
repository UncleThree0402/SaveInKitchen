package fragments.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.textfield.TextInputLayout;
import com.unclethree.saveinkitchen.R;
import com.unclethree.saveinkitchen.RecipeActivity;
import models.Recipe;
import viewmodels.RecipeViewModel;

public class AddRecipeNameFragment extends Fragment implements View.OnClickListener{

    //UI
    private TextInputLayout mNameInputLayout;
    private Button mConfirmButton;


    //Var
    private RecipeViewModel mRecipeViewModel;
    private boolean isNameExist;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_add_name_fragment,container,false);

        mNameInputLayout = view.findViewById(R.id.add_recipe_name_input_layout);
        mConfirmButton = view.findViewById(R.id.add_recipe_name_confirm_button);

        mConfirmButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
    }

    private void validName(){
        String name = mNameInputLayout.getEditText().getText().toString().trim();
        if(name.isEmpty()){
            mNameInputLayout.setError("Field can't be empty");
        }
        else if(isNameExist){
            mNameInputLayout.setError("Already exist");
        }
        else{
            mNameInputLayout.setError(null);
            mRecipeViewModel.insertRecipe(new Recipe(mNameInputLayout.getEditText().getText().toString()));
            addRecipeIntent();
            ((DialogFragment) getParentFragment()).dismiss();
        }
    }

    private void checkRecipeName() {
        mRecipeViewModel.getSpecificRecipe(mNameInputLayout.getEditText().getText().toString()).observe(getViewLifecycleOwner(), new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                isNameExist = recipe != null;
                validName();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_recipe_name_confirm_button:
                checkRecipeName();
                break;
        }
    }

    private void addRecipeIntent(){
                Intent intent = new Intent(getActivity(), RecipeActivity.class);
                intent.putExtra("Recipe Name",mNameInputLayout.getEditText().getText().toString());
                intent.putExtra("Mode",true);
                startActivity(intent);
            }

}
