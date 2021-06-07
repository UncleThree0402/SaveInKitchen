package viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import models.RecipeFood;
import models.RecipeNote;
import presistance.SaveInKitchenRepository;

import java.util.List;

public class RecipeNoteViewModel extends AndroidViewModel {

    private final SaveInKitchenRepository mSaveInKitchenRepository;

    private final LiveData<List<RecipeNote>> mRecipeNote;

    public RecipeNoteViewModel(@NonNull Application application) {
        super(application);
        mSaveInKitchenRepository = new SaveInKitchenRepository(application);
        mRecipeNote = mSaveInKitchenRepository.getRecipeNote();
    }

    public void insertRecipeNote(RecipeNote recipeNote){
        mSaveInKitchenRepository.insertRecipeNote(recipeNote);
    }

    public void updateRecipeNote(RecipeNote recipeNote){
        mSaveInKitchenRepository.updateRecipeNote(recipeNote);
    }

    public void deleteRecipeNote(RecipeNote recipeNote){
        mSaveInKitchenRepository.deleteRecipeNote(recipeNote);
    }

    public LiveData<List<RecipeNote>> getRecipeNote(){
        return mRecipeNote;
    }
}
