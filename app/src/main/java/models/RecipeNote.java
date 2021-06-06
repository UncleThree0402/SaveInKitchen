package models;

import androidx.room.*;

@Entity(tableName = "recipe_note",foreignKeys = {@ForeignKey(entity = Recipe.class,parentColumns = "recipe_id", childColumns = "recipe_id",onDelete = ForeignKey.CASCADE)})
public class RecipeNote {

    @PrimaryKey(autoGenerate = true)
    private int recipe_note_id;

    @ColumnInfo(name = "recipe_id")
    private int recipe_id;

    @ColumnInfo(name = "recipe-note")
    private String note;

    public RecipeNote(int recipe_note_id, int recipe_id, String note) {
        this.recipe_note_id = recipe_note_id;
        this.recipe_id = recipe_id;
        this.note = note;
    }

    @Ignore
    public RecipeNote() {
    }

    public int getRecipe_note_id() {
        return recipe_note_id;
    }

    public void setRecipe_note_id(int recipe_note_id) {
        this.recipe_note_id = recipe_note_id;
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
