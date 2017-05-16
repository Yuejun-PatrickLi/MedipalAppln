package iss.nus.edu.medipalappln.activity;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import iss.nus.edu.medipalappln.R;
import iss.nus.edu.medipalappln.fragment.AppointmentFragment;

public class MainAppointmentActivity extends AppCompatActivity
        implements AppointmentFragment.OnFragmentInteractionListener {

    private static final String TAG = "MainAppointmentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AppointmentFragment fragment = new AppointmentFragment();

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
