package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.unclethree.saveinkitchen.R;
import formatters.NumberFormatter;
import models.DiaryHistory;

import java.util.ArrayList;
import java.util.List;

public class DiaryHistoryRecycleViewAdapter extends RecyclerView.Adapter<DiaryHistoryRecycleViewAdapter.ViewHolder> {

    private List<DiaryHistory> mDiaryHistoryList = new ArrayList<>();

    public DiaryHistoryRecycleViewAdapter(List<DiaryHistory> mDiaryHistoryList) {
        this.mDiaryHistoryList = mDiaryHistoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diary_food_recycle_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mFoodNameTextView.setText(mDiaryHistoryList.get(position).getName());
        String quantity = NumberFormatter.quantityFormatter(mDiaryHistoryList.get(position).getQuantity()) + " " + mDiaryHistoryList.get(position).getUnit();
        holder.mQuantityTextView.setText(quantity);
        holder.mCostTextView.setText(NumberFormatter.moneyFormatter(mDiaryHistoryList.get(position).getCost()));
    }

    @Override
    public int getItemCount() {
        return mDiaryHistoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mFoodNameTextView;
        private TextView mQuantityTextView;
        private TextView mCostTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mFoodNameTextView = itemView.findViewById(R.id.eat_food_history_recycle_view_name);
            mQuantityTextView = itemView.findViewById(R.id.eat_food_history_recycle_view_quantity);
            mCostTextView = itemView.findViewById(R.id.eat_food_history_recycle_view_cost);
        }
    }
}
