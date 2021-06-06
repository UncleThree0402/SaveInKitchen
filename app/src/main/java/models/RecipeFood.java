package models;

import androidx.room.*;

@Entity(tableName = "recipe_food",foreignKeys = {@ForeignKey(entity = Recipe.class,parentColumns = "recipe_id", childColumns = "recipe_id",onDelete = ForeignKey.CASCADE),@ForeignKey(entity = FoodType.class,parentColumns = "food_type_id", childColumns = "food_type_id",onDelete = ForeignKey.CASCADE)})
public class RecipeFood {

    @PrimaryKey(autoGenerate = true)
    private int recipe_food_id;

    @ColumnInfo(name = "recipe_id")
    private int recipe_id;

    @ColumnInfo(name = "food_type_id")
    private int food_type_id;

    @ColumnInfo(name = "recipe_food_quantity")
    private double quantity;

    @ColumnInfo(name = "recipe_food_unit")
    private String unit;

    public RecipeFood(int recipe_food_id, int recipe_id, int food_type_id, double quantity, String unit) {
        this.recipe_food_id = recipe_food_id;
        this.recipe_id = recipe_id;
        this.food_type_id = food_type_id;
        this.quantity = quantity;
        this.unit = unit;
    }

    @Ignore
    public RecipeFood() {
    }

    public int getRecipe_food_id() {
        return recipe_food_id;
    }

    public void setRecipe_food_id(int recipe_food_id) {
        this.recipe_food_id = recipe_food_id;
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public int getFood_type_id() {
        return food_type_id;
    }

    public void setFood_type_id(int food_type_id) {
        this.food_type_id = food_type_id;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
