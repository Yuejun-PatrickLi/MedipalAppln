package iss.nus.edu.medipalappln.asynTask;

import android.content.Context;
import android.os.AsyncTask;

import iss.nus.edu.medipalappln.dao.MedicineDAO;
import iss.nus.edu.medipalappln.medipal.Medicine;

/**
 * Created by Niv on 29/12/2016.
 */

public class DeleteMedicine extends AsyncTask<Medicine, Void, Integer> {
    Medicine medicine = null;
    private MedicineDAO medicineDAO;

    public DeleteMedicine(Context context) {
        this.medicineDAO = new MedicineDAO(context);
    }

    @Override
    protected Integer doInBackground(Medicine... params) {
        int result = medicineDAO.delete(params[0]);
        return result;
    }

    @Override
    protected void onPostExecute(Integer result) {
        if (result != -1)

            if (medicineDAO != null)
                medicineDAO.close();
    }
}
