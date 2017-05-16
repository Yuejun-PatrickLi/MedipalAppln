package iss.nus.edu.medipalappln.asynTask;

import android.content.Context;
import android.os.AsyncTask;

import iss.nus.edu.medipalappln.dao.ReminderDAO;
import iss.nus.edu.medipalappln.medipal.Reminder;

/**
 * Created by Darryl on 29/12/2016.
 */

public class GetReminder extends AsyncTask<Integer, Void, Reminder> {
    //private final WeakReference<Activity> activityWeakRef;

    Reminder reminder;
    private ReminderDAO reminderDAO;

    public GetReminder(Context context) {
        //this.activityWeakRef = new WeakReference<Activity>(context);
        this.reminderDAO = new ReminderDAO(context);
    }

    @Override
    protected Reminder doInBackground(Integer... params) {
        Reminder reminder = reminderDAO.getReminder(params[0]);
        return reminder;
    }

    @Override
    protected void onPostExecute(Reminder r) {
        //if (activityWeakRef.get() != null && !activityWeakRef.get().isFinishing()) {
           // Log.d("medicines", r.toString());
            reminder = r;

        if (r == null) {
            reminder = new Reminder();
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

    public Reminder getReminder() {
        return this.reminder;
    }
}
