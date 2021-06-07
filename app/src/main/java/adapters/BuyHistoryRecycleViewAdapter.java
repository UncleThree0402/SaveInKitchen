package adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.unclethree.saveinkitchen.R;
import formatters.NumberFormatter;
import models.BuyHistory;
import viewmodels.BuyHistoryViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BuyHistoryRecycleViewAdapter extends RecyclerView.Adapter<BuyHistoryRecycleViewAdapter.ViewHolder>{
    private static final String TAG = "BuyHistoryRecycleViewAd";

    private List<BuyHistory> mBuyHistory = new ArrayList<>();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");


    public BuyHistoryRecycleViewAdapter(List<BuyHistory> mBuyHistory) {
        this.mBuyHistory = mBuyHistory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mNameTextView.setText(mBuyHistory.get(position).getName());
        holder.mStatusTextView.setText(mBuyHistory.get(position).getStatus());
        holder.mBuyDateTextView.setText(simpleDateFormat.format(mBuyHistory.get(position).getBuyDate()));
        String quantity = "Quantity : " + NumberFormatter.quantityFormatter(mBuyHistory.get(position).getQuantity()) + " " + mBuyHistory.get(position).getUnit();
        holder.mQuantityTextView.setText(quantity);
        String cost = "Cost : " + NumberFormatter.moneyFormatter(mBuyHistory.get(position).getCost());
        holder.mCostTextView.setText(cost);
        holder.mExpireDateTextView.setText(simpleDateFormat.format(mBuyHistory.get(position).getExpireDate()));

    }

    @Override
    public int getItemCount() {
        return mBuyHistory.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout mParentRelativeLayout;
        private RelativeLayout mChildRelativeLayout;
        private TextView mNameTextView;
        private TextView mStatusTextView;
        private TextView mBuyDateTextView;
        private TextView mQuantityTextView;
        private TextView mCostTextView;
        private TextView mExpireDateTextView;

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
