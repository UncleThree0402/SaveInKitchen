package dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.unclethree.saveinkitchen.R;
import fragments.AddBuyListFoodTypeFragment;
import fragments.DeleteFoodTypeWarningFragment;
import models.FoodType;

public class DeleteFoodTypeWarningDialog extends DialogFragment {
    //Var
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private final Bundle bundle;

    public DeleteFoodTypeWarningDialog(Bundle bundle) {
        this.bundle = bundle;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.delete_food_type_warning_dialog,container,false);

        fragmentManager = getChildFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        DeleteFoodTypeWarningFragment deleteFoodTypeWarningFragment = new DeleteFoodTypeWarningFragment();
        deleteFoodTypeWarningFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.delete_food_type_warning_frame_layout, deleteFoodTypeWarningFragment);
        fragmentTransaction.commit();

        return view;
    }
}
