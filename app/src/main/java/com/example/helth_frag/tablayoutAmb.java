package com.example.helth_frag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class tablayoutAmb extends AppCompatActivity {

    TabLayout tabLayout;
    TabItem active , inactive;
    PageAdapterAmb pageAdtrAmb;

    private Toolbar topAppBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablayout_amb);

        active = findViewById(R.id.activeAmb);
        inactive = findViewById(R.id.inactiveAmb);

        ViewPager viewPager = findViewById(R.id.fragmentContaineAmb);
        tabLayout = findViewById(R.id.includeAmb);

        pageAdtrAmb =new PageAdapterAmb(getSupportFragmentManager(),2);
        viewPager.setAdapter(pageAdtrAmb);




        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0 || tab.getPosition() == 1 ) {
                    pageAdtrAmb.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //for swipping on mbl
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


    }
}