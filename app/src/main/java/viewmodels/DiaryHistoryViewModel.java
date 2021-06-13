package viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import models.BuyHistory;
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

    public LiveData<List<DiaryHistory>> getDiaryHistoryByDate(String type, long dateIn, long dateOut){
        return mSaveInKitchenRepository.getDiaryHistoryByDate(type,dateIn,dateOut);
    }

    public LiveData<Double> getTotalCostDiaryHistory(long dateIn, long dateOut){
        return mSaveInKitchenRepository.getTotalCostDiaryHistory(dateIn,dateOut);
    }

    public LiveData<List<DiaryHistory>> getDiaryHistory30Days(long dateIn){
        return mSaveInKitchenRepository.getDiaryHistory30Days(dateIn);
    }

    public LiveData<Double> getDiaryHistory30DaysCost(long date){
        return mSaveInKitchenRepository.getDiaryHistory30DaysCost(date);
    }

    public LiveData<Double> getDiaryHistory30DaysAverage(long date){
        return mSaveInKitchenRepository.getDiaryHistory30DaysAverage(date);
    }

    public LiveData<DiaryHistory> getDiaryHistoryMostExpensive(long date){
        return mSaveInKitchenRepository.getDiaryHistoryMostExpensive(date);
    }

}
