package viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import models.Recipe;
import presistance.SaveInKitchenRepository;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {

    private SaveInKitchenRepository mSaveInKitchenRepository;

    private LiveData<List<Recipe>> mRecipe;

    public RecipeViewModel(@NonNull Application application) {
        super(application);
        mSaveInKitchenRepository = new SaveInKitchenRepository(application);
        mRecipe = mSaveInKitchenRepository.getRecipe();
    }

    public void insertRecipe(Recipe recipe){
        mSaveInKitchenRepository.insertRecipe(recipe);
    }

    public void updateRecipe(Recipe recipe){
        mSaveInKitchenRepository.updateRecipe(recipe);
    }

    public void deleteRecipe(Recipe recipe){
        mSaveInKitchenRepository.deleteRecipe(recipe);
    }

    public LiveData<List<Recipe>> getRecipe(){
        return mRecipe;
    }

    public LiveData<List<Recipe>> getSearchRecipe(String name){
        return mSaveInKitchenRepository.getSearchRecipe("%" + name + "%");
    }
}
