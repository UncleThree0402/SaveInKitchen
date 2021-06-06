package async.food;

import android.os.AsyncTask;
import models.BuyFood;
import models.Food;
import presistance.SaveInKitchenDao;

public class UpdateFoodAsyncTask extends AsyncTask<Food,Void,Void> {

    private SaveInKitchenDao mSaveInKitchenDao;

    public UpdateFoodAsyncTask(SaveInKitchenDao mSaveInKitchenDao) {
        this.mSaveInKitchenDao = mSaveInKitchenDao;
    }

    @Override
    protected Void doInBackground(Food... foods) {
        mSaveInKitchenDao.updateFood(foods);
        return null;
    }
}
