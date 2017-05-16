package iss.nus.edu.medipalappln.dao;

/**
 * Created by niv on 26/12/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import iss.nus.edu.medipalappln.medipal.Medicine;

public class MedicineDAO extends DBDAO {
    private static final String WHERE_ID_EQUALS = DataBaseHelper.MEDICINE.ID + " =?";
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    public MedicineDAO(Context context) {
        super(context);
    }

    public long save(Medicine medicine) {
        ContentValues values = new ContentValues();
        //values.put(DataBaseHelper.MID_COLUMN, medicine.getMemberNumber());
        //TODO lack boolean remind
        values.put(DataBaseHelper.MEDICINE.Name.toString(), medicine.getMedName());
        values.put(DataBaseHelper.MEDICINE.Description.toString(), medicine.getMedDesc());
        values.put(DataBaseHelper.MEDICINE.CatID.toString(), medicine.getCatId());
        values.put(DataBaseHelper.MEDICINE.ReminderID.toString(), medicine.getRemindId());
        values.put(DataBaseHelper.MEDICINE.Remind.toString(), medicine.getRemindFlag());
        values.put(DataBaseHelper.MEDICINE.Quantity.toString(), medicine.getQuantity());
        values.put(DataBaseHelper.MEDICINE.Dosage.toString(), medicine.getDosage());
        values.put(DataBaseHelper.MEDICINE.Threshold.toString(), medicine.getThreshold());
        values.put(DataBaseHelper.MEDICINE.DateIssued.toString(), formatter.format(medicine.getDateIssued()));
        values.put(DataBaseHelper.MEDICINE.ExpiryFactor.toString(), medicine.getExpireFactor());
        Log.d("MEDICINE!!!!!!!!!!!","RUNRUNRUNRUNRUN +++++++++++++++++");
        return database.insert(DataBaseHelper.TABLE_MEDICINE, null, values);
    }

    public long update(Medicine medicine) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.MEDICINE.Name.toString(), medicine.getMedName());
        values.put(DataBaseHelper.MEDICINE.Description.toString(), medicine.getMedDesc());
        values.put(DataBaseHelper.MEDICINE.CatID.toString(), medicine.getCatId());
        values.put(DataBaseHelper.MEDICINE.ReminderID.toString(), medicine.getRemindId());
        values.put(DataBaseHelper.MEDICINE.Remind.toString(), medicine.getRemindFlag());
        values.put(DataBaseHelper.MEDICINE.Quantity.toString(), medicine.getQuantity());
        values.put(DataBaseHelper.MEDICINE.Dosage.toString(), medicine.getDosage());
        values.put(DataBaseHelper.MEDICINE.Threshold.toString(), medicine.getThreshold());
        values.put(DataBaseHelper.MEDICINE.DateIssued.toString(), formatter.format(medicine.getDateIssued()));
        values.put(DataBaseHelper.MEDICINE.ExpiryFactor.toString(), medicine.getExpireFactor());


        long result = database.update(DataBaseHelper.TABLE_MEDICINE, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(medicine.getMedId()) });
        Log.d("Update Result:", "=" + result);
        return result;

    }

    public int delete(Medicine medicine) {
        return database.delete(DataBaseHelper.TABLE_MEDICINE, WHERE_ID_EQUALS,
                new String[] { medicine.getMedId() + "" });
    }
    //USING query() method
   public ArrayList<Medicine> getMembers() {
        ArrayList<Medicine> medicines = new ArrayList<Medicine>();
        String[] querystr = new String[]{DataBaseHelper.MEDICINE.ID.toString(), DataBaseHelper.MEDICINE.Name.toString(), DataBaseHelper.MEDICINE.Description.toString(),
                DataBaseHelper.MEDICINE.CatID.toString(), DataBaseHelper.MEDICINE.ReminderID.toString(), DataBaseHelper.MEDICINE.Remind.toString(), DataBaseHelper.MEDICINE.Quantity.toString(),
                DataBaseHelper.MEDICINE.Dosage.toString(), DataBaseHelper.MEDICINE.Threshold.toString(), DataBaseHelper.MEDICINE.DateIssued.toString(),
                DataBaseHelper.MEDICINE.ExpiryFactor.toString()};
        Cursor cursor = database.query(DataBaseHelper.TABLE_MEDICINE,
                querystr, null, null, null,
                null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String mName = cursor.getString(1);
            String mDesc = cursor.getString(2);
            int mCatId = cursor.getInt(3);
            int mReminderID = cursor.getInt(4);
            String mReminderFlag = cursor.getString(5);
            int mQuantity = cursor.getInt(6);
            int mDosage = cursor.getInt(7);
            int mThreshold = cursor.getInt(8);
            Date mDateIssued = null;
            try {
                mDateIssued = formatter.parse(cursor.getString(9));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int mExpiryFactor = cursor.getInt(10);

            Medicine medicine = new Medicine(id, mName, mDesc, mCatId, mReminderID,mReminderFlag, mQuantity, mDosage, mThreshold, mDateIssued, mExpiryFactor);
            medicines.add(medicine);
        }
        return medicines;
    }

    //USING rawQuery() method
//    public ArrayList<Medicine> getMembersRQ() {
//        ArrayList<Medicine> medicines = new ArrayList<Medicine>();
//
//        String sql = "SELECT " + DataBaseHelper.MED_ID + ","
//                + DataBaseHelper.MED_DESC + ","
//                + DataBaseHelper.MED_CATEGORY + ","
//                + DataBaseHelper.MED_REMIND + ","
//                + DataBaseHelper.MED_QUANTITY + ","
//                + DataBaseHelper.MED_DOSAGE + ","
//                + DataBaseHelper.MED_THRESHOLD + ","
//                + DataBaseHelper.MED_DATE_ISSUED + ","
//                + DataBaseHelper.MED_EXPIRY_FACTOR + " FROM "
//                + DataBaseHelper.MEMBER_TABLE;
//
//        Cursor cursor = database.rawQuery(sql, null);
//
//        while (cursor.moveToNext()) {
//            int id = cursor.getInt(0);
//            String mName = cursor.getString(1);
//            String mDesc = cursor.getString(2);
//            int mCategory = cursor.getInt(3);
//            int mReminder = cursor.getInt(4);
//            int mQuantity = cursor.getInt(5);
//            int mDosage = cursor.getInt(6);
//            int mThreshold = cursor.getInt(7);
//
//            Date mDateIssued = null;
//
//            try {
//                mDateIssued = formatter.parse(cursor.getString(8));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//            int mExpiryFactor = cursor.getInt(9);
//
//            Medicine medicine = new Medicine(id, mName, mDesc, mCategory, mReminder, mQuantity, mDosage, mThreshold, mDateIssued, mExpiryFactor);
//            medicines.add(medicine);
//        }
//        return medicines;
//    }

    //Retrieves a single member record with the given id
    public Medicine getMember(long id) {
        Medicine medicine = null;

        String sql = "SELECT * FROM " + DataBaseHelper.TABLE_MEDICINE
                + " WHERE " + DataBaseHelper.MEDICINE.ID.toString() + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { id + "" });

        if (cursor.moveToNext()) {
            int mid = cursor.getInt(0);
            String mName = cursor.getString(1);
            String mDesc = cursor.getString(2);
            int mCategory = cursor.getInt(3);
            int mReminder = cursor.getInt(4);
            String mReminderFlag = cursor.getString(5);
            int mQuantity = cursor.getInt(6);
            int mDosage = cursor.getInt(7);
            int mThreshold = cursor.getInt(8);
            Date mDateIssued = null;

            try {
                mDateIssued = formatter.parse(cursor.getString(9));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int mExpiryFactor = cursor.getInt(10);

            medicine = new Medicine(mid, mName, mDesc, mCategory, mReminder,mReminderFlag, mQuantity, mDosage, mThreshold, mDateIssued, mExpiryFactor);

        }
        return medicine;
    }
}
