package viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import models.Dishes;
import models.FoodType;
import presistance.SaveInKitchenRepository;

import java.util.List;

public class DishesViewModel extends AndroidViewModel {

    private final SaveInKitchenRepository mSaveInKitchenRepository;

    private final LiveData<List<Dishes>> mDishes ;

    public DishesViewModel(@NonNull Application application) {
        super(application);
        mSaveInKitchenRepository = new SaveInKitchenRepository(application);
        mDishes = mSaveInKitchenRepository.getDishes();
    }


    public void insertDishes(Dishes foodType){
        mSaveInKitchenRepository.insertDishes(foodType);
    }

    public void updateDishes(Dishes foodType){
        mSaveInKitchenRepository.updateDishes(foodType);
    }

    public void deleteDishes(Dishes foodType){
        mSaveInKitchenRepository.deleteDishes(foodType);
    }

    public LiveData<List<Dishes>> getDishes(){
        return mDishes;
    }

    public LiveData<List<Dishes>> getSearchDish(String name){
        return mSaveInKitchenRepository.getSearchDish("%" + name + "%");
    }

    
}
