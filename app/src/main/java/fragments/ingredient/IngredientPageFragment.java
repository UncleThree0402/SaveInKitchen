package fragments.ingredient;

import adapters.IngredientRecycleViewAdapter;
import adapters.PageFragmentAdapter;
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
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.unclethree.saveinkitchen.R;
import dialogs.BuyListDialog;
import dialogs.StockDialog;
import fragments.buylist.BuyListContentFragment;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import models.Food;
import util.VerticalSpacingItemDecorator;
import viewmodels.FoodViewModel;

import java.util.ArrayList;
import java.util.List;

public class IngredientPageFragment extends Fragment{

    //UI
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ExtendedFloatingActionButton mFloatingActionButton;

    //Var
    private PageFragmentAdapter pageFragmentAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ingredients_page_fragment, container, false);

        mViewPager = view.findViewById(R.id.ingredient_view_page);
        mTabLayout = view.findViewById(R.id.ingredient_tab_layout);
        mFloatingActionButton = view.findViewById(R.id.ingredient_add_button);

        mTabLayout.setupWithViewPager(mViewPager);

        pageFragmentAdapter = new PageFragmentAdapter(getChildFragmentManager());
        setViewPage(mViewPager);


        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StockDialog stockDialog = new StockDialog();
                stockDialog.show(getChildFragmentManager(), "Add stock dialog");
            }
        });
        return view;
    }

    private void setViewPage(ViewPager viewPager) {
        pageFragmentAdapter.addFragment(new IngredientContentFragment(),"All");
        pageFragmentAdapter.addFragment(new IngredientStockFragment(), "Stock");
        pageFragmentAdapter.addFragment(new IngredientContentFragment("Fresh"),"Fresh");
        pageFragmentAdapter.addFragment(new IngredientContentFragment("Frozen"),"Frozen");
        pageFragmentAdapter.addFragment(new IngredientContentFragment("Dry Goods"),"Dry Goods");
        pageFragmentAdapter.addFragment(new IngredientContentFragment("Condiment"),"Condiment");
        viewPager.setAdapter(pageFragmentAdapter);
    }

}
