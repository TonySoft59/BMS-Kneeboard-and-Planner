package com.oldgoat5.bmstacticalreference.navigation.aegean;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/*********************************************************************
 * Copyright © Michael Evans - All Rights Reserved.
 *
 * @author Michael Evans
 * @since 8/29/2015
 *********************************************************************/
public class AegeanNavigationFragmentPageAdapter extends FragmentPagerAdapter
{
    private final int COUNT = 3;
    private Context CONTEXT;

    public AegeanNavigationFragmentPageAdapter(FragmentManager fm, Context context)
    {
        super(fm);
        this.CONTEXT = context;
    }

    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:
                //return new CyprusAegeanChartFragment();
                return new GreeceAegeanChartFragment();
            case 1:
                return new GreeceAegeanChartFragment();
            case 2:
                //return new TurkeyAegeanChartFragment();
                break;

            default:
                break;
        }

        return null;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        switch (position) //south korea, north korea, japan, china, russia
        {
            case 0:
                return "Cyrpus";
            case 1:
                return "Greece";
            case 2:
                return "Turkiye";

            default:
                break;
        }

        return null;
    }

    @Override
    public int getCount()
    {
        return COUNT;
    }
}
