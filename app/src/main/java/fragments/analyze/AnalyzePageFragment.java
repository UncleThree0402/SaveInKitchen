package fragments.analyze;

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
import dialogs.StockDialog;
import fragments.ingredient.IngredientContentFragment;
import fragments.ingredient.IngredientStockFragment;

public class AnalyzePageFragment extends Fragment {
    //UI
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ExtendedFloatingActionButton mFloatingActionButton;

    //Var
    private PageFragmentAdapter pageFragmentAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.analyze_page_fragment, container, false);

        mViewPager = view.findViewById(R.id.analyze_view_page);
        mTabLayout = view.findViewById(R.id.analyze_tab_layout);

        mTabLayout.setupWithViewPager(mViewPager);

        pageFragmentAdapter = new PageFragmentAdapter(getChildFragmentManager());
        setViewPage(mViewPager);


        return view;
    }

    private void setViewPage(ViewPager viewPager) {
        pageFragmentAdapter.addFragment(new AnalyzeBuyingFragment() , "Buying");
        pageFragmentAdapter.addFragment(new AnalyzeEatingFragment() , "Eating");
        viewPager.setAdapter(pageFragmentAdapter);
    }
}
