package iss.nus.edu.medipalappln.asynTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import iss.nus.edu.medipalappln.dao.ReminderDAO;
import iss.nus.edu.medipalappln.medipal.Reminder;

/**
 * Created by Darryl on 29/12/2016.
 */

public class AddReminder extends AsyncTask<Reminder, Void, Long> {
    //private final WeakReference<Activity> activityWeakRef;

    Reminder reminder = null;
    private ReminderDAO reminderDAO;

    public AddReminder(Context context) {
        //this.activityWeakRef = new WeakReference<Activity>(context.getApplicationContext());
        this.reminderDAO = new ReminderDAO(context);
    }

    @Override
    protected Long doInBackground(Reminder... params) {
        long result = reminderDAO.save(params[0]);
        Log.d("Niv", "Saving medicine in background");
        return result;
    }

    @Override
    protected void onPostExecute(Long result) {
        //if (activityWeakRef.get() != null && !activityWeakRef.get().isFinishing()) {
            if (result != -1)
                //Toast.makeText(context, "Medicine Saved", Toast.LENGTH_LONG).show();

            if (reminderDAO != null)
                reminderDAO.close();
        //}
    }
}
