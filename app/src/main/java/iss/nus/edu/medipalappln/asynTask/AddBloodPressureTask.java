package iss.nus.edu.medipalappln.asynTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import iss.nus.edu.medipalappln.dao.BloodPressureDataBaseAdapter;
import iss.nus.edu.medipalappln.medipal.BloodPressure;

public class AddBloodPressureTask extends AsyncTask<BloodPressure, Void, Long> {

    private static final String TAG = "AddBloodPressureTask";

    private BloodPressureDataBaseAdapter bloodPressureDataBaseAdapter;

    public AddBloodPressureTask(Context context) {
        this.bloodPressureDataBaseAdapter = new BloodPressureDataBaseAdapter(context);
    }

    @Override
    protected Long doInBackground(BloodPressure... params) {
        long result = 0;
        //Date date = (Date) Calendar.getInstance().getTime();

        //BloodPressure bloodPressure = new BloodPressure(100, 80, "2017-02-01 10:00:00");
        result = bloodPressureDataBaseAdapter.add(params[0]);

        return result;

    }

    @Override
    protected void onPostExecute(Long result) {
        if (result != -1) {
            //Toast.makeText(context, "Record added", Toast.LENGTH_LONG).show();
            Log.i(TAG, "Record added");
        }

        if (bloodPressureDataBaseAdapter != null) {
            bloodPressureDataBaseAdapter.close();
        }

    }

}
