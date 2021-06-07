package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.unclethree.saveinkitchen.R;
import formatters.NumberFormatter;
import models.BuyFood;

import java.util.ArrayList;
import java.util.List;

public class BuyListRecycleViewAdapter extends RecyclerView.Adapter<BuyListRecycleViewAdapter.ViewHolder>{

    private List<BuyFood> mBuyFood = new ArrayList<>();

    public BuyListRecycleViewAdapter(List<BuyFood> mBuyFood) {
        this.mBuyFood = mBuyFood;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buy_list_recycle_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mTypeNameTextView.setText(mBuyFood.get(position).getName());
        holder.mStatusTextView.setText(mBuyFood.get(position).getStatus());
        String quantity = NumberFormatter.quantityFormatter(mBuyFood.get(position).getQuantity()) + " " + mBuyFood.get(position).getUnit();
        holder.mQuantityTextView.setText(quantity);
    }

    @Override
    public int getItemCount() {
        return mBuyFood.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mTypeNameTextView;
        private TextView mStatusTextView;
        private TextView mQuantityTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTypeNameTextView = itemView.findViewById(R.id.buy_list_recycle_view_type);
            mStatusTextView = itemView.findViewById(R.id.buy_list_recycle_view_status);
            mQuantityTextView = itemView.findViewById(R.id.buy_list_recycle_view_quantity);
        }
    }
}
