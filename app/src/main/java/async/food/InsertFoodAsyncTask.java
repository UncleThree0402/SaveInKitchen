package async.food;

import android.os.AsyncTask;
import models.BuyFood;
import models.Food;
import presistance.SaveInKitchenDao;

public class InsertFoodAsyncTask extends AsyncTask<Food,Void,Void> {

    private SaveInKitchenDao mSaveInKitchenDao;

    public InsertFoodAsyncTask(SaveInKitchenDao mSaveInKitchenDao) {
        this.mSaveInKitchenDao = mSaveInKitchenDao;
    }

    @Override
    protected Void doInBackground(Food... foods) {
        mSaveInKitchenDao.insertFood(foods);
        return null;
    }
}
