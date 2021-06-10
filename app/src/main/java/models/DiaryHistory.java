package models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "diary_history")
public class DiaryHistory {

    @PrimaryKey(autoGenerate = true)
    private int diary_history_id;

    @ColumnInfo(name = "diary_history_name")
    private String name;

    @ColumnInfo(name = "diary_history_type")
    private String time;

    @ColumnInfo(name = "diary_history_quantity")
    private double quantity;

    @ColumnInfo(name = "diary_history_unit")
    private String unit;

    @ColumnInfo(name = "diary_history_cost")
    private double cost;

    @ColumnInfo(name = "diary_history_date")
    private long date;

    public DiaryHistory(int diary_history_id, String name, String time, double quantity, String unit, double cost, long date) {
        this.diary_history_id = diary_history_id;
        this.name = name;
        this.time = time;
        this.quantity = quantity;
        this.unit = unit;
        this.cost = cost;
        this.date = date;
    }

    @Ignore
    public DiaryHistory() {
    }

    public int getDiary_history_id() {
        return diary_history_id;
    }

    public void setDiary_history_id(int diary_history_id) {
        this.diary_history_id = diary_history_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
