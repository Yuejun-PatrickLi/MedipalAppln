package iss.nus.edu.medipalappln.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import iss.nus.edu.medipalappln.fragment.CategoryFragment;

import iss.nus.edu.medipalappln.fragment.MedicineFragment;

/**
 * Created by Swarna on 8/6/2016.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
  int numOfTabs;

  public PagerAdapter(FragmentManager fm, int numOfTabs) {
    super(fm);
    this.numOfTabs = numOfTabs;
  }

  @Override
  public Fragment getItem(int position) {
    switch (position) {
      case 0:
        MedicineFragment medicineFragment = new MedicineFragment();
        return medicineFragment;
      default:
        CategoryFragment categoryFragment = new CategoryFragment();
        return categoryFragment;
    }
  }

  @Override
  public int getCount() {
    return numOfTabs;
  }
}
