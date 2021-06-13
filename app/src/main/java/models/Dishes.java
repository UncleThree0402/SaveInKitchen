package models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "dishes")
public class Dishes {

    @PrimaryKey(autoGenerate = true)
    private int dishes_id;

    @ColumnInfo(name = "dishes_name")
    private String name;

    @ColumnInfo(name = "dishes_servings")
    private double servings;

    @ColumnInfo(name = "dishes_cost")
    private double cost;

    @ColumnInfo(name = "dishes_cost_per_serving")
    private double costPerServing;

    @ColumnInfo(name = "dishes_date",defaultValue = "CURRENT_TIMESTAMP")
    private long date;


    public Dishes(int dishes_id, String name, double servings, double cost, double costPerServing, long date) {
        this.dishes_id = dishes_id;
        this.name = name;
        this.servings = servings;
        this.cost = cost;
        this.costPerServing = costPerServing;
        this.date = date;
    }

    @Ignore
    public Dishes() {
    }

    public int getDishes_id() {
        return dishes_id;
    }

    public void setDishes_id(int dishes_id) {
        this.dishes_id = dishes_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getServings() {
        return servings;
    }

    public void setServings(double servings) {
        this.servings = servings;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getCostPerServing() {
        return costPerServing;
    }

    public void setCostPerServing(double costPerServing) {
        this.costPerServing = costPerServing;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
