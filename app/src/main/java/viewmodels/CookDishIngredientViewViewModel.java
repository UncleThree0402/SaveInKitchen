package viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import models.CookDishIngredientView;
import models.Recipe;
import presistance.SaveInKitchenRepository;

import java.util.List;

public class CookDishIngredientViewViewModel extends AndroidViewModel {

    private final SaveInKitchenRepository mSaveInKitchenRepository;


    public CookDishIngredientViewViewModel(@NonNull Application application) {
        super(application);
        mSaveInKitchenRepository = new SaveInKitchenRepository(application);
    }


    public LiveData<List<CookDishIngredientView>> getCookDishIngredientView(int cook_dish_id, int recipe_food_id, int food_id){
        return mSaveInKitchenRepository.getCookDishIngredientView(cook_dish_id, recipe_food_id,food_id);
    }

}
