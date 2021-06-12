package async.cookdish;

import android.os.AsyncTask;
import models.CookDish;
import presistance.SaveInKitchenDao;

public class DeleteCookDishHistoryAsyncTask extends AsyncTask<CookDish,Void,Void> {

    private final SaveInKitchenDao mSaveInKitchenDao;

    public DeleteCookDishHistoryAsyncTask(SaveInKitchenDao mSaveInKitchenDao) {
        this.mSaveInKitchenDao = mSaveInKitchenDao;
    }

    @Override
    protected Void doInBackground(CookDish... cookDishes) {
        mSaveInKitchenDao.deleteCookDish(cookDishes);
        return null;
    }
}
