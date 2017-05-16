package iss.nus.edu.medipalappln.asynTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import iss.nus.edu.medipalappln.dao.MedicineDAO;
import iss.nus.edu.medipalappln.medipal.Medicine;

/**
 * Created by Darryl on 29/12/2016.
 */

public class AddMedicine extends AsyncTask<Medicine, Void, Long> {
    //private final WeakReference<Activity> activityWeakRef;

    Medicine medicine = null;
    private MedicineDAO medicineDAO;

    public AddMedicine(Context context) {
        //this.activityWeakRef = new WeakReference<Activity>(context.getApplicationContext());
        this.medicineDAO = new MedicineDAO(context);
    }

    @Override
    protected Long doInBackground(Medicine... params) {
        long result = medicineDAO.save(params[0]);
        Log.d("Niv", "Saving medicine in background");
        return result;
    }

    @Override
    protected void onPostExecute(Long result) {
        //if (activityWeakRef.get() != null && !activityWeakRef.get().isFinishing()) {
            if (result != -1)
                //Toast.makeText(context, "Medicine Saved", Toast.LENGTH_LONG).show();

            if (medicineDAO != null)
                medicineDAO.close();
        //}
    }
}
