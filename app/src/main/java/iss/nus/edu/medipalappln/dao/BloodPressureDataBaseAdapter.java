package iss.nus.edu.medipalappln.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import iss.nus.edu.medipalappln.medipal.BloodPressure;

public class BloodPressureDataBaseAdapter extends DBDAO {

    private static final String TAG = "DBDAO";

    //begin SQL statements
    public static final String SELECT_ALL = "SELECT * FROM " + DataBaseHelper.TABLE_MEASUREMENT +
            " WHERE 1";
    //end SQL statements

    public BloodPressureDataBaseAdapter(Context context) {
        super(context);
    }

    public long add(BloodPressure bloodPressure) {
        ContentValues values = new ContentValues();
        //String date = new SimpleDateFormat("yyyy-MM-dd").format(bloodPressure.getMeasuredOn());
        String date = bloodPressure.getMeasuredOn().toString();

        values.put(DataBaseHelper.MEASUREMENT.Systolic.toString(), bloodPressure.getSystolic());
        values.put(DataBaseHelper.MEASUREMENT.Diastolic.toString(), bloodPressure.getDiastolic());
        values.put(DataBaseHelper.MEASUREMENT.MeasuredOn.toString(), date);

        return database.insert(DataBaseHelper.TABLE_MEASUREMENT, null, values);
    }

    public long update(BloodPressure bloodPressure, String key) {
        ContentValues values = new ContentValues();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(bloodPressure.getMeasuredOn());
        String[] args = new String[] {key};

        values.put(DataBaseHelper.MEASUREMENT.Systolic.toString(), bloodPressure.getSystolic());
        values.put(DataBaseHelper.MEASUREMENT.Diastolic.toString(), bloodPressure.getDiastolic());
        values.put(DataBaseHelper.MEASUREMENT.MeasuredOn.toString(), date);

        return database.update(DataBaseHelper.TABLE_MEASUREMENT, values, "ID = ?", args);
    }

    public long delete(BloodPressure bloodPressure) {
        String[] args = new String[] {bloodPressure.getID() + " "};

        Log.i(TAG, "key >> " + args + " >> " + bloodPressure.getID());
        return database.delete(DataBaseHelper.TABLE_MEASUREMENT, DataBaseHelper.MEASUREMENT.ID + "= ?", args);
    }

    public ArrayList<BloodPressure> get() {
        ArrayList<BloodPressure> bloodPressures = new ArrayList<BloodPressure>();
        String query[] = { DataBaseHelper.MEASUREMENT.ID.toString(),
                        DataBaseHelper.MEASUREMENT.Systolic.toString(),
                        DataBaseHelper.MEASUREMENT.Diastolic.toString(),
                        DataBaseHelper.MEASUREMENT.MeasuredOn.toString() };
        String where = DataBaseHelper.MEASUREMENT.Systolic + " > 0 or " +
                        DataBaseHelper.MEASUREMENT.Diastolic + " > 0 " +
                        " order by " + DataBaseHelper.MEASUREMENT.MeasuredOn;
        int id;

        Log.i(TAG, "where " + where);
        Cursor cursor = database.query(DataBaseHelper.TABLE_MEASUREMENT, query,
                where, null, null, null, null);

        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
            BloodPressure bp = new BloodPressure(
                    id,
                    cursor.getInt(cursor.getColumnIndex(DataBaseHelper.MEASUREMENT.Systolic.toString())),
                    cursor.getInt(cursor.getColumnIndex(DataBaseHelper.MEASUREMENT.Diastolic.toString())),
                    cursor.getString(cursor.getColumnIndex(DataBaseHelper.MEASUREMENT.MeasuredOn.toString())));
            bloodPressures.add(bp);
            Log.i(TAG, id + ": " + bp.getSystolic() + "/" + bp.getDiastolic() + "/" + bp.getMeasuredOn());
        }

        return bloodPressures;
    }

    public String databaseToString() {
        String dbString = "";

        Cursor cursor = database.rawQuery(SELECT_ALL, null);
        cursor.moveToFirst();

        Log.i(TAG, "Record(s) count " + cursor.getCount());

        for( int i = 0; i < cursor.getCount(); i++) {
            if(cursor.getString(cursor.getColumnIndex(DataBaseHelper.MEASUREMENT.ID.toString())) != null) {
                dbString += cursor.getString(cursor.getColumnIndex(DataBaseHelper.MEASUREMENT.Systolic.toString()));
                dbString += "\t";
                dbString += cursor.getString(cursor.getColumnIndex(DataBaseHelper.MEASUREMENT.Diastolic.toString()));
                dbString += "\t";
                dbString += cursor.getString(cursor.getColumnIndex(DataBaseHelper.MEASUREMENT.MeasuredOn.toString()));
                dbString += "\n";
                Log.i(TAG, "Record: " + dbString);
            }
            cursor.moveToNext();
        }

        return dbString;
    }
}
