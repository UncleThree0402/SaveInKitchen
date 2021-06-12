package async.dishes;

import android.os.AsyncTask;
import models.Dishes;
import models.Food;
import presistance.SaveInKitchenDao;

public class InsertDishesAsyncTask extends AsyncTask<Dishes,Void,Void> {

    private final SaveInKitchenDao mSaveInKitchenDao;

    public InsertDishesAsyncTask(SaveInKitchenDao mSaveInKitchenDao) {
        this.mSaveInKitchenDao = mSaveInKitchenDao;
    }

    @Override
    protected Void doInBackground(Dishes... dishes) {
        mSaveInKitchenDao.insertDishes(dishes);
        return null;
    }
}
