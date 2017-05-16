package iss.nus.edu.medipalappln.asynTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import iss.nus.edu.medipalappln.dao.BloodPressureDataBaseAdapter;
import iss.nus.edu.medipalappln.medipal.BloodPressure;

public class ViewBloodPressureTask extends AsyncTask<Void, Void, ArrayList<BloodPressure>> {

    private static final String TAG = "ViewBloodPressureTask";

    private BloodPressureDataBaseAdapter bloodPressureDataBaseAdapter;


    public ViewBloodPressureTask(Context context) {
        this.bloodPressureDataBaseAdapter = new BloodPressureDataBaseAdapter(context);
    }

    @Override
    protected ArrayList<BloodPressure> doInBackground(Void... params) {
        ArrayList<BloodPressure> list = bloodPressureDataBaseAdapter.get();

        Log.i(TAG, "Number of row(s): " + String.valueOf(list.size()));
        return list;
    }

}
