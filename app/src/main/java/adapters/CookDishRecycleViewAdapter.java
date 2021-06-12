package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.unclethree.saveinkitchen.R;
import formatters.NumberFormatter;
import models.CookDish;
import models.Dishes;

import java.util.ArrayList;
import java.util.List;

public class CookDishRecycleViewAdapter extends RecyclerView.Adapter<CookDishRecycleViewAdapter.ViewHolder> {

    private List<CookDish> mCookDish = new ArrayList<>();
    private final OnCookDishClickListener onCookDishClickListener;

    public CookDishRecycleViewAdapter(List<CookDish> mCookDish, OnCookDishClickListener onCookDishClickListener) {
        this.mCookDish = mCookDish;
        this.onCookDishClickListener = onCookDishClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cook_dish_recycle_view_item, parent, false);
        return new ViewHolder(view, onCookDishClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mFoodNameTextView.setText(mCookDish.get(position).getName());
        holder.mQuantityTextView.setText(mCookDish.get(position).getServing() + " Servings");
    }

    @Override
    public int getItemCount() {
        return mCookDish.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mFoodNameTextView;
        private final TextView mQuantityTextView;
        private final OnCookDishClickListener onCookDishClickListener;

        public ViewHolder(@NonNull View itemView, OnCookDishClickListener onCookDishClickListener) {
            super(itemView);
            mFoodNameTextView = itemView.findViewById(R.id.cook_dish_recycle_view_name);
            mQuantityTextView = itemView.findViewById(R.id.cook_dish_recycle_view_quantity);
            this.onCookDishClickListener = onCookDishClickListener;
        }

        @Override
        public void onClick(View v) {
            onCookDishClickListener.OnCookDishClick(getAbsoluteAdapterPosition());
        }
    }

    public interface OnCookDishClickListener {
        void OnCookDishClick(int position);
    }
}
