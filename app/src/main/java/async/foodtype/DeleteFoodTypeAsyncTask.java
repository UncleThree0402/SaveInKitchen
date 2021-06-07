package async.foodtype;

import android.os.AsyncTask;
import models.FoodType;
import presistance.SaveInKitchenDao;

public class DeleteFoodTypeAsyncTask extends AsyncTask<FoodType,Void,Void> {

    private final SaveInKitchenDao mSaveInKitchenDao;

    public DeleteFoodTypeAsyncTask(SaveInKitchenDao mSaveInKitchenDao) {
        this.mSaveInKitchenDao = mSaveInKitchenDao;
    }

    @Override
    protected Void doInBackground(FoodType... foodTypes) {
        mSaveInKitchenDao.deleteFoodType(foodTypes);
        return null;
    }
}
