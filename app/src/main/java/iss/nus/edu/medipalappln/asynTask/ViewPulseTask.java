package iss.nus.edu.medipalappln.asynTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import iss.nus.edu.medipalappln.dao.PulseDataBaseAdapter;
import iss.nus.edu.medipalappln.medipal.Pulse;

public class ViewPulseTask extends AsyncTask<Void, Void, ArrayList<Pulse>> {

    private static final String TAG = "ViewPulseTask";

    private PulseDataBaseAdapter pulseDataBaseAdapter;


    public ViewPulseTask(Context context) {
        this.pulseDataBaseAdapter = new PulseDataBaseAdapter(context);
    }

    @Override
    protected ArrayList<Pulse> doInBackground(Void... params) {
        ArrayList<Pulse> list = pulseDataBaseAdapter.get();

        Log.i(TAG, "Number of row(s): " + String.valueOf(list.size()));
        return list;
    }

}
