package async.cookdishingredient;

import android.os.AsyncTask;
import models.CookDishIngredient;
import presistance.SaveInKitchenDao;

public class DeleteCookDishIngredientAsyncTask extends AsyncTask<CookDishIngredient,Void,Void> {

    private final SaveInKitchenDao mSaveInKitchenDao;

    public DeleteCookDishIngredientAsyncTask(SaveInKitchenDao mSaveInKitchenDao) {
        this.mSaveInKitchenDao = mSaveInKitchenDao;
    }

    @Override
    protected Void doInBackground(CookDishIngredient... cookDishIngredients) {
        mSaveInKitchenDao.deleteCookDishIngredient(cookDishIngredients);
        return null;
    }
}
