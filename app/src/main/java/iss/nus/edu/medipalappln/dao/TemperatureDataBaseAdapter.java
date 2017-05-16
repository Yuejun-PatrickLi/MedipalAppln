package iss.nus.edu.medipalappln.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import iss.nus.edu.medipalappln.medipal.Temperature;

public class TemperatureDataBaseAdapter extends DBDAO {

    private static final String TAG = "DBDAO";

    //begin SQL statements
    public static final String SELECT_ALL = "SELECT * FROM " + DataBaseHelper.TABLE_MEASUREMENT +
            " WHERE 1";
    //end SQL statements

    public TemperatureDataBaseAdapter(Context context) {
        super(context);
    }

    public long add(Temperature temperature) {
        ContentValues values = new ContentValues();
        //String date = new SimpleDateFormat("yyyy-MM-dd").format(Temperature.getMeasuredOn());
        String date = temperature.getMeasuredOn().toString();

        values.put(DataBaseHelper.MEASUREMENT.Temperature.toString(), temperature.getTemperature());
        values.put(DataBaseHelper.MEASUREMENT.MeasuredOn.toString(), date);

        return database.insert(DataBaseHelper.TABLE_MEASUREMENT, null, values);
    }

    public long update(Temperature Temperature, String key) {
        ContentValues values = new ContentValues();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(Temperature.getMeasuredOn());
        String[] args = new String[] {key};

        values.put(DataBaseHelper.MEASUREMENT.Temperature.toString(), Temperature.getTemperature());
        values.put(DataBaseHelper.MEASUREMENT.MeasuredOn.toString(), date);

        return database.update(DataBaseHelper.TABLE_MEASUREMENT, values, "ID = ?", args);
    }

    public long delete(Temperature Temperature) {
        String[] args = new String[] {Temperature.getID() + " "};

        Log.i(TAG, "key >> " + args + " >> " + Temperature.getID());
        return database.delete(DataBaseHelper.TABLE_MEASUREMENT, DataBaseHelper.MEASUREMENT.ID + "= ?", args);
    }

    public ArrayList<Temperature> get() {
        ArrayList<Temperature> Temperatures = new ArrayList<Temperature>();
        String query[] = { DataBaseHelper.MEASUREMENT.ID.toString(),
                        DataBaseHelper.MEASUREMENT.Temperature.toString(),
                        DataBaseHelper.MEASUREMENT.MeasuredOn.toString() };
        String where = DataBaseHelper.MEASUREMENT.Temperature + " > 0 ";
        int id;

        Log.i(TAG, "where " + where);
        Cursor cursor = database.query(DataBaseHelper.TABLE_MEASUREMENT, query,
                where, null, null, null, null);

        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
            Temperature temperature = new Temperature(
                    id,
                    cursor.getDouble(cursor.getColumnIndex(DataBaseHelper.MEASUREMENT.Temperature.toString())),
                    cursor.getString(cursor.getColumnIndex(DataBaseHelper.MEASUREMENT.MeasuredOn.toString())));
            Temperatures.add(temperature);
            Log.i(TAG, id + ": " + temperature.getTemperature() + "/" + temperature.getMeasuredOn());
        }

        cursor.close();
        return Temperatures;
    }
}
