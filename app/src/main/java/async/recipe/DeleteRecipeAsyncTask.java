package async.recipe;

import android.os.AsyncTask;
import models.Recipe;
import presistance.SaveInKitchenDao;

public class DeleteRecipeAsyncTask extends AsyncTask<Recipe,Void,Void> {

    private SaveInKitchenDao mSaveInKitchenDao;

    public DeleteRecipeAsyncTask(SaveInKitchenDao mSaveInKitchenDao) {
        this.mSaveInKitchenDao = mSaveInKitchenDao;
    }

    @Override
    protected Void doInBackground(Recipe... recipes) {
        mSaveInKitchenDao.deleteRecipe(recipes);
        return null;
    }
}
