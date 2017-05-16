package iss.nus.edu.medipalappln.asynTask;

import android.content.Context;
import android.os.AsyncTask;

import iss.nus.edu.medipalappln.dao.MedicineDAO;
import iss.nus.edu.medipalappln.medipal.Medicine;

/**
 * Created by rama on 3/21/2017.
 */

public class UpdateMedicine extends AsyncTask<Medicine, Void, Long> {

    Medicine medicine = null;
    private MedicineDAO medicineDAO;

    public UpdateMedicine(Context context) {
        this.medicineDAO = new MedicineDAO(context);
    }

    @Override
    protected Long doInBackground(Medicine... params) {
        long result = medicineDAO.update(params[0]);
        return result;
    }

    @Override
    protected void onPostExecute(Long result) {
        if (result != -1)

            if (medicineDAO != null)
                medicineDAO.close();
    }



}
