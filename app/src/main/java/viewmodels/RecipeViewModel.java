package viewmodels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Ignore;
import models.Recipe;
import presistance.SaveInKitchenRepository;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {

    private final SaveInKitchenRepository mSaveInKitchenRepository;

    private final LiveData<List<Recipe>> mRecipe;

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

    public LiveData<Recipe> getSpecificRecipe(String name){
        return mSaveInKitchenRepository.getSpecificRecipe(name);
    }

    public LiveData<List<Recipe>> getSearchRecipe(String name){
        return mSaveInKitchenRepository.getSearchRecipe("%" + name + "%");
    }
}
