package async.dishes;

import android.os.AsyncTask;
import models.Dishes;
import models.Food;
import presistance.SaveInKitchenDao;

public class DeleteDishesAsyncTask extends AsyncTask<Dishes,Void,Void> {

    private final SaveInKitchenDao mSaveInKitchenDao;

    public DeleteDishesAsyncTask(SaveInKitchenDao mSaveInKitchenDao) {
        this.mSaveInKitchenDao = mSaveInKitchenDao;
    }

    @Override
    protected Void doInBackground(Dishes... dishes) {
        mSaveInKitchenDao.deleteDishes(dishes);
        return null;
    }
}
