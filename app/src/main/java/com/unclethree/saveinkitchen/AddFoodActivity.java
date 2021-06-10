package com.unclethree.saveinkitchen;

import adapters.PageFragmentAdapter;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import fragments.*;

public class AddFoodActivity extends AppCompatActivity {

    //UI
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    //Var
    private PageFragmentAdapter pageFragmentAdapter;
    private String mType;
    private long mDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        Intent intent = getIntent();
        mType = intent.getStringExtra("Type");
        mDateTime = intent.getLongExtra("Time",0);

        mViewPager = findViewById(R.id.add_food_view_page);
        mTabLayout = findViewById(R.id.add_food_tab_layout);

        mTabLayout.setupWithViewPager(mViewPager);

        pageFragmentAdapter = new PageFragmentAdapter(getSupportFragmentManager());
        setViewPage(mViewPager);
    }

    private void setViewPage(ViewPager viewPager) {
        pageFragmentAdapter.addFragment(new DiaryHistoryAddFoodFragment(mType,mDateTime), "Stock");
        viewPager.setAdapter(pageFragmentAdapter);
    }
}