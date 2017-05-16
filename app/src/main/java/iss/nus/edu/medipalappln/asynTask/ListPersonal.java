package iss.nus.edu.medipalappln.asynTask;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import iss.nus.edu.medipalappln.dao.BioDataBaseAdapter;
import iss.nus.edu.medipalappln.medipal.Personal;

/**
 * Created by root on 22/3/17.
 */

public class ListPersonal extends AsyncTask <Void, Void, ArrayList<Personal>>  {
    ArrayList<Personal> personals;
    private BioDataBaseAdapter bioDataBaseAdapter;

    public ListPersonal(Context context){
        bioDataBaseAdapter=new BioDataBaseAdapter(context);

    }

    @Override
    protected ArrayList<Personal> doInBackground(Void... params) {
        ArrayList<Personal> list=bioDataBaseAdapter.getFormDatas();
        return list;

    }
}
