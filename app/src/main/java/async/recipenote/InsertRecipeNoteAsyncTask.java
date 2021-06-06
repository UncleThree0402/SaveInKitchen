package async.recipenote;

import android.os.AsyncTask;
import models.BuyFood;
import models.RecipeNote;
import presistance.SaveInKitchenDao;

public class InsertRecipeNoteAsyncTask extends AsyncTask<RecipeNote,Void,Void> {

    private SaveInKitchenDao mSaveInKitchenDao;

    public InsertRecipeNoteAsyncTask(SaveInKitchenDao mSaveInKitchenDao) {
        this.mSaveInKitchenDao = mSaveInKitchenDao;
    }

    @Override
    protected Void doInBackground(RecipeNote... recipeNotes) {
        mSaveInKitchenDao.insertRecipeNote(recipeNotes);
        return null;
    }
}
