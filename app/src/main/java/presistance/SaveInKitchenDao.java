package presistance;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import models.*;

import java.util.List;

@Dao
public interface SaveInKitchenDao {

    @Insert
    long[] insertBuyFood(BuyFood... buyFoods);

    @Insert
    long[] insertFood(Food... foods);

    @Insert
    long[] insertFoodType(FoodType... foodTypes);

    @Insert
    long[] insertRecipe(Recipe... recipes);

    @Insert
    long[] insertRecipeFood(RecipeFood... recipeFoods);

    @Insert
    long[] insertRecipeNote(RecipeNote... recipeNotes);

    @Insert
    long[] insertBuyHistory(BuyHistory... buyHistories);

    @Query("SELECT * FROM buy_list")
    LiveData<List<BuyFood>> getBuyFood();

    @Query("SELECT food_type.food_type, buy_list.buy_food_status ,buy_list.buy_food_quantity, buy_list.buy_food_unit FROM buy_list " +
            "INNER JOIN food_type " +
            "ON food_type.food_type_id = buy_list.food_type_id")
    LiveData<List<BuyListItem>> getBuyFoodList();

    @Query("SELECT * FROM food")
    LiveData<List<Food>> getFood();

    @Query("SELECT * FROM food WHERE food_type_id = :food_type_id")
    LiveData<List<Food>> getSpecificFoodList(int food_type_id);

    @Query("SELECT * FROM food_type")
    LiveData<List<FoodType>> getFoodType();

    @Query("SELECT food_type FROM food_type")
    LiveData<List<String>> getFoodTypeName();

    @Query("SELECT food_type FROM food_type WHERE food_type = :food_type")
    LiveData<List<String>> getSpecificFoodTypeName(String food_type);

    @Query("SELECT * FROM food_type WHERE food_type = :food_type")
    LiveData<FoodType> getSpecificFoodType(String food_type);

    @Query("SELECT * FROM recipe")
    LiveData<List<Recipe>> getRecipe();

    @Query("SELECT * FROM recipe_food")
    LiveData<List<RecipeFood>> getRecipeFood();

    @Query("SELECT * FROM recipe_note")
    LiveData<List<RecipeNote>> getRecipeNote();

    @Query("SELECT * FROM buy_history")
    LiveData<List<BuyHistory>> getBuyHistory();

    @Query("SELECT SUM(buy_history_cost) FROM buy_history WHERE buy_history_buy_date >= :date")
    LiveData<Double> getBuyHistory30DaysCost(long date);

    @Update
    int updateBuyFood(BuyFood... buyFoods);

    @Update
    int updateFood(Food... foods);

    @Update
    int updateFoodType(FoodType... foodTypes);

    @Update
    int updateRecipe(Recipe... recipes);

    @Update
    int updateRecipeFood(RecipeFood... recipeFoods);

    @Update
    int updateRecipeNote(RecipeNote... recipeNotes);

    @Update
    int updateBuyHistory(BuyHistory... buyHistories);

    @Delete
    int deleteBuyFood(BuyFood... buyFoods);

    @Delete
    int deleteFood(Food... foods);

    @Delete
    int deleteFoodType(FoodType... foodTypes);

    @Delete
    int deleteRecipe(Recipe... recipes);

    @Delete
    int deleteRecipeFood(RecipeFood... recipeFoods);

    @Delete
    int deleteRecipeNote(RecipeNote... recipeNotes);

    @Delete
    int deleteBuyHistory(BuyHistory... buyHistories);

}
