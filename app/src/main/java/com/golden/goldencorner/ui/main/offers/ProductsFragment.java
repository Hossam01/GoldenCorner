package com.golden.goldencorner.ui.main.offers;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.golden.goldencorner.R;
import com.golden.goldencorner.data.local.SharedPreferencesManager;
import com.golden.goldencorner.ui.base.SectionsPagerAdapter;
import com.golden.goldencorner.ui.main.latestProducts.LastProductsFragment;
import com.golden.goldencorner.ui.main.mostRequested.MustRequestedFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductsFragment extends Fragment {

    public static final String SELECTED_FRAGMENT_NAME = "selectedFragmentName";
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
//    @BindView(R.id.OffersTabItem)
//    TabItem OffersTabItem;
//    @BindView(R.id.lastTabItem)
//    TabItem lastTabItem;
//    @BindView(R.id.mustTabItem)
//    TabItem mustTabItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_products, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
//        OffersTabItem.text = getString(R.string.offers);
//        lastTabItem.text = getString(R.string.latest_products);
//        mustTabItem.text = getString(R.string.Most_requested);

//        tabs.addTab(tabs.newTab().setText(getString(R.string.offers)));
//        tabs.addTab(tabs.newTab().setText(getString(R.string.latest_products)));
//        tabs.addTab(tabs.newTab().setText(getString(R.string.Most_requested)));
//        tabs.setTabGravity(TabLayout.GRAVITY_FILL);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new OffersFragment());
        fragments.add(new LastProductsFragment());
        fragments.add(new MustRequestedFragment());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(
                getChildFragmentManager(), fragments);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs.setupWithViewPager(viewPager);

        tabs.getTabAt(0).setText(R.string.offers);
        tabs.getTabAt(1).setText(R.string.latest_products);
        tabs.getTabAt(2).setText(R.string.Most_requested);

        String name = SharedPreferencesManager.getString(SELECTED_FRAGMENT_NAME);
        Log.i(name, name);
        navToSelectedFragment(name);

    }

    private void navToSelectedFragment(String arguments) {
        if (arguments != null) {
            if (arguments.equals(OffersFragment.class.getSimpleName())) {
                tabs.getTabAt(0).select();
                tabs.setScrollPosition(0, 0, true);
                viewPager.setCurrentItem(0);
            } else if (arguments.equals(LastProductsFragment.class.getSimpleName())) {
                tabs.getTabAt(1).select();
                tabs.setScrollPosition(1, 0, true);
                viewPager.setCurrentItem(1);
            } else if (arguments.equals(MustRequestedFragment.class.getSimpleName())) {
                tabs.getTabAt(2).select();
                tabs.setScrollPosition(2, 0, true);
                viewPager.setCurrentItem(2);
            }
        }
    }
}