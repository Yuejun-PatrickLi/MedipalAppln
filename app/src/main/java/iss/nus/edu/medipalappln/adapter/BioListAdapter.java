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
import iss.nus.edu.medipalappln.medipal.Personal;

public class BioListAdapter extends ArrayAdapter<Personal> {
    private static final String  TAG = "BioListAdapter";

    private Context context;
    private List<Personal> formDatas = new ArrayList<>();

    public BioListAdapter(Context context,int resource, int textViewResourceId) {
        super(context, R.layout.biolistlayout);
        this.context = context;
        // refreshMembejava.lang.Stringrs();
    }

    @Override public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.biolistlayout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewBlood = (TextView) convertView.findViewById(R.id.blood);
            viewHolder.textViewAddress = (TextView) convertView.findViewById(R.id.address);
            viewHolder.textViewDob = (TextView) convertView.findViewById(R.id.Dob);
            viewHolder.textViewName = (TextView) convertView.findViewById(R.id.text_view_measured_on);
            viewHolder.textViewWeight = (TextView) convertView.findViewById(R.id.weight);
            viewHolder.textViewHeight = (TextView) convertView.findViewById(R.id.height);
            viewHolder.textViewPincode = (TextView) convertView.findViewById(R.id.postalcode);
            viewHolder.textViewPhone = (TextView) convertView.findViewById(R.id.phone);
            viewHolder.buttonUpdate = (Button) convertView.findViewById(R.id.btn_remove);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Personal formData = formDatas.get(position);

        viewHolder.textViewAddress.setText(formData.getAddress());
        viewHolder.textViewBlood.setText(formData.getBloodtype());
        viewHolder.textViewPincode.setText(formData.getPostcode());
        viewHolder.textViewPhone.setText(formData.getPhone());
        viewHolder.textViewWeight.setText(formData.getHeight());
        viewHolder.textViewDob.setText(formData.getDob());
        viewHolder.textViewHeight.setText(formData.getHeight());
        viewHolder.textViewName.setText(formData.getName());

        viewHolder.buttonUpdate.setOnClickListener(new View.OnClickListener() {
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
            formDatas.clear();
            //formDatas.addAll(App.user.getPersonal(context));
            Log.i(TAG, "refreshList");
            notifyDataSetChanged();
        }
    }

    @Override public int getCount() {
        return formDatas.size();
    }

    static class ViewHolder {
        TextView textViewPhone;
        TextView textViewBlood;
        TextView textViewDob;
        TextView textViewHeight;
        TextView textViewWeight;
        TextView textViewAddress;
        TextView textViewPincode;
        TextView textViewName;


        Button buttonUpdate;
    }
}