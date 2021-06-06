package models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "food_type")
public class FoodType implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int food_type_id;

    @ColumnInfo(name = "food_type")
    private String foodType;

    @ColumnInfo(name = "inStock")
    private boolean inStock;

    public FoodType(int food_type_id, String foodType, boolean inStock) {
        this.food_type_id = food_type_id;
        this.foodType = foodType;
        this.inStock = inStock;
    }

    @Ignore
    public FoodType(String foodType) {
        this.foodType = foodType;
        this.inStock = false;
    }

    @Ignore
    public FoodType() {
    }

    protected FoodType(Parcel in) {
        food_type_id = in.readInt();
        foodType = in.readString();
        inStock = in.readByte() != 0;
    }

    public static final Creator<FoodType> CREATOR = new Creator<FoodType>() {
        @Override
        public FoodType createFromParcel(Parcel in) {
            return new FoodType(in);
        }

        @Override
        public FoodType[] newArray(int size) {
            return new FoodType[size];
        }
    };

    public int getFood_type_id() {
        return food_type_id;
    }

    public void setFood_type_id(int food_type_id) {
        this.food_type_id = food_type_id;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(food_type_id);
        dest.writeString(foodType);
        dest.writeByte((byte) (inStock ? 1 : 0));
    }
}
