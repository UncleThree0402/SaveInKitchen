package models;

import androidx.room.*;

@Entity(tableName = "buy_list",foreignKeys = {@ForeignKey(entity = FoodType.class,parentColumns = "food_type_id", childColumns = "food_type_id",onDelete = ForeignKey.CASCADE)})
public class BuyFood {

    @PrimaryKey(autoGenerate = true)
    private int buy_list_id;

    @ColumnInfo(name = "food_type_id")
    private int food_type_id;

    @ColumnInfo(name = "buy_food_status")
    private String status;

    @ColumnInfo(name = "buy_food_quantity")
    private double quantity;

    @ColumnInfo(name = "buy_food_unit")
    private String unit;

    public BuyFood(int buy_list_id, int food_type_id, String status, double quantity, String unit) {
        this.buy_list_id = buy_list_id;
        this.food_type_id = food_type_id;
        this.status = status;
        this.quantity = quantity;
        this.unit = unit;
    }

    @Ignore
    public BuyFood() {
    }

    public int getBuy_list_id() {
        return buy_list_id;
    }

    public void setBuy_list_id(int buy_list_id) {
        this.buy_list_id = buy_list_id;
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
