package iss.nus.edu.medipalappln.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import iss.nus.edu.medipalappln.medipal.Weight;

public class WeightDataBaseAdapter extends DBDAO {

    private static final String TAG = "DBDAO";

    //begin SQL statements
    public static final String SELECT_ALL = "SELECT * FROM " + DataBaseHelper.TABLE_MEASUREMENT +
            " WHERE 1";
    //end SQL statements

    public WeightDataBaseAdapter(Context context) {
        super(context);
    }

    public long add(Weight weight) {
        ContentValues values = new ContentValues();
        //String date = new SimpleDateFormat("yyyy-MM-dd").format(Weight.getMeasuredOn());
        String date = weight.getMeasuredOn();

        values.put(DataBaseHelper.MEASUREMENT.Weight.toString(), weight.getWeight());
        values.put(DataBaseHelper.MEASUREMENT.MeasuredOn.toString(), date);

        return database.insert(DataBaseHelper.TABLE_MEASUREMENT, null, values);
    }

    public long update(Weight Weight, String key) {
        ContentValues values = new ContentValues();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(Weight.getMeasuredOn());
        String[] args = new String[] {key};

        values.put(DataBaseHelper.MEASUREMENT.Weight.toString(), Weight.getWeight());
        values.put(DataBaseHelper.MEASUREMENT.MeasuredOn.toString(), date);

        return database.update(DataBaseHelper.TABLE_MEASUREMENT, values, "ID = ?", args);
    }

    public long delete(Weight Weight) {
        String[] args = new String[] {Weight.getID() + " "};

        Log.i(TAG, "key >> " + args + " >> " + Weight.getID());
        return database.delete(DataBaseHelper.TABLE_MEASUREMENT, DataBaseHelper.MEASUREMENT.ID + "= ?", args);
    }

    public ArrayList<Weight> get() {
        ArrayList<Weight> Weights = new ArrayList<Weight>();
        String query[] = { DataBaseHelper.MEASUREMENT.ID.toString(),
                        DataBaseHelper.MEASUREMENT.Weight.toString(),
                        DataBaseHelper.MEASUREMENT.MeasuredOn.toString() };
        String where = DataBaseHelper.MEASUREMENT.Weight + " > 0 ";
        int id;

        Log.i(TAG, "where " + where);
        Cursor cursor = database.query(DataBaseHelper.TABLE_MEASUREMENT, query,
                where, null, null, null, null);

        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
            Weight weight = new Weight(
                    id,
                    cursor.getInt(cursor.getColumnIndex(DataBaseHelper.MEASUREMENT.Weight.toString())),
                    cursor.getString(cursor.getColumnIndex(DataBaseHelper.MEASUREMENT.MeasuredOn.toString())));
            Weights.add(weight);
            Log.i(TAG, id + ": " + weight.getWeight() + "/" + weight.getMeasuredOn());
        }

        cursor.close();
        return Weights;
    }
}
