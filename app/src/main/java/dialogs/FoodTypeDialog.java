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
import fragments.foodtype.AddFoodTypeFragment;

public class FoodTypeDialog extends DialogFragment {

    //Var
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.food_type_dialog,container,false);

        fragmentManager = getChildFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.add_food_type_frame_layout, new AddFoodTypeFragment());
        fragmentTransaction.commit();

        return view;
    }
}
