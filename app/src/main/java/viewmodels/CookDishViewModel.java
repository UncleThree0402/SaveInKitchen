package viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import models.CookDish;
import models.Dishes;
import presistance.SaveInKitchenRepository;

import java.util.List;

public class CookDishViewModel extends AndroidViewModel {

    private final SaveInKitchenRepository mSaveInKitchenRepository;

    private final LiveData<List<CookDish>> mCookDish ;

    public CookDishViewModel(@NonNull Application application) {
        super(application);
        mSaveInKitchenRepository = new SaveInKitchenRepository(application);
        mCookDish = mSaveInKitchenRepository.getCookDish();
    }


    public void insertCookDish(CookDish foodType){
        mSaveInKitchenRepository.insertCookDish(foodType);
    }

    public void updateCookDish(CookDish foodType){
        mSaveInKitchenRepository.updateCookDish(foodType);
    }

    public void deleteCookDish(CookDish foodType){
        mSaveInKitchenRepository.deleteCookDish(foodType);
    }

    public LiveData<List<CookDish>> getCookDish(){
        return mCookDish;
    }

    public LiveData<List<CookDish>> getSearchCookDish(String name){
        return mSaveInKitchenRepository.getSearchCookDish("%" + name + "%");
    }

    public LiveData<CookDish> getCookDishesById(int cook_dish_id){
        return mSaveInKitchenRepository.getCookDishesById(cook_dish_id);
    }

    
}
