package adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.unclethree.saveinkitchen.R;
import models.Food;
import models.FoodType;
import viewmodels.FoodViewModel;

import java.util.ArrayList;
import java.util.List;

public class StockListRecycleViewAdapter extends RecyclerView.Adapter<StockListRecycleViewAdapter.ViewHolder>{

    private List<FoodType> mFoodType = new ArrayList<>();
    private ViewModelStoreOwner mViewModelStoreOwner;
    private LifecycleOwner mLifecycleOwner;
    private Context mContext;

    public StockListRecycleViewAdapter(List<FoodType> mFoodType, ViewModelStoreOwner mViewModelStoreOwner, LifecycleOwner mLifecycleOwner, Context mContext) {
        this.mFoodType = mFoodType;
        this.mViewModelStoreOwner = mViewModelStoreOwner;
        this.mLifecycleOwner = mLifecycleOwner;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_list_recycle_view_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mFoodViewModel = new ViewModelProvider(mViewModelStoreOwner).get(FoodViewModel.class);
        holder.mFoodViewModel.getSpecificFoodList(mFoodType.get(position).getFood_type_id()).observe(mLifecycleOwner, new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                if(holder.mFood.size()>0){
                    holder.mFood.clear();
                }
                if(holder.mFood != null){
                    holder.mFood.addAll(foods);
                }
                holder.mSpecificFoodListRecycleViewAdapter.notifyDataSetChanged();
            }
        });
        holder.initFoodList();
        holder.mTypeTextView.setText(mFoodType.get(position).getFoodType());
        String inStock;
        if(mFoodType.get(position).isInStock()){
            inStock = "In Stock";
            holder.mStockTextView.setTextColor(Color.parseColor("#008000"));
        }else {
            inStock = "Out Of Stock";
            holder.mStockTextView.setTextColor(Color.parseColor("#FF0000"));
        }
        holder.mStockTextView.setText(inStock);

    }

    @Override
    public int getItemCount() {
        return mFoodType.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mTypeTextView;
        private TextView mStockTextView;
        private RecyclerView mFoodRecycleView;

        private SpecificFoodListRecycleViewAdapter mSpecificFoodListRecycleViewAdapter;
        private ArrayList<Food> mFood = new ArrayList<>();
        private FoodViewModel mFoodViewModel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTypeTextView = itemView.findViewById(R.id.stock_list_recycle_view_type);
            mStockTextView = itemView.findViewById(R.id.stock_list_recycle_view_stock);
            mFoodRecycleView = itemView.findViewById(R.id.stock_list_recycle_view_child_recycle_view);
        }

        private void initFoodList(){
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            mFoodRecycleView.setLayoutManager(linearLayoutManager);
            mSpecificFoodListRecycleViewAdapter = new SpecificFoodListRecycleViewAdapter(mFood);
            mFoodRecycleView.setAdapter(mSpecificFoodListRecycleViewAdapter);
        }
    }
}
