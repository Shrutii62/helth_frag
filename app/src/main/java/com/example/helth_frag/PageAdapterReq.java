package com.example.helth_frag;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapterReq  extends FragmentPagerAdapter {

    int tabcountamb;

    public PageAdapterReq(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcountamb= behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return  new acceptReq();

            case 1:
                return  new RequestinTabh();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabcountamb;
    }
}
