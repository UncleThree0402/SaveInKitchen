package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.unclethree.saveinkitchen.R;
import formatters.NumberFormatter;
import models.CookDishIngredientView;
import viewmodels.CookDishIngredientViewViewModel;

import java.util.ArrayList;
import java.util.List;

public class PrepareRecipeIngredientAddedRecycleViewAdapter extends RecyclerView.Adapter<PrepareRecipeIngredientAddedRecycleViewAdapter.ViewHolder> {

    private List<CookDishIngredientView> mCookDishIngredientView = new ArrayList<>();

    public PrepareRecipeIngredientAddedRecycleViewAdapter(List<CookDishIngredientView> mCookDishIngredientView) {
        this.mCookDishIngredientView = mCookDishIngredientView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prepare_recipe_ingredients_added_recycle_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mNameTextView.setText(mCookDishIngredientView.get(position).getFood_name());
        holder.mStatusTextView.setText(mCookDishIngredientView.get(position).getFood_status());
        String quantity = NumberFormatter.quantityFormatter(mCookDishIngredientView.get(position).getCook_dish_ingredient_quantity()) + " " + mCookDishIngredientView.get(position).getFood_unit();
        holder.mQuantityTextView.setText(quantity);

    }

    @Override
    public int getItemCount() {
        return mCookDishIngredientView.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mNameTextView;
        private final TextView mStatusTextView;
        private final TextView mQuantityTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.prepare_recipe_ingredients_added_recycle_view_name);
            mStatusTextView = itemView.findViewById(R.id.prepare_recipe_ingredients_added_recycle_view_status);
            mQuantityTextView = itemView.findViewById(R.id.prepare_recipe_ingredients_added_recycle_view_quantity);
        }
    }
}
