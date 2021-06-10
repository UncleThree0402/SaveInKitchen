package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.unclethree.saveinkitchen.R;
import formatters.NumberFormatter;
import models.RecipeFood;

import java.util.ArrayList;
import java.util.List;

public class RecipeIngredientRecycleViewAdapter extends RecyclerView.Adapter<RecipeIngredientRecycleViewAdapter.ViewHolder>{

    private List<RecipeFood> mRecipeFood = new ArrayList<>();

    public RecipeIngredientRecycleViewAdapter(List<RecipeFood> mRecipeFood) {
        this.mRecipeFood = mRecipeFood;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_ingerdients_recycle_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mFoodNameTextView.setText(mRecipeFood.get(position).getName());
        holder.mStatusTextView.setText(mRecipeFood.get(position).getStatus());
        String quantity = NumberFormatter.quantityFormatter(mRecipeFood.get(position).getQuantity()) + " " + mRecipeFood.get(position).getUnit();
        holder.mQuantityTextView.setText(quantity);
        holder.mDescriptionTextView.setText(mRecipeFood.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return mRecipeFood.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView mFoodNameTextView;
        private final TextView mStatusTextView;
        private final TextView mQuantityTextView;
        private final TextView mDescriptionTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mFoodNameTextView = itemView.findViewById(R.id.recipe_food_recycle_view_type);
            mStatusTextView = itemView.findViewById(R.id.recipe_food_recycle_view_status);
            mQuantityTextView = itemView.findViewById(R.id.recipe_food_recycle_view_quantity);
            mDescriptionTextView = itemView.findViewById(R.id.recipe_food_recycle_view_description);
        }
    }
}
