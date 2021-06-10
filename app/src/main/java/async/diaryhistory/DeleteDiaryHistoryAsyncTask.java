package async.diaryhistory;

import android.os.AsyncTask;
import models.DiaryHistory;
import presistance.SaveInKitchenDao;

public class DeleteDiaryHistoryAsyncTask extends AsyncTask<DiaryHistory,Void,Void> {

    private final SaveInKitchenDao mSaveInKitchenDao;

    public DeleteDiaryHistoryAsyncTask(SaveInKitchenDao mSaveInKitchenDao) {
        this.mSaveInKitchenDao = mSaveInKitchenDao;
    }

    @Override
    protected Void doInBackground(DiaryHistory... diaryHistories) {
        mSaveInKitchenDao.deleteDiaryHistory(diaryHistories);
        return null;
    }
}
