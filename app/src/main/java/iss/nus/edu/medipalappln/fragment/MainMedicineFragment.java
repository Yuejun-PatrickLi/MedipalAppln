package iss.nus.edu.medipalappln.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import iss.nus.edu.medipalappln.R;
import iss.nus.edu.medipalappln.adapter.PagerAdapter;
import iss.nus.edu.medipalappln.medipal.BloodPressure;
import iss.nus.edu.medipalappln.medipal.Pulse;
import iss.nus.edu.medipalappln.medipal.Temperature;
import iss.nus.edu.medipalappln.medipal.Weight;

public class MainMedicineFragment extends Fragment {

    private static final String TAG = "MainMedicineFragment";

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");;
    private LineGraphSeries<DataPoint> series1, series2;
    private Button btnShowAll;

    private LineGraphSeries<DataPoint> series;
    private Button btn_show_all;
    private List<BloodPressure> bloodPressures = new ArrayList<BloodPressure>();
    private List<Pulse> pulses = new ArrayList<Pulse>();
    private List<Temperature> temperatures = new ArrayList<Temperature>();
    private List<Weight> weights = new ArrayList<Weight>();

    private OnFragmentInteractionListener mListener;

    public MainMedicineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_medicine, container, false);
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
        PagerAdapter pagerAdapter = new PagerAdapter(fragmentManager, 3);
        viewPager.setAdapter(pagerAdapter);
        final TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Medicine"));
        tabLayout.addTab(tabLayout.newTab().setText("Medicine Category"));
        tabLayout.addTab(tabLayout.newTab().setText("Consumption"));
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

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (MainMedicineFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
