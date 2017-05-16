package iss.nus.edu.medipalappln.asynTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import iss.nus.edu.medipalappln.dao.TemperatureDataBaseAdapter;
import iss.nus.edu.medipalappln.medipal.Temperature;

public class DeleteTemperatureTask extends AsyncTask<Temperature, Void, Long> {

    private static final String TAG = "DeleteTemperatureTask";

    private Context context;
    private TemperatureDataBaseAdapter temperatureDataBaseAdapter;

    public DeleteTemperatureTask(Context context) {
        this.temperatureDataBaseAdapter = new TemperatureDataBaseAdapter(context);
    }

    @Override
    protected Long doInBackground(Temperature... params) {
        long result = 0;

        result = temperatureDataBaseAdapter.delete(params[0]);

        return result;
    }

    @Override
    protected void onPostExecute(Long result) {
        if (result != -1) {
            //Toast.makeText(context, "Record deleted", Toast.LENGTH_LONG).show();
            Log.i(TAG, "Record deleted");
        }

        if (temperatureDataBaseAdapter != null) {
            temperatureDataBaseAdapter.close();
        }

    }

}
