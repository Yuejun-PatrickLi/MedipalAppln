package iss.nus.edu.medipalappln.adapter;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
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
import iss.nus.edu.medipalappln.medipal.Emergency;

public class EmergencyListAdapter extends ArrayAdapter {

    private static final String TAG = "EmergencyListAdapter";

    private Context context;
    private ArrayList<Emergency> emergencies = new ArrayList<Emergency>();
    private ArrayList<Emergency> dbList = new ArrayList<Emergency>();

    public EmergencyListAdapter(Context context) {
        super(context, R.layout.emergency_row_layout);
        this.context = context;
        refreshList();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        Log.i(TAG, "Retrieving record " + position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.emergency_row_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.editTextName = (TextView) convertView.findViewById(R.id.editTextName);
            viewHolder.editTextPhone = (TextView) convertView.findViewById(R.id.editTextPhone);
            viewHolder.editTextRelation = (TextView) convertView.findViewById(R.id.editTextRelation);
            viewHolder.buttonDelete = (Button) convertView.findViewById(R.id.button_delete);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ArrayList<Emergency> emergencies = App.user.getEmergency(context);

        final Emergency e = emergencies.get(position);

        viewHolder.editTextName = (TextView) convertView.findViewById(R.id.editTextName);
        viewHolder.editTextPhone = (TextView) convertView.findViewById(R.id.editTextPhone);
        viewHolder.editTextRelation = (TextView) convertView.findViewById(R.id.editTextRelation);
        viewHolder.buttonDelete = (Button) convertView.findViewById(R.id.button_delete);
        viewHolder.editTextName.setText("Name-" + e.getName().toString());
        viewHolder.editTextPhone.setText("Phone-" + e.getPhone().toString());
        viewHolder.editTextRelation.setText("Relation-" + e.getDesc().toString());

        viewHolder.buttonDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                callIntent.setData(Uri.parse("tel:" + "+65" +e.getPhone().toString().trim()));

                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                getContext().startActivity(callIntent);

                    /*App.user.deleteEmergency(context, e.getID().toString());
                    refreshList();
                    Log.i(TAG, "Deleted record: " + e.getID());*/
            }
        });

        return convertView;
    }

    public void refreshList() {
        if (App.user == null) {
            Log.e(TAG, "Critical: App.user object is null");
        }
        else {
            emergencies.clear();
            emergencies.addAll(App.user.getEmergency(context));
            dbList = (ArrayList<Emergency>) emergencies.clone();
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
            ArrayList<Emergency> newList = new ArrayList<Emergency>();

            if (startDate.isEmpty()) {
                startDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
            }

            if (endDate.isEmpty()) {
                endDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
            }

            for (int i=0; i<initial_row; i++)
            {
                Log.i(TAG, "item: " + dbList.get(i).getName());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date listDate = Calendar.getInstance().getTime();
                Date sDate = null;
                Date eDate = null;
                try {
                    sDate = dateFormat.parse(startDate);
                    eDate = dateFormat.parse(endDate);
                    listDate = dateFormat.parse(dbList.get(i).getPhone());
                } catch (ParseException e) {
                    e.printStackTrace();
                    Log.e(TAG, "error parsing item: " + dbList.get(i).getDesc());
                }

                if (listDate.after(sDate) && listDate.before(eDate)) {
                    newList.add(i, dbList.get(i));
                }
            }
            emergencies.clear();
            emergencies = newList;
            Log.i(TAG, "filterList");
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return emergencies.size();
    }

    static class ViewHolder {
        TextView editTextName;
        TextView editTextPhone;
        TextView editTextRelation;
        Button buttonDelete;
    }
}



/*
package iss.nus.edu.medipalappln.adapter;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import iss.nus.edu.medipalappln.R;
import iss.nus.edu.medipalappln.medipal.App;
import iss.nus.edu.medipalappln.medipal.Emergency;
public class EmergencyListAdapter extends ArrayAdapter<Emergency> {
    private static final String  TAG = "EMERGENCYLISTADAPTER";
    private Context context;
    private List<Emergency> emergencies = new ArrayList<>();
    public EmergencyListAdapter(Context context,int resource, int textViewResourceId) {
        super(context, R.layout.emergencylist);
        this.context = context;
       // refreshMembejava.lang.Stringrs();
    }
    @Override public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.emergency_row_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.text_view_name);
            viewHolder.tvPhone=(TextView)convertView.findViewById(R.id.phonenum);
            viewHolder.tvRelation=(TextView)convertView.findViewById(R.id.relation);
            //viewHolder.btnRemove = (Button) convertView.findViewById(R.id.btn_remove);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Emergency emergency = emergencies.get(position);
        viewHolder.tvName.setText(emergency.getName());
        viewHolder.tvPhone.setText(emergency.getPhone());
        viewHolder.tvRelation.setText(emergency.getDesc());
        viewHolder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
               // App.user.removeMember(emergency.getMemberNumber());
                refreshList();
            }
        });
        return convertView;
    }
    public void refreshList() {
        if (App.user == null) {
            Log.i(TAG, "App.user is null");
        }
        else {
            emergencies.clear();
            //mergencies.addAll(App.user.getEmergency(context));
            Log.i(TAG, "refreshList");
            notifyDataSetChanged();
        }
    }
    @Override public int getCount() {
        return emergencies.size();
    }
    static class ViewHolder {
        TextView tvName;
        TextView tvRelation;
        TextView tvPhone;
        Button btnRemove;
    }
}*/