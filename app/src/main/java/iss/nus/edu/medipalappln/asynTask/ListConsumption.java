package iss.nus.edu.medipalappln.asynTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import iss.nus.edu.medipalappln.dao.ConsumptionDAO;
import iss.nus.edu.medipalappln.medipal.Consumption;

/**
 * Created by Darryl on 29/12/2016.
 */

public class ListConsumption extends AsyncTask<Void, Void, ArrayList<Consumption>> {
    ArrayList<Consumption> consumptions;
    private ConsumptionDAO consumptionDAO;

    public ListConsumption(Context context) {
        this.consumptionDAO = new ConsumptionDAO(context);
    }

    @Override
    protected ArrayList<Consumption> doInBackground(Void... arg0) {
        ArrayList<Consumption> bookingList = consumptionDAO.getConsumptions();
        return bookingList;
    }

    @Override
    protected void onPostExecute(ArrayList<Consumption> conList) {
        Log.d("facilities", conList.toString());
        consumptions = conList;

        if (conList == null) {
            consumptions = new ArrayList<Consumption>();
        }
    }
}
