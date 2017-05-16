package iss.nus.edu.medipalappln.asynTask;

import android.content.Context;
import android.os.AsyncTask;

import iss.nus.edu.medipalappln.dao.ReminderDAO;

/**
 * Created by rama on 3/23/2017.
 */

public class GetMaxReminderId extends AsyncTask<Void, Void, Integer> {

    int maxRemindId;
    private ReminderDAO reminderDAO;

    public GetMaxReminderId(Context context) {
        this.reminderDAO = new ReminderDAO(context);
    }

    @Override
    protected Integer doInBackground(Void... params) {
        int maxRemindId = reminderDAO.getMaxReminderId();
        return maxRemindId;
    }
}
