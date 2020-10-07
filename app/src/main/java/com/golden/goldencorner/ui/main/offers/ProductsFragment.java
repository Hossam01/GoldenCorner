package com.golden.goldencorner.ui.main.offers;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.golden.goldencorner.R;
import com.golden.goldencorner.ui.base.SectionsPagerAdapter;
import com.golden.goldencorner.ui.main.latestProducts.LastProductsFragment;
import com.golden.goldencorner.ui.main.mostRequested.MustRequestedFragment;
import com.google.android.material.tabs.TabItem;
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

        navToSelectedFragment(getArguments());
    }

    private void navToSelectedFragment(Bundle arguments) {
        if (arguments != null) {
            String fragmentName = (String) getArguments().get(SELECTED_FRAGMENT_NAME);
            if (TextUtils.isEmpty(fragmentName)) {
                if (fragmentName.equalsIgnoreCase(OffersFragment.class.getSimpleName())) {
                    tabs.getTabAt(0).select();
                }
                if (fragmentName.equalsIgnoreCase(LastProductsFragment.class.getSimpleName())) {
                    tabs.getTabAt(1).select();
                }
                if (fragmentName.equalsIgnoreCase(MustRequestedFragment.class.getSimpleName())) {
                    tabs.getTabAt(2).select();
                }
            }
        }
    }
}