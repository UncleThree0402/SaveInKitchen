package async.buyfood;

import android.os.AsyncTask;
import models.BuyFood;
import presistance.SaveInKitchenDao;

public class InsertBuyFoodAsyncTask extends AsyncTask<BuyFood,Void,Void> {

    private SaveInKitchenDao mSaveInKitchenDao;

    public InsertBuyFoodAsyncTask(SaveInKitchenDao mSaveInKitchenDao) {
        this.mSaveInKitchenDao = mSaveInKitchenDao;
    }

    @Override
    protected Void doInBackground(BuyFood... buyFoods) {
        mSaveInKitchenDao.insertBuyFood(buyFoods);
        return null;
    }
}
