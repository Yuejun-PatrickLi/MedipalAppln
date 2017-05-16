package iss.nus.edu.medipalappln.asynTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import iss.nus.edu.medipalappln.dao.WeightDataBaseAdapter;
import iss.nus.edu.medipalappln.medipal.Weight;

public class DeleteWeightTask extends AsyncTask<Weight, Void, Long> {

    private static final String TAG = "DeleteWeightTask";

    private Context context;
    private WeightDataBaseAdapter weightDataBaseAdapter;

    public DeleteWeightTask(Context context) {
        this.weightDataBaseAdapter = new WeightDataBaseAdapter(context);
    }

    @Override
    protected Long doInBackground(Weight... params) {
        long result = 0;

        result = weightDataBaseAdapter.delete(params[0]);

        return result;
    }

    @Override
    protected void onPostExecute(Long result) {
        if (result != -1) {
            //Toast.makeText(context, "Record deleted", Toast.LENGTH_LONG).show();
            Log.i(TAG, "Record deleted");
        }

        if (weightDataBaseAdapter != null) {
            weightDataBaseAdapter.close();
        }

    }

}
