package presistance;

import android.content.Context;
import androidx.lifecycle.LiveData;
import async.buyfood.DeleteBuyFoodAsyncTask;
import async.buyfood.InsertBuyFoodAsyncTask;
import async.buyfood.UpdateBuyFoodAsyncTask;
import async.buyhistory.DeleteBuyHistoryAsyncTask;
import async.buyhistory.InsertBuyHistoryAsyncTask;
import async.buyhistory.UpdateBuyHistoryAsyncTask;
import async.cookdish.DeleteCookDishHistoryAsyncTask;
import async.cookdish.InsertCookDishAsyncTask;
import async.cookdish.UpdateCookDishAsyncTask;
import async.cookdishingredient.DeleteCookDishIngredientAsyncTask;
import async.cookdishingredient.InsertCookDishIngredientAsyncTask;
import async.cookdishingredient.UpdateCookDishIngredientAsyncTask;
import async.diaryhistory.DeleteDiaryHistoryAsyncTask;
import async.diaryhistory.InsertDiaryHistoryAsyncTask;
import async.diaryhistory.UpdateDiaryHistoryAsyncTask;
import async.dishes.DeleteDishesAsyncTask;
import async.dishes.InsertDishesAsyncTask;
import async.dishes.UpdateDishesAsyncTask;
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
import models.*;

import java.util.List;

public class SaveInKitchenRepository {

    private final SaveInKitchenDatabase mSaveInKitchenDatabase;

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


    public void insertBuyHistory(BuyHistory buyHistory) {
        new InsertBuyHistoryAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(buyHistory);
    }

    public void insertDiaryHistory(DiaryHistory diaryHistory){
        new InsertDiaryHistoryAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(diaryHistory);
    }

    public void insertDishes(Dishes dishes){
        new InsertDishesAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(dishes);
    }
    
    public void insertCookDish(CookDish cookDish){
        new InsertCookDishAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(cookDish);
    }

    public void insertCookDishIngredient(CookDishIngredient cookDishIngredient){
        new InsertCookDishIngredientAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(cookDishIngredient);
    }

    public LiveData<List<BuyFood>> getBuyFood() {
        return mSaveInKitchenDatabase.getSaveForFoodDao().getBuyFood();
    }

    public LiveData<BuyFood> getSpecificBuyFood(int id) {
        return mSaveInKitchenDatabase.getSaveForFoodDao().getSpecificBuyFood(id);
    }

    public LiveData<List<BuyFood>> getSpecificTypeBuyFood(String buy_food_status, String name){
        return mSaveInKitchenDatabase.getSaveForFoodDao().getSpecificTypeBuyFood(buy_food_status, name);
    }

    public LiveData<List<BuyFood>> getSpecificNameBuyFood(String name){
        return mSaveInKitchenDatabase.getSaveForFoodDao().getSpecificNameBuyFood(name);
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

    public LiveData<List<Food>> getSpecificTypeFood(String food_status , String name){
        return mSaveInKitchenDatabase.getSaveForFoodDao().getSpecificTypeFood(food_status,name);
    }

    public LiveData<Food> getSpecificFoodById(int food_id){
        return mSaveInKitchenDatabase.getSaveForFoodDao().getSpecificFoodById(food_id);
    }

    public LiveData<List<FoodType>> getFoodType() {
        return mSaveInKitchenDatabase.getSaveForFoodDao().getFoodType();
    }

    public LiveData<List<FoodType>> getSearchFoodType(String name){
        return mSaveInKitchenDatabase.getSaveForFoodDao().getSearchFoodType(name);
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

    public LiveData<Recipe> getSpecificRecipe(String name){
        return mSaveInKitchenDatabase.getSaveForFoodDao().getSpecificRecipe(name);
    }

    public LiveData<List<Recipe>> getSearchRecipe(String name) {
        return mSaveInKitchenDatabase.getSaveForFoodDao().getSearchRecipe(name);
    }

    public LiveData<Recipe> getIdRecipe(int id){
        return mSaveInKitchenDatabase.getSaveForFoodDao().getIdRecipe(id);
    }

    public LiveData<List<RecipeFood>> getRecipeFood() {
        return mSaveInKitchenDatabase.getSaveForFoodDao().getRecipeFood();
    }

    public LiveData<List<RecipeFood>> getSpecificRecipeFood(int id){
        return mSaveInKitchenDatabase.getSaveForFoodDao().getSpecificRecipeFood(id);
    }

    public LiveData<List<RecipeFood>> getSpecificNameRecipeFood(String name){
        return mSaveInKitchenDatabase.getSaveForFoodDao().getSpecificNameRecipeFood(name);
    }


    public LiveData<List<BuyHistory>> getBuyHistory() {
        return mSaveInKitchenDatabase.getSaveForFoodDao().getBuyHistory();
    }

    public LiveData<Double> getBuyHistory30DaysCost(long date) {
        return mSaveInKitchenDatabase.getSaveForFoodDao().getBuyHistory30DaysCost(date);
    }

    public LiveData<Double> getBuyHistory30DaysAverage(long date){
        return mSaveInKitchenDatabase.getSaveForFoodDao().getBuyHistory30DaysAverage(date);
    }

    public LiveData<BuyHistory> getBuyHistoryMostExpensive(long date){
        return mSaveInKitchenDatabase.getSaveForFoodDao().getBuyHistoryMostExpensive(date);
    }

    public LiveData<List<BuyHistory>> getBuyHistory30Days(long dateIn) {
        return mSaveInKitchenDatabase.getSaveForFoodDao().getBuyHistory30Days(dateIn);
    }

    public LiveData<List<BuyHistory>> getSearchBuyHistory(String name) {
        return mSaveInKitchenDatabase.getSaveForFoodDao().getSearchBuyHistory(name);
    }

    public LiveData<List<DiaryHistory>> getDiaryHistory(){
        return mSaveInKitchenDatabase.getSaveForFoodDao().getDiaryHistory();
    }

    public LiveData<List<DiaryHistory>> getDiaryHistoryByDate(String type, long dateIn, long dateOut){
        return mSaveInKitchenDatabase.getSaveForFoodDao().getDiaryHistoryByDate(type,dateIn,dateOut);
    }

    public LiveData<List<DiaryHistory>> getDiaryHistory30Days(long dateIn){
        return mSaveInKitchenDatabase.getSaveForFoodDao().getDiaryHistory30Days(dateIn);
    }

    public LiveData<Double> getTotalCostDiaryHistory(long dateIn,long dateOut){
        return mSaveInKitchenDatabase.getSaveForFoodDao().getTotalCostDiaryHistory(dateIn,dateOut);
    }

    public LiveData<Double> getDiaryHistory30DaysCost(long date) {
        return mSaveInKitchenDatabase.getSaveForFoodDao().getCostDiaryHistory30DaysCost(date);
    }

    public LiveData<Double> getDiaryHistory30DaysAverage(long date){
        return mSaveInKitchenDatabase.getSaveForFoodDao().getCostDiaryHistory30DaysAverage(date);
    }

    public LiveData<DiaryHistory> getDiaryHistoryMostExpensive(long date){
        return mSaveInKitchenDatabase.getSaveForFoodDao().getCostDiaryHistoryMostExpensive(date);
    }

    public LiveData<List<Dishes>> getDishes(){
        return mSaveInKitchenDatabase.getSaveForFoodDao().getDishes();
    }

    public LiveData<List<Dishes>> getSearchDish(String name){
        return mSaveInKitchenDatabase.getSaveForFoodDao().getSearchDishes(name);
    }

    public LiveData<List<CookDish>> getCookDish(){
        return mSaveInKitchenDatabase.getSaveForFoodDao().getCookDishes();
    }

    public LiveData<List<CookDish>> getSearchCookDish(String name){
        return mSaveInKitchenDatabase.getSaveForFoodDao().getSearchCookDishes(name);
    }

    public LiveData<CookDish> getCookDishesById(int cook_dish_id){
        return mSaveInKitchenDatabase.getSaveForFoodDao().getCookDishesById(cook_dish_id);
    }

    public LiveData<List<CookDishIngredient>> getCookDishIngredient(){
        return mSaveInKitchenDatabase.getSaveForFoodDao().getCookDishIngredient();
    }

    public LiveData<List<CookDishIngredient>> getSpecificCookDishIngredient(int recipe_food_id, int cook_dish_id){
        return  mSaveInKitchenDatabase.getSaveForFoodDao().getSpecificCookDishIngredient(recipe_food_id, cook_dish_id);
    }

    public LiveData<Double> getSumOfSpecificCookDishIngredient(int food_id){
        return mSaveInKitchenDatabase.getSaveForFoodDao().getSumOfSpecificCookDishIngredient(food_id);
    }

    public LiveData<CookDishIngredient> getSpecificCookDishIngredientById(int food_id){
        return mSaveInKitchenDatabase.getSaveForFoodDao().getSpecificCookDishIngredientById(food_id);
    }

    public LiveData<List<CookDishIngredient>> getSpecificCookDishIngByDish(int cook_dish_id){
        return mSaveInKitchenDatabase.getSaveForFoodDao().getSpecificCookDishIngByDish(cook_dish_id);
    }

    public LiveData<Double> getCostOfDish(int cook_dish_id){
        return mSaveInKitchenDatabase.getSaveForFoodDao().getCostOfDish(cook_dish_id);
    }

    public LiveData<List<CookDishIngredientView>> getCookDishIngredientView(int cook_dish_id, int recipe_food_id){
        return mSaveInKitchenDatabase.getSaveForFoodDao().getCookDishIngredientView(cook_dish_id,recipe_food_id);
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

    public void updateBuyHistory(BuyHistory buyHistory) {
        new UpdateBuyHistoryAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(buyHistory);
    }

    public void updateDiaryHistory(DiaryHistory diaryHistory){
        new UpdateDiaryHistoryAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(diaryHistory);
    }

    public void updateDishes(Dishes dishes){
        new UpdateDishesAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(dishes);
    }

    public void updateCookDish(CookDish cookDish){
        new UpdateCookDishAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(cookDish);
    }

    public void updateCookDishIngredient(CookDishIngredient cookDishIngredient){
        new UpdateCookDishIngredientAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(cookDishIngredient);
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

    public void deleteBuyHistory(BuyHistory buyHistory) {
        new DeleteBuyHistoryAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(buyHistory);
    }

    public void deleteDiaryHistory(DiaryHistory diaryHistory){
        new DeleteDiaryHistoryAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(diaryHistory);
    }

    public void deleteDishes(Dishes dishes){
        new DeleteDishesAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(dishes);
    }

    public void deleteCookDish(CookDish cookDish){
        new DeleteCookDishHistoryAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(cookDish);
    }

    public void deleteCookDishIngredient(CookDishIngredient cookDishIngredient){
        new DeleteCookDishIngredientAsyncTask(mSaveInKitchenDatabase.getSaveForFoodDao()).execute(cookDishIngredient);
    }

}
