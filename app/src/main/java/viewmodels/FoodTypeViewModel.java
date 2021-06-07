package viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import models.BuyFood;
import models.FoodType;
import presistance.SaveInKitchenRepository;

import java.util.List;

public class FoodTypeViewModel extends AndroidViewModel {

    private final SaveInKitchenRepository mSaveInKitchenRepository;

    private final LiveData<List<FoodType>> mFoodType;
    private final LiveData<List<String>> mFoodTypeName;

    public FoodTypeViewModel(@NonNull Application application) {
        super(application);
        mSaveInKitchenRepository = new SaveInKitchenRepository(application);
        mFoodType = mSaveInKitchenRepository.getFoodType();
        mFoodTypeName = mSaveInKitchenRepository.getFoodTypeName();
    }

    public void insertFoodType(FoodType foodType){
        mSaveInKitchenRepository.insertFoodType(foodType);
    }

    public void updateFoodType(FoodType foodType){
        mSaveInKitchenRepository.updateFoodType(foodType);
    }

    public void deleteFoodType(FoodType foodType){
        mSaveInKitchenRepository.deleteFoodType(foodType);
    }

    public LiveData<List<FoodType>> getFoodType(){
        return mFoodType;
    }

    public LiveData<List<String>> getFoodTypeName(){
        return mFoodTypeName;
    }

    public LiveData<List<String>> getSpecificFoodTypeName(String name){
        return mSaveInKitchenRepository.getSpecificFoodTypeName(name);
    }

    public LiveData<FoodType> getSpecificFoodType(String name){
        return mSaveInKitchenRepository.getSpecificFoodType(name);
    }

}
