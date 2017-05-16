package iss.nus.edu.medipalappln.asynTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import iss.nus.edu.medipalappln.dao.WeightDataBaseAdapter;
import iss.nus.edu.medipalappln.medipal.Weight;

public class AddWeightTask extends AsyncTask<Weight, Void, Long> {

    private static final String TAG = "AddWeightTask";

    private WeightDataBaseAdapter weightDataBaseAdapter;

    public AddWeightTask(Context context) {
        this.weightDataBaseAdapter = new WeightDataBaseAdapter(context);
    }

    @Override
    protected Long doInBackground(Weight... params) {
        long result = 0;

        result = weightDataBaseAdapter.add(params[0]);

        return result;

    }

    @Override
    protected void onPostExecute(Long result) {
        if (result != -1) {
            //Toast.makeText(context, "Record added", Toast.LENGTH_LONG).show();
            Log.i(TAG, "Record added");
        }

        if (weightDataBaseAdapter != null) {
            weightDataBaseAdapter.close();
        }

    }

}
