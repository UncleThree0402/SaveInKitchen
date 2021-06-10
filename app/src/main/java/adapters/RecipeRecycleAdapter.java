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

public class RecipeRecycleAdapter extends RecyclerView.Adapter<RecipeRecycleAdapter.ViewHolder>{

    private List<Recipe> mRecipe = new ArrayList<>();

    private final Activity mActivity;

    public RecipeRecycleAdapter(List<Recipe> mRecipe, Activity mActivity) {
        this.mRecipe = mRecipe;
        this.mActivity = mActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_recycle_view_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mRecipeName.setText(mRecipe.get(position).getName());
        holder.mRecipeCost.setText(NumberFormatter.moneyFormatter(mRecipe.get(position).getCost()));

        holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity,RecipeActivity.class);
                intent.putExtra("Recipe Name",mRecipe.get(position).getName());
                intent.putExtra("Mode",false);
                mActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecipe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private final RelativeLayout mRelativeLayout;
        private final TextView mRecipeName;
        private final TextView mRecipeCost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mRelativeLayout = itemView.findViewById(R.id.recipe_list_item_container);
            mRecipeName = itemView.findViewById(R.id.buy_list_recycle_name);
            mRecipeCost = itemView.findViewById(R.id.buy_list_recycle_cost);

        }


    }

}
