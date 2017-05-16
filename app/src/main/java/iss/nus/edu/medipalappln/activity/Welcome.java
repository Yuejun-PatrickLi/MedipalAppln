package iss.nus.edu.medipalappln.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import iss.nus.edu.medipalappln.R;
import iss.nus.edu.medipalappln.dao.BioDataBaseAdapter;
import iss.nus.edu.medipalappln.fragment.Dashboard;
import iss.nus.edu.medipalappln.fragment.IceDetails;
import iss.nus.edu.medipalappln.fragment.MainFragment;
import iss.nus.edu.medipalappln.fragment.PersonalBioForm;
import iss.nus.edu.medipalappln.fragment.PersonalBioFragment;
import iss.nus.edu.medipalappln.fragment.ShowEmergency;

public class Welcome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                    MainFragment.OnFragmentInteractionListener,
                    Dashboard.OnFragmentInteractionListener,
                    PersonalBioForm.OnFragmentInteractionListener,
                    PersonalBioFragment.OnFragmentInteractionListener,
                    IceDetails.OnFragmentInteractionListener,
                    ShowEmergency.OnFragmentInteractionListener {
    public Session session;
    private static Context sContext = null;
    BioDataBaseAdapter bioDataBaseAdapter;

    public static Context getContext() {
        return sContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomehome);

        sContext = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            Fragment fragment = null;
            Class fragmentClass = null;
            session=new Session(this);
            fragmentClass = MainFragment.class;
            //fragmentClass = Dashboard.class;
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;
        if (id == R.id.nav_home) {
            fragmentClass = MainFragment.class;
        } else if (id == R.id.PersonalBio) {
            //fragmentClass = PersonalBioForm.class;
            fragmentClass = PersonalBioFragment.class;
        } else if (id == R.id.nav_ice) {
            fragmentClass = IceDetails.class;
        } else if (id == R.id.nav_show_ice) {
            fragmentClass = ShowEmergency.class;
        }
        /*
        else if (id == R.id.Logout) {
            logout();
            fragmentClass = PersonalBioFragment.class;
        }*/


        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void logout() {
        session.setLoggedin(Boolean.FALSE,session.username());
        finish();
        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
    }

}
