package iss.nus.edu.medipalappln.asynTask;

import android.content.Context;
import android.os.AsyncTask;

import iss.nus.edu.medipalappln.dao.BioDataBaseAdapter;
import iss.nus.edu.medipalappln.medipal.Personal;

/**
 * Created by root on 23/3/17.
 */

public class AddPersonal extends AsyncTask<Personal,Void,Long> {

    private static final String TAG = "AddMeasurement";
    Personal formData=null;
    private BioDataBaseAdapter bioDataBaseAdapter;
    String result;

    public AddPersonal(Context context) {
        this.bioDataBaseAdapter = new BioDataBaseAdapter(context);
    }


    @Override
    protected Long doInBackground(Personal... params) {
        long result=bioDataBaseAdapter.addValues(params[0]);
        return result;
    }

    @Override
    protected void onPostExecute(Long result) {
        if(bioDataBaseAdapter!=null){
            bioDataBaseAdapter.close();
        }
    }

   }

