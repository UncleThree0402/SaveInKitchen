package com.unclethree.saveinkitchen;

import adapters.PageFragmentAdapter;
import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import dialogs.DiaryQuickAddDialog;
import fragments.diary.DiaryHistoryAddDishesFragment;
import fragments.diary.DiaryHistoryAddFoodFragment;

public class AddFoodActivity extends AppCompatActivity {

    //UI
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ExtendedFloatingActionButton mQuickAdd;

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
        mQuickAdd = findViewById(R.id.add_food_quick_add);

        mTabLayout.setupWithViewPager(mViewPager);

        pageFragmentAdapter = new PageFragmentAdapter(getSupportFragmentManager());
        setViewPage(mViewPager);

        mQuickAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiaryQuickAddDialog diaryQuickAddDialog = new DiaryQuickAddDialog(mType,mDateTime);
                diaryQuickAddDialog.show(getSupportFragmentManager(),"diaryQuickAdd");
            }
        });
    }

    private void setViewPage(ViewPager viewPager) {
        pageFragmentAdapter.addFragment(new DiaryHistoryAddFoodFragment(mType,mDateTime), "Stock");
        pageFragmentAdapter.addFragment(new DiaryHistoryAddDishesFragment(mType,mDateTime), "Dishes");
        viewPager.setAdapter(pageFragmentAdapter);
    }
}