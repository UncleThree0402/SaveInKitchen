package async.food;

import android.os.AsyncTask;
import models.Food;
import presistance.SaveInKitchenDao;

public class DeleteFoodAsyncTask extends AsyncTask<Food,Void,Void> {

    private final SaveInKitchenDao mSaveInKitchenDao;

    public DeleteFoodAsyncTask(SaveInKitchenDao mSaveInKitchenDao) {
        this.mSaveInKitchenDao = mSaveInKitchenDao;
    }

    @Override
    protected Void doInBackground(Food... foods) {
        mSaveInKitchenDao.deleteFood(foods);
        return null;
    }
}
