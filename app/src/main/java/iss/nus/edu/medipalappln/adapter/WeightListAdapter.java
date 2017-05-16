package iss.nus.edu.medipalappln.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import iss.nus.edu.medipalappln.R;
import iss.nus.edu.medipalappln.medipal.App;
import iss.nus.edu.medipalappln.medipal.Weight;

public class WeightListAdapter extends ArrayAdapter {

    private static final String TAG = "WeightListAdapter";

    private Context context;
    private ArrayList<Weight> weights = new ArrayList<Weight>();
    private ArrayList<Weight> dbList = new ArrayList<Weight>();

    public WeightListAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
        this.context = context;
        refreshList();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        Log.i(TAG, "Retrieving record " + position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.measurement_row_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewWeight = (TextView) convertView.findViewById(R.id.text_view_col1);
            viewHolder.textViewMeasuredOn = (TextView) convertView.findViewById(R.id.text_view_measured_on);
            viewHolder.buttonDelete = (Button) convertView.findViewById(R.id.button_delete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (weights.size() != 0) {
            final Weight weight = weights.get(position);

            viewHolder.textViewWeight.setText(weight.getWeight().toString());
            viewHolder.textViewMeasuredOn.setText(weight.getMeasuredOn());

            viewHolder.buttonDelete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    App.user.deleteWeight(context, weight.getID());
                    refreshList();
                    Log.i(TAG, "Deleted record: " + weight.getID());
                }
            });
        }
        else
        {
            Log.i(TAG, "No record found");
        }

        return convertView;
    }

    public void refreshList() {
        if (App.user == null) {
            Log.e(TAG, "Critical: App.user object is null");
        }
        else {
            weights.clear();
            weights.addAll(App.user.getWeight(context));
            dbList = (ArrayList<Weight>) weights.clone();
            Log.i(TAG, "refreshList");
            notifyDataSetChanged();
        }
    }

    public void refreshList(String startDate, String endDate) {

        if (App.user == null) {
            Log.e(TAG, "Critical: App.user object is null");
        }
        else {
            int initial_row = dbList.size();
            ArrayList<Weight> newList = new ArrayList<Weight>();

            if (startDate.isEmpty()) {
                startDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
            }

            if (endDate.isEmpty()) {
                endDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
            }

            for (int i=0; i<initial_row; i++)
            {
                Log.i(TAG, "item: " + dbList.get(i).getMeasuredOn());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date listDate = Calendar.getInstance().getTime();
                Date sDate = null;
                Date eDate = null;
                try {
                    sDate = dateFormat.parse(startDate);
                    eDate = dateFormat.parse(endDate);
                    listDate = dateFormat.parse(dbList.get(i).getMeasuredOn());
                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.e(TAG, "error parsing item: " + dbList.get(i).getMeasuredOn());
                }

                if (listDate.after(sDate) && listDate.before(eDate)) {
                    newList.add(i, dbList.get(i));
                }
            }
            weights.clear();
            weights = newList;
            Log.i(TAG, "filterList");
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return weights.size();
    }

    static class ViewHolder {
        TextView textViewWeight;
        TextView textViewMeasuredOn;
        Button buttonDelete;
    }

}
