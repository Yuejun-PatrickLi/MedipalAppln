package iss.nus.edu.medipalappln.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import iss.nus.edu.medipalappln.R;
import iss.nus.edu.medipalappln.Service.RemindService;
import iss.nus.edu.medipalappln.adapter.AppointmentAdapter;
import iss.nus.edu.medipalappln.dao.AppointmentDataBaseAdapter;
import iss.nus.edu.medipalappln.medipal.Appointment;

/**
 * Created by cherry on 2017/3/21.
 */

public class AppointmentFragment extends Fragment {

    public static int Flag = 0;
    public static int fFlag = 0;

    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    public static AppointmentAdapter appointmentAdapter;
    private AppointmentDataBaseAdapter appointmentDataBaseAdapter;

    public Appointment appointment;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private AppointmentFragment.OnFragmentInteractionListener mListener;

    public AppointmentFragment() {

    }

    public static AppointmentFragment newInstance(String param1, String param2) {
        AppointmentFragment fragment = new AppointmentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointment, container, false);

        appointmentDataBaseAdapter = new AppointmentDataBaseAdapter(getActivity());

        appointmentAdapter = new AppointmentAdapter();

        refresh();

        fab = (FloatingActionButton)view.findViewById(R.id.fab_addapp);

        recyclerView = (RecyclerView)view.findViewById(R.id.rcv_app);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(appointmentAdapter);

        SystemClock.sleep(100);

        Log.d("123",AppointmentAdapter.mId.size()+"");

        if(fFlag == 0){
            for(int i = 0; i < AppointmentAdapter.mId.size();i++){
                AppointmentAdapter.flag.add(1);
            }
            fFlag = 1;
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = addAppDialog(getContext());
                dialog.show();
            }
        });



        return view;
    }

    public void refresh(){
        QueryAll queryAll = new QueryAll();
        queryAll.start();
        //view
        appointmentAdapter.notifyDataSetChanged();
    }


    public Dialog addAppDialog(final Context context){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_adddialog,null);

        final TextInputEditText textInputEditTextLocation = (TextInputEditText)view.findViewById(R.id.ed_location);

        final TextInputEditText textInputEditTextDescription = (TextInputEditText)view.findViewById(R.id.ed_description);

        final DatePicker datePicker = (DatePicker)view.findViewById(R.id.datePicker_app);

        final TimePicker timePicker = (TimePicker) view.findViewById(R.id.timePicker_app);

        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.cb_remind);

        final TextView tv1 = (TextView) view.findViewById(R.id.tv1);

        final TextView tv2 = (TextView) view.findViewById(R.id.tv2);

        final View v1 = (View) view.findViewById(R.id.view1);

        final View v2 = (View) view.findViewById(R.id.view2);

        final DatePicker datePicker1 = (DatePicker) view.findViewById(R.id.datePicker_remind);

        final TimePicker timePicker1 = (TimePicker) view.findViewById(R.id.timePicker_remind);


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

        timePicker.setIs24HourView(true);
        timePicker1.setIs24HourView(true);


        tv1.setVisibility(View.INVISIBLE);
        tv2.setVisibility(View.INVISIBLE);
        v1.setVisibility(View.INVISIBLE);
        v2.setVisibility(View.INVISIBLE);
        datePicker1.setVisibility(View.INVISIBLE);
        timePicker1.setVisibility(View.INVISIBLE);


        builder.setView(view)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Do the database operating
                        String Location = textInputEditTextLocation.getText().toString();
                        String Description = textInputEditTextDescription.getText().toString();
                        String Month;
                        String Day;
                        String Hour;
                        String Min;
                        int month = datePicker.getMonth();
                        int day = datePicker.getDayOfMonth();
                        int hour = timePicker.getHour();
                        int min = timePicker.getMinute();
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
                        if(tv1.getVisibility() == View.VISIBLE){
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

                            String Timere = Integer.toString(datePicker1.getYear()) + "-" + Monthre + "-" + Dayre + " " + Hourre + ":" + Minre + ":" + "00";
                            Date date;
                            long value = 0;
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                date = sdf.parse(Timere);
                                value = date.getTime();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Log.e("Timere=",Timere);
                            Log.e("value=",value+"");
                            long value2 = System.currentTimeMillis();
                            if(value <= value2){
                                Toast.makeText(context,"Invalid Time", Toast.LENGTH_LONG).show();
                            }
                            int delay = (int) (value - value2);
                            RemindService.addNotification(delay,"Appointment Remind", Location, Time);
                            AppointmentAdapter.flag.add(0);
                            appointment = new Appointment(Location,Time,Description);
                            AddAppointment addAppointment = new AddAppointment();
                            addAppointment.start();
                            refresh();
                            builder.create().dismiss();
                        }else{
                            AppointmentAdapter.flag.add(1);
                            appointment = new Appointment(Location,Time,Description);
                            AddAppointment addAppointment = new AddAppointment();
                            addAppointment.start();
                            refresh();
                            builder.create().dismiss();
                        }

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        builder.create().dismiss();
                    }
                });

        return builder.create();
    }

    class QueryAll extends Thread{
        @Override
        public void run(){
            try {
                appointmentDataBaseAdapter.queryAll();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class AddAppointment extends Thread{
        @Override
        public void run(){
            try {
                appointmentDataBaseAdapter.addAppointment(appointment);
                refresh();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AppointmentFragment.OnFragmentInteractionListener) {
            mListener = (AppointmentFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
