package iss.nus.edu.medipalappln.dao;

/**
 * Created by darryl on 26/12/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import iss.nus.edu.medipalappln.medipal.Consumption;
import iss.nus.edu.medipalappln.medipal.Medicine;


public class ConsumptionDAO extends DBDAO {
    private static final String WHERE_ID_EQUALS = DataBaseHelper.CONSUMPTION.ID.toString() + " =?";
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH);
    private static final SimpleDateFormat medformatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);

    public ConsumptionDAO(Context context) {
        super(context);
    }

    public long save(Consumption consumption) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.CONSUMPTION.MedicineID.toString(), consumption.getMedId());
        values.put(DataBaseHelper.CONSUMPTION.Quantity.toString(), consumption.getQuantity());
        values.put(DataBaseHelper.CONSUMPTION.ConsumedOn.toString(), formatter.format(consumption.getConDate()));
        return database.insert(DataBaseHelper.TABLE_CONSUMPTION, null, values);
    }

    //USING query() method
    public ArrayList<Consumption> getConsumptions() {
        ArrayList<Consumption> consumptions = new ArrayList<Consumption>();
        String[] querystr = new String[]{DataBaseHelper.CONSUMPTION.ID.toString(), DataBaseHelper.CONSUMPTION.MedicineID.toString(),
                DataBaseHelper.CONSUMPTION.Quantity.toString(), DataBaseHelper.CONSUMPTION.ConsumedOn.toString()};
        Cursor cursor = database.query(DataBaseHelper.TABLE_CONSUMPTION,
                querystr, null, null, null,
                null, null);

        while (cursor.moveToNext()) {
            int bid = cursor.getInt(0);
            int mid = cursor.getInt(1);
            int quan = cursor.getInt(2);
            Date cdate = null;

            try {
                cdate = formatter.parse(cursor.getString(3));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Consumption consumption = null;
            consumption = new Consumption(bid,mid,quan,cdate);
            consumptions.add(consumption);
        }
        return consumptions;
    }

    public ArrayList<Consumption> getConsumptionsByCategory(int catid){
        ArrayList<Consumption> bookings = new ArrayList<Consumption>();
        String[] querystr = new String[]{DataBaseHelper.CONSUMPTION.ID.toString(), DataBaseHelper.CONSUMPTION.MedicineID.toString(),
                DataBaseHelper.CONSUMPTION.Quantity.toString(), DataBaseHelper.CONSUMPTION.ConsumedOn.toString()};
        Cursor cursor = database.query(DataBaseHelper.TABLE_CONSUMPTION,
                querystr, null, null, null,
                null, null);
        return  bookings;
    }


    //Retrieves a single booking record with the given id
    public Consumption getConsumption(long id) {
        Consumption consumption = null;

        String sql = "SELECT * FROM " + DataBaseHelper.TABLE_CONSUMPTION
                + " WHERE " + DataBaseHelper.CONSUMPTION.ID.toString() + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { id + "" });

        if (cursor.moveToNext()) {
            int bid = cursor.getInt(0);
            int mid = cursor.getInt(1);
            int quan = cursor.getInt(2);
            Date cdate = null;
            try {
                cdate = formatter.parse(cursor.getString(3));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            consumption = new Consumption(bid, mid, quan, cdate );

        }
        return consumption;
    }

    public Medicine getConsumptionMedicine(Consumption consumption){
        Medicine medicine = null;
        int medId = consumption.getMedId();
        String sql = "SELECT * FROM " + DataBaseHelper.TABLE_MEDICINE
                + " WHERE " + DataBaseHelper.MEDICINE.ID.toString() + " = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{Integer.toString(medId)});
        if (cursor.moveToFirst()){
            String medName = cursor.getString(cursor.getColumnIndex(DataBaseHelper.MEDICINE.Name.toString()));
            String medDescription = cursor.getString(cursor.getColumnIndex(DataBaseHelper.MEDICINE.Description.toString()));
            int medCAT = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.MEDICINE.CatID.toString()));
            int medRemindId = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.MEDICINE.ReminderID.toString()));
            String medRemindFlag = cursor.getString(cursor.getColumnIndex(DataBaseHelper.MEDICINE.Remind.toString()));
            int medQuan = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.MEDICINE.Quantity.toString()));
            int medDosage = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.MEDICINE.Dosage.toString()));
            int medThreshold = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.MEDICINE.Threshold.toString()));
            String str = cursor.getString(cursor.getColumnIndex(DataBaseHelper.MEDICINE.DateIssued.toString()));
            ParsePosition pos = new ParsePosition(0);
            Date medIssued = medformatter.parse(str,pos);
            int medExpir = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.MEDICINE.ExpiryFactor.toString()));
            //TODO boolean remind
            medicine = new Medicine(medId, medName,medDescription,medCAT,medRemindId,medRemindFlag, medQuan,medDosage,medThreshold,medIssued,medExpir);
        }
        else{
            Log.d("BookingDao","Retrive MED from BOOKING failed");
        }

        return medicine;
    }
}
