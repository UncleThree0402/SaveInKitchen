package fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.textfield.TextInputLayout;
import com.unclethree.saveinkitchen.R;
import models.FoodType;
import viewmodels.FoodTypeViewModel;

import java.util.List;

public class AddFoodTypeFragment extends Fragment implements View.OnClickListener {

    //UI
    private TextInputLayout mNameInputLayout;
    private Button mConfirmButton;


    //Var
    private FoodTypeViewModel mFoodTypeViewModel;
    private boolean isNameExist;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_food_type_fragment,container,false);
        mNameInputLayout = view.findViewById(R.id.add_food_type_input_layout);
        mConfirmButton = view.findViewById(R.id.add_food_type_confirm_button);

        mConfirmButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFoodTypeViewModel = new ViewModelProvider(this).get(FoodTypeViewModel.class);
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
            mFoodTypeViewModel.insertFoodType(new FoodType(mNameInputLayout.getEditText().getText().toString()));
            ((DialogFragment) getParentFragment()).dismiss();
        }
    }

    private void checkFoodTypeName() {
        mFoodTypeViewModel.getSpecificFoodTypeName(mNameInputLayout.getEditText().getText().toString()).observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                isNameExist = strings.size() > 0;
                validName();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_food_type_confirm_button:
                checkFoodTypeName();
                break;
        }
    }


}
