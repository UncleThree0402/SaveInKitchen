package async.recipenote;

import android.os.AsyncTask;
import models.RecipeNote;
import presistance.SaveInKitchenDao;

public class UpdateRecipeNoteAsyncTask extends AsyncTask<RecipeNote,Void,Void> {

    private SaveInKitchenDao mSaveInKitchenDao;

    public UpdateRecipeNoteAsyncTask(SaveInKitchenDao mSaveInKitchenDao) {
        this.mSaveInKitchenDao = mSaveInKitchenDao;
    }

    @Override
    protected Void doInBackground(RecipeNote... recipeNotes) {
        mSaveInKitchenDao.updateRecipeNote(recipeNotes);
        return null;
    }
}
