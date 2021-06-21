package viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import models.CookDishIngredient;
import models.CookDishIngredient;
import presistance.SaveInKitchenRepository;

import java.util.List;

public class CookDishIngredientViewModel extends AndroidViewModel {

    private final SaveInKitchenRepository mSaveInKitchenRepository;

    private final LiveData<List<CookDishIngredient>> mCookDishIngredient;

    public CookDishIngredientViewModel(@NonNull Application application) {
        super(application);
        mSaveInKitchenRepository = new SaveInKitchenRepository(application);
        mCookDishIngredient = mSaveInKitchenRepository.getCookDishIngredient();
    }

    public void insertCookDishIngredient(CookDishIngredient recipe){
        mSaveInKitchenRepository.insertCookDishIngredient(recipe);
    }

    public void updateCookDishIngredient(CookDishIngredient recipe){
        mSaveInKitchenRepository.updateCookDishIngredient(recipe);
    }

    public void deleteCookDishIngredient(CookDishIngredient recipe){
        mSaveInKitchenRepository.deleteCookDishIngredient(recipe);
    }

    public LiveData<List<CookDishIngredient>> getCookDishIngredient(){
        return mCookDishIngredient;
    }

    public LiveData<List<CookDishIngredient>> getSpecificCookDishIngredient(int recipe_food_id, int cook_dish_id){
        return mSaveInKitchenRepository.getSpecificCookDishIngredient(recipe_food_id, cook_dish_id);
    }

    public LiveData<Double> getSumOfSpecificCookDishIngredient(int food_id){
        return mSaveInKitchenRepository.getSumOfSpecificCookDishIngredient(food_id);
    }

    public LiveData<CookDishIngredient> getSpecificCookDishIngredientById(int food_id){
        return mSaveInKitchenRepository.getSpecificCookDishIngredientById(food_id);
    }

    public LiveData<Double> getCostOfDish(int cook_dish_id){
        return mSaveInKitchenRepository.getCostOfDish(cook_dish_id);
    }

    public LiveData<List<CookDishIngredient>> getSpecificCookDishIngByDish(int cook_dish_id){
        return mSaveInKitchenRepository.getSpecificCookDishIngByDish(cook_dish_id);
    }

    
}
