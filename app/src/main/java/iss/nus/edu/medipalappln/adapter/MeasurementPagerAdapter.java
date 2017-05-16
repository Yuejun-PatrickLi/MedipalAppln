package iss.nus.edu.medipalappln.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import iss.nus.edu.medipalappln.fragment.ViewBloodPressureFragment;
import iss.nus.edu.medipalappln.fragment.ViewPulseFragment;
import iss.nus.edu.medipalappln.fragment.ViewTemperatureFragment;
import iss.nus.edu.medipalappln.fragment.ViewWeightFragment;

public class MeasurementPagerAdapter extends FragmentStatePagerAdapter {

    String datePattern = "yyyy-MM-dd";
    private int numTabs;

    public MeasurementPagerAdapter(FragmentManager fm, int numTabs) {
        super(fm);
        this.numTabs = numTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ViewBloodPressureFragment();
            case 1:
                return new ViewPulseFragment();
            case 2:
                return new ViewTemperatureFragment();
            case 3:
                return new ViewWeightFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
