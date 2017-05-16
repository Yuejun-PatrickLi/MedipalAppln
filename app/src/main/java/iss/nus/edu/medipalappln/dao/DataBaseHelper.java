package iss.nus.edu.medipalappln.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper
{
    private static final String TAG = "DBH";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MediPal.db";

    public static final String TABLE_PERSONALBIO = "PersonalBio";
    public static final String TABLE_HEALTHBIO = "HealthBio";
    public static final String TABLE_CATEGORY = "Categories";
    public static final String TABLE_MEDICINE = "Medicine";
    public static final String TABLE_MEASUREMENT = "Measurement";
    public static final String TABLE_CONSUMPTION = "Consumption";
    public static final String TABLE_REMINDER = "Reminders";
    public static final String TABLE_APPOINTMENT = "Appointment";
    public static final String TABLE_ICE = "ICE";

    //table columns
    public enum PERSONALBIO {ID, Name, DOB, IDNo, Address, PostalCode, Height, BloodType, Phone};
    public enum HEALTHBIO {ID, Condition, StartDate, ConditionType};
    public enum CATEGORY {ID, Name, Code, Description, Remind};
    public enum MEDICINE {ID, Name, Description, CatID, ReminderID, Remind, Quantity, Dosage,
        Threshold, DateIssued, ExpiryFactor};
    public enum MEASUREMENT {ID, Systolic, Diastolic, Pulse, Temperature, Weight , MeasuredOn};
    public enum CONSUMPTION {ID, MedicineID, Quantity, ConsumedOn};
    public enum REMINDER {ID, Frequency, StartTime, Interval};
    public enum APPOINTMENT {ID, Location, Appointment, Description};
    public enum ICE {ID, Name, ContactNo, ContactType, Sequence};

    //begin SQL statement
    public static final String CREATE_TABLE_PERSONALBIO = "CREATE TABLE " + TABLE_PERSONALBIO +
            "(" +
            PERSONALBIO.ID + " VARCHAR(20), " +
            PERSONALBIO.Name + " VARCHAR(100), " +
            PERSONALBIO.DOB + " VARCHAR(20), " +
            PERSONALBIO.IDNo + " VARCHAR(20), " +
            PERSONALBIO.Address + " VARCHAR(100), " +
            PERSONALBIO.PostalCode + " VARCHAR(10), " +
            PERSONALBIO.Height + " INTEGER, " +
            PERSONALBIO.BloodType + " VARCHAR(10) " +
            ");";

    public static final String CREATE_TABLE_HEALTHBIO = "CREATE TABLE " + TABLE_HEALTHBIO +
            "(" +
            HEALTHBIO.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            HEALTHBIO.Condition + " VARCHAR(255), " +
            HEALTHBIO.StartDate + " DATE, " +
            HEALTHBIO.ConditionType + " VARCHAR(1) " +
            ");";

    public static final String CREATE_TABLE_CATEGORY = "CREATE TABLE " + TABLE_CATEGORY +
            "(" +
            CATEGORY.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CATEGORY.Name + " VARCHAR(50) not null, " +
            CATEGORY.Code + " VARCHAR(5) not null, " +
            CATEGORY.Description + " VARCHAR(255), " +
            CATEGORY.Remind + " VARCHAR(100) not null" + //there is no boolean type in sqlite
            ");";

    public static final String CREATE_TABLE_MEDICINE = "CREATE TABLE " + TABLE_MEDICINE +
            "(" +
            MEDICINE.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MEDICINE.Name + " VARCHAR(50) not null, " +
            MEDICINE.Description + " VARCHAR(255), " +
            MEDICINE.CatID + " INTEGER not null, " +
            MEDICINE.ReminderID + " INTEGER, " +
            MEDICINE.Remind + " VARCHAR(50) not null, " +
            MEDICINE.Quantity + " INTEGER not null, " +
            MEDICINE.Dosage + " INTEGER not null, " +
            MEDICINE.Threshold + " INTEGER not null, " +
            MEDICINE.DateIssued  + " DATE not null, " +
            MEDICINE.ExpiryFactor  + " INTEGER " +
            ");";

    public static final String CREATE_TABLE_MEASUREMENT = "CREATE TABLE " + TABLE_MEASUREMENT +
            "(" +
            MEASUREMENT.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MEASUREMENT.Systolic + " INTEGER, " +
            MEASUREMENT.Diastolic + " INTEGER, " +
            MEASUREMENT.Pulse + " INTEGER, " +
            MEASUREMENT.Temperature + " DECIMAL(5,2), " +
            MEASUREMENT.Weight + " INTEGER, " +
            MEASUREMENT.MeasuredOn + " DATETIME" +
            ");";

    public static final String CREATE_TABLE_CONSUMPTION = "CREATE TABLE " + TABLE_CONSUMPTION +
            "(" +
            CONSUMPTION.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CONSUMPTION.MedicineID + " INTEGER, " +
            CONSUMPTION.Quantity + " INTEGER, " +
            CONSUMPTION.ConsumedOn + " DATETIME " +
            ");";

    public static final String CREATE_TABLE_REMINDER = "CREATE TABLE " + TABLE_REMINDER +
            "(" +
            REMINDER.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            REMINDER.Frequency + " INTEGER not null, " +
            REMINDER.StartTime + " DATETIME, " +
            REMINDER.Interval + " VARCHAR(100) " +
            ");";

    public static final String CREATE_TABLE_APPOINTMENT = "CREATE TABLE " + TABLE_APPOINTMENT +
            "(" +
            APPOINTMENT.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            APPOINTMENT.Location + " VARCHAR(100), " +
            APPOINTMENT.Appointment + " DATETIME, " +
            APPOINTMENT.Description + " VARCHAR(255) " +
            ");";

    public static final String CREATE_TABLE_ICE = "CREATE TABLE " + TABLE_ICE +
            "(" +
            ICE.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ICE.Name + " VARCHAR(100), " +
            ICE.ContactNo + " VARCHAR(20) ," +
            ICE.ContactType + " INTEGER, " +
            ICE.Sequence + " VARCHAR(255) " +
            // ICE.Sequence + " INTEGER " +
            ");";

    //Initial Data Insert String
    private static final String INITIAL_CATEGORY = "INSERT INTO " + TABLE_CATEGORY +
            "(" + CATEGORY.Name + "," + CATEGORY.Code + "," + CATEGORY.Description + "," + CATEGORY.Remind + ")" +
            " VALUES " +
            "('Supplement','SUP','Set reminder option for consumption of supplement is optional','REMINDER DISABLED')," +
            "('Incidental','INC','For unplanned incidents with prescription from general practitioners','REMINDER ENABLED')," +
            "('Complete Course','COM','For antibiotics medication like sinus infection, pneumonia, bronchitis, acne, strep throat, cellulitis, etc','REMINDER ENABLED')," +
            "('Self Apply','SEL','To note down any self-prescribed medication','REMINDER ENABLED')," +
            "('Chronic','CHR','To categorise medication for long-term/life-time consumption for diseases','REMINDER DISABLED')" ;


    public static final String DROP_TABLE = "DROP TABLE IF EXISTS ";
    //end of SQL statements

    private static DataBaseHelper instance;

    public static synchronized DataBaseHelper getHelper(Context context) {
        if (instance == null) {
            instance = new DataBaseHelper(context);
            Log.d(TAG,"!!!!!!!!!!!!!!!");
        }
        return instance;
    }

    //TODO: Change to private. Create table in DataBaseHelper in ONLY.
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DataBaseHelper(Context context, String DBNAME, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase _db)
    {

        try {
            _db.execSQL(CREATE_TABLE_PERSONALBIO);
            Log.d(TAG,"2 Build PERSONBIO Database SUCCESSFUL!!!!!!!!");
            _db.execSQL(CREATE_TABLE_HEALTHBIO);
            Log.d(TAG,"3 Build HEALTHBIO Database SUCCESSFUL!!!!!!!!");
            _db.execSQL(CREATE_TABLE_CATEGORY);
            Log.d(TAG,"4 Build CATEGORY Database SUCCESSFUL!!!!!!!!");
            _db.execSQL(CREATE_TABLE_MEDICINE);
            Log.d(TAG,"5 Build MEDICINE Database SUCCESSFUL!!!!!!!!");
            _db.execSQL(CREATE_TABLE_MEASUREMENT);
            Log.d(TAG,"6 Build MEASUREMENT Database SUCCESSFUL!!!!!!!!");
            _db.execSQL(CREATE_TABLE_CONSUMPTION);
            Log.d(TAG,"7 Build CONSUMPTION Database SUCCESSFUL!!!!!!!!");
            _db.execSQL(CREATE_TABLE_REMINDER);
            Log.d(TAG,"8 Build REMINDER Database SUCCESSFUL!!!!!!!!");
            _db.execSQL(CREATE_TABLE_APPOINTMENT);
            Log.d(TAG,"9 Build APPOINTMENT Database SUCCESSFUL!!!!!!!!");
            _db.execSQL(CREATE_TABLE_ICE);
            Log.d(TAG,"10 Build ICE Database SUCCESSFUL!!!!!!!!");
            //initial Category data
            _db.execSQL(INITIAL_CATEGORY);
            Log.d(TAG,"INITIAL CATEGORY SUCCESS!!!!!!!!!!");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion)
    {
        // Log the version upgrade.
        Log.w(TAG, "Upgrading from version " +_oldVersion + " to " +_newVersion + ", " +
                "which will destroy all old data");

        _db.execSQL(DROP_TABLE + TABLE_PERSONALBIO);
        _db.execSQL(DROP_TABLE + TABLE_HEALTHBIO);
        _db.execSQL(DROP_TABLE + TABLE_CATEGORY);
        _db.execSQL(DROP_TABLE + TABLE_MEDICINE);
        _db.execSQL(DROP_TABLE + TABLE_MEASUREMENT);
        _db.execSQL(DROP_TABLE + TABLE_CONSUMPTION);
        _db.execSQL(DROP_TABLE + TABLE_REMINDER);
        _db.execSQL(DROP_TABLE + TABLE_APPOINTMENT);
        _db.execSQL(DROP_TABLE + TABLE_ICE);

        onCreate(_db);
    }

    @Override
    public void onOpen(SQLiteDatabase _db) {
        super.onOpen(_db);
    }
}
