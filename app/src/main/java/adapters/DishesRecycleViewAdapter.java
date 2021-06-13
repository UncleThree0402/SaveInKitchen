package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.unclethree.saveinkitchen.R;
import formatters.NumberFormatter;
import models.Dishes;

import java.util.ArrayList;
import java.util.List;

public class DishesRecycleViewAdapter extends RecyclerView.Adapter<DishesRecycleViewAdapter.ViewHolder> {

    private List<Dishes> mDishes = new ArrayList<>();
    private final OnDishClickListener onDishClickListener;

    public DishesRecycleViewAdapter(List<Dishes> mDishes, OnDishClickListener onDishClickListener) {
        this.mDishes = mDishes;
        this.onDishClickListener = onDishClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_recycle_view_item, parent, false);
        return new ViewHolder(view, onDishClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mFoodNameTextView.setText(mDishes.get(position).getName());
        holder.mQuantityTextView.setText(Double.toString(mDishes.get(position).getServings()));
        holder.mCostTextView.setText(NumberFormatter.moneyFormatter(mDishes.get(position).getCost()));
    }

    @Override
    public int getItemCount() {
        return mDishes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mFoodNameTextView;
        private final TextView mQuantityTextView;
        private final TextView mCostTextView;
        private final OnDishClickListener onDishClickListener;

        public ViewHolder(@NonNull View itemView, OnDishClickListener onDishClickListener) {
            super(itemView);
            mFoodNameTextView = itemView.findViewById(R.id.dishes_recycle_view_name);
            mQuantityTextView = itemView.findViewById(R.id.dishes_recycle_view_quantity);
            mCostTextView = itemView.findViewById(R.id.dishes_recycle_view_cost);
            this.onDishClickListener = onDishClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onDishClickListener.OnDishClick(getAbsoluteAdapterPosition());
        }
    }

    public interface OnDishClickListener{
        void OnDishClick(int position);
    }
}
