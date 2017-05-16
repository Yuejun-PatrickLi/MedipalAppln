package iss.nus.edu.medipalappln.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import iss.nus.edu.medipalappln.fragment.ConsumptionCategoryFragment;
import iss.nus.edu.medipalappln.fragment.ConsumptionHistoryFragment;
import iss.nus.edu.medipalappln.fragment.ConsumptionMedicineFragment;


public class ConsumptionPagerAdapter extends FragmentStatePagerAdapter {
    int numOfTabs;

    public ConsumptionPagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ConsumptionHistoryFragment consumptionHistoryFragment = new ConsumptionHistoryFragment();
                return consumptionHistoryFragment;
            case 1:
                ConsumptionMedicineFragment consumptionMedicineFragment = new ConsumptionMedicineFragment();
                return consumptionMedicineFragment;
            default:
                ConsumptionCategoryFragment consumptionCategoryFragment = new ConsumptionCategoryFragment();
                return consumptionCategoryFragment;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
