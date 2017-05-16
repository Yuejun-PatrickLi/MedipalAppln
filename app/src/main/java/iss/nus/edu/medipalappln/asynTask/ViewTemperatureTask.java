package iss.nus.edu.medipalappln.asynTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import iss.nus.edu.medipalappln.dao.TemperatureDataBaseAdapter;
import iss.nus.edu.medipalappln.medipal.Temperature;

public class ViewTemperatureTask extends AsyncTask<Void, Void, ArrayList<Temperature>> {

    private static final String TAG = "ViewTemperatureTask";

    private TemperatureDataBaseAdapter temperatureDataBaseAdapter;

    public ViewTemperatureTask(Context context) {
        this.temperatureDataBaseAdapter = new TemperatureDataBaseAdapter(context);
    }

    @Override
    protected ArrayList<Temperature> doInBackground(Void... params) {
        ArrayList<Temperature> list = temperatureDataBaseAdapter.get();

        Log.i(TAG, "Number of row(s): " + String.valueOf(list.size()));
        return list;
    }

}
