package viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import models.DiaryHistory;
import presistance.SaveInKitchenRepository;

import java.util.List;

public class DiaryHistoryViewModel extends AndroidViewModel {

    private final SaveInKitchenRepository mSaveInKitchenRepository;

    private final LiveData<List<DiaryHistory>> mDiaryHistory;

    public DiaryHistoryViewModel(@NonNull Application application) {
        super(application);
        mSaveInKitchenRepository = new SaveInKitchenRepository(application);
        mDiaryHistory = mSaveInKitchenRepository.getDiaryHistory();
    }

    public void insertEatFoodHistory(DiaryHistory diaryHistory){
        mSaveInKitchenRepository.insertDiaryHistory(diaryHistory);
    }

    public void updateEatFoodHistory(DiaryHistory diaryHistory){
        mSaveInKitchenRepository.updateDiaryHistory(diaryHistory);
    }

    public void deleteEatFoodHistory(DiaryHistory diaryHistory){
        mSaveInKitchenRepository.deleteDiaryHistory(diaryHistory);
    }

    public LiveData<List<DiaryHistory>> getDiaryHistory(){
        return mDiaryHistory;
    }

    public LiveData<List<DiaryHistory>> getSpecificTypeEatFoodHistory(String type, long dateIn, long dateOut){
        return mSaveInKitchenRepository.getDiaryHistoryByDate(type,dateIn,dateOut);
    }

    public LiveData<Double> getTotalCostEatFoodHistory(long dateIn,long dateOut){
        return mSaveInKitchenRepository.getTotalCostDiaryHistory(dateIn,dateOut);
    }

}
