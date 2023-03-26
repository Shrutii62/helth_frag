package com.example.helth_frag;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {

    int tabcount;


    public PageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount= behavior;
    }

    @NonNull

    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return  new D_get_pApp_details();

            case 1:
                return  new d_historyappointment();

            default:
                return null;
        }
    }


    public int getCount() {

        return tabcount;
    }
}
