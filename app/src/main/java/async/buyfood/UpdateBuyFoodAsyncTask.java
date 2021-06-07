package async.buyfood;

import android.os.AsyncTask;
import models.BuyFood;
import presistance.SaveInKitchenDao;

public class UpdateBuyFoodAsyncTask extends AsyncTask<BuyFood,Void,Void> {

    private final SaveInKitchenDao mSaveInKitchenDao;

    public UpdateBuyFoodAsyncTask(SaveInKitchenDao mSaveInKitchenDao) {
        this.mSaveInKitchenDao = mSaveInKitchenDao;
    }

    @Override
    protected Void doInBackground(BuyFood... buyFoods) {
        mSaveInKitchenDao.updateBuyFood(buyFoods);
        return null;
    }
}
