package com.example.hp.myslamer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class ViewPageAdapter extends FragmentStatePagerAdapter {
    CharSequence Titles[];
    int numbOfTab;

    public ViewPageAdapter(FragmentManager fm,CharSequence mTitle[],int mnumOfTab) {
        super(fm);
        this.Titles=mTitle;
        this.numbOfTab=mnumOfTab;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) // if the position is 0 we are returning the First tab
        {
            com.example.hp.myslamer.FragmentShowAll tab1 = new com.example.hp.myslamer.FragmentShowAll();
            return tab1;
        }
        else             // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            com.example.hp.myslamer.FragmentAdd tab2 = new com.example.hp.myslamer.FragmentAdd();
            return tab2;
        }
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return numbOfTab;
    }
}
