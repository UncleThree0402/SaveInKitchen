package async.cookdishingredient;

import android.os.AsyncTask;
import models.CookDishIngredient;
import presistance.SaveInKitchenDao;

public class InsertCookDishIngredientAsyncTask extends AsyncTask<CookDishIngredient,Void,Void> {

    private final SaveInKitchenDao mSaveInKitchenDao;

    public InsertCookDishIngredientAsyncTask(SaveInKitchenDao mSaveInKitchenDao) {
        this.mSaveInKitchenDao = mSaveInKitchenDao;
    }

    @Override
    protected Void doInBackground(CookDishIngredient... cookDishIngredients) {
        mSaveInKitchenDao.insertCookDishIngredient(cookDishIngredients);
        return null;
    }
}
