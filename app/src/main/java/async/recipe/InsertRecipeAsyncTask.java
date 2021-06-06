package async.recipe;

import android.os.AsyncTask;
import models.BuyFood;
import models.Recipe;
import presistance.SaveInKitchenDao;

public class InsertRecipeAsyncTask extends AsyncTask<Recipe,Void,Void> {

    private SaveInKitchenDao mSaveInKitchenDao;

    public InsertRecipeAsyncTask(SaveInKitchenDao mSaveInKitchenDao) {
        this.mSaveInKitchenDao = mSaveInKitchenDao;
    }

    @Override
    protected Void doInBackground(Recipe... recipes) {
        mSaveInKitchenDao.insertRecipe(recipes);
        return null;
    }
}
