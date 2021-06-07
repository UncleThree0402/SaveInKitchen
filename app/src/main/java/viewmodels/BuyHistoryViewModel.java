package viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import models.BuyHistory;
import models.Recipe;
import presistance.SaveInKitchenRepository;

import java.util.List;

public class BuyHistoryViewModel extends AndroidViewModel {

    private final SaveInKitchenRepository mSaveInKitchenRepository;

    private final LiveData<List<BuyHistory>> mBuyHistory;

    public BuyHistoryViewModel(@NonNull Application application) {
        super(application);
        mSaveInKitchenRepository = new SaveInKitchenRepository(application);
        mBuyHistory = mSaveInKitchenRepository.getBuyHistory();
    }

    public void insertBuyHistory(BuyHistory buyHistory){
        mSaveInKitchenRepository.insertBuyHistory(buyHistory);
    }

    public void updateBuyHistory(BuyHistory buyHistory){
        mSaveInKitchenRepository.updateBuyHistory(buyHistory);
    }

    public void deleteBuyHistory(BuyHistory buyHistory){
        mSaveInKitchenRepository.deleteBuyHistory(buyHistory);
    }

    public LiveData<List<BuyHistory>> getBuyHistory(){
        return mBuyHistory;
    }

    public LiveData<Double> getBuyHistory30DaysCost(long date){
        return mSaveInKitchenRepository.getBuyHistory30DaysCost(date);
    }

    public  LiveData<List<BuyHistory>> getBuyHistory30Days(long dateIn){
        return mSaveInKitchenRepository.getBuyHistory30Days(dateIn);
    }

    public  LiveData<List<BuyHistory>> getSearchBuyHistory(String name){
        return mSaveInKitchenRepository.getSearchBuyHistory("%" + name + "%");
    }
}
