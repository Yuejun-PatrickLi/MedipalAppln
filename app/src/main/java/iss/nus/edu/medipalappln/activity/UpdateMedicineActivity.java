package iss.nus.edu.medipalappln.activity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import iss.nus.edu.medipalappln.R;
import iss.nus.edu.medipalappln.Service.NotificationCancelReceiver;
import iss.nus.edu.medipalappln.Service.NotificationReceiver;
import iss.nus.edu.medipalappln.medipal.App;
import iss.nus.edu.medipalappln.medipal.Category;
import iss.nus.edu.medipalappln.medipal.Medicine;
import iss.nus.edu.medipalappln.medipal.Reminder;

/**
 * Created by niv on 3/21/2017.
 */

public class UpdateMedicineActivity extends AppCompatActivity {

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
    private static final SimpleDateFormat formatter = new SimpleDateFormat("d-MMM-yyyy H:mm", Locale.ENGLISH);
    private SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a", Locale.getDefault());

    Calendar currentCal = Calendar.getInstance();
    Calendar selectedDate = Calendar.getInstance();


    EditText editName, editDescription, editCategory, editRemind, editQuantity, editDosage, editThreshold, editDateIssued,
            editExpiryFactor, etReminderFreq, etReminderInterval, txtStTime;
    Button updateBtn;
    int quantity, dosage, threshold, expiryFactor;
    int catId, remID;
    Context context;
    List<Category> categoryList = null;
    Spinner spnFacility;
    Switch reminderToggle;
    //String remindFlag = "FALSE";
    LinearLayout remindLayout, remindContentLayout;
    Reminder r1, r2, r3;
    int remId = 0;
    String remindFlag = "FALSE";
    boolean toSetReminder = false;
    boolean category = false;
    String categorySelected;
    Category selectedCategory;

    NotificationManager notificationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_medicine);

        getLayoutInflater().inflate(R.layout.fragment_medicine, null);

        reminderToggle = (Switch) findViewById(R.id.rem_upd_switch1);

        remindLayout = (LinearLayout) findViewById(R.id.rem_switch_layout2);
        remindContentLayout = (LinearLayout) findViewById(R.id.reminder_content_update_layout);

        remindLayout.setVisibility(View.GONE);
        remindContentLayout.setVisibility(View.GONE);

        spnFacility = (Spinner) findViewById(R.id.spn_facility_1);

        editName = (EditText) findViewById(R.id.et_medName);
        editDescription = (EditText) findViewById(R.id.et_medDesc);

        editQuantity = (EditText) findViewById(R.id.et_quantity_upd);
        editDosage = (EditText) findViewById(R.id.et_medDosage_upd);
        editThreshold = (EditText) findViewById(R.id.et_medThreshold_upd);

        editExpiryFactor = (EditText) findViewById(R.id.et_medExpiryFactor_upd);

        etReminderFreq = (EditText) findViewById(R.id.et_remind_freq_update);
        etReminderInterval = (EditText) findViewById(R.id.et_remindInterval_update);

        txtStTime = (EditText) findViewById(R.id.select_start_time_update);
        editDateIssued = (EditText) findViewById(R.id.et_medDateIssued_upd);

        Intent intent = getIntent();
        final int medId = intent.getExtras().getInt("medId");
        Log.d("Update", String.valueOf(medId));

        final Medicine m1 = App.user.getMember(medId, context);

        Category c1 = App.user.getFacility(m1.getCatId(), context);
        catId = c1.getCatId();

        categoryList = App.user.getFacilities(context);
        categoryList.remove(c1);

        final List<String> spnFacList = new ArrayList<>();

        spnFacList.add(c1.getName());
        for (Category category : categoryList) {
            spnFacList.add(category.getName());
        }
        final ArrayAdapter<String> spnFacAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spnFacList);
        spnFacility.setAdapter(spnFacAdapter);

        if (c1.getReminder().equalsIgnoreCase("REMINDER ENABLED") && m1.getRemindId() != 0) {
            r1 = App.user.getReminder(m1.getRemindId(), context);
            etReminderFreq.setText(r1.getFrequency());
            etReminderInterval.setText(r1.getInterval());
            txtStTime.setText(timeFormatter.format(r1.getStartTime()).toString());

            reminderToggle.setText("Reminder ON", null);
            reminderToggle.setChecked(true);
            remindLayout.setVisibility(View.VISIBLE);
            remindContentLayout.setVisibility(View.VISIBLE);
            selectedCategory = c1;
            category = true;
            deleteNotification(r1.getRemindId());
        } else if (c1.getReminder().equalsIgnoreCase("REMINDER ENABLED") && m1.getRemindId() == 0) {
            reminderToggle.setChecked(false);
            remindLayout.setVisibility(View.VISIBLE);
            toSetReminder = true;
            selectedCategory = c1;
            category = true;
        }

        spnFacility.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categorySelected = (String) parent.getSelectedItem();
                Log.d("Niv1", categorySelected);

                for (Category c : categoryList) {
                    if (c.getName() == categorySelected) {
                        selectedCategory = c;
                        if (c.getReminder().equalsIgnoreCase("REMINDER ENABLED")) {
                            remindLayout.setVisibility(View.VISIBLE);
                            reminderToggle.setText("Reminder ON", null);
                            //toSetReminder = true;
                            category = true;
                            r2 = new Reminder();
                        } else {
                            remindLayout.setVisibility(View.GONE);
                            reminderToggle.setText("Reminder OFF", null);
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                category = false;
            }
        });

        reminderToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && toSetReminder) {
                    Log.d("Niv_view", "checked");
                    reminderToggle.setText("Reminder ON", null);
                    remindContentLayout.setVisibility(View.VISIBLE);

                } else if (isChecked && category) {
                    reminderToggle.setText("Reminder ON", null);
                    remindContentLayout.setVisibility(View.VISIBLE);
                } else {
                    reminderToggle.setText("Reminder OFF", null);
                    remindContentLayout.setVisibility(View.GONE);
                }
            }
        });

        Calendar timeCalendar = Calendar.getInstance();
        timeCalendar.setTime(currentCal.getTime());
        timeCalendar.set(Calendar.HOUR, currentCal.get(Calendar.HOUR));
        timeCalendar.set(Calendar.MINUTE, currentCal.get(Calendar.MINUTE));
        timeCalendar.set(Calendar.AM_PM, currentCal.get(Calendar.AM_PM));
        timeCalendar.add(Calendar.HOUR, 1);

        View.OnClickListener timeClickListener = new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final EditText editText = (EditText) v;
                TimePickerDialog.OnTimeSetListener timeSetListener =
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                                        calendar.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
                                editText.setText(timeFormatter.format(calendar.getTime()));
                            }
                        };
                Calendar timeCalendar = Calendar.getInstance();
                try {
                    timeCalendar.setTime(timeFormatter.parse(editText.getText().toString()));
                } catch (ParseException e) {
                    Toast.makeText(UpdateMedicineActivity.this, R.string.generic_error, Toast.LENGTH_SHORT)
                            .show();
                }
                TimePickerDialog timePickerDialog =
                        new TimePickerDialog(UpdateMedicineActivity.this, timeSetListener,
                                timeCalendar.get(Calendar.HOUR_OF_DAY), timeCalendar.get(Calendar.MINUTE), false);
                timePickerDialog.show();
            }
        };

        txtStTime.setOnClickListener(timeClickListener);

        editDateIssued.setText(dateFormatter.format(m1.getDateIssued().getTime()));

        editDateIssued.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener onDateSetListener =
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                selectedDate = calendar;
                                editDateIssued.setText(dateFormatter.format(calendar.getTime()));
                            }
                        };
                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(UpdateMedicineActivity.this, onDateSetListener,
                                currentCal.get(Calendar.YEAR), currentCal.get(Calendar.MONTH),
                                currentCal.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        editName.setText(m1.getMedName());
        editDescription.setText(m1.getMedDesc());
        Log.d("Update", m1.getMedName());
        Log.d("Update", String.valueOf(m1.getDosage()));

        try {
            editQuantity.setText("" + m1.getQuantity());
            editDosage.setText("" + m1.getDosage());
            editThreshold.setText("" + m1.getThreshold());
            editExpiryFactor.setText("" + m1.getExpireFactor());
            Log.d("Update", String.valueOf(m1.getExpireFactor()));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        updateBtn = (Button) findViewById(R.id.btn_med_update);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    quantity = Integer.parseInt(editQuantity.getText().toString());
                    dosage = Integer.parseInt(editDosage.getText().toString());
                    threshold = Integer.parseInt(editThreshold.getText().toString());
                    expiryFactor = Integer.parseInt(editExpiryFactor.getText().toString());

                    Calendar selectedEtTime = Calendar.getInstance();
                    try {
                        selectedEtTime.setTime(timeFormatter.parse(txtStTime.getText().toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }


                Calendar selectedStTime = Calendar.getInstance();
                try {
                    selectedStTime.setTime(dateFormatter.parse(editDateIssued.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                etReminderFreq = (EditText) findViewById(R.id.et_remind_freq_update);
                etReminderInterval = (EditText) findViewById(R.id.et_remindInterval_update);


                if (reminderToggle.isChecked() && category) {

                    Calendar selectedRemindTime = Calendar.getInstance();
                    try {
                        selectedRemindTime.setTime(timeFormatter.parse(txtStTime.getText().toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    r3 = new Reminder();
                    r3.setStartTime(selectedRemindTime.getTime());
                    r3.setFrequency(etReminderFreq.getText().toString());
                    r3.setInterval(etReminderInterval.getText().toString());
                    remindFlag = "TRUE";

                    if (isValidReminder()) {

                        r3.setRemindId(App.user.addReminder(etReminderFreq.getText().toString(), selectedRemindTime.getTime(),
                                etReminderInterval.getText().toString(), getApplicationContext()));


                      //  Log.d("Reminder ID", ""+r1.getRemindId());
                        createNotificationMessage(r3.getRemindId(),
                                "Reminder: Please take your medication");
                        Toast.makeText(UpdateMedicineActivity.this, "Reminder updated"+r3.getRemindId(),
                                Toast.LENGTH_SHORT).show();
                    }

                    if (isValidMedicine()) {

                        Medicine m = new Medicine(medId, editName.getText().toString(), editDescription.getText().toString(),
                                selectedCategory.getCatId(), r3.getRemindId(), remindFlag,
                                quantity, dosage, threshold,
                                selectedStTime.getTime(), expiryFactor);

                        App.user.updateMember(m, getApplicationContext());

                        Toast.makeText(UpdateMedicineActivity.this, getString(R.string.save_successful),
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }

                } else {
                    remindFlag = "FALSE";

                    if (isValidMedicine()) {
                        Medicine m = new Medicine(medId, editName.getText().toString(), editDescription.getText().toString(),
                                m1.getCatId(), 0, remindFlag,
                                quantity, dosage, threshold,
                                selectedStTime.getTime(), expiryFactor);

                        //deleteNotification(m1.getRemindId());
                        App.user.updateMember(m, getApplicationContext());

                        Toast.makeText(UpdateMedicineActivity.this, getString(R.string.save_successful),
                                Toast.LENGTH_SHORT).show();
                        finish();


                    }
                }

            }
        });
    }


    private boolean isValidMedicine() {
        boolean isValid = true;
        if (TextUtils.isEmpty(editName.getText().toString().trim())) {
            editName.requestFocus();
            editName.setError("Please fill in medicine name");
            isValid = false;
        }

        if (TextUtils.isEmpty(editDescription.getText().toString().trim())) {
            editDescription.requestFocus();
            editDescription.setError("Please fill in medicine description");
            isValid = false;
        }

        if (TextUtils.isEmpty(editDateIssued.getText().toString().trim())) {
            editDateIssued.requestFocus();
            editDateIssued.setError("Please set date issued");
            isValid = false;
        }

        if (TextUtils.isEmpty(editQuantity.getText().toString().trim())) {
            editQuantity.requestFocus();
            editQuantity.setError("Please fill in quantity");
            isValid = false;
        }

        if (TextUtils.isEmpty(editDosage.getText().toString().trim())) {
            editDosage.requestFocus();
            editDosage.setError("Please fill in dosage");
            isValid = false;
        }

        if (TextUtils.isEmpty(editThreshold.getText().toString().trim())) {
            editThreshold.requestFocus();
            editThreshold.setError("Please fill in threshold");
            isValid = false;
        }
        if (TextUtils.isEmpty(editExpiryFactor.getText().toString().trim())) {
            editExpiryFactor.requestFocus();
            editExpiryFactor.setError("Please fill in expiry factor");
            isValid = false;
        }
        return isValid;
    }


    private boolean isValidReminder() {
        boolean isValid = true;
        if (TextUtils.isEmpty(etReminderFreq.getText().toString().trim())) {
            etReminderFreq.requestFocus();
            etReminderFreq.setError("Please fill in reminder frequency");
            isValid = false;
        }

        if (TextUtils.isEmpty(etReminderInterval.getText().toString().trim())) {
            etReminderInterval.requestFocus();
            etReminderInterval.setError("Please fill in reminder interval");
            isValid = false;
        }
        return isValid;
    }

    public void deleteNotification(Integer id) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationCancelReceiver.class);
        intent.putExtra("ID", id);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0);

        alarmManager.set(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 60 * 60 * 1000, pendingIntent);


    }


    public void createNotificationMessage(int notificationId, String message) {
        Calendar selStartDate = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        Intent intent = new Intent(this, NotificationReceiver.class);

        //notificationId = Integer.parseInt(editTextAppID.getText().toString());
        intent.putExtra("ID", notificationId);

        intent.putExtra("MESSAGE", "You have appointment at _xxx_");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, notificationId, intent, 0);

        //calendar.set(year, month, day, hour, minutes); //to obtain date/time from user input
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //SimpleDateFormat df = new SimpleDateFormat("dd")
        SimpleDateFormat df = new SimpleDateFormat("hh:mm");

//      Log.d("TIME-----",strtime);
//      Date remindTime = df.parse(strtime);
        alarmManager.set(AlarmManager.RTC_WAKEUP, selStartDate.getTime().getTime(), pendingIntent);


        Log.i("Reminder", "ID:" + notificationId);
        Log.d("Reminder", "Time:" + calendar.getTimeInMillis());

    }
}
