package viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import models.BuyFood;
import models.BuyListItem;
import presistance.SaveInKitchenRepository;

import java.util.ArrayList;
import java.util.List;

public class BuyFoodViewModel extends AndroidViewModel {

    private SaveInKitchenRepository mSaveInKitchenRepository;

    private LiveData<List<BuyFood>> mBuyFood;
    private LiveData<List<BuyListItem>> mBuyFoodList;

    public BuyFoodViewModel(@NonNull Application application) {
        super(application);
        mSaveInKitchenRepository = new SaveInKitchenRepository(application);
        mBuyFood = mSaveInKitchenRepository.getBuyFood();
        mBuyFoodList = mSaveInKitchenRepository.getBuyFoodList();
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

    public LiveData<List<BuyListItem>> getBuyFoodList(){
        return mBuyFoodList;
    }
}
