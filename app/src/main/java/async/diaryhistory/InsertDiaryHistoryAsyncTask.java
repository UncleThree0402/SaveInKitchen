package async.diaryhistory;

import android.os.AsyncTask;
import models.DiaryHistory;
import presistance.SaveInKitchenDao;

public class InsertDiaryHistoryAsyncTask extends AsyncTask<DiaryHistory,Void,Void> {

    private final SaveInKitchenDao mSaveInKitchenDao;

    public InsertDiaryHistoryAsyncTask(SaveInKitchenDao mSaveInKitchenDao) {
        this.mSaveInKitchenDao = mSaveInKitchenDao;
    }

    @Override
    protected Void doInBackground(DiaryHistory... diaryHistories) {
        mSaveInKitchenDao.insertDiaryHistory(diaryHistories);
        return null;
    }
}
