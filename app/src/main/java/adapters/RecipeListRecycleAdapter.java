package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.unclethree.saveinkitchen.R;
import formatters.NumberFormatter;
import models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeListRecycleAdapter extends RecyclerView.Adapter<RecipeListRecycleAdapter.ViewHolder>{

    private List<Recipe> mRecipe = new ArrayList<>();

    public RecipeListRecycleAdapter(List<Recipe> mRecipe) {
        this.mRecipe = mRecipe;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mRecipeName.setText(mRecipe.get(position).getName());
        holder.mRecipeCost.setText(NumberFormatter.moneyFormatter(mRecipe.get(position).getCost()));
    }

    @Override
    public int getItemCount() {
        return mRecipe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mRecipeName;
        private TextView mRecipeCost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mRecipeName = itemView.findViewById(R.id.buy_list_recycle_name);
            mRecipeCost = itemView.findViewById(R.id.buy_list_recycle_cost);
        }
    }

}
