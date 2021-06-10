package models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.*;

@Entity(tableName = "buy_list",foreignKeys = {@ForeignKey(entity = FoodType.class,parentColumns = "food_type_id", childColumns = "food_type_id",onDelete = ForeignKey.CASCADE)})
public class BuyFood implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int buy_list_id;

    @ColumnInfo(name = "food_type_id")
    private int food_type_id;

    @ColumnInfo(name = "buy_food_name")
    private String name;

    @ColumnInfo(name = "buy_food_status")
    private String status;

    @ColumnInfo(name = "buy_food_quantity")
    private Double quantity;

    @ColumnInfo(name = "buy_food_unit")
    private String unit;

    public BuyFood(int buy_list_id, int food_type_id, String name, String status, double quantity, String unit) {
        this.buy_list_id = buy_list_id;
        this.food_type_id = food_type_id;
        this.name = name;
        this.status = status;
        this.quantity = quantity;
        this.unit = unit;
    }

    @Ignore
    public BuyFood(int buy_list_id) {
        this.buy_list_id = buy_list_id;
    }

    @Ignore
    public BuyFood() {
    }

    protected BuyFood(Parcel in) {
        buy_list_id = in.readInt();
        food_type_id = in.readInt();
        name = in.readString();
        status = in.readString();
        if (in.readByte() == 0) {
            quantity = null;
        } else {
            quantity = in.readDouble();
        }
        unit = in.readString();
    }

    public static final Creator<BuyFood> CREATOR = new Creator<BuyFood>() {
        @Override
        public BuyFood createFromParcel(Parcel in) {
            return new BuyFood(in);
        }

        @Override
        public BuyFood[] newArray(int size) {
            return new BuyFood[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(buy_list_id);
        dest.writeInt(food_type_id);
        dest.writeString(name);
        dest.writeString(status);
        if (quantity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(quantity);
        }
        dest.writeString(unit);
    }
}
