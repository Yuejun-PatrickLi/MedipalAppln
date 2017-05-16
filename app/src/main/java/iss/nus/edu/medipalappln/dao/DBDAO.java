package iss.nus.edu.medipalappln.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class DBDAO {

    private static final String TAG = "DBDAO";

    protected SQLiteDatabase database;
    private DataBaseHelper dbHelper;
    private Context context;

    public DBDAO(Context context) {
        this.context = context;
        dbHelper = DataBaseHelper.getHelper(context);
        open();
    }

    public void open() throws SQLiteException {
        if (dbHelper == null) {
            dbHelper = DataBaseHelper.getHelper(context);
        }
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
        database = null;
    }
}
