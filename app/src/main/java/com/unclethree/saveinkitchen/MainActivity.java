package com.unclethree.saveinkitchen;

import adapters.PageFragmentAdapter;
import android.annotation.SuppressLint;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigationrail.NavigationRailView;
import fragments.FoodPageFragment;
import fragments.HistoryPageFragment;
import fragments.HomePageFragment;
import fragments.RecipePageFragment;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener , View.OnTouchListener, GestureDetector.OnGestureListener {


    //UI
    private ViewPager mViewPager;
    private NavigationRailView mNavigationRailView;
    private DrawerLayout mDrawerLayout;


    //Var
    private PageFragmentAdapter pageFragmentAdapter;
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Locale.setDefault(Locale.US);

        mViewPager = findViewById(R.id.home_page_view_pager);
        mNavigationRailView = findViewById(R.id.bottom_navigation);
        mDrawerLayout = findViewById(R.id.main_drawer_layout);
        mNavigationRailView.setOnItemSelectedListener(this);

        mViewPager.setOnTouchListener(this);
        mGestureDetector = new GestureDetector(this,this);


        pageFragmentAdapter = new PageFragmentAdapter(getSupportFragmentManager());
        setViewPage(mViewPager);
    }

    private void setViewPage(ViewPager viewPager) {
        pageFragmentAdapter.addFragment(new HomePageFragment(), "HomePage");
        pageFragmentAdapter.addFragment(new RecipePageFragment(), "Recipe Page");
        pageFragmentAdapter.addFragment(new FoodPageFragment(), "FoodPage");
        pageFragmentAdapter.addFragment(new HistoryPageFragment(), "HistoryPage");
        viewPager.setAdapter(pageFragmentAdapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_home:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.nav_recipe:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.nav_food:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.nav_history:
                mViewPager.setCurrentItem(3);
                break;
        }
        mDrawerLayout.closeDrawers();
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }
}