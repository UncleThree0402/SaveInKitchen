package com.unclethree.saveinkitchen;

import adapters.PageFragmentAdapter;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import fragments.HomePageFragment;

public class MainActivity extends AppCompatActivity {


    //UI
    ViewPager mViewPager;

    //Var
    private PageFragmentAdapter pageFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = findViewById(R.id.home_page_view_pager);

        pageFragmentAdapter = new PageFragmentAdapter(getSupportFragmentManager());
        setViewPage(mViewPager);
    }

    private void setViewPage(ViewPager viewPager) {
        pageFragmentAdapter.addFragment(new HomePageFragment(), "HomePage");

        viewPager.setAdapter(pageFragmentAdapter);
    }
}