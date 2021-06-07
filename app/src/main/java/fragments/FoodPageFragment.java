package fragments;

import adapters.BuyHistoryRecycleViewAdapter;
import adapters.FoodListRecycleViewAdapter;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import com.unclethree.saveinkitchen.R;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import models.BuyHistory;
import models.Food;
import models.FoodType;
import util.VerticalSpacingItemDecorator;
import viewmodels.BuyHistoryViewModel;
import viewmodels.FoodTypeViewModel;
import viewmodels.FoodViewModel;

import java.util.ArrayList;
import java.util.List;

public class FoodPageFragment extends Fragment {

    //UI
    private RelativeLayout mRelativeLayout;
    private RecyclerView mFoodRecyclerView;
    private TextView mFoodRecyclerViewTextView;
    private androidx.appcompat.widget.SearchView mSearchView;

    //Var
    private FoodListRecycleViewAdapter mFoodListRecycleViewAdapter;
    private ArrayList<Food> mFood = new ArrayList<>();
    private FoodViewModel mFoodViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.food_page_fragment,container,false);

        mRelativeLayout = view.findViewById(R.id.food_page_rl);
        mFoodRecyclerView = view.findViewById(R.id.food_list_recycle_view);
        mFoodRecyclerViewTextView = view.findViewById(R.id.food_list_recycle_view_text);
        mSearchView = view.findViewById(R.id.food_search_view);

        initStockListRecycleView();

       mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               return false;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
               mFoodViewModel.getSearchFood(newText).observe(getViewLifecycleOwner(), new Observer<List<Food>>() {
                   @Override
                   public void onChanged(List<Food> foods) {
                       if (mFood.size() > 0) {
                           mFood.clear();
                       }
                       if (mFood != null) {
                           mFood.addAll(foods);
                       }
                       if (foods.size() == 0) {
                           mFoodRecyclerView.setVisibility(View.GONE);
                           mFoodRecyclerViewTextView.setVisibility(View.VISIBLE);
                       } else {
                           mFoodRecyclerView.setVisibility(View.VISIBLE);
                           mFoodRecyclerViewTextView.setVisibility(View.GONE);
                       }
                       mFoodListRecycleViewAdapter.notifyDataSetChanged();
                   }
               });
               return false;
           }
       });

        mFoodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);

        mFoodViewModel.getFood().observe(getViewLifecycleOwner(), new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                if (mFood.size() > 0) {
                    mFood.clear();
                }
                if (mFood != null) {
                    mFood.addAll(foods);
                }
                if (foods.size() == 0) {
                    mFoodRecyclerView.setVisibility(View.GONE);
                    mFoodRecyclerViewTextView.setVisibility(View.VISIBLE);
                } else {
                    mFoodRecyclerView.setVisibility(View.VISIBLE);
                    mFoodRecyclerViewTextView.setVisibility(View.GONE);
                }
                mFoodListRecycleViewAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    private void initStockListRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mFoodRecyclerView.setLayoutManager(linearLayoutManager);
        mFoodListRecycleViewAdapter = new FoodListRecycleViewAdapter(mFood);
        VerticalSpacingItemDecorator verticalSpacingItemDecorator = new VerticalSpacingItemDecorator(8);
        mFoodRecyclerView.addItemDecoration(verticalSpacingItemDecorator);
        new ItemTouchHelper(foodSimpleCallback).attachToRecyclerView(mFoodRecyclerView);
        mFoodRecyclerView.setAdapter(mFoodListRecycleViewAdapter);
    }

    private ItemTouchHelper.SimpleCallback foodSimpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            switch (direction) {
                case ItemTouchHelper.RIGHT:
                    Food deleteFood = mFood.get(viewHolder.getAbsoluteAdapterPosition());
                    mFoodViewModel.deleteFood(mFood.get(viewHolder.getAbsoluteAdapterPosition()));
                    Snackbar.make(mRelativeLayout, deleteFood.getName() + " is Deleted.", Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mFoodViewModel.insertFood(deleteFood);
                                }
                            }).show();
                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_delete_24)
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_edit_24)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };


}
