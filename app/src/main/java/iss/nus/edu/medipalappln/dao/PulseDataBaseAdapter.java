package iss.nus.edu.medipalappln.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import iss.nus.edu.medipalappln.medipal.Pulse;

public class PulseDataBaseAdapter extends DBDAO {

    private static final String TAG = "DBDAO";

    //begin SQL statements
    public static final String SELECT_ALL = "SELECT * FROM " + DataBaseHelper.TABLE_MEASUREMENT +
            " WHERE 1";
    //end SQL statements

    public PulseDataBaseAdapter(Context context) {
        super(context);
    }

    public long add(Pulse pulse) {
        ContentValues values = new ContentValues();
        //String date = new SimpleDateFormat("yyyy-MM-dd").format(Pulse.getMeasuredOn());
        String date = pulse.getMeasuredOn();

        values.put(DataBaseHelper.MEASUREMENT.Pulse.toString(), pulse.getPulse());
        values.put(DataBaseHelper.MEASUREMENT.MeasuredOn.toString(), date);

        return database.insert(DataBaseHelper.TABLE_MEASUREMENT, null, values);
    }

    public long update(Pulse Pulse, String key) {
        ContentValues values = new ContentValues();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(Pulse.getMeasuredOn());
        String[] args = new String[] {key};

        values.put(DataBaseHelper.MEASUREMENT.Pulse.toString(), Pulse.getPulse());
        values.put(DataBaseHelper.MEASUREMENT.MeasuredOn.toString(), date);

        return database.update(DataBaseHelper.TABLE_MEASUREMENT, values, "ID = ?", args);
    }

    public long delete(Pulse Pulse) {
        String[] args = new String[] {Pulse.getID() + " "};

        Log.i(TAG, "key >> " + args + " >> " + Pulse.getID());
        return database.delete(DataBaseHelper.TABLE_MEASUREMENT, DataBaseHelper.MEASUREMENT.ID + "= ?", args);
    }

    public ArrayList<Pulse> get() {
        ArrayList<Pulse> Pulses = new ArrayList<Pulse>();
        String query[] = { DataBaseHelper.MEASUREMENT.ID.toString(),
                        DataBaseHelper.MEASUREMENT.Pulse.toString(),
                        DataBaseHelper.MEASUREMENT.MeasuredOn.toString() };
        String where = DataBaseHelper.MEASUREMENT.Pulse + " > 0 ";
        int id;

        Log.i(TAG, "where " + where);
        Cursor cursor = database.query(DataBaseHelper.TABLE_MEASUREMENT, query,
                where, null, null, null, null);

        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
            Pulse pulse = new Pulse(
                    id,
                    cursor.getInt(cursor.getColumnIndex(DataBaseHelper.MEASUREMENT.Pulse.toString())),
                    cursor.getString(cursor.getColumnIndex(DataBaseHelper.MEASUREMENT.MeasuredOn.toString())));
            Pulses.add(pulse);
            Log.i(TAG, id + ": " + pulse.getPulse() + "/" + pulse.getMeasuredOn());
        }

        cursor.close();

        return Pulses;
    }
}
