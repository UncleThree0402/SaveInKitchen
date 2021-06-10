package presistance;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import models.*;

@Database(entities = {FoodType.class, Food.class, Recipe.class, RecipeFood.class, BuyFood.class,BuyHistory.class, DiaryHistory.class},version = 9)
public abstract class SaveInKitchenDatabase extends RoomDatabase {

    public static final String DATA_BASENAME = "SaveInKitchen_db";

    private static SaveInKitchenDatabase instance;

    static SaveInKitchenDatabase getInstance(final Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),SaveInKitchenDatabase.class,DATA_BASENAME).build();
        }
        return instance;
    }

    public abstract SaveInKitchenDao getSaveForFoodDao();

}
