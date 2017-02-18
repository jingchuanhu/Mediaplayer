package com.mediaplayer.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mediaplayer.adapter.BaseViewPagerAdapter;
import com.mediaplayer.app.R;
import com.mediaplayer.customviews.BaseViewPager;
import com.mediaplayer.customviews.BastTablayout;
import com.mediaplayer.interfaces.TabsContainerHelpers;

/**
 * Created by shrikanth on 1/21/17.
 */

public abstract class TabsContainer extends BaseFragment implements TabsContainerHelpers{
    protected BaseViewPager viewPager;
    protected BaseViewPagerAdapter adapter;
    protected BastTablayout tablayout = null;
    protected Toolbar toolbar = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.base_tabs_container_fragment, container, false);
        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        getAppCompactActivity().setSupportActionBar(toolbar);
        viewPager = (BaseViewPager)v.findViewById(R.id.base_view_pager);
        tablayout = (BastTablayout) v.findViewById(R.id.tab_layout);
        adapter = new BaseViewPagerAdapter(getChildFragmentManager(), this);
        viewPager.setAdapter(adapter);
        initializeTabs();
        return v;
    }

    private void initializeTabs(){
        if(tablayout != null) {
            for (int i = 0; i < adapter.getCount(); i++) {
                TabLayout.Tab tab = tablayout.newTab();
                tab.setText(getTabTitle(i));
                tablayout.addTab(tab);
            }
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
            tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }
    }
}
