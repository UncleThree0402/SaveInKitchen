package models;

import androidx.room.Ignore;

public class BuyListItem {

    private String food_type;

    private String buy_food_status;

    private String buy_food_quantity;

    private String buy_food_unit;

    public BuyListItem(String food_type, String buy_food_status, String buy_food_quantity, String buy_food_unit) {
        this.food_type = food_type;
        this.buy_food_status = buy_food_status;
        this.buy_food_quantity = buy_food_quantity;
        this.buy_food_unit = buy_food_unit;
    }

    @Ignore
    public BuyListItem() {
    }

    public String getFood_type() {
        return food_type;
    }

    public void setFood_type(String food_type) {
        this.food_type = food_type;
    }

    public String getBuy_food_status() {
        return buy_food_status;
    }

    public void setBuy_food_status(String buy_food_status) {
        this.buy_food_status = buy_food_status;
    }

    public String getBuy_food_quantity() {
        return buy_food_quantity;
    }

    public void setBuy_food_quantity(String buy_food_quantity) {
        this.buy_food_quantity = buy_food_quantity;
    }

    public String getBuy_food_unit() {
        return buy_food_unit;
    }

    public void setBuy_food_unit(String buy_food_unit) {
        this.buy_food_unit = buy_food_unit;
    }
}
