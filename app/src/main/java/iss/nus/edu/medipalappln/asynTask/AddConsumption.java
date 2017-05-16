package iss.nus.edu.medipalappln.asynTask;

import android.content.Context;
import android.os.AsyncTask;

import iss.nus.edu.medipalappln.dao.ConsumptionDAO;
import iss.nus.edu.medipalappln.medipal.Consumption;

/**
 * Created by Darryl on 29/12/2016.
 */

public class AddConsumption extends AsyncTask<Consumption, Void, Long> {
    Consumption consumption = null;
    private ConsumptionDAO consumptionDAO;

    public AddConsumption(Context context) {
        this.consumptionDAO = new ConsumptionDAO(context);
    }

    @Override
    protected Long doInBackground(Consumption... params) {
        long result = consumptionDAO.save(params[0]);
        return result;
    }

    @Override
    protected void onPostExecute(Long result) {
        if (result != -1)

            if (consumptionDAO != null)
                consumptionDAO.close();
    }
}
