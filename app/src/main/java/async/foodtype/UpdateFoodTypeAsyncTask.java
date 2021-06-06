package async.foodtype;

import android.os.AsyncTask;
import models.BuyFood;
import models.FoodType;
import presistance.SaveInKitchenDao;

public class UpdateFoodTypeAsyncTask extends AsyncTask<FoodType,Void,Void> {

    private SaveInKitchenDao mSaveInKitchenDao;

    public UpdateFoodTypeAsyncTask(SaveInKitchenDao mSaveInKitchenDao) {
        this.mSaveInKitchenDao = mSaveInKitchenDao;
    }

    @Override
    protected Void doInBackground(FoodType... foodTypes) {
        mSaveInKitchenDao.updateFoodType(foodTypes);
        return null;
    }
}
