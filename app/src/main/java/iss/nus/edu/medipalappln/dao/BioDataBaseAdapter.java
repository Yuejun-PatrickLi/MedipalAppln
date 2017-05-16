package iss.nus.edu.medipalappln.dao;
/**
 * Created by Raghu on 7/3/17.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import iss.nus.edu.medipalappln.activity.Session;
import iss.nus.edu.medipalappln.medipal.Personal;

public class BioDataBaseAdapter extends DBDAO {
    Session session;
    private static final String TAG = "DBDAO";


    //begin SQL statements
    public static final String SELECT_ALL = "SELECT * FROM " + DataBaseHelper.TABLE_PERSONALBIO +
            " WHERE 1";
    //end SQL statements

    public BioDataBaseAdapter(Context context) {
        super(context);
        //session=new Session(context);
    }



    public long addValues (Personal formData){
        ContentValues values = new ContentValues();
        //values.put(DataBaseHelper.PERSONALBIO.ID.toString(), formData.getID());
        values.put(DataBaseHelper.PERSONALBIO.Name.toString(), formData.getName());
        values.put(DataBaseHelper.PERSONALBIO.DOB.toString(), formData.getDob());
        values.put(DataBaseHelper.PERSONALBIO.IDNo.toString(), formData.getIdno());
        values.put(DataBaseHelper.PERSONALBIO.Address.toString(), formData.getAddress());
        values.put(DataBaseHelper.PERSONALBIO.PostalCode.toString(), formData.getPostcode());
        values.put(DataBaseHelper.PERSONALBIO.Height.toString(), formData.getHeight());
        //values.put(DataBaseHelper.PERSONALBIO.Weight.toString(),formData.getWeight());
        values.put(DataBaseHelper.PERSONALBIO.BloodType.toString(), formData.getBloodtype());
        values.put(DataBaseHelper.PERSONALBIO.Phone.toString(),formData.getPhone());

        return database.insert(DataBaseHelper.TABLE_PERSONALBIO, null, values);

    }


    public long updateValues(Personal formData,String key) {
        ContentValues values = new ContentValues();
        String[] args = new String[] {key};

        values.put(DataBaseHelper.PERSONALBIO.ID.toString(),formData.getID());
        values.put(DataBaseHelper.PERSONALBIO.Name.toString(),formData.getName());
        values.put(DataBaseHelper.PERSONALBIO.DOB.toString(),formData.getDob());
        values.put(DataBaseHelper.PERSONALBIO.IDNo.toString(),formData.getIdno());
        values.put(DataBaseHelper.PERSONALBIO.Address.toString(), formData.getAddress());
        values.put(DataBaseHelper.PERSONALBIO.PostalCode.toString(), formData.getPostcode());
        values.put(DataBaseHelper.PERSONALBIO.Height.toString(), formData.getHeight());
        //values.put(DataBaseHelper.PERSONALBIO.Weight.toString(),formData.getWeight());
        values.put(DataBaseHelper.PERSONALBIO.BloodType.toString(), formData.getBloodtype());
        values.put(DataBaseHelper.PERSONALBIO.Phone.toString(),formData.getPhone());

        return database.update(DataBaseHelper.TABLE_PERSONALBIO, values, "ID = ?", args);
    }

    public long deleteValues(String key) {
        String[] args = new String[] {key};

        return database.delete(DataBaseHelper.TABLE_PERSONALBIO, "ID = ?", args);
    }
//String id=session.username();


    public ArrayList<Personal> getFormDatas() {
        ArrayList<Personal> formDatas = new ArrayList<Personal>();
        String query[] = { DataBaseHelper.PERSONALBIO.ID.toString(),
                DataBaseHelper.PERSONALBIO.Name.toString(),
                DataBaseHelper.PERSONALBIO.DOB.toString(),
                DataBaseHelper.PERSONALBIO.IDNo.toString(),
                DataBaseHelper.PERSONALBIO.Address.toString(),
                DataBaseHelper.PERSONALBIO.PostalCode.toString(),
                DataBaseHelper.PERSONALBIO.Height.toString(),
                //DataBaseHelper.PERSONALBIO.Weight.toString(),
                DataBaseHelper.PERSONALBIO.BloodType.toString()
        };
        //String where = DataBaseHelper.PERSONALBIO.Weight + " >1 ";
        //int id;

        //Cursor cursor = database.query(DataBaseHelper.TABLE_PERSONALBIO, query, where, null, null,
        Cursor cursor = database.query(DataBaseHelper.TABLE_PERSONALBIO, query, null, null, null,
                null, null);

        while (cursor.moveToNext()) {
            //id = cursor.getInt(0);
            try {


                Personal formData = new Personal(cursor.getString(cursor.getColumnIndex(DataBaseHelper.PERSONALBIO.ID.toString())),
                        cursor.getString(cursor.getColumnIndex(DataBaseHelper.PERSONALBIO.Name.toString())),
                        cursor.getString(cursor.getColumnIndex(DataBaseHelper.PERSONALBIO.DOB.toString())),
                        cursor.getString(cursor.getColumnIndex(DataBaseHelper.PERSONALBIO.IDNo.toString())),
                        cursor.getString(cursor.getColumnIndex(DataBaseHelper.PERSONALBIO.Address.toString())),
                        cursor.getString(cursor.getColumnIndex(DataBaseHelper.PERSONALBIO.PostalCode.toString())),
                        cursor.getString(cursor.getColumnIndex(DataBaseHelper.PERSONALBIO.Height.toString())),
                        //cursor.getString(cursor.getColumnIndex(DataBaseHelper.PERSONALBIO.Weight.toString())),
                        cursor.getString(cursor.getColumnIndex(DataBaseHelper.PERSONALBIO.BloodType.toString())),
                        cursor.getString(cursor.getColumnIndex(DataBaseHelper.PERSONALBIO.Phone.toString())), "");
                formDatas.add(formData);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return formDatas;


    }



    public Personal getformData(String id) {
        Personal formData = null;
        Cursor cursor=null;
        String sql = "SELECT * FROM " + DataBaseHelper.TABLE_PERSONALBIO;
        //+ " WHERE " + id + " = ?";

        cursor = database.rawQuery(sql,null);

        if (cursor.moveToNext()) {
            String eid = cursor.getString(0);
            String name = cursor.getString(1);
            String dob = cursor.getString(2);
            String idno = cursor.getString(3);
            String address=cursor.getString(4);
            String postalcode=cursor.getString(5);
            String height=cursor.getString(6);
            String weight=cursor.getString(7);
            String bloodtype=cursor.getString(8);

            String phone=cursor.getString(9);

            //formData = new Personal(eid, name, dob,idno,address,postalcode,height,weight,bloodtype,bloodtype);
            formData = new Personal(eid, name, dob,idno,address,postalcode,height,bloodtype,bloodtype, "");
            //return formData;
        }
        return formData;
    }






    public String getSingleEntry() {
        String dbString = "";

        Cursor cursor = database.rawQuery(SELECT_ALL, null);
        cursor.moveToFirst();

        Log.i(TAG, "Record(s) count " + cursor.getCount());

        for( int i = 0; i < cursor.getCount(); i++) {
            if(cursor.getString(cursor.getColumnIndex(DataBaseHelper.PERSONALBIO.ID.toString())) != null) {

                dbString += cursor.getString(cursor.getColumnIndex(DataBaseHelper.PERSONALBIO.Name.toString()));
                dbString += "\t";
                dbString += cursor.getString(cursor.getColumnIndex(DataBaseHelper.PERSONALBIO.DOB.toString()));
                dbString += "\t";
                dbString += cursor.getString(cursor.getColumnIndex(DataBaseHelper.PERSONALBIO.IDNo.toString()));
                dbString += "\t";
                dbString += cursor.getString(cursor.getColumnIndex(DataBaseHelper.PERSONALBIO.Address.toString()));
                dbString += "\t";
                dbString += cursor.getString(cursor.getColumnIndex(DataBaseHelper.PERSONALBIO.PostalCode.toString()));
                dbString += "\t";
                dbString += cursor.getString(cursor.getColumnIndex(DataBaseHelper.PERSONALBIO.Height.toString()));
                dbString += "\t";
                dbString += cursor.getString(cursor.getColumnIndex(DataBaseHelper.PERSONALBIO.BloodType.toString()));
                dbString += "\t";
                Log.i(TAG, "Record: " + dbString);
            }
            cursor.moveToNext();
        }

        return dbString;
    }











}



















/*
/**
 * Created by root on 15/3/17.
 *//*
import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
public class BioDataBaseAdapter
{
    static final String DATABASE_NAME = "login.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table "+"PersonalBio"+
            "( " + "formDataNAME text,BLOODTYPE  text,DOB integer,HEIGHT text,WEIGHT text,ADDRESS text,PINCODE integer,PHONENUMBER integer); ";
    // Variable to hold the database instance
    public SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private DataBaseHelper2 dbHelper;
    public  BioDataBaseAdapter(Context _context)
    {
        context = _context;
       dbHelper = new DataBaseHelper2(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public  BioDataBaseAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        db.close();
    }
    public  SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }
    public void insertEntry(String username, String Blood, String Dob, String Height, String Weight, String Address, String Pincode, String MobileNumb)
    {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("USERNAME",username);
        newValues.put("BLOODTYPE", Blood);
        newValues.put("DOB",Dob);
        newValues.put("HEIGHT",Height);
        newValues.put("WEIGHT",Weight);
        newValues.put("ADDRESS",Address);
        newValues.put("PINCODE",Pincode);
        newValues.put("PHONENUMBER",MobileNumb);
        // Insert the row into your table
        db.insert("PersonalBio", null, newValues);
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }
   */
/* public int deleteEntry(String UserName)
    {
        //String id=String.valueOf(ID);
        String where="USERNAME=?";
        int numberOFEntriesDeleted= db.delete("LOGIN", where, new String[]{UserName}) ;
        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }
    public String getSingleEntry(String userName)
    {
        Cursor cursor=db.query("LOGIN", null, " USERNAME=?", new String[]{userName}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password= cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return password;
    }
   */
/* public void  updateEntry(String userName,String password)
    {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put("USERNAME", userName);
        updatedValues.put("PASSWORD",password);
        String where="USERNAME = ?";
        db.update("LOGIN",updatedValues, where, new String[]{userName});
    }*//*
}
*/