package models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "buy_history")
public class BuyHistory {
    
    @PrimaryKey(autoGenerate = true)
    private int buy_hist_id;

    @ColumnInfo(name = "buy_history_name")
    private String name;

    @ColumnInfo(name = "buy_history_status")
    private String status;

    @ColumnInfo(name = "buy_history_quantity")
    private double quantity;

    @ColumnInfo(name = "buy_history_unit")
    private String unit;

    @ColumnInfo(name = "buy_history_cost")
    private double cost;

    @ColumnInfo(name = "buy_history_buy_date")
    private long buyDate;

    @ColumnInfo(name = "buy_history_expire_date")
    private long expireDate;

    public BuyHistory(int buy_hist_id, String name, String status, double quantity, String unit, double cost, long buyDate, long expireDate) {
        this.buy_hist_id = buy_hist_id;
        this.name = name;
        this.status = status;
        this.quantity = quantity;
        this.unit = unit;
        this.cost = cost;
        this.buyDate = buyDate;
        this.expireDate = expireDate;
    }

    @Ignore
    public BuyHistory(String name, String status, double quantity, String unit, double cost, long buyDate, long expireDate) {
        this.name = name;
        this.status = status;
        this.quantity = quantity;
        this.unit = unit;
        this.cost = cost;
        this.buyDate = buyDate;
        this.expireDate = expireDate;
    }

    @Ignore
    public BuyHistory() {
    }

    public int getBuy_hist_id() {
        return buy_hist_id;
    }

    public void setBuy_hist_id(int buy_hist_id) {
        this.buy_hist_id = buy_hist_id;
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
