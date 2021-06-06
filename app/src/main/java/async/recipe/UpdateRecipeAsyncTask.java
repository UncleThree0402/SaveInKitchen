package async.recipe;

import android.os.AsyncTask;
import models.Recipe;
import presistance.SaveInKitchenDao;

public class UpdateRecipeAsyncTask extends AsyncTask<Recipe,Void,Void> {

    private SaveInKitchenDao mSaveInKitchenDao;

    public UpdateRecipeAsyncTask(SaveInKitchenDao mSaveInKitchenDao) {
        this.mSaveInKitchenDao = mSaveInKitchenDao;
    }

    @Override
    protected Void doInBackground(Recipe... recipes) {
        mSaveInKitchenDao.updateRecipe(recipes);
        return null;
    }
}
