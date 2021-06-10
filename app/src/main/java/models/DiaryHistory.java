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

    @ColumnInfo(name = "food_id")
    private int food_id;

    @ColumnInfo(name = "food_type_id")
    private int food_type_id;

    @ColumnInfo(name = "food_status")
    private String status;

    @ColumnInfo(name = "food_before_quantity")
    private double beforeQuantity;

    @ColumnInfo(name = "food_cost_per_unit")
    private double costPerUnit;

    @ColumnInfo(name = "food_buy_date")
    private long buyDate;

    @ColumnInfo(name = "food_expire_date")
    private long expireDate;

    public DiaryHistory(int diary_history_id, String name, String time, double quantity, String unit, double cost, long date, int food_id, int food_type_id, String status, double beforeQuantity, double costPerUnit, long buyDate, long expireDate) {
        this.diary_history_id = diary_history_id;
        this.name = name;
        this.time = time;
        this.quantity = quantity;
        this.unit = unit;
        this.cost = cost;
        this.date = date;
        this.food_id = food_id;
        this.food_type_id = food_type_id;
        this.status = status;
        this.beforeQuantity = beforeQuantity;
        this.costPerUnit = costPerUnit;
        this.buyDate = buyDate;
        this.expireDate = expireDate;
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

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public int getFood_type_id() {
        return food_type_id;
    }

    public void setFood_type_id(int food_type_id) {
        this.food_type_id = food_type_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getBeforeQuantity() {
        return beforeQuantity;
    }

    public void setBeforeQuantity(double beforeQuantity) {
        this.beforeQuantity = beforeQuantity;
    }

    public double getCostPerUnit() {
        return costPerUnit;
    }

    public void setCostPerUnit(double costPerUnit) {
        this.costPerUnit = costPerUnit;
    }

    public long getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(long buyDate) {
        this.buyDate = buyDate;
    }

    public long getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(long expireDate) {
        this.expireDate = expireDate;
    }
}
