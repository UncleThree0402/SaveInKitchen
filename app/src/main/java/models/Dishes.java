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

    @ColumnInfo(name = "dishes_percentage")
    private double percentage;

    @ColumnInfo(name = "dishes_cost")
    private double cost;

    @ColumnInfo(name = "dishes_date",defaultValue = "CURRENT_TIMESTAMP")
    private long date;


    public Dishes(int dishes_id, String name, double percentage, double cost, long date) {
        this.dishes_id = dishes_id;
        this.name = name;
        this.percentage = percentage;
        this.cost = cost;
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

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
