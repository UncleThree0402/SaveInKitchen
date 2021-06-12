package async.dishes;

import android.os.AsyncTask;
import models.Dishes;
import models.Food;
import presistance.SaveInKitchenDao;

public class UpdateDishesAsyncTask extends AsyncTask<Dishes,Void,Void> {

    private final SaveInKitchenDao mSaveInKitchenDao;

    public UpdateDishesAsyncTask(SaveInKitchenDao mSaveInKitchenDao) {
        this.mSaveInKitchenDao = mSaveInKitchenDao;
    }

    @Override
    protected Void doInBackground(Dishes... dishes) {
        mSaveInKitchenDao.updateDishes(dishes);
        return null;
    }
}
