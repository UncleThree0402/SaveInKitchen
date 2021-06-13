package async.cookdishingredient;

import android.os.AsyncTask;
import models.CookDishIngredient;
import presistance.SaveInKitchenDao;

public class UpdateCookDishIngredientAsyncTask extends AsyncTask<CookDishIngredient,Void,Void> {

    private final SaveInKitchenDao mSaveInKitchenDao;

    public UpdateCookDishIngredientAsyncTask(SaveInKitchenDao mSaveInKitchenDao) {
        this.mSaveInKitchenDao = mSaveInKitchenDao;
    }

    @Override
    protected Void doInBackground(CookDishIngredient... cookDishIngredients) {
        mSaveInKitchenDao.updateCookDishIngredient(cookDishIngredients);
        return null;
    }
}
