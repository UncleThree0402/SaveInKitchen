package async.foodtype;

import android.os.AsyncTask;
import models.BuyFood;
import models.FoodType;
import presistance.SaveInKitchenDao;

public class InsertFoodTypeAsyncTask extends AsyncTask<FoodType,Void,Void> {

    private SaveInKitchenDao mSaveInKitchenDao;

    public InsertFoodTypeAsyncTask(SaveInKitchenDao mSaveInKitchenDao) {
        this.mSaveInKitchenDao = mSaveInKitchenDao;
    }

    @Override
    protected Void doInBackground(FoodType... foodTypes) {
        mSaveInKitchenDao.insertFoodType(foodTypes);
        return null;
    }
}
