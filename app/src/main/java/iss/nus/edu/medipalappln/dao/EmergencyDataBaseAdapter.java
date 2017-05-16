package iss.nus.edu.medipalappln.dao;
/**
 * Created by Raghu on 7/3/17.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import iss.nus.edu.medipalappln.medipal.Emergency;

public class EmergencyDataBaseAdapter extends DBDAO {
    private static final String TAG = "DBDAO";
    //begin SQL statements
    public static final String SELECT_ALL = "SELECT * FROM " + DataBaseHelper.TABLE_ICE +
            " WHERE 1";
    public EmergencyDataBaseAdapter(Context context) {
        super(context);
    }
    public long addValues(Emergency phone) {
        ContentValues values = new ContentValues();
        //values.put(DataBaseHelper.ICE.ID.toString(),phone.getID());
        values.put(DataBaseHelper.ICE.Name.toString(),phone.getName());
        values.put(DataBaseHelper.ICE.ContactNo.toString(),phone.getPhone());
        values.put(DataBaseHelper.ICE.ContactType.toString(), phone.getDesc());
        values.put(DataBaseHelper.ICE.Sequence.toString(),phone.getPriority());


        return database.insert(DataBaseHelper.TABLE_ICE, null, values);
    }

    public long updateValues(Emergency phone,String key) {
        ContentValues values = new ContentValues();
        String[] args = new String[] {key};

        values.put(DataBaseHelper.ICE.ID.toString(),phone.getID());
        values.put(DataBaseHelper.ICE.Name.toString(),phone.getName());
        values.put(DataBaseHelper.ICE.Sequence.toString(),phone.getPriority());
        values.put(DataBaseHelper.ICE.ContactNo.toString(),phone.getPhone());
        values.put(DataBaseHelper.ICE.ContactType.toString(), phone.getDesc());

        return database.update(DataBaseHelper.TABLE_ICE, values, "ID = ?", args);
    }

    public long deleteValues(String key) {
        String[] args = new String[] {key};

        return database.delete(DataBaseHelper.TABLE_ICE, "ID = ?", args);
    }
    public Emergency getEmergency(int id) {
        Emergency emergency = null;

        String sql = "SELECT * FROM " + DataBaseHelper.TABLE_ICE
                + " WHERE " + id + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { id + "" });

        if (cursor.moveToNext()) {
            int eid = cursor.getInt(0);
            String priority = cursor.getString(3);
            String name = cursor.getString(1);
            String phone = cursor.getString(2);
            String desc=cursor.getString(4);

            emergency = new Emergency(name, phone, priority,desc);
        }
        return emergency;
    }


    public long delete(Emergency emergency) {
        String[] args = new String[] {emergency.getID() + " "};

        Log.i(TAG, "key >> " + args + " >> " + emergency.getID());
        return database.delete(DataBaseHelper.TABLE_ICE, DataBaseHelper.ICE.ID + "= ?", args);
    }

    public ArrayList<Emergency> get() {
        ArrayList<Emergency> emergencies = new ArrayList<Emergency>();
        Cursor cursor1=null;
        int id;
        // cursor1.moveToFirst();
        String sql = "SELECT * FROM " + DataBaseHelper.TABLE_ICE;
        cursor1 = database.rawQuery(sql,null);
        while(cursor1.moveToNext()) {
            id = cursor1.getInt(0);
            String name = cursor1.getString(1);
            String phone = cursor1.getString(2);
            String relation = cursor1.getString(3);
            //String priority=cursor1.getString(4);
            Emergency bp = new Emergency(name,phone,relation,"First to be called");
            emergencies.add(bp);

        }
        cursor1.close();
        return emergencies;
    }



    public String getSingleEntry() {
        String dbString = "";

        Cursor cursor = database.rawQuery(SELECT_ALL, null);
        cursor.moveToFirst();

        Log.i(TAG, "Record(s) count " + cursor.getCount());

        for( int i = 0; i < cursor.getCount(); i++) {
            if(cursor.getString(cursor.getColumnIndex(DataBaseHelper.ICE.ID.toString())) != null) {

                dbString += cursor.getString(cursor.getColumnIndex(DataBaseHelper.ICE.Name.toString()));
                dbString += "\t";
                dbString += cursor.getString(cursor.getColumnIndex(DataBaseHelper.ICE.ContactNo.toString()));
                dbString += "\t";
                dbString += cursor.getString(cursor.getColumnIndex(DataBaseHelper.ICE.ContactType.toString()));
                dbString += "\t";
                dbString += cursor.getString(cursor.getColumnIndex(DataBaseHelper.ICE.Sequence.toString()));
                dbString += "\t";

                Log.i(TAG, "Record: " + dbString);
            }
            cursor.moveToNext();
        }

        return dbString;
    }

}