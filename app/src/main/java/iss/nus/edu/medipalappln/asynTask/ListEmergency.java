package iss.nus.edu.medipalappln.asynTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import iss.nus.edu.medipalappln.dao.EmergencyDataBaseAdapter;
import iss.nus.edu.medipalappln.medipal.Emergency;

public class ListEmergency extends AsyncTask<Void, Void, ArrayList<Emergency>> {

    private static final String TAG = "ViewBloodPressureTask";

    private EmergencyDataBaseAdapter emergencyDataBaseAdapter;


    public ListEmergency(Context context) {
        this.emergencyDataBaseAdapter = new EmergencyDataBaseAdapter(context);
    }

    @Override
    protected ArrayList<Emergency> doInBackground(Void... params) {
        ArrayList<Emergency> list = emergencyDataBaseAdapter.get();

        Log.i(TAG, "Number of row(s): " + String.valueOf(list.size()));
        return list;
    }

}
