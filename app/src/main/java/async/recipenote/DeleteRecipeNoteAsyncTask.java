package async.recipenote;

import android.os.AsyncTask;
import models.RecipeNote;
import presistance.SaveInKitchenDao;

public class DeleteRecipeNoteAsyncTask extends AsyncTask<RecipeNote,Void,Void> {

    private SaveInKitchenDao mSaveInKitchenDao;

    public DeleteRecipeNoteAsyncTask(SaveInKitchenDao mSaveInKitchenDao) {
        this.mSaveInKitchenDao = mSaveInKitchenDao;
    }

    @Override
    protected Void doInBackground(RecipeNote... recipeNotes) {
        mSaveInKitchenDao.deleteRecipeNote(recipeNotes);
        return null;
    }
}
