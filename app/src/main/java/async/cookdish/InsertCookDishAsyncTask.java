package async.cookdish;

import android.os.AsyncTask;
import models.CookDish;
import presistance.SaveInKitchenDao;

public class InsertCookDishAsyncTask extends AsyncTask<CookDish,Void,Void> {

    private final SaveInKitchenDao mSaveInKitchenDao;

    public InsertCookDishAsyncTask(SaveInKitchenDao mSaveInKitchenDao) {
        this.mSaveInKitchenDao = mSaveInKitchenDao;
    }

    @Override
    protected Void doInBackground(CookDish... cookDishes) {
        mSaveInKitchenDao.insertCookDish(cookDishes);
        return null;
    }
}
