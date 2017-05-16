package iss.nus.edu.medipalappln.adapter;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import iss.nus.edu.medipalappln.R;
import iss.nus.edu.medipalappln.Service.NotificationCancelReceiver;
import iss.nus.edu.medipalappln.activity.UpdateMedicineActivity;
import iss.nus.edu.medipalappln.medipal.App;
import iss.nus.edu.medipalappln.medipal.Category;
import iss.nus.edu.medipalappln.medipal.Medicine;


/**
 * Created by Niv on 8/6/2016.
 */
public class MedicineListAdapter extends ArrayAdapter<Medicine> {
    private Context context;
    private List<Medicine> medicines = new ArrayList<>();
    Category category;
    int medId = 0;
    NotificationManager notificationManager;


    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    public MedicineListAdapter(Context context) {
        super(context, R.layout.medicine_row_layout);
        this.context = context;
        refreshMembers();
    }

    @Override public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.medicine_row_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvMedId = (TextView) convertView.findViewById(R.id.tv_medId);
            viewHolder.tvMedName = (TextView) convertView.findViewById(R.id.tv_medName);
            viewHolder.tvMedDesc = (TextView) convertView.findViewById(R.id.tv_medDesc);
            viewHolder.tvMedCat = (TextView) convertView.findViewById(R.id.tv_medCode);
            viewHolder.tvMedReminder = (TextView) convertView.findViewById(R.id.tv_medRem);
            viewHolder.btnMedUpdate = (Button) convertView.findViewById(R.id.btn_med_update);
            viewHolder.btnMedRemove = (Button) convertView.findViewById(R.id.btn_med_delete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Medicine medicine = medicines.get(position);

        final Medicine m1 = App.user.getMember(medicine.getMedId(), context);

        viewHolder.tvMedName.setText(m1.getMedName().toString());
        viewHolder.tvMedDesc.setText(m1.getMedDesc().toString());
        category = App.user.getFacility(medicine.getCatId(), getContext());

        viewHolder.tvMedCat.setText(category.getName());
        viewHolder.tvMedReminder.setText("Reminder Flag: "+m1.getRemindFlag());

        viewHolder.btnMedRemove.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Log.d("Delete", ""+m1.getRemindId());
                deleteNotification(m1.getRemindId());

                App.user.removeMember(medicine, getContext());
                refreshMembers();
                Toast toast = Toast.makeText(context, "Medicine is deleted", Toast.LENGTH_LONG);
                toast.show();



            }
        });


        viewHolder.btnMedUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateMedicineActivity.class);
                intent.putExtra("medId", m1.getMedId());
                context.startActivity(intent);
                refreshMembers();
            }
        });


        return convertView;
    }

    public void refreshMembers() {
        medicines.clear();
        medicines.addAll(App.user.getMedicines(this.context));
        notifyDataSetChanged();
    }

    @Override public int getCount() {
        return medicines.size();
    }

    static class ViewHolder {
        TextView tvMedId, tvMedName, tvMedDesc, tvMedCat, tvMedReminder, tvMedQuantity, tvMedDosage, tvMedThreshold, tvMedDateIssued, tvMedExpireFac;
        Button btnMedUpdate, btnMedRemove;
    }

    public void deleteNotification(Integer id) {
       /* NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        notificationManager.notify(id, mBuilder.build());
*/
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(context, NotificationCancelReceiver.class);
        intent.putExtra("ID", id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0);

        alarmManager.set(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 60 * 60 * 1000, pendingIntent);
    }
}
