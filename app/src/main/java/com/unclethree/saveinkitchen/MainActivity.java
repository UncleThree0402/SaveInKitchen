package com.unclethree.saveinkitchen;

import adapters.PageFragmentAdapter;
import android.app.Activity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigationrail.NavigationRailView;
import fragments.analyze.AnalyzePageFragment;
import fragments.buylist.BuyListPageFragment;
import fragments.cooking.CookingPageFragment;
import fragments.diary.DiaryHistoryFragment;
import fragments.history.HistoryPageFragment;
import fragments.ingredient.IngredientPageFragment;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnTouchListener {


    //UI
    private ViewPager mViewPager;
    private NavigationRailView mNavigationRailView;
    private DrawerLayout mDrawerLayout;


    //Var
    private PageFragmentAdapter pageFragmentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Locale.setDefault(Locale.US);

        mViewPager = findViewById(R.id.home_page_view_pager);
        mNavigationRailView = findViewById(R.id.bottom_navigation);
        mDrawerLayout = findViewById(R.id.main_drawer_layout);
        mNavigationRailView.setOnItemSelectedListener(this);

        if(savedInstanceState != null){
            mViewPager.setCurrentItem(savedInstanceState.getInt("Page"));
        }

        mDrawerLayout.setOnTouchListener(this);

        pageFragmentAdapter = new PageFragmentAdapter(getSupportFragmentManager());
        setViewPage(mViewPager);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Page",mViewPager.getCurrentItem());
    }

    private void setViewPage(ViewPager viewPager) {
        pageFragmentAdapter.addFragment(new DiaryHistoryFragment(), "DiaryPage");
        pageFragmentAdapter.addFragment(new BuyListPageFragment(), "BuyListPage");
        pageFragmentAdapter.addFragment(new CookingPageFragment(), "Cooking Page");
        pageFragmentAdapter.addFragment(new IngredientPageFragment(), "FoodPage");
        pageFragmentAdapter.addFragment(new AnalyzePageFragment(), "AnalyzePage");
        pageFragmentAdapter.addFragment(new HistoryPageFragment(), "HistoryPage");
        viewPager.setAdapter(pageFragmentAdapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_diary:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.nav_buy_list:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.nav_cook:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.nav_food:
                mViewPager.setCurrentItem(3);
                break;
            case R.id.nav_analyze:
                mViewPager.setCurrentItem(4);
                break;
            case R.id.nav_history:
                mViewPager.setCurrentItem(5);
                break;
        }
        mDrawerLayout.closeDrawers();
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if(view == null){
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        return false;
    }

}