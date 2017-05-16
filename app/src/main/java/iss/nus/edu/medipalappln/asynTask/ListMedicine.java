package iss.nus.edu.medipalappln.asynTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import iss.nus.edu.medipalappln.dao.MedicineDAO;
import iss.nus.edu.medipalappln.medipal.Medicine;

/**
 * Created by Darryl on 29/12/2016.
 */

public class ListMedicine extends AsyncTask<Void, Void, ArrayList<Medicine>> {
    //private final WeakReference<Activity> activityWeakRef;

    ArrayList<Medicine> medicines;
    private MedicineDAO medicineDAO;

    public ListMedicine(Context context) {
        //this.activityWeakRef = new WeakReference<Activity>(context);
        this.medicineDAO = new MedicineDAO(context);
    }

    @Override
    protected ArrayList<Medicine> doInBackground(Void... arg0) {
        ArrayList<Medicine> medicineList = medicineDAO.getMembers();
        return medicineList;
    }

    @Override
    protected void onPostExecute(ArrayList<Medicine> memList) {
        //if (activityWeakRef.get() != null && !activityWeakRef.get().isFinishing()) {
            Log.d("medicines", memList.toString());
            medicines = memList;

        if (memList == null) {
            medicines = new ArrayList<Medicine>();
        }

        /*
            if (memList != null) {
                if (memList.size() != 0) {
                    // do something if required

                    //memberListAdapter = new ReminderListAdapter(getApplicationContext(), remList);
                    //reminderListView.setAdapter(reminderListAdapter);
                } else {
                    Toast.makeText(context, "No medicine", Toast.LENGTH_LONG).show();
                }
            }
            */
        //}
    }

    public ArrayList<Medicine> getMemberList() {
        return this.medicines;
    }
}
