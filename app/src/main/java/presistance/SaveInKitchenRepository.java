package presistance;

import android.content.Context;
import androidx.lifecycle.LiveData;
import async.buyfood.DeleteBuyFoodAsyncTask;
import async.buyfood.InsertBuyFoodAsyncTask;
import async.buyfood.UpdateBuyFoodAsyncTask;
import async.buyhistory.DeleteBuyHistoryAsyncTask;
import async.buyhistory.InsertBuyHistoryAsyncTask;
import async.buyhistory.UpdateBuyHistoryAsyncTask;
import async.food.DeleteFoodAsyncTask;
import async.food.InsertFoodAsyncTask;
import async.food.UpdateFoodAsyncTask;
import async.foodtype.DeleteFoodTypeAsyncTask;
import async.foodtype.InsertFoodTypeAsyncTask;
import async.foodtype.UpdateFoodTypeAsyncTask;
import async.recipe.DeleteRecipeAsyncTask;
import async.recipe.InsertRecipeAsyncTask;
import async.recipe.UpdateRecipeAsyncTask;
import async.recipefood.DeleteRecipeFoodAsyncTask;
import async.recipefood.InsertRecipeFoodAsyncTask;
import async.recipefood.UpdateRecipeFoodAsyncTask;
import async.recipenote.DeleteRecipeNoteAsyncTask;
import async.recipenote.InsertRecipeNoteAsyncTask;
import async.recipenote.UpdateRecipeNoteAsyncTask;
import models.*;

import java.util.List;

public class SaveInKitchenRepository {

    private SaveInKitchenDatabase mSaveInKitchenDatabase;

    public SaveInKitchenRepository(Context context) {
        mSaveInKitchenDatabase = SaveInKitchenDatabase.getInstance(context);
    }

    public void insertBuyFood(BuyFood buyFood) {
        new InsertBuyFoodAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(buyFood);
    }

    public void insertFood(Food food) {
        new InsertFoodAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(food);
    }

    public void insertFoodType(FoodType foodType) {
        new InsertFoodTypeAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(foodType);
    }

    public void insertRecipe(Recipe recipe) {
        new InsertRecipeAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(recipe);
    }

    public void insertRecipeFood(RecipeFood recipeFood) {
        new InsertRecipeFoodAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(recipeFood);
    }

    public void insertRecipeNote(RecipeNote recipeNote) {
        new InsertRecipeNoteAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(recipeNote);
    }

    public void insertBuyHistory(BuyHistory buyHistory) {
        new InsertBuyHistoryAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(buyHistory);
    }

    public LiveData<List<BuyFood>> getBuyFood() {
        return mSaveInKitchenDatabase.getSaveForFoodDao().getBuyFood();
    }

    public LiveData<BuyFood> getSpecificBuyFood(int id) {
        return mSaveInKitchenDatabase.getSaveForFoodDao().getSpecificBuyFood(id);
    }

    public LiveData<List<Food>> getFood() {
        return mSaveInKitchenDatabase.getSaveForFoodDao().getFood();
    }

    public LiveData<List<Food>> getSpecificFoodList(int id) {
        return mSaveInKitchenDatabase.getSaveForFoodDao().getSpecificFoodList(id);
    }

    public LiveData<List<Food>> getSearchFood(String name) {
        return mSaveInKitchenDatabase.getSaveForFoodDao().getSearchFood(name);
    }

    public LiveData<Integer> getFoodTypeCount(int id) {
        return mSaveInKitchenDatabase.getSaveForFoodDao().getFoodTypeCount(id);
    }

    public LiveData<List<FoodType>> getFoodType() {
        return mSaveInKitchenDatabase.getSaveForFoodDao().getFoodType();
    }

    public LiveData<List<String>> getFoodTypeName() {
        return mSaveInKitchenDatabase.getSaveForFoodDao().getFoodTypeName();
    }

    public LiveData<List<String>> getSpecificFoodTypeName(String name) {
        return mSaveInKitchenDatabase.getSaveForFoodDao().getSpecificFoodTypeName(name);
    }

    public LiveData<FoodType> getSpecificFoodType(String name) {
        return mSaveInKitchenDatabase.getSaveForFoodDao().getSpecificFoodType(name);
    }

    public LiveData<List<Recipe>> getRecipe() {
        return mSaveInKitchenDatabase.getSaveForFoodDao().getRecipe();
    }

    public LiveData<List<Recipe>> getSearchRecipe(String name) {
        return mSaveInKitchenDatabase.getSaveForFoodDao().getSearchRecipe(name);
    }

    public LiveData<List<RecipeFood>> getRecipeFood() {
        return mSaveInKitchenDatabase.getSaveForFoodDao().getRecipeFood();
    }

    public LiveData<List<RecipeNote>> getRecipeNote() {
        return mSaveInKitchenDatabase.getSaveForFoodDao().getRecipeNote();
    }

    public LiveData<List<BuyHistory>> getBuyHistory() {
        return mSaveInKitchenDatabase.getSaveForFoodDao().getBuyHistory();
    }

    public LiveData<Double> getBuyHistory30DaysCost(long date) {
        return mSaveInKitchenDatabase.getSaveForFoodDao().getBuyHistory30DaysCost(date);
    }

    public LiveData<List<BuyHistory>> getBuyHistory30Days(long dateIn) {
        return mSaveInKitchenDatabase.getSaveForFoodDao().getBuyHistory30Days(dateIn);
    }

    public LiveData<List<BuyHistory>> getSearchBuyHistory(String name) {
        return mSaveInKitchenDatabase.getSaveForFoodDao().getSearchBuyHistory(name);
    }

    public void updateBuyFood(BuyFood buyFood) {
        new UpdateBuyFoodAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(buyFood);
    }

    public void updateFood(Food food) {
        new UpdateFoodAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(food);
    }

    public void updateFoodType(FoodType foodType) {
        new UpdateFoodTypeAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(foodType);
    }

    public void updateRecipe(Recipe recipe) {
        new UpdateRecipeAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(recipe);
    }

    public void updateRecipeFood(RecipeFood recipeFood) {
        new UpdateRecipeFoodAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(recipeFood);
    }

    public void updateRecipeNote(RecipeNote recipeNote) {
        new UpdateRecipeNoteAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(recipeNote);
    }

    public void updateBuyHistory(BuyHistory buyHistory) {
        new UpdateBuyHistoryAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(buyHistory);
    }

    public void deleteBuyFood(BuyFood buyFood) {
        new DeleteBuyFoodAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(buyFood);
    }

    public void deleteFood(Food food) {
        new DeleteFoodAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(food);
    }

    public void deleteFoodType(FoodType foodType) {
        new DeleteFoodTypeAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(foodType);
    }

    public void deleteRecipe(Recipe recipe) {
        new DeleteRecipeAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(recipe);
    }

    public void deleteRecipeFood(RecipeFood recipeFood) {
        new DeleteRecipeFoodAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(recipeFood);
    }

    public void deleteRecipeNote(RecipeNote recipeNote) {
        new DeleteRecipeNoteAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(recipeNote);
    }

    public void deleteBuyHistory(BuyHistory buyHistory) {
        new DeleteBuyHistoryAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(buyHistory);
    }


}
