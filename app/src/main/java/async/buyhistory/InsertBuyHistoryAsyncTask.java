package async.buyhistory;

import android.os.AsyncTask;
import models.BuyHistory;
import presistance.SaveInKitchenDao;

public class InsertBuyHistoryAsyncTask extends AsyncTask<BuyHistory,Void,Void> {

    private final SaveInKitchenDao mSaveInKitchenDao;

    public InsertBuyHistoryAsyncTask(SaveInKitchenDao mSaveInKitchenDao) {
        this.mSaveInKitchenDao = mSaveInKitchenDao;
    }

    @Override
    protected Void doInBackground(BuyHistory... buyHistories) {
        mSaveInKitchenDao.insertBuyHistory(buyHistories);
        return null;
    }
}
