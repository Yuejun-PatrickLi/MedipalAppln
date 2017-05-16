
package iss.nus.edu.medipalappln.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import iss.nus.edu.medipalappln.medipal.PersonalBio;

public class PerBioDataBaseAdapter extends DBDAO {

    private static final String TAG = "DBDAO";

    //begin SQL statements
    public static final String SELECT_ALL = "SELECT * FROM " + DataBaseHelper.TABLE_PERSONALBIO +
            " WHERE 1";
    //end SQL statements

    public PerBioDataBaseAdapter(Context context) {
        super(context);
    }

    public long add (PersonalBio formData){
        ContentValues values = new ContentValues();

        values.put(DataBaseHelper.PERSONALBIO.ID.toString(), formData.getID());
        values.put(DataBaseHelper.PERSONALBIO.Name.toString(), formData.getName());
        values.put(DataBaseHelper.PERSONALBIO.DOB.toString(), formData.getDOB());
        values.put(DataBaseHelper.PERSONALBIO.IDNo.toString(), formData.getID());
        values.put(DataBaseHelper.PERSONALBIO.Address.toString(), formData.getAddress());
        values.put(DataBaseHelper.PERSONALBIO.PostalCode.toString(), formData.getPostalCode());
        values.put(DataBaseHelper.PERSONALBIO.Height.toString(), formData.getHeight());
        values.put(DataBaseHelper.PERSONALBIO.BloodType.toString(), formData.getBloodType());

        return database.insert(DataBaseHelper.TABLE_PERSONALBIO, null, values);
    }

    public long update(PersonalBio formData) {
        ContentValues values = new ContentValues();

        values.put(DataBaseHelper.PERSONALBIO.ID.toString(),formData.getID());
        values.put(DataBaseHelper.PERSONALBIO.Name.toString(),formData.getName());
        values.put(DataBaseHelper.PERSONALBIO.DOB.toString(),formData.getDOB());
        values.put(DataBaseHelper.PERSONALBIO.IDNo.toString(),formData.getID());
        values.put(DataBaseHelper.PERSONALBIO.Address.toString(), formData.getAddress());
        values.put(DataBaseHelper.PERSONALBIO.PostalCode.toString(), formData.getPostalCode());
        values.put(DataBaseHelper.PERSONALBIO.Height.toString(), formData.getHeight());
        values.put(DataBaseHelper.PERSONALBIO.BloodType.toString(), formData.getBloodType());

        return database.update(DataBaseHelper.TABLE_PERSONALBIO, values, null, null);
    }

    public long delete(String key) {
        String[] args = new String[] {key};

        return database.delete(DataBaseHelper.TABLE_PERSONALBIO, "ID = ?", args);
    }

    public PersonalBio getSingleEntry() {
        PersonalBio personalBio; //currently support single user only

        Cursor cursor = database.rawQuery(SELECT_ALL, null);
        cursor.moveToFirst();

        Log.i(TAG, "Record(s) count " + cursor.getCount());

        if (cursor.getCount() <= 0)
        {
            personalBio = null;
        }
        else
        {
            personalBio = new PersonalBio(
                    cursor.getString(cursor.getColumnIndex(DataBaseHelper.PERSONALBIO.ID.toString())),
                    cursor.getString(cursor.getColumnIndex(DataBaseHelper.PERSONALBIO.Name.toString())),
                    cursor.getString(cursor.getColumnIndex(DataBaseHelper.PERSONALBIO.DOB.toString())),
                    cursor.getString(cursor.getColumnIndex(DataBaseHelper.PERSONALBIO.IDNo.toString())),
                    cursor.getString(cursor.getColumnIndex(DataBaseHelper.PERSONALBIO.Address.toString())),
                    cursor.getString(cursor.getColumnIndex(DataBaseHelper.PERSONALBIO.PostalCode.toString())),
                    cursor.getInt(cursor.getColumnIndex(DataBaseHelper.PERSONALBIO.Height.toString())),
                    cursor.getString(cursor.getColumnIndex(DataBaseHelper.PERSONALBIO.BloodType.toString())));

        }

        return personalBio;
    }
}
