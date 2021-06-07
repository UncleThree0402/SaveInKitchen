package async.buyhistory;

import android.os.AsyncTask;
import models.BuyFood;
import models.BuyHistory;
import presistance.SaveInKitchenDao;

public class DeleteBuyHistoryAsyncTask extends AsyncTask<BuyHistory,Void,Void> {

    private final SaveInKitchenDao mSaveInKitchenDao;

    public DeleteBuyHistoryAsyncTask(SaveInKitchenDao mSaveInKitchenDao) {
        this.mSaveInKitchenDao = mSaveInKitchenDao;
    }

    @Override
    protected Void doInBackground(BuyHistory... buyHistories) {
        mSaveInKitchenDao.deleteBuyHistory(buyHistories);
        return null;
    }
}
