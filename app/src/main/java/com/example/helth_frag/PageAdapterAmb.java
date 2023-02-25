package com.example.helth_frag;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapterAmb  extends FragmentPagerAdapter {

    int tabcountamb;

    public PageAdapterAmb(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcountamb= behavior;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return  new amb_shwBooking();

            case 1:
                return  new ambshw_inactiveBooking();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabcountamb;
    }
}
