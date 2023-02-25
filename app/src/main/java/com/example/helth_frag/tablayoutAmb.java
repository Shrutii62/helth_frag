package com.example.helth_frag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class tablayoutAmb extends AppCompatActivity {

    TabLayout tabLayout;
    TabItem active , inactive;
    PageAdapterAmb pageAdtrAmb;

    private Toolbar topAppBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tablayout_amb);



//        setHasOptionsMenu(true);
//
//
//        topAppBar = view.findViewById(R.id.topAppBar);


        topAppBar.inflateMenu(R.menu.main_dotmenu);

//        ((AppCompatActivity) getActivity()).setSupportActionBar(topAppBar);

        float radius = getResources().getDimension(R.dimen.default_corner_radius);
        MaterialShapeDrawable materialShapeDrawable = (MaterialShapeDrawable)topAppBar.getBackground();
        materialShapeDrawable.setShapeAppearanceModel(materialShapeDrawable.getShapeAppearanceModel()
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED,radius)
                .build());



        topAppBar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.men1:
                    FirebaseAuth.getInstance().signOut();

                     Toast.makeText(tablayoutAmb.this, "logged out", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.men2:
                    // Save profile changes
                    return true;
                default:
                    return false;
            }
        });







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