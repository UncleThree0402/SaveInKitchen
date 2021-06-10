package viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import models.Food;
import models.FoodType;
import models.Recipe;
import presistance.SaveInKitchenRepository;

import java.util.List;

public class FoodViewModel extends AndroidViewModel {

    private final SaveInKitchenRepository mSaveInKitchenRepository;

    private final LiveData<List<Food>> mFood;

    public FoodViewModel(@NonNull Application application) {
        super(application);
        mSaveInKitchenRepository = new SaveInKitchenRepository(application);
        mFood = mSaveInKitchenRepository.getFood();
    }

    public void insertFood(Food food){
        mSaveInKitchenRepository.insertFood(food);
    }

    public void updateFood(Food food){
        mSaveInKitchenRepository.updateFood(food);
    }

    public void deleteFood(Food food){
        mSaveInKitchenRepository.deleteFood(food);
    }

    public LiveData<List<Food>> getFood(){
        return mFood;
    }

    public LiveData<List<Food>> getSpecificFoodList(int foodTypeId){
        return mSaveInKitchenRepository.getSpecificFoodList(foodTypeId);
    }

    public LiveData<List<Food>> getSearchFood(String name){
        return mSaveInKitchenRepository.getSearchFood("%" + name + "%");
    }

    public LiveData<Integer> getFoodTypeCount(int id){
        return mSaveInKitchenRepository.getFoodTypeCount(id);
    }

    public LiveData<List<Food>> getSpecificTypeFood(String food_status , String name){
        return mSaveInKitchenRepository.getSpecificTypeFood(food_status,"%" + name + "%");
    }
}
