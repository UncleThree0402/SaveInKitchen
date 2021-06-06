package models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.*;

@Entity(tableName = "food",foreignKeys = {@ForeignKey(entity = FoodType.class,parentColumns = "food_type_id", childColumns = "food_type_id",onDelete = ForeignKey.CASCADE)})
public class Food implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int food_id;

    @ColumnInfo(name = "food_type_id")
    private int food_type_id;

    @ColumnInfo(name = "food_name")
    private String name;

    @ColumnInfo(name = "food_status")
    private String status;

    @ColumnInfo(name = "food_quantity")
    private double quantity;

    @ColumnInfo(name = "food_unit")
    private String unit;

    @ColumnInfo(name = "food_cost")
    private double cost;

    @ColumnInfo(name = "food_cost_per_unit")
    private double costPerUnit;

    @ColumnInfo(name = "food_buy_date")
    private long buyDate;

    @ColumnInfo(name = "food_expire_date")
    private long expireDate;

    public Food(int food_id, int food_type_id, String name, String status, double quantity, String unit, double cost, double costPerUnit, long buyDate, long expireDate) {
        this.food_id = food_id;
        this.food_type_id = food_type_id;
        this.name = name;
        this.status = status;
        this.quantity = quantity;
        this.unit = unit;
        this.cost = cost;
        this.costPerUnit = costPerUnit;
        this.buyDate = buyDate;
        this.expireDate = expireDate;
    }

    @Ignore
    public Food() {
    }

    protected Food(Parcel in) {
        food_id = in.readInt();
        food_type_id = in.readInt();
        name = in.readString();
        status = in.readString();
        quantity = in.readDouble();
        unit = in.readString();
        cost = in.readDouble();
        costPerUnit = in.readDouble();
        buyDate = in.readLong();
        expireDate = in.readLong();
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(food_id);
        dest.writeInt(food_type_id);
        dest.writeString(name);
        dest.writeString(status);
        dest.writeDouble(quantity);
        dest.writeString(unit);
        dest.writeDouble(cost);
        dest.writeDouble(costPerUnit);
        dest.writeLong(buyDate);
        dest.writeLong(expireDate);
    }
}
