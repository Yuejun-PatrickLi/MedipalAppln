package iss.nus.edu.medipalappln.asynTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import iss.nus.edu.medipalappln.dao.TemperatureDataBaseAdapter;
import iss.nus.edu.medipalappln.medipal.Temperature;

public class AddTemperatureTask extends AsyncTask<Temperature, Void, Long> {

    private static final String TAG = "AddTemperatureTask";

    private TemperatureDataBaseAdapter temperatureDataBaseAdapter;

    public AddTemperatureTask(Context context) {
        this.temperatureDataBaseAdapter = new TemperatureDataBaseAdapter(context);
    }

    @Override
    protected Long doInBackground(Temperature... params) {
        long result = 0;

        result = temperatureDataBaseAdapter.add(params[0]);

        return result;

    }

    @Override
    protected void onPostExecute(Long result) {
        if (result != -1) {
            //Toast.makeText(context, "Record added", Toast.LENGTH_LONG).show();
            Log.i(TAG, "Record added");
        }

        if (temperatureDataBaseAdapter != null) {
            temperatureDataBaseAdapter.close();
        }

    }

}
