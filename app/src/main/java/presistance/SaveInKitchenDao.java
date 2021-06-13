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
    long[] insertBuyHistory(BuyHistory... buyHistories);

    @Insert
    long[] insertDiaryHistory(DiaryHistory... diaryHistories);

    @Insert
    long[] insertDishes(Dishes... dishes);

    @Insert
    long[] insertCookDish(CookDish... cookDishes);

    @Insert
    long[] insertCookDishIngredient(CookDishIngredient... cookDishIngredients);

    @Query("SELECT * FROM buy_list")
    LiveData<List<BuyFood>> getBuyFood();

    @Query("SELECT * FROM buy_list WHERE buy_list_id = :buy_list_id")
    LiveData<BuyFood> getSpecificBuyFood(int buy_list_id);

    @Query("SELECT * FROM buy_list WHERE buy_food_status = :buy_food_status AND buy_food_name LIKE :name")
    LiveData<List<BuyFood>> getSpecificTypeBuyFood(String buy_food_status, String name);

    @Query("SELECT * FROM buy_list WHERE  buy_food_name LIKE :name")
    LiveData<List<BuyFood>> getSpecificNameBuyFood(String name);

    @Query("SELECT * FROM food")
    LiveData<List<Food>> getFood();

    @Query("SELECT * FROM food WHERE food_type_id = :food_type_id")
    LiveData<List<Food>> getSpecificFoodList(int food_type_id);

    @Query("SELECT COUNT(*) FROM food WHERE food_type_id = :food_type_id")
    LiveData<Integer> getFoodTypeCount(int food_type_id);

    @Query("SELECT * FROM food WHERE food_name LIKE :name ")
    LiveData<List<Food>> getSearchFood(String name);

    @Query("SELECT * FROM food WHERE food_status = :food_status AND food_name LIKE :name")
    LiveData<List<Food>> getSpecificTypeFood(String food_status, String name);

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

    @Query("SELECT * FROM recipe WHERE recipe_name = :name ")
    LiveData<Recipe> getSpecificRecipe(String name);

    @Query("SELECT * FROM recipe WHERE recipe_name LIKE :name ")
    LiveData<List<Recipe>> getSearchRecipe(String name);

    @Query("SELECT * FROM recipe WHERE recipe_id = :id")
    LiveData<Recipe> getIdRecipe(int id);

    @Query("SELECT * FROM recipe_food")
    LiveData<List<RecipeFood>> getRecipeFood();

    @Query("SELECT * FROM recipe_food WHERE recipe_id = :id")
    LiveData<List<RecipeFood>> getSpecificRecipeFood(int id);

    @Query("SELECT * FROM recipe_food WHERE recipe_food_name = :name")
    LiveData<List<RecipeFood>> getSpecificNameRecipeFood(String name);

    @Query("SELECT * FROM buy_history")
    LiveData<List<BuyHistory>> getBuyHistory();

    @Query("SELECT SUM(buy_history_cost) FROM buy_history WHERE buy_history_buy_date >= :date")
    LiveData<Double> getBuyHistory30DaysCost(long date);

    @Query("SELECT * FROM buy_history WHERE buy_history_buy_date >= :dateIn ")
    LiveData<List<BuyHistory>> getBuyHistory30Days(long dateIn);

    @Query("SELECT * FROM buy_history WHERE buy_history_name LIKE :name ")
    LiveData<List<BuyHistory>> getSearchBuyHistory(String name);

    @Query("SELECT * FROM diary_history")
    LiveData<List<DiaryHistory>> getDiaryHistory();

    @Query("SELECT * FROM diary_history WHERE diary_history_type = :type AND diary_history_date >= :dateIn AND diary_history_date < :dateOut")
    LiveData<List<DiaryHistory>> getDiaryHistoryByDate(String type, long dateIn, long dateOut);

    @Query("SELECT SUM(diary_history_cost) FROM diary_history WHERE diary_history_date >= :dateIn AND diary_history_date < :dateOut")
    LiveData<Double> getTotalCostDiaryHistory(long dateIn, long dateOut);

    @Query("SELECT * FROM dishes")
    LiveData<List<Dishes>> getDishes();

    @Query("SELECT * FROM dishes WHERE dishes_name LIKE :name")
    LiveData<List<Dishes>> getSearchDishes(String name);

    @Query("SELECT * FROM cook_dish")
    LiveData<List<CookDish>> getCookDishes();

    @Query("SELECT * FROM cook_dish WHERE  cook_dish_name LIKE :name")
    LiveData<List<CookDish>> getSearchCookDishes(String name);

    @Query("SELECT * FROM cook_dish WHERE  cook_dish_id = :cook_dish_id")
    LiveData<CookDish> getCookDishesById(int cook_dish_id);

    @Query("SELECT * FROM cook_dish_ingredient")
    LiveData<List<CookDishIngredient>> getCookDishIngredient();

    @Query("SELECT * FROM cook_dish_ingredient WHERE recipe_food_id = :recipe_food_id AND cook_dish_id = :cook_dish_id")
    LiveData<List<CookDishIngredient>> getSpecificCookDishIngredient(int recipe_food_id, int cook_dish_id);

    @Query("SELECT SUM(cook_dish_ingredient_quantity) FROM cook_dish_ingredient WHERE food_id = :food_id")
    LiveData<Double> getSumOfSpecificCookDishIngredient(int food_id);

    @Query("SELECT * FROM cook_dish_ingredient WHERE food_id = :food_id")
    LiveData<CookDishIngredient> getSpecificCookDishIngredient(int food_id);

    @Query("SELECT SUM(cook_dish_ingredient_cost) FROM cook_dish_ingredient WHERE cook_dish_id = :cook_dish_id")
    LiveData<Double> getCostOfDish(int cook_dish_id);

    @Query("SELECT * FROM cook_dish_ingredient WHERE cook_dish_id = :cook_dish_id")
    LiveData<List<CookDishIngredient>> getSpecificCookDishIngByDish(int cook_dish_id);

    @Query("SELECT cook_dish_ingredient.cook_dish_ingredient_id ,food.food_name, food.food_status, cook_dish_ingredient.cook_dish_ingredient_quantity, food.food_unit FROM cook_dish_ingredient INNER JOIN food ON food.food_id = cook_dish_ingredient.food_id " +
            "WHERE cook_dish_ingredient.cook_dish_id = :cook_dish_id AND cook_dish_ingredient.recipe_food_id = :recipe_food_id AND cook_dish_ingredient.food_id = :food_id")
    LiveData<List<CookDishIngredientView>> getCookDishIngredientView(int cook_dish_id, int recipe_food_id, int food_id);

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
    int updateBuyHistory(BuyHistory... buyHistories);

    @Update
    int updateDiaryHistory(DiaryHistory... diaryHistories);

    @Update
    int updateDishes(Dishes... dishes);

    @Update
    int updateCookDish(CookDish... cookDishes);

    @Update
    int updateCookDishIngredient(CookDishIngredient... cookDishIngredients);

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
    int deleteBuyHistory(BuyHistory... buyHistories);

    @Delete
    int deleteDiaryHistory(DiaryHistory... diaryHistories);

    @Delete
    int deleteDishes(Dishes... dishes);

    @Delete
    int deleteCookDish(CookDish... cookDishes);

    @Delete
    int deleteCookDishIngredient(CookDishIngredient... cookDishIngredients);

}
