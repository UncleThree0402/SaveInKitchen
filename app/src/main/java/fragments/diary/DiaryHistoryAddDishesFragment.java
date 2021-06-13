package fragments.diary;

import adapters.DiaryHistoryIngredientRecycleViewAdapter;
import adapters.DishesRecycleViewAdapter;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import dialogs.DiaryAddDishesDialog;
import dialogs.DiaryAddFoodDialog;
import dialogs.StockDialog;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import models.Dishes;
import models.Food;
import util.VerticalSpacingItemDecorator;
import viewmodels.DishesViewModel;
import viewmodels.FoodViewModel;

import java.util.ArrayList;
import java.util.List;

public class DiaryHistoryAddDishesFragment extends Fragment implements DishesRecycleViewAdapter.OnDishClickListener {
    private static final String TAG = "DiaryHistoryAddDishesFr";
    //UI
    private RelativeLayout mRelativeLayout;
    private RecyclerView mDishesRecyclerView;
    private TextView mDishesRecyclerViewTextView;
    private SearchView mSearchView;
    private ImageView mAddDishesListIcon;

    //Var
    private DishesRecycleViewAdapter mDishesRecycleViewAdapter;
    private final ArrayList<Dishes> mDishes = new ArrayList<>();
    private DishesViewModel mDishesViewModel;


    private final String mType;
    private final long mDateTime;

    public DiaryHistoryAddDishesFragment(String mType, long mDateTime) {
        this.mType = mType;
        this.mDateTime = mDateTime;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diary_add_food_dishes_fragment,container,false);

        mDishesViewModel = new ViewModelProvider(this).get(DishesViewModel.class);

        mRelativeLayout = view.findViewById(R.id.add_dishes_rl);
        mDishesRecyclerView = view.findViewById(R.id.add_dishes_recycle_view);
        mDishesRecyclerViewTextView = view.findViewById(R.id.add_dishes_recycle_view_text);
        mSearchView = view.findViewById(R.id.add_dishes_search_view);

        initDishesRecycleView();

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mDishesViewModel.getSearchDish(newText).observe(getViewLifecycleOwner(), new Observer<List<Dishes>>() {
                    @Override
                    public void onChanged(List<Dishes> dishes) {
                        if (mDishes.size() > 0) {
                            mDishes.clear();
                        }
                        if (mDishes != null) {
                            mDishes.addAll(dishes);
                        }
                        if (dishes.size() == 0) {
                            mDishesRecyclerView.setVisibility(View.GONE);
                            mDishesRecyclerViewTextView.setVisibility(View.VISIBLE);
                        } else {
                            mDishesRecyclerView.setVisibility(View.VISIBLE);
                            mDishesRecyclerViewTextView.setVisibility(View.GONE);
                        }
                        mDishesRecycleViewAdapter.notifyDataSetChanged();
                    }
                });
                return false;
            }
        });

        mDishesViewModel.getDishes().observe(getViewLifecycleOwner(), new Observer<List<Dishes>>() {
            @Override
            public void onChanged(List<Dishes> dishes) {
                if (mDishes.size() > 0) {
                    mDishes.clear();
                }
                if (mDishes != null) {
                    mDishes.addAll(dishes);
                }
                if (dishes.size() == 0) {
                    mDishesRecyclerView.setVisibility(View.GONE);
                    mDishesRecyclerViewTextView.setVisibility(View.VISIBLE);
                } else {
                    mDishesRecyclerView.setVisibility(View.VISIBLE);
                    mDishesRecyclerViewTextView.setVisibility(View.GONE);
                }
                mDishesRecycleViewAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }


    private void initDishesRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mDishesRecyclerView.setLayoutManager(linearLayoutManager);
        mDishesRecycleViewAdapter = new DishesRecycleViewAdapter(mDishes,this);
        VerticalSpacingItemDecorator verticalSpacingItemDecorator = new VerticalSpacingItemDecorator(8);
        mDishesRecyclerView.addItemDecoration(verticalSpacingItemDecorator);
        mDishesRecyclerView.setAdapter(mDishesRecycleViewAdapter);
    }

    @Override
    public void OnDishClick(int position) {
        DiaryAddDishesDialog diaryAddDishesDialog = new DiaryAddDishesDialog(mDishes.get(position),mType,mDateTime);
        diaryAddDishesDialog.show(getChildFragmentManager(), "Add dishes dialog");
    }
}
