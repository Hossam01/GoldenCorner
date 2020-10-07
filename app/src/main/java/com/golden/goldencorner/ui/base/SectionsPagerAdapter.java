package com.golden.goldencorner.ui.base;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import java.util.ArrayList;
import java.util.List;

public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();
    public SectionsPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments.addAll(fragments);
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}