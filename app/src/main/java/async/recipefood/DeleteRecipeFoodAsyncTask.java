package async.recipefood;

import android.os.AsyncTask;
import models.RecipeFood;
import presistance.SaveInKitchenDao;

public class DeleteRecipeFoodAsyncTask extends AsyncTask<RecipeFood,Void,Void> {

    private final SaveInKitchenDao mSaveInKitchenDao;

    public DeleteRecipeFoodAsyncTask(SaveInKitchenDao mSaveInKitchenDao) {
        this.mSaveInKitchenDao = mSaveInKitchenDao;
    }

    @Override
    protected Void doInBackground(RecipeFood... recipeFoods) {
        mSaveInKitchenDao.deleteRecipeFood(recipeFoods);
        return null;
    }
}
