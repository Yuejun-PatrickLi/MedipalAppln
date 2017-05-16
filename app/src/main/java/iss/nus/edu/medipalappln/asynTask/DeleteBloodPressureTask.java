package iss.nus.edu.medipalappln.asynTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import iss.nus.edu.medipalappln.dao.BloodPressureDataBaseAdapter;
import iss.nus.edu.medipalappln.medipal.BloodPressure;

public class DeleteBloodPressureTask extends AsyncTask<BloodPressure, Void, Long> {

    private static final String TAG = "DeleteBloodPressureTask";

    private Context context;
    private BloodPressureDataBaseAdapter bloodPressureDataBaseAdapter;

    public DeleteBloodPressureTask(Context context) {
        this.bloodPressureDataBaseAdapter = new BloodPressureDataBaseAdapter(context);
    }

    @Override
    protected Long doInBackground(BloodPressure... params) {
        long result = 0;

        result = bloodPressureDataBaseAdapter.delete(params[0]);

        return result;
    }

    @Override
    protected void onPostExecute(Long result) {
        if (result != -1) {
            //Toast.makeText(context, "Record deleted", Toast.LENGTH_LONG).show();
            Log.i(TAG, "Record deleted");
        }

        if (bloodPressureDataBaseAdapter != null) {
            bloodPressureDataBaseAdapter.close();
        }

    }

}
