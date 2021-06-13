package models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "cook_dish_ingredient")
public class CookDishIngredient {

    @PrimaryKey(autoGenerate = true)
    private int cook_dish_ingredient_id;

    @ColumnInfo(name = "cook_dish_id")
    private int cook_dish_id;

    @ColumnInfo(name = "recipe_food_id")
    private int recipe_food_id;

    @ColumnInfo(name = "food_id")
    private int food_id;

    @ColumnInfo(name = "cook_dish_ingredient_quantity")
    private Double quantity;

    @ColumnInfo(name = "cook_dish_ingredient_cost")
    private Double cost;

    public CookDishIngredient(int cook_dish_ingredient_id, int cook_dish_id, int recipe_food_id, int food_id, Double quantity, Double cost) {
        this.cook_dish_ingredient_id = cook_dish_ingredient_id;
        this.cook_dish_id = cook_dish_id;
        this.recipe_food_id = recipe_food_id;
        this.food_id = food_id;
        this.quantity = quantity;
        this.cost = cost;
    }

    @Ignore
    public CookDishIngredient() {
    }

    public int getCook_dish_ingredient_id() {
        return cook_dish_ingredient_id;
    }

    public void setCook_dish_ingredient_id(int cook_dish_ingredient_id) {
        this.cook_dish_ingredient_id = cook_dish_ingredient_id;
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

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
