package async.buyhistory;

import android.os.AsyncTask;
import models.BuyHistory;
import presistance.SaveInKitchenDao;

public class UpdateBuyHistoryAsyncTask extends AsyncTask<BuyHistory,Void,Void> {

    private SaveInKitchenDao mSaveInKitchenDao;

    public UpdateBuyHistoryAsyncTask(SaveInKitchenDao mSaveInKitchenDao) {
        this.mSaveInKitchenDao = mSaveInKitchenDao;
    }

    @Override
    protected Void doInBackground(BuyHistory... buyHistories) {
        mSaveInKitchenDao.updateBuyHistory(buyHistories);
        return null;
    }
}
