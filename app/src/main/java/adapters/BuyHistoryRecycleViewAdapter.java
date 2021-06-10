package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.unclethree.saveinkitchen.R;
import formatters.DateFormatter;
import formatters.NumberFormatter;
import models.BuyHistory;

import java.util.ArrayList;
import java.util.List;

public class BuyHistoryRecycleViewAdapter extends RecyclerView.Adapter<BuyHistoryRecycleViewAdapter.ViewHolder>{
    private static final String TAG = "BuyHistoryRecycleViewAd";

    private List<BuyHistory> mBuyHistory = new ArrayList<>();

    public BuyHistoryRecycleViewAdapter(List<BuyHistory> mBuyHistory) {
        this.mBuyHistory = mBuyHistory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_recycle_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mNameTextView.setText(mBuyHistory.get(position).getName());
        holder.mStatusTextView.setText(mBuyHistory.get(position).getStatus());
        holder.mBuyDateTextView.setText(DateFormatter.dayMonthYearFormatter(mBuyHistory.get(position).getBuyDate()));
        String quantity = "Quantity : " + NumberFormatter.quantityFormatter(mBuyHistory.get(position).getQuantity()) + " " + mBuyHistory.get(position).getUnit();
        holder.mQuantityTextView.setText(quantity);
        String cost = "Cost : " + NumberFormatter.moneyFormatter(mBuyHistory.get(position).getCost());
        holder.mCostTextView.setText(cost);
        holder.mExpireDateTextView.setText(DateFormatter.dayMonthYearFormatter(mBuyHistory.get(position).getExpireDate()));

    }

    @Override
    public int getItemCount() {
        return mBuyHistory.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout mParentRelativeLayout;
        private final RelativeLayout mChildRelativeLayout;
        private final TextView mNameTextView;
        private final TextView mStatusTextView;
        private final TextView mBuyDateTextView;
        private final TextView mQuantityTextView;
        private final TextView mCostTextView;
        private final TextView mExpireDateTextView;

        private boolean isOpen = false;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mParentRelativeLayout = itemView.findViewById(R.id.history_list_recycle_view_parent_layout);
            mChildRelativeLayout = itemView.findViewById(R.id.history_list_recycle_view_child_layout);
            mNameTextView = itemView.findViewById(R.id.history_list_recycle_view_name);
            mStatusTextView = itemView.findViewById(R.id.history_lis_recycle_view_status);
            mBuyDateTextView = itemView.findViewById(R.id.history_list_buy_date);
            mQuantityTextView = itemView.findViewById(R.id.history_list_text_view_quantity);
            mCostTextView = itemView.findViewById(R.id.history_list_text_view_cost);
            mExpireDateTextView = itemView.findViewById(R.id.history_list_text_view_expire_date);

            mParentRelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isOpen) {
                        mChildRelativeLayout.setVisibility(View.GONE);
                        isOpen = false;
                    } else {
                        mChildRelativeLayout.setVisibility(View.VISIBLE);
                        isOpen = true;
                    }
                }
            });

        }
    }
}
