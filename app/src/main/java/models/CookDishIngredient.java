package models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "cook_dish_ingredient")
public class CookDishIngredient {

    @PrimaryKey(autoGenerate = true)
    private int cook_dish_ingredient;

    @ColumnInfo(name = "cook_dish_id")
    private int cook_dish_id;

    @ColumnInfo(name = "recipe_food_id")
    private int recipe_food_id;

    @ColumnInfo(name = "food_id")
    private int food_id;

    @ColumnInfo(name = "cook_dish_ingredient_quantity")
    private double quantity;

    public CookDishIngredient(int cook_dish_ingredient, int cook_dish_id, int recipe_food_id, int food_id, double quantity) {
        this.cook_dish_ingredient = cook_dish_ingredient;
        this.cook_dish_id = cook_dish_id;
        this.recipe_food_id = recipe_food_id;
        this.food_id = food_id;
        this.quantity = quantity;
    }

    @Ignore
    public CookDishIngredient() {
    }

    public int getCook_dish_ingredient() {
        return cook_dish_ingredient;
    }

    public void setCook_dish_ingredient(int cook_dish_ingredient) {
        this.cook_dish_ingredient = cook_dish_ingredient;
    }

    public int getCook_dish_id() {
        return cook_dish_id;
    }

    public void setCook_dish_id(int cook_dish_id) {
        this.cook_dish_id = cook_dish_id;
    }

    public int getRecipe_food_id() {
        return recipe_food_id;
    }

    public void setRecipe_food_id(int recipe_food_id) {
        this.recipe_food_id = recipe_food_id;
    }

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
