package models;

import androidx.room.*;

@Entity(tableName = "cook_dish",foreignKeys = {@ForeignKey(entity = Recipe.class,parentColumns = "recipe_id", childColumns = "recipe_id",onDelete = ForeignKey.CASCADE)})
public class CookDish {

    @PrimaryKey(autoGenerate = true)
    private int cook_dish_id;

    @ColumnInfo(name = "recipe_id")
    private int recipe_id;

    @ColumnInfo(name = "cook_dish_name")
    private String name;

    @ColumnInfo(name = "cook_dish_serving")
    private double serving;

    public CookDish(int cook_dish_id, int recipe_id, String name, double serving) {
        this.cook_dish_id = cook_dish_id;
        this.recipe_id = recipe_id;
        this.name = name;
        this.serving = serving;
    }

    @Ignore
    public CookDish() {
    }

    public int getCook_dish_id() {
        return cook_dish_id;
    }

    public void setCook_dish_id(int cook_dish_id) {
        this.cook_dish_id = cook_dish_id;
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getServing() {
        return serving;
    }

    public void setServing(double serving) {
        this.serving = serving;
    }
}
