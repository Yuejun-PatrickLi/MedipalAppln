package iss.nus.edu.medipalappln.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;


/**
 * Created by kiruba on 3/21/17.
 */

import iss.nus.edu.medipalappln.R;
import iss.nus.edu.medipalappln.dao.HealthBioContract.HealthBioEntry;

public class HealthBioCursorAdapter extends CursorAdapter {

    public HealthBioCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_health_bio_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView healthTextView = (TextView) view.findViewById(R.id.list_health_condition);
        TextView startDateTextView = (TextView) view.findViewById(R.id.list_start_date);
        //
        TextView conTypeTextView = (TextView) view.findViewById(R.id.list_condition_type);


        int healthConditionColumnIndex = cursor.getColumnIndex(HealthBioEntry.COLUMN_MEDICAL_CONDITION);
        int startDateColumnIndex = cursor.getColumnIndex(HealthBioEntry.COLUMN_START_DATE);
        //
        int conTypeColumnIndex = cursor.getColumnIndex(HealthBioEntry.COLUMN_CONDITION_TYPE);

        String healthCondition = cursor.getString(healthConditionColumnIndex);
        String startDate = cursor.getString(startDateColumnIndex);
        //
        String conType = cursor.getString(conTypeColumnIndex);


        healthTextView.setText(healthCondition);
        startDateTextView.setText(startDate);
        //
        //conTypeTextView.setText(conType);

    }
}
