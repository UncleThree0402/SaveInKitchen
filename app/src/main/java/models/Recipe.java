package models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipe")
public class Recipe implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int recipe_id;

    @ColumnInfo(name = "recipe_name")
    private String name;

    @ColumnInfo(name = "recipe_note")
    private String note;

    @ColumnInfo(name = "recipe_methods")
    private String methods;

    @ColumnInfo(name = "recipe_servings")
    private int servings;

    @ColumnInfo(name = "recipe_cost")
    private double cost;

    public Recipe(int recipe_id, String name, String note, String methods, int servings, double cost) {
        this.recipe_id = recipe_id;
        this.name = name;
        this.note = note;
        this.methods = methods;
        this.servings = servings;
        this.cost = cost;
    }

    @Ignore
    public Recipe(String name) {
        this.name = name;
    }

    @Ignore
    public Recipe() {
    }

    protected Recipe(Parcel in) {
        recipe_id = in.readInt();
        name = in.readString();
        note = in.readString();
        methods = in.readString();
        servings = in.readInt();
        cost = in.readDouble();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getMethods() {
        return methods;
    }

    public void setMethods(String methods) {
        this.methods = methods;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(recipe_id);
        dest.writeString(name);
        dest.writeString(note);
        dest.writeString(methods);
        dest.writeInt(servings);
        dest.writeDouble(cost);
    }
}
