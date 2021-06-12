package fragments.buylist;

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

public class BuyListPageFragment extends Fragment {

    //UI
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ExtendedFloatingActionButton mFloatingActionButton;

    //Var
    private PageFragmentAdapter pageFragmentAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.buy_list_page_fragment, container, false);

        mViewPager = view.findViewById(R.id.buy_list_view_page);
        mTabLayout = view.findViewById(R.id.buy_list_tab_layout);
        mFloatingActionButton = view.findViewById(R.id.buy_list_add_button);

        mTabLayout.setupWithViewPager(mViewPager);

        pageFragmentAdapter = new PageFragmentAdapter(getChildFragmentManager());
        setViewPage(mViewPager);

        mViewPager.setCurrentItem(0);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuyListDialog buyListDialog = new BuyListDialog();
                buyListDialog.show(getChildFragmentManager(), "Add buy list dialog");
            }
        });


        return view;
    }

    private void setViewPage(ViewPager viewPager) {
        pageFragmentAdapter.addFragment(new BuyListContentFragment(),"All");
        pageFragmentAdapter.addFragment(new BuyListContentFragment("Fresh"),"Fresh");
        pageFragmentAdapter.addFragment(new BuyListContentFragment("Frozen"),"Frozen");
        pageFragmentAdapter.addFragment(new BuyListContentFragment("Dry Goods"),"Dry Goods");
        pageFragmentAdapter.addFragment(new BuyListContentFragment("Condiment"),"Condiment");
        viewPager.setAdapter(pageFragmentAdapter);
    }

}
