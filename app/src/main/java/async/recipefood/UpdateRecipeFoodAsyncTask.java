package async.recipefood;

import android.os.AsyncTask;
import models.RecipeFood;
import presistance.SaveInKitchenDao;

public class UpdateRecipeFoodAsyncTask extends AsyncTask<RecipeFood,Void,Void> {

    private final SaveInKitchenDao mSaveInKitchenDao;

    public UpdateRecipeFoodAsyncTask(SaveInKitchenDao mSaveInKitchenDao) {
        this.mSaveInKitchenDao = mSaveInKitchenDao;
    }

    @Override
    protected Void doInBackground(RecipeFood... recipeFoods) {
        mSaveInKitchenDao.updateRecipeFood(recipeFoods);
        return null;
    }
}
