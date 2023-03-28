package com.example.helth_frag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class tablayout_request extends AppCompatActivity {

    TabLayout tabLayoutR;
    TabItem accept , reject;
    PageAdapterReq pageAdapterReq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablayout_request);
        getSupportActionBar().hide();
//        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        accept = findViewById(R.id.activeReq);
        reject = findViewById(R.id.inactiveReq);

        ViewPager viewPager = findViewById(R.id.fragmentContaineReq);
        tabLayoutR = findViewById(R.id.includeReq);

        pageAdapterReq =new PageAdapterReq(getSupportFragmentManager(),2);
        viewPager.setAdapter(pageAdapterReq);


        tabLayoutR.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
//                if (tab.getPosition() == 0 || tab.getPosition() == 1 ) {
//                    pageAdapterReq.notifyDataSetChanged();
//                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //for swipping on mbl
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutR));


    }
}