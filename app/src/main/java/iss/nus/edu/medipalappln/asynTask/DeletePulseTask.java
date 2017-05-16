package iss.nus.edu.medipalappln.asynTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import iss.nus.edu.medipalappln.dao.PulseDataBaseAdapter;
import iss.nus.edu.medipalappln.medipal.Pulse;

public class DeletePulseTask extends AsyncTask<Pulse, Void, Long> {

    private static final String TAG = "DeletePulseTask";

    private Context context;
    private PulseDataBaseAdapter pulseDataBaseAdapter;

    public DeletePulseTask(Context context) {
        this.pulseDataBaseAdapter = new PulseDataBaseAdapter(context);
    }

    @Override
    protected Long doInBackground(Pulse... params) {
        long result = 0;

        result = pulseDataBaseAdapter.delete(params[0]);

        return result;
    }

    @Override
    protected void onPostExecute(Long result) {
        if (result != -1) {
            //Toast.makeText(context, "Record deleted", Toast.LENGTH_LONG).show();
            Log.i(TAG, "Record deleted");
        }

        if (pulseDataBaseAdapter != null) {
            pulseDataBaseAdapter.close();
        }

    }

}
