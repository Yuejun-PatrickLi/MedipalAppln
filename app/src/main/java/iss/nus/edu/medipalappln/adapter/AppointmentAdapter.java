package iss.nus.edu.medipalappln.adapter;

import android.app.NotificationManager;
import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import iss.nus.edu.medipalappln.R;
import iss.nus.edu.medipalappln.Service.RemindService;
import iss.nus.edu.medipalappln.activity.Welcome;
import iss.nus.edu.medipalappln.dao.AppointmentDataBaseAdapter;
import iss.nus.edu.medipalappln.fragment.AppointmentFragment;
import iss.nus.edu.medipalappln.medipal.Appointment;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by cherry on 2017/3/21.
 */

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder>{
    public static ArrayList<Integer> mId = new ArrayList<Integer>();
    public static ArrayList<String> mLocation = new ArrayList<String>();
    public static ArrayList<String> mDescription = new ArrayList<String>();
    public static ArrayList<String> mTime = new ArrayList<String>();
    public static ArrayList<Integer> flag = new ArrayList<Integer>();

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public View rootview;
        public TextView locationView;
        public TextView timeView;
        public ViewGroup mParent;


        public ViewHolder(View itemView, ViewGroup parent) {
            super(itemView);
            mParent = parent;
            locationView = (TextView) itemView.findViewById(R.id.tv_location);
            timeView = (TextView) itemView.findViewById(R.id.tv_time);
            rootview =  itemView.findViewById(R.id.rcv_ll_appointment);
        }
    }

    public AppointmentAdapter(){

    }


    @Override
    public AppointmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.d("OnCreateViewHolder:", viewType+"");

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rcv_appointment,null);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        return new ViewHolder(view, parent);
    }

    @Override
    public void onBindViewHolder(AppointmentAdapter.ViewHolder holder, final int position) {

        Log.d("OnBindViewHolder", ""+position);

        final ViewHolder mHolder = (ViewHolder) holder;

        mHolder.rootview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(mHolder.mParent.getContext());

                View view = LayoutInflater.from(mHolder.mParent.getContext()).inflate(R.layout.dialog_detail,null);

                TextView tv_location = (TextView)view.findViewById(R.id.tv_view_location);

                TextView tv_time = (TextView)view.findViewById(R.id.tv_view_time);

                TextView tv_des = (TextView)view.findViewById(R.id.tv_view_des);

                tv_location.setText(mLocation.get(position));

                tv_time.setText(mTime.get(position));

                tv_des.setText(mDescription.get(position));

                builder.setView(view)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                AppointmentDataBaseAdapter appointmentDataBaseAdapter = new AppointmentDataBaseAdapter(mHolder.mParent.getContext());
                                appointmentDataBaseAdapter.deleteAppointment(mId.get(position));
                                appointmentDataBaseAdapter.queryAll();
                                AppointmentFragment.appointmentAdapter.notifyDataSetChanged();
                                builder.create().dismiss();
                            }
                        })
                        .setNeutralButton("Modify", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                builder.create().dismiss();

                                final AlertDialog.Builder builder2 = new AlertDialog.Builder(mHolder.mParent.getContext());

                                View view2 = LayoutInflater.from(mHolder.mParent.getContext()).inflate(R.layout.dialog_adddialog,null);

                                TextView tv_title = (TextView) view2.findViewById(R.id.tv_title);

                                tv_title.setText("Modify Appointment");

                                final TextInputEditText textInputEditTextLocation = (TextInputEditText)view2.findViewById(R.id.ed_location);

                                final TextInputEditText textInputEditTextDescription = (TextInputEditText)view2.findViewById(R.id.ed_description);

                                final DatePicker datePicker = (DatePicker)view2.findViewById(R.id.datePicker_app);

                                final TimePicker timePicker = (TimePicker) view2.findViewById(R.id.timePicker_app);

                                final CheckBox checkBox = (CheckBox) view2.findViewById(R.id.cb_remind);

                                final TextView tv1 = (TextView) view2.findViewById(R.id.tv1);

                                final TextView tv2 = (TextView) view2.findViewById(R.id.tv2);

                                final TextView tv3 = (TextView) view2.findViewById(R.id.tv_cbti);

                                final View v1 = (View) view2.findViewById(R.id.view1);

                                final View v2 = (View) view2.findViewById(R.id.view2);

                                final View v3 = (View) view2.findViewById(R.id.v_cbline);

                                final DatePicker datePicker1 = (DatePicker) view2.findViewById(R.id.datePicker_remind);

                                final TimePicker timePicker1 = (TimePicker) view2.findViewById(R.id.timePicker_remind);

                                if(flag.get(position) == 0){
                                    checkBox.setVisibility(View.INVISIBLE);
                                    tv3.setVisibility(View.INVISIBLE);
                                    v3.setVisibility(View.INVISIBLE);
                                }else{
                                    checkBox.setVisibility(View.VISIBLE);
                                }
                                if(checkBox.getVisibility() == View.VISIBLE){
                                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                            if(isChecked){
                                                tv1.setVisibility(View.VISIBLE);
                                                tv2.setVisibility(View.VISIBLE);
                                                v1.setVisibility(View.VISIBLE);
                                                v2.setVisibility(View.VISIBLE);
                                                datePicker1.setVisibility(View.VISIBLE);
                                                timePicker1.setVisibility(View.VISIBLE);
                                            }else{
                                                tv1.setVisibility(View.INVISIBLE);
                                                tv2.setVisibility(View.INVISIBLE);
                                                v1.setVisibility(View.INVISIBLE);
                                                v2.setVisibility(View.INVISIBLE);
                                                datePicker1.setVisibility(View.INVISIBLE);
                                                timePicker1.setVisibility(View.INVISIBLE);
                                            }
                                        }
                                    });
                                }
                                timePicker.setIs24HourView(true);

                                timePicker1.setIs24HourView(true);

                                tv1.setVisibility(View.INVISIBLE);
                                tv2.setVisibility(View.INVISIBLE);
                                v1.setVisibility(View.INVISIBLE);
                                v2.setVisibility(View.INVISIBLE);
                                datePicker1.setVisibility(View.INVISIBLE);
                                timePicker1.setVisibility(View.INVISIBLE);

                                builder2.setView(view2)
                                        .setPositiveButton("Modify", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int id) {
                                                // Do the database operating
                                                String Location = textInputEditTextLocation.getText().toString();
                                                String Description = textInputEditTextDescription.getText().toString();
                                                String Month;
                                                String Day;
                                                String Hour;
                                                String Min;
                                                int month = datePicker1.getMonth();
                                                int day = datePicker1.getDayOfMonth();
                                                int hour = timePicker1.getHour();
                                                int min = timePicker1.getMinute();
                                                if(month < 10){
                                                    Month = "0" + Integer.toString(month+1);
                                                }else{
                                                    Month = Integer.toString(month+1);
                                                }

                                                if(day < 10){
                                                    Day = "0" + Integer.toString(day);
                                                }else{
                                                    Day = Integer.toString(day);
                                                }

                                                if(hour < 10){
                                                    Hour = "0" + Integer.toString(hour);
                                                }else{
                                                    Hour = Integer.toString(hour);
                                                }

                                                if(min < 10){
                                                    Min = "0" + Integer.toString(min);
                                                }else{
                                                    Min = Integer.toString(min);
                                                }

                                                String Time = Integer.toString(datePicker.getYear()) + "-" + Month + "-" + Day + " " + Hour + ":" + Min;
                                                Log.d("Record", Location + Description + Time);

                                                if(tv1.getVisibility() == View.VISIBLE && checkBox.getVisibility() == View.VISIBLE){
                                                    String Monthre;
                                                    String Dayre;
                                                    String Hourre;
                                                    String Minre;
                                                    int monthre = datePicker1.getMonth();
                                                    int dayre = datePicker1.getDayOfMonth();
                                                    int hourre = timePicker1.getHour();
                                                    int minre = timePicker1.getMinute();
                                                    if(monthre < 10){
                                                        Monthre = "0" + Integer.toString(monthre+1);
                                                    }else{
                                                        Monthre = Integer.toString(monthre+1);
                                                    }

                                                    if(dayre < 10){
                                                        Dayre = "0" + Integer.toString(dayre);
                                                    }else{
                                                        Dayre = Integer.toString(dayre);
                                                    }

                                                    if(hourre < 10){
                                                        Hourre = "0" + Integer.toString(hourre);
                                                    }else{
                                                        Hourre = Integer.toString(hourre);
                                                    }

                                                    if(minre < 10){
                                                        Minre = "0" + Integer.toString(minre);
                                                    }else{
                                                        Minre = Integer.toString(minre);
                                                    }

                                                    String Timere = Integer.toString(datePicker1.getYear()) + "-" + Monthre + "-" + Dayre + " " + Hourre + ":" + Minre + ":" +"00";
                                                    Date date;
                                                    long value = 0;
                                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                    try {
                                                        date = sdf.parse(Timere);
                                                        value = date.getTime();
                                                        System.out.println("Time Now:"+value);
                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }
                                                    long value2 = System.currentTimeMillis();
                                                    if(value <= value2){
                                                        Toast.makeText(Welcome.getContext(),"Invalid Time", Toast.LENGTH_LONG).show();
                                                    }
                                                    int delay = (int) (value - value2);
                                                    RemindService.addNotification(delay,"Appointment Remind", Location, Time);
                                                    AppointmentAdapter.flag.set(position,0);
                                                    Appointment appointment = new Appointment(Location,Time,Description);
                                                    AppointmentDataBaseAdapter appointmentDataBaseAdapter = new AppointmentDataBaseAdapter(mHolder.mParent.getContext());
                                                    appointmentDataBaseAdapter.updateAppointment(appointment,mId.get(position));
                                                    appointmentDataBaseAdapter.queryAll();
                                                    AppointmentFragment.appointmentAdapter.notifyDataSetChanged();
                                                    builder.create().dismiss();
                                                }else{
                                                    AppointmentAdapter.flag.set(position,1);
                                                    Appointment appointment = new Appointment(Location,Time,Description);
                                                    AppointmentDataBaseAdapter appointmentDataBaseAdapter = new AppointmentDataBaseAdapter(mHolder.mParent.getContext());
                                                    appointmentDataBaseAdapter.updateAppointment(appointment,mId.get(position));
                                                    appointmentDataBaseAdapter.queryAll();
                                                    AppointmentFragment.appointmentAdapter.notifyDataSetChanged();
                                                    builder.create().dismiss();
                                                }

                                            }
                                        })
                                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                builder.create().dismiss();
                                            }
                                        });
                                builder2.create().show();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                builder.create().dismiss();
                            }
                        });
                builder.create().show();
            }
        });

        mHolder.locationView.setText(mLocation.get(position));
        mHolder.timeView.setText(mTime.get(position));
    }

    @Override
    public int getItemCount() {
        if (mTime.isEmpty()) {
            return 0;
        }else{
            return mTime.size();
        }
    }
}
