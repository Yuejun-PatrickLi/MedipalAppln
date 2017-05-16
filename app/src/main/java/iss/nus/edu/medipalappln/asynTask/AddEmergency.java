package iss.nus.edu.medipalappln.asynTask;

import android.content.Context;
import android.os.AsyncTask;

import iss.nus.edu.medipalappln.dao.EmergencyDataBaseAdapter;
import iss.nus.edu.medipalappln.medipal.Emergency;

/**
 * Created by root on 21/3/17.
 */

public class AddEmergency extends AsyncTask<Emergency, Void, Long> {

    private static final String TAG = "AddEmergency";
    Emergency emergency=null;
    private EmergencyDataBaseAdapter emergencyDataBaseAdapter;
    String result;

    public AddEmergency(Context context) {
        this.emergencyDataBaseAdapter = new EmergencyDataBaseAdapter(context);
    }


    @Override
    protected Long doInBackground(Emergency... params) {
        long result=emergencyDataBaseAdapter.addValues(params[0]);
        return result;
    }

    @Override
    protected void onPostExecute(Long result) {
        if(emergencyDataBaseAdapter!=null){
            emergencyDataBaseAdapter.close();
        }
    }

}
