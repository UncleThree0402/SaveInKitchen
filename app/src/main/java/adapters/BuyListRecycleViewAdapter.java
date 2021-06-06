package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.unclethree.saveinkitchen.R;
import models.BuyFood;
import models.BuyListItem;

import java.util.ArrayList;
import java.util.List;

public class BuyListRecycleViewAdapter extends RecyclerView.Adapter<BuyListRecycleViewAdapter.ViewHolder>{

    private List<BuyListItem> mBuyList = new ArrayList<>();

    public BuyListRecycleViewAdapter(List<BuyListItem> mBuyList) {
        this.mBuyList = mBuyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buy_list_recycle_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mTypeNameTextView.setText(mBuyList.get(position).getFood_type());
        holder.mStatusTextView.setText(mBuyList.get(position).getBuy_food_status());
        String quantity = mBuyList.get(position).getBuy_food_quantity() + mBuyList.get(position).getBuy_food_unit();
        holder.mQuantityTextView.setText(quantity);
    }

    @Override
    public int getItemCount() {
        return mBuyList.size();
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
