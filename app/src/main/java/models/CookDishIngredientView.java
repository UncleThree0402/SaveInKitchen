package models;

import androidx.room.ColumnInfo;

public class CookDishIngredientView {

    private int cook_dish_ingredient_id;

    private String food_name;

    private String food_status;

    private double cook_dish_ingredient_quantity;

    private String food_unit;

    public CookDishIngredientView(int cook_dish_ingredient_id, String food_name, String food_status, double cook_dish_ingredient_quantity, String food_unit) {
        this.cook_dish_ingredient_id = cook_dish_ingredient_id;
        this.food_name = food_name;
        this.food_status = food_status;
        this.cook_dish_ingredient_quantity = cook_dish_ingredient_quantity;
        this.food_unit = food_unit;
    }

    public CookDishIngredientView() {
    }

    public int getCook_dish_ingredient_id() {
        return cook_dish_ingredient_id;
    }

    public void setCook_dish_ingredient_id(int cook_dish_ingredient_id) {
        this.cook_dish_ingredient_id = cook_dish_ingredient_id;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getFood_status() {
        return food_status;
    }

    public void setFood_status(String food_status) {
        this.food_status = food_status;
    }

    public double getCook_dish_ingredient_quantity() {
        return cook_dish_ingredient_quantity;
    }

    public void setCook_dish_ingredient_quantity(double cook_dish_ingredient_quantity) {
        this.cook_dish_ingredient_quantity = cook_dish_ingredient_quantity;
    }

    public String getFood_unit() {
        return food_unit;
    }

    public void setFood_unit(String food_unit) {
        this.food_unit = food_unit;
    }
}
