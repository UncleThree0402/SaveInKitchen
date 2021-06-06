package models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipe")
public class Recipe {

    @PrimaryKey(autoGenerate = true)
    private int recipe_id;

    @ColumnInfo(name = "recipe_name")
    private String recipe_name;

    public Recipe(int recipe_id, String recipe_name) {
        this.recipe_id = recipe_id;
        this.recipe_name = recipe_name;
    }

    @Ignore
    public Recipe() {
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getRecipe_name() {
        return recipe_name;
    }

    public void setRecipe_name(String recipe_name) {
        this.recipe_name = recipe_name;
    }
}
