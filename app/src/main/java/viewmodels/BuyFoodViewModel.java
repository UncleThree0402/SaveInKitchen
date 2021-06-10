package viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import models.BuyFood;
import presistance.SaveInKitchenRepository;

import java.util.List;

public class BuyFoodViewModel extends AndroidViewModel {

    private final SaveInKitchenRepository mSaveInKitchenRepository;

    private final LiveData<List<BuyFood>> mBuyFood;

    public BuyFoodViewModel(@NonNull Application application) {
        super(application);
        mSaveInKitchenRepository = new SaveInKitchenRepository(application);
        mBuyFood = mSaveInKitchenRepository.getBuyFood();
    }

    public void insertBuyFood(BuyFood buyFood){
        mSaveInKitchenRepository.insertBuyFood(buyFood);
    }

    public void updateBuyFood(BuyFood buyFood){
        mSaveInKitchenRepository.updateBuyFood(buyFood);
    }

    public void deleteBuyFood(BuyFood buyFood){
        mSaveInKitchenRepository.deleteBuyFood(buyFood);
    }

    public LiveData<List<BuyFood>> getBuyFood(){
        return mBuyFood;
    }

    public LiveData<BuyFood> getSpecificBuyFood(int id){
        return mSaveInKitchenRepository.getSpecificBuyFood(id);
    }

    public LiveData<List<BuyFood>> getSpecificTypeBuyFood(String buy_food_status, String name){
        return  mSaveInKitchenRepository.getSpecificTypeBuyFood(buy_food_status, "%" + name + "%");
    }

    public LiveData<List<BuyFood>> getSpecificNameBuyFood(String name){
        return mSaveInKitchenRepository.getSpecificNameBuyFood("%" + name + "%");
    }
}
