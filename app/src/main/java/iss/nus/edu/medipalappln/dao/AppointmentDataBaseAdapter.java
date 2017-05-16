package iss.nus.edu.medipalappln.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import iss.nus.edu.medipalappln.adapter.AppointmentAdapter;
import iss.nus.edu.medipalappln.fragment.AppointmentFragment;
import iss.nus.edu.medipalappln.medipal.Appointment;


/**
 * Created by cherry on 2017/3/22.
 */

public class AppointmentDataBaseAdapter extends DBDAO {

    private static final String TAG = "DBDAO";

    public AppointmentDataBaseAdapter(Context context) {
        super(context);
    }

    public long addAppointment(Appointment appointment) {
        ContentValues values = new ContentValues();

        values.put(DataBaseHelper.APPOINTMENT.Location.toString(), appointment.getLocation());
        values.put(DataBaseHelper.APPOINTMENT.Description.toString(), appointment.getDescription());
        values.put(DataBaseHelper.APPOINTMENT.Appointment.toString(), appointment.getDate());

        return database.insert(DataBaseHelper.TABLE_APPOINTMENT, null, values);
    }

    public long updateAppointment(Appointment appointment, int id) {
        ContentValues values = new ContentValues();
       // String date = new SimpleDateFormat(Appointment.datepattern).format(appointment.getDate());
        String[] args = new String[] {Integer.toString(id)};

        values.put(DataBaseHelper.APPOINTMENT.Location.toString(), appointment.getLocation());
        values.put(DataBaseHelper.APPOINTMENT.Description.toString(), appointment.getDescription());
        values.put(DataBaseHelper.APPOINTMENT.Appointment.toString(), appointment.getDate());

        return database.update(DataBaseHelper.TABLE_APPOINTMENT, values, "ID = ?", args);
    }

    public long deleteAppointment(int id) {
        String ID = Integer.toString(id);
        String[] args = new String[]{ID};

        return database.delete(DataBaseHelper.TABLE_APPOINTMENT, "ID = ?", args);
    }

    public long queryAll(){
        Cursor cursor = database.query(DataBaseHelper.TABLE_APPOINTMENT, new String[]{"ID","Location","Appointment","Description"}, null, null, null, null, null);
        if(cursor.getCount() < 1){
            Log.d("Records Count","No Record");
            return 0;
        }
        if(AppointmentFragment.Flag == 0){
            AppointmentFragment.Flag = 1;
        }else{
            AppointmentAdapter.mId.clear();
            AppointmentAdapter.mLocation.clear();
            AppointmentAdapter.mDescription.clear();
            AppointmentAdapter.mTime.clear();
        }
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.APPOINTMENT.ID.toString()));
            String location = cursor.getString(cursor.getColumnIndex(DataBaseHelper.APPOINTMENT.Location.toString()));
            String description = cursor.getString(cursor.getColumnIndex(DataBaseHelper.APPOINTMENT.Description.toString()));
            String time = cursor.getString(cursor.getColumnIndex(DataBaseHelper.APPOINTMENT.Appointment.toString()));

            AppointmentAdapter.mId.add(id);
            AppointmentAdapter.mLocation.add(location);
            AppointmentAdapter.mDescription.add(description);
            AppointmentAdapter.mTime.add(time);

            Log.d("QueryAll",cursor.getInt(cursor.getColumnIndex(DataBaseHelper.APPOINTMENT.ID.toString())) +
                    cursor.getString(cursor.getColumnIndex(DataBaseHelper.APPOINTMENT.Location.toString())) +
                    cursor.getString(cursor.getColumnIndex(DataBaseHelper.APPOINTMENT.Description.toString())) +
                    cursor.getString(cursor.getColumnIndex(DataBaseHelper.APPOINTMENT.Appointment.toString())));
        }
        return 0;
    }
}
