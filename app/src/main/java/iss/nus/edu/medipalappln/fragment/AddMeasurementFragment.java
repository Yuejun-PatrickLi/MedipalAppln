package iss.nus.edu.medipalappln.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import iss.nus.edu.medipalappln.R;
import iss.nus.edu.medipalappln.medipal.App;

public class AddMeasurementFragment extends Fragment {

    private static final String TAG = "AddMeasurementFragment";
    private Calendar calendar;
    private CheckBox checkBoxBP, checkBoxPulse, checkBoxWeight, checkBoxTemperature;
    private TextView dateView, timeView;
    private EditText editTextSystolic, editTextDiastolic,
            editTextPulse,
            editTextWeight,
            editTextTemperature;
    private Button btn_calendar, btn_time, btn_add, btn_cancel;
    boolean isBp = false, isPulse = false, isWeight = false, isTemperature = false;
    private OnFragmentInteractionListener mListener;

    public AddMeasurementFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_add_measurement, container, false);

        int year, month, day;
        editTextSystolic = (EditText) view.findViewById(R.id.edit_text_systolic);
        editTextDiastolic = (EditText) view.findViewById(R.id.edit_text_diastolic);
        editTextPulse = (EditText) view.findViewById(R.id.edit_text_pulse);
        editTextWeight = (EditText) view.findViewById(R.id.edit_text_weight);
        editTextTemperature = (EditText) view.findViewById(R.id.edit_text_temperature);
        dateView = (TextView) view.findViewById(R.id.text_view_measured_on);
        timeView = (TextView) view.findViewById(R.id.text_view_measured_on_time);
        btn_add = (Button) view.findViewById(R.id.btn_add);
        btn_calendar = (Button) view.findViewById(R.id.btn_calendar);
        btn_time = (Button) view.findViewById(R.id.btn_time);
        btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        checkBoxBP = (CheckBox) view.findViewById(R.id.chkbox_option_bp);
        checkBoxPulse = (CheckBox) view.findViewById(R.id.chkbox_option_pulse);
        checkBoxWeight = (CheckBox) view.findViewById(R.id.chkbox_option_weight);
        checkBoxTemperature = (CheckBox) view.findViewById(R.id.chkbox_option_temp);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        dateView.setText(year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day));
        timeView.setText("00:00");

        checkBoxBP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    editTextSystolic.setVisibility(View.VISIBLE);
                    editTextDiastolic.setVisibility(View.VISIBLE);
                    isBp = true;
                }
                else {
                    editTextSystolic.setVisibility(View.GONE);
                    editTextDiastolic.setVisibility(View.GONE);
                    isBp = false;
                }
            }
        });

        checkBoxPulse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    editTextPulse.setVisibility(View.VISIBLE);
                    isPulse = true;
                }
                else {
                    editTextPulse.setVisibility(View.GONE);
                    isPulse = false;
                }
            }
        });

        checkBoxTemperature.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    editTextTemperature.setVisibility(View.VISIBLE);
                    isTemperature = true;
                }
                else {
                    editTextTemperature.setVisibility(View.GONE);
                    isTemperature = false;
                }
            }
        });

        checkBoxWeight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    editTextWeight.setVisibility(View.VISIBLE);
                    isWeight = true;
                }
                else {
                    editTextWeight.setVisibility(View.GONE);
                    isWeight = false;
                }
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValidBP() ) {
                    Integer systolic = Integer.parseInt(editTextSystolic.getText().toString());
                    Integer diastolic = Integer.parseInt(editTextDiastolic.getText().toString());
                    String dateTime = new StringBuilder(dateView.getText().toString().trim() + " " +
                                        timeView.getText().toString().trim()).toString();

                    App.user.addBloodPressure(getContext(), systolic, diastolic, dateTime);
                    Toast.makeText(getContext(), getString(R.string.save_success),
                            Toast.LENGTH_SHORT).show();

                    returnToShowAllMeasurementFragment();
                }

                if (isValidPulse() ) {
                    Integer pulse = Integer.parseInt(editTextPulse.getText().toString());
                    String dateTime = new StringBuilder(dateView.getText().toString().trim() + " " +
                            timeView.getText().toString().trim()).toString();

                    App.user.addPulse(getContext(), pulse, dateTime);
                    Toast.makeText(getContext(), getString(R.string.save_success),
                            Toast.LENGTH_SHORT).show();

                    returnToShowAllMeasurementFragment();
                }

                if (isValidTemperature() ) {
                    Double temp = Double.parseDouble(editTextTemperature.getText().toString());
                    String dateTime = new StringBuilder(dateView.getText().toString().trim() + " " +
                            timeView.getText().toString().trim()).toString();

                    App.user.addTemperature(getContext(), temp, dateTime);
                    Toast.makeText(getContext(), getString(R.string.save_success),
                            Toast.LENGTH_SHORT).show();

                    returnToShowAllMeasurementFragment();
                }

                if (isValidWeight() ) {
                    Integer weight = Integer.parseInt(editTextWeight.getText().toString());
                    String dateTime = new StringBuilder(dateView.getText().toString().trim() + " " +
                            timeView.getText().toString().trim()).toString();

                    App.user.addWeight(getContext(), weight, dateTime);
                    Toast.makeText(getContext(), getString(R.string.save_success),
                            Toast.LENGTH_SHORT).show();

                    returnToShowAllMeasurementFragment();

                }

                //getActivity().finish();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowAllMeasurementFragment fragment = new ShowAllMeasurementFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flContent, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final DatePicker datePicker = new DatePicker(getContext());
                datePicker.setCalendarViewShown(false);

                builder.setTitle("Measurement date");
                builder.setView(datePicker);
                builder.setNegativeButton("Cancel", null);
                builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String year, month, day;
                        year = String.format("%04d", datePicker.getYear());
                        month = String.format("%02d", datePicker.getMonth()+1); //index begin from 0
                        day = String.format("%02d", datePicker.getDayOfMonth());

                        dateView.setText(year + "-" + month + "-" + day);
                    }
                });

                builder.show();
            }
        });

        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final TimePicker timePicker = new TimePicker(getContext());
                timePicker.setIs24HourView(true);

                builder.setTitle("Measurement time");
                builder.setView(timePicker);
                builder.setNegativeButton("Cancel", null);
                builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String hour, min;
                        hour = String.format("%02d", timePicker.getHour());
                        min = String.format("%02d", timePicker.getMinute());

                        timeView.setText(hour + ":" + min);
                    }
                });

                builder.show();
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private boolean isValidBP() {
        boolean isValid = true;

        if (!isBp)
            isValid = false;

        if (!TextUtils.isDigitsOnly(editTextSystolic.getText()) ||
                (editTextSystolic.getText().toString().trim().isEmpty())) {
            editTextSystolic.setError(getString(R.string.error_invalid_data));
            isValid = false;
        }
        if (!TextUtils.isDigitsOnly(editTextDiastolic.getText()) ||
                (editTextDiastolic.getText().toString().trim().isEmpty())) {
            editTextDiastolic.setError(getString(R.string.error_invalid_data));
            isValid = false;
        }

        return isValid;
    }

    private boolean isValidPulse() {
        boolean isValid = true;

        if (!isPulse)
            isValid = false;

        if (!TextUtils.isDigitsOnly(editTextPulse.getText())) {
            editTextPulse.setError(getString(R.string.error_invalid_data));
            isValid = false;
        }

        return isValid;
    }

    private boolean isValidTemperature() {
        boolean isValid = true;

        if (!isTemperature)
            isValid = false;

        if (editTextTemperature.getText().toString().trim().isEmpty()){
            editTextTemperature.setError(getString(R.string.error_invalid_data));
            isValid = false;
        }

        return isValid;
    }

    private boolean isValidWeight() {
        boolean isValid = true;

        if (!isWeight)
            isValid = false;

        if (!TextUtils.isDigitsOnly(editTextWeight.getText())) {
            editTextWeight.setError(getString(R.string.error_invalid_data));
            isValid = false;
        }

        return isValid;
    }

    public void returnToShowAllMeasurementFragment() {
        ShowAllMeasurementFragment fragment = new ShowAllMeasurementFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContent, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
