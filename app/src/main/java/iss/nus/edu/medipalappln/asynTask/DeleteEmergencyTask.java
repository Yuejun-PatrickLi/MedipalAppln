package iss.nus.edu.medipalappln.asynTask;

/**
 * Created by root on 26/3/17.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import iss.nus.edu.medipalappln.dao.EmergencyDataBaseAdapter;
import iss.nus.edu.medipalappln.medipal.Emergency;

public class DeleteEmergencyTask extends AsyncTask<Emergency, Void, Long> {

    private static final String TAG = "DeleteBloodPressureTask";

    private Context context;
    private EmergencyDataBaseAdapter emergencyDataBaseAdapter;

    public DeleteEmergencyTask(Context context) {
        this.emergencyDataBaseAdapter = new EmergencyDataBaseAdapter(context);
    }

    @Override
    protected Long doInBackground(Emergency... params) {
        long result = 0;

        result = emergencyDataBaseAdapter.delete(params[0]);

        return result;
    }

    @Override
    protected void onPostExecute(Long result) {
        if (result != -1) {
            //Toast.makeText(context, "Record deleted", Toast.LENGTH_LONG).show();
            Log.i(TAG, "Record deleted");
        }

        if (emergencyDataBaseAdapter != null) {
            emergencyDataBaseAdapter.close();
        }

    }

}