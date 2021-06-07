package async.recipefood;

import android.os.AsyncTask;
import models.BuyFood;
import models.RecipeFood;
import presistance.SaveInKitchenDao;

public class InsertRecipeFoodAsyncTask extends AsyncTask<RecipeFood,Void,Void> {

    private final SaveInKitchenDao mSaveInKitchenDao;

    public InsertRecipeFoodAsyncTask(SaveInKitchenDao mSaveInKitchenDao) {
        this.mSaveInKitchenDao = mSaveInKitchenDao;
    }

    @Override
    protected Void doInBackground(RecipeFood... recipeFoods) {
        mSaveInKitchenDao.insertRecipeFood(recipeFoods);
        return null;
    }
}
