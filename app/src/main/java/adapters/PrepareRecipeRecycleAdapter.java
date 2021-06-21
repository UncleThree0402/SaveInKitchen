package adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.unclethree.saveinkitchen.R;
import com.unclethree.saveinkitchen.RecipeActivity;
import formatters.NumberFormatter;
import models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class PrepareRecipeRecycleAdapter extends RecyclerView.Adapter<PrepareRecipeRecycleAdapter.ViewHolder>{

    private List<Recipe> mRecipe = new ArrayList<>();
    private final OnPrepareRecipeClickListener mOnPrepareRecipeClickListener;

    public PrepareRecipeRecycleAdapter(List<Recipe> mRecipe, OnPrepareRecipeClickListener onPrepareRecipeClickListener) {
        this.mRecipe = mRecipe;
        this.mOnPrepareRecipeClickListener = onPrepareRecipeClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_recycle_view_item, parent,false);
        return new ViewHolder(view, mOnPrepareRecipeClickListener);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mRecipeName;
        private final TextView mRecipeCost;
        private final OnPrepareRecipeClickListener mOnPrepareRecipeClickListener;

        public ViewHolder(@NonNull View itemView, OnPrepareRecipeClickListener onPrepareRecipeClickListener) {
            super(itemView);
            mRecipeName = itemView.findViewById(R.id.buy_list_recycle_name);
            mRecipeCost = itemView.findViewById(R.id.buy_list_recycle_cost);
            mOnPrepareRecipeClickListener = onPrepareRecipeClickListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            mOnPrepareRecipeClickListener.OnPrepareRecipeClick(getAbsoluteAdapterPosition());
        }
    }

    public interface OnPrepareRecipeClickListener{
        void OnPrepareRecipeClick(int position);
    }

}
