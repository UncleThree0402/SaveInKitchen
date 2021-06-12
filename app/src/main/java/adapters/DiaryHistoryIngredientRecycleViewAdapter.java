package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.unclethree.saveinkitchen.R;
import formatters.NumberFormatter;
import models.Food;

import java.util.ArrayList;
import java.util.List;

public class DiaryHistoryIngredientRecycleViewAdapter extends RecyclerView.Adapter<DiaryHistoryIngredientRecycleViewAdapter.ViewHolder> {

    private List<Food> mFood = new ArrayList<>();
    private final OnFoodClickListener onFoodClickListener;

    public DiaryHistoryIngredientRecycleViewAdapter(List<Food> mFood, OnFoodClickListener onFoodClickListener) {
        this.mFood = mFood;
        this.onFoodClickListener = onFoodClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredients_recycle_view_item, parent, false);
        return new ViewHolder(view, onFoodClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mNameTextView.setText(mFood.get(position).getName());
        holder.mStatusTextView.setText(mFood.get(position).getStatus());
        String quantity = NumberFormatter.quantityFormatter(mFood.get(position).getQuantity()) + " " + mFood.get(position).getUnit();
        holder.mQuantityTextView.setText(quantity);
    }

    @Override
    public int getItemCount() {
        return mFood.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mNameTextView;
        private final TextView mStatusTextView;
        private final TextView mQuantityTextView;
        private final OnFoodClickListener onFoodClickListener;

        public ViewHolder(@NonNull View itemView, OnFoodClickListener onFoodClickListener) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.food_page_recycle_view_name);
            mStatusTextView = itemView.findViewById(R.id.food_page_recycle_view_status);
            mQuantityTextView = itemView.findViewById(R.id.food_page_recycle_view_quantity);
            this.onFoodClickListener = onFoodClickListener;

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            onFoodClickListener.OnFoodClick(getAbsoluteAdapterPosition());
        }
    }

    public interface OnFoodClickListener{
        void OnFoodClick(int position);
    }
}
