package async.diaryhistory;

import android.os.AsyncTask;
import models.DiaryHistory;
import presistance.SaveInKitchenDao;

public class UpdateDiaryHistoryAsyncTask extends AsyncTask<DiaryHistory,Void,Void> {

    private final SaveInKitchenDao mSaveInKitchenDao;

    public UpdateDiaryHistoryAsyncTask(SaveInKitchenDao mSaveInKitchenDao) {
        this.mSaveInKitchenDao = mSaveInKitchenDao;
    }

    @Override
    protected Void doInBackground(DiaryHistory... diaryHistories) {
        mSaveInKitchenDao.updateDiaryHistory(diaryHistories);
        return null;
    }
}
