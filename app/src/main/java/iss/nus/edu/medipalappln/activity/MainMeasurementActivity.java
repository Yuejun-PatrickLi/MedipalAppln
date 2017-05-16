package iss.nus.edu.medipalappln.activity;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import iss.nus.edu.medipalappln.R;
import iss.nus.edu.medipalappln.fragment.AddMeasurementFragment;
import iss.nus.edu.medipalappln.fragment.MainMeasurementFragment;
import iss.nus.edu.medipalappln.fragment.ShowAllMeasurementFragment;
import iss.nus.edu.medipalappln.fragment.ShowEmergency;
import iss.nus.edu.medipalappln.fragment.ViewBloodPressureFragment;
import iss.nus.edu.medipalappln.fragment.ViewPulseFragment;
import iss.nus.edu.medipalappln.fragment.ViewTemperatureFragment;
import iss.nus.edu.medipalappln.fragment.ViewWeightFragment;
import iss.nus.edu.medipalappln.medipal.BloodPressure;
import iss.nus.edu.medipalappln.medipal.Pulse;
import iss.nus.edu.medipalappln.medipal.Temperature;
import iss.nus.edu.medipalappln.medipal.Weight;

public class MainMeasurementActivity extends AppCompatActivity
        implements MainMeasurementFragment.OnFragmentInteractionListener,
                ShowAllMeasurementFragment.OnFragmentInteractionListener,
                AddMeasurementFragment.OnFragmentInteractionListener,
                ViewBloodPressureFragment.OnFragmentInteractionListener,
                ViewPulseFragment.OnFragmentInteractionListener,
                ShowEmergency.OnFragmentInteractionListener,
                ViewTemperatureFragment.OnFragmentInteractionListener,
                ViewWeightFragment.OnFragmentInteractionListener {

    private static final String TAG = "MainMeasurementActivity";

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");;
    private LineGraphSeries<DataPoint> series1, series2;
    private Button btnShowAll;

    private LineGraphSeries<DataPoint> series;
    private Button btn_show_all;
    private List<BloodPressure> bloodPressures = new ArrayList<BloodPressure>();
    private List<Pulse> pulses = new ArrayList<Pulse>();
    private List<Temperature> temperatures = new ArrayList<Temperature>();
    private List<Weight> weights = new ArrayList<Weight>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MainMeasurementFragment fragment = new MainMeasurementFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContent, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
//     new AlertDialog.Builder(this)
//              .setMessage("Are you sure you want to exit?")
//              .setCancelable(false)
//              .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int id) {
//                  finish();
//                }
//              })
//              .setNegativeButton("No", null)
//              .show();
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
