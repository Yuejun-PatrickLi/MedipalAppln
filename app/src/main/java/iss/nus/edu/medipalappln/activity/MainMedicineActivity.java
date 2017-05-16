package iss.nus.edu.medipalappln.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import iss.nus.edu.medipalappln.R;
import iss.nus.edu.medipalappln.adapter.PagerAdapter;
import iss.nus.edu.medipalappln.fragment.MedicineFragment;

public class MainMedicineActivity extends AppCompatActivity
        implements MedicineFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {

      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_medicine);
      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);

      final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
      PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), 3);
      viewPager.setAdapter(pagerAdapter);

      final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
      tabLayout.addTab(tabLayout.newTab().setText("Medicine"));
      tabLayout.addTab(tabLayout.newTab().setText("Medicine Category"));
      tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

      viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }


    @Override
    protected void onStop() {
      //App.user.saveAll(this);
      super.onStop();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
