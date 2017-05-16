package iss.nus.edu.medipalappln.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import iss.nus.edu.medipalappln.dao.HealthBioContract.HealthBioEntry;


public class HealthBioDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "healthbio.db";
    private static final int DATABASE_VERSION = 1;

    public HealthBioDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_HEALTH_TABLE =  "CREATE TABLE " + HealthBioEntry.TABLE_NAME + " ("
                + HealthBioEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HealthBioEntry.COLUMN_MEDICAL_CONDITION + " VARCHAR(255), "
                + HealthBioEntry.COLUMN_START_DATE + " DATE, "
                + HealthBioEntry.COLUMN_CONDITION_TYPE + " VARCHAR(1)" + ")";

        db.execSQL(SQL_CREATE_HEALTH_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}