package iss.nus.edu.medipalappln.asynTask;

import android.content.Context;
import android.os.AsyncTask;

import iss.nus.edu.medipalappln.dao.MedicineDAO;
import iss.nus.edu.medipalappln.medipal.Medicine;

/**
 * Created by Niv on 29/12/2016.
 */

public class GetMedicine extends AsyncTask<Integer, Void, Medicine> {
    //private final WeakReference<Activity> activityWeakRef;

    Medicine medicine;
    private MedicineDAO medicineDAO;

    public GetMedicine(Context context) {
        //this.activityWeakRef = new WeakReference<Activity>(context);
        this.medicineDAO = new MedicineDAO(context);
    }

    @Override
    protected Medicine doInBackground(Integer... params) {
        medicine = medicineDAO.getMember(params[0]);
        return medicine;
    }

    @Override
    protected void onPostExecute(Medicine m) {
        //if (activityWeakRef.get() != null && !activityWeakRef.get().isFinishing()) {
//            Log.d("category", f.toString());
            medicine = m;

        if (m != null)

            if (medicineDAO != null)
                medicineDAO.close();
    }

    public Medicine getMember() {
        return this.medicine;
    }
}
