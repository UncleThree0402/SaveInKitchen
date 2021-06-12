package fragments.cooking;

import adapters.PageFragmentAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.unclethree.saveinkitchen.R;
import dialogs.BuyListDialog;
import fragments.buylist.BuyListContentFragment;
import fragments.cooking.dishes.DishesFragment;
import fragments.cooking.preparing.PrepareFragment;
import fragments.cooking.recipe.RecipePageFragment;

public class CookingPageFragment extends Fragment {

    //UI
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    //Var
    private PageFragmentAdapter pageFragmentAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cook_page_fragment, container, false);

        mViewPager = view.findViewById(R.id.cooking_page_view_page);
        mTabLayout = view.findViewById(R.id.cooking_page_tab_layout);

        mTabLayout.setupWithViewPager(mViewPager);

        pageFragmentAdapter = new PageFragmentAdapter(getChildFragmentManager());
        setViewPage(mViewPager);

        mViewPager.setCurrentItem(0);


        return view;
    }

    private void setViewPage(ViewPager viewPager) {
        pageFragmentAdapter.addFragment(new PrepareFragment(),"Preparing");
        pageFragmentAdapter.addFragment(new RecipePageFragment(),"Recipes");
        pageFragmentAdapter.addFragment(new DishesFragment(),"Dishes");
        viewPager.setAdapter(pageFragmentAdapter);
    }

}
