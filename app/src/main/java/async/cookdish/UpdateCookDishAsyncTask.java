package async.cookdish;

import android.os.AsyncTask;
import models.CookDish;
import presistance.SaveInKitchenDao;

public class UpdateCookDishAsyncTask extends AsyncTask<CookDish,Void,Void> {

    private final SaveInKitchenDao mSaveInKitchenDao;

    public UpdateCookDishAsyncTask(SaveInKitchenDao mSaveInKitchenDao) {
        this.mSaveInKitchenDao = mSaveInKitchenDao;
    }

    @Override
    protected Void doInBackground(CookDish... cookDishes) {
        mSaveInKitchenDao.updateCookDish(cookDishes);
        return null;
    }
}
