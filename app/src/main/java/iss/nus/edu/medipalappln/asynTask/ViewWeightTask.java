package iss.nus.edu.medipalappln.asynTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import iss.nus.edu.medipalappln.dao.WeightDataBaseAdapter;
import iss.nus.edu.medipalappln.medipal.Weight;

public class ViewWeightTask extends AsyncTask<Void, Void, ArrayList<Weight>> {

    private static final String TAG = "ViewWeightTask";

    private WeightDataBaseAdapter weightDataBaseAdapter;


    public ViewWeightTask(Context context) {
        this.weightDataBaseAdapter = new WeightDataBaseAdapter(context);
    }

    @Override
    protected ArrayList<Weight> doInBackground(Void... params) {
        ArrayList<Weight> list = weightDataBaseAdapter.get();

        Log.i(TAG, "Number of row(s): " + String.valueOf(list.size()));
        return list;
    }

}
