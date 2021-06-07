package async.buyfood;

import android.os.AsyncTask;
import models.BuyFood;
import presistance.SaveInKitchenDao;

public class DeleteBuyFoodAsyncTask extends AsyncTask<BuyFood,Void,Void> {

    private final SaveInKitchenDao mSaveInKitchenDao;

    public DeleteBuyFoodAsyncTask(SaveInKitchenDao mSaveInKitchenDao) {
        this.mSaveInKitchenDao = mSaveInKitchenDao;
    }

    @Override
    protected Void doInBackground(BuyFood... buyFoods) {
        mSaveInKitchenDao.deleteBuyFood(buyFoods);
        return null;
    }
}
