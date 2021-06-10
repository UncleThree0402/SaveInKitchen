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
import fragments.AddRecipeFoodFragment;

public class RecipeIngredientDialog extends DialogFragment {
    //Var
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private final String mName;

    public RecipeIngredientDialog(String name) {
        this.mName = name;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_add_ingerdient_dialog,container,false);
        fragmentManager = getChildFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putString("Name", mName);
        AddRecipeFoodFragment addRecipeFoodFragment = new AddRecipeFoodFragment();
        addRecipeFoodFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.add_recipe_food_frame_layout, addRecipeFoodFragment);
        fragmentTransaction.commit();

        return view;
    }
}
