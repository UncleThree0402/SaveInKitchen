package fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.unclethree.saveinkitchen.R;
import models.FoodType;
import viewmodels.FoodTypeViewModel;

public class DeleteFoodTypeWarningFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "DeleteFoodTypeWarningFr";

    //Ui
    private Button mCancelButton;
    private Button mConfirmButton;

    //Var
    private FoodType mFoodType;
    private FoodTypeViewModel mFoodTypeViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.delete_food_type_waring_fragment, container, false);

        mCancelButton = view.findViewById(R.id.delete_food_type_cancel_button);
        mConfirmButton = view.findViewById(R.id.delete_food_type_confirm_button);

        Bundle bundle = this.getArguments();
        mFoodType = bundle.getParcelable("Food Type");

        Log.d(TAG, "onCreateView: " + mFoodType);


        mConfirmButton.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFoodTypeViewModel = new ViewModelProvider(this).get(FoodTypeViewModel.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete_food_type_cancel_button:
                ((DialogFragment) getParentFragment()).dismiss();
                break;
            case R.id.delete_food_type_confirm_button:
                mFoodTypeViewModel.deleteFoodType(mFoodType);
                ((DialogFragment) getParentFragment()).dismiss();
                break;
        }
    }
}
