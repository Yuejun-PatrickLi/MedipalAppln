package iss.nus.edu.medipalappln.activity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import iss.nus.edu.medipalappln.Service.NotificationReceiver;
import iss.nus.edu.medipalappln.medipal.App;
import iss.nus.edu.medipalappln.medipal.Category;
import iss.nus.edu.medipalappln.medipal.Reminder;

public class AddMedicineActivity extends AppCompatActivity {

  private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
  private static final SimpleDateFormat formatter = new SimpleDateFormat("d-MMM-yyyy H:mm", Locale.ENGLISH);
  private SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a", Locale.getDefault());

  Calendar currentCal = Calendar.getInstance();
  Calendar selectedDate = Calendar.getInstance();

  private EditText etMedicineName, etMedicineDesc,etQuantity, etDosage, etThreshold, etDateIssued, etExpireFactor, txtStTime;
  private EditText etReminderFreq, etReminderStTime, etReminderInterval;
  Reminder reminderObj;
  int remId =0;

  boolean flag = false;

  Switch remindSwitch;
  int quantity, dosage, threshold, expiryFactor;
  List<Category> categoryList = null;
  Spinner spnFacility;
  LinearLayout remindLayout, remindContentLayout;
  Category selectedCategory;
  String categorySelected;
  String remindFlag;
  Button btnStTimePicker;
  private int mDay, mHour, mMinute;
  Context context;
  Reminder r1;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_medicine);

    remindLayout = (LinearLayout) findViewById(R.id.switch_layout2);
    remindContentLayout = (LinearLayout) findViewById(R.id.reminder_content_layout3) ;
    remindLayout.setVisibility(View.GONE);
    remindContentLayout.setVisibility(View.GONE);

    spnFacility = (Spinner) findViewById(R.id.spn_facility);

    categoryList = App.user.getFacilities(this);
    final List<String> spnFacList = new ArrayList<>();
    spnFacList.add("<Select Category>");
    for (Category category : categoryList) {
      spnFacList.add(category.getName());
    }
    final ArrayAdapter<String> spnFacAdapter =
            new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spnFacList);
    spnFacility.setAdapter(spnFacAdapter);
    spnFacility.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        categorySelected = (String) parent.getSelectedItem();
        Log.d("Niv1", categorySelected);

        for(Category c: categoryList){
          if(c.getName()==categorySelected){
            selectedCategory = c;
            if(c.getReminder().equalsIgnoreCase("REMINDER ENABLED")){
              remindLayout.setVisibility(View.VISIBLE);
              remindSwitch.setText("Reminder ON", null);
              r1 = new Reminder();
            }else
              remindLayout.setVisibility(View.GONE);
            remindSwitch.setText("Reminder OFF", null);
          }
        }
      }
      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    remindSwitch = (Switch) findViewById(R.id.switch1);
    remindSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked == true) {
          Log.d("Niv_view", "checked");
          remindSwitch.setText("Reminder ON", null);
          remindContentLayout.setVisibility(View.VISIBLE);
        } else{
          remindSwitch.setText("Reminder OFF", null);
          remindContentLayout.setVisibility(View.GONE);
        }
      }
    });


    txtStTime = (EditText)findViewById(R.id.et_select_strt_time);

    Calendar timeCalendar = Calendar.getInstance();
    timeCalendar.setTime(currentCal.getTime());
    timeCalendar.set(Calendar.HOUR, currentCal.get(Calendar.HOUR));
    timeCalendar.set(Calendar.MINUTE, currentCal.get(Calendar.MINUTE));
    timeCalendar.set(Calendar.AM_PM, currentCal.get(Calendar.AM_PM));
    timeCalendar.add(Calendar.HOUR, 1);
    txtStTime.setText(timeFormatter.format(timeCalendar.getTime()));


    View.OnClickListener timeClickListener = new View.OnClickListener() {
      @Override public void onClick(final View v) {
        final EditText editText = (EditText) v;
        TimePickerDialog.OnTimeSetListener timeSetListener =
                new TimePickerDialog.OnTimeSetListener() {
                  @Override public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
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
          Toast.makeText(AddMedicineActivity.this, R.string.generic_error, Toast.LENGTH_SHORT)
                  .show();
        }
        TimePickerDialog timePickerDialog =
                new TimePickerDialog(AddMedicineActivity.this, timeSetListener,
                        timeCalendar.get(Calendar.HOUR_OF_DAY), timeCalendar.get(Calendar.MINUTE), false);
        timePickerDialog.show();
      }
    };

    txtStTime.setOnClickListener(timeClickListener);


    etDateIssued = (EditText) findViewById(R.id.et_date_issued);

    etDateIssued.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        DatePickerDialog.OnDateSetListener onDateSetListener =
                new DatePickerDialog.OnDateSetListener() {
                  @Override
                  public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, monthOfYear, dayOfMonth);
                    selectedDate = calendar;
                    etDateIssued.setText(dateFormatter.format(calendar.getTime()));
                  }
                };
        DatePickerDialog datePickerDialog =
                new DatePickerDialog(AddMedicineActivity.this, onDateSetListener,
                        selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH),
                        selectedDate.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
      }
    });
    etDateIssued.setOnClickListener(timeClickListener);
    // = App.user.getMaxReminderId(getApplicationContext());

    Button btnSave = (Button) findViewById(R.id.btn_save);

    btnSave.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        etMedicineName = (EditText) findViewById(R.id.et_medicine_name);
        etMedicineDesc = (EditText) findViewById(R.id.et_medicine_desc);

        Calendar selectedStTime = Calendar.getInstance();
        try {
          selectedStTime.setTime(dateFormatter.parse(etDateIssued.getText().toString()));
        } catch (ParseException e) {
          e.printStackTrace();
        }

        etQuantity = (EditText) findViewById(R.id.et_quantity);
        etDosage = (EditText) findViewById(R.id.et_dosage);
        etThreshold = (EditText) findViewById(R.id.et_threshold);
        etExpireFactor = (EditText) findViewById(R.id.et_expiry_factor);

        etReminderFreq = (EditText) findViewById(R.id.et_remind_freq);
        etReminderInterval = (EditText) findViewById(R.id.et_remindInterval);


        try {
          quantity = Integer.parseInt(etQuantity.getText().toString());
          dosage = Integer.parseInt(etDosage.getText().toString());
          threshold = Integer.parseInt(etThreshold.getText().toString());
          expiryFactor = Integer.parseInt(etExpireFactor.getText().toString());
        }catch (NumberFormatException e){
          e.printStackTrace();
        }

        if(remindSwitch.isChecked()) {

          Calendar selectedEtTime = Calendar.getInstance();
          try {
            selectedEtTime.setTime(timeFormatter.parse(txtStTime.getText().toString()));
          } catch (ParseException e) {
            e.printStackTrace();
          }

          r1.setStartTime(selectedEtTime.getTime());
          r1.setFrequency(etReminderFreq.getText().toString());
          r1.setInterval(etReminderInterval.getText().toString());
          remindFlag = "TRUE";

          if(isValidReminder()) {

            r1.setRemindId(App.user.addReminder(etReminderFreq.getText().toString(), selectedEtTime.getTime(),
                    etReminderInterval.getText().toString(), getApplicationContext()));

            createNotificationMessage(r1.getRemindId(),"take your medication!!!!!!");
          }

          if(isValidMedicine()) {

            App.user.addMember(etMedicineName.getText().toString().trim(), etMedicineDesc.getText().toString().trim(),
                    selectedCategory.getCatId(), r1.getRemindId(), remindFlag,
                    quantity,
                    dosage, threshold,
                    selectedStTime.getTime(), expiryFactor, getApplicationContext());

            Toast.makeText(AddMedicineActivity.this, getString(R.string.save_successful),
                    Toast.LENGTH_SHORT).show();
            finish();
          }

        }else{
          remindFlag = "FALSE";

          if(isValidMedicine()) {
            App.user.addMember(etMedicineName.getText().toString().trim(), etMedicineDesc.getText().toString().trim(),
                    selectedCategory.getCatId(), 0, remindFlag,
                    quantity,
                    dosage, threshold,
                    selectedStTime.getTime(), expiryFactor, getApplicationContext());

            Toast.makeText(AddMedicineActivity.this, getString(R.string.save_successful),
                    Toast.LENGTH_SHORT).show();
            finish();
          }
        }
      }
    });

  }


  private boolean isValidMedicine() {
    boolean isValid = true;
    if (TextUtils.isEmpty(etMedicineName.getText().toString().trim())) {
      etMedicineName.requestFocus();
      etMedicineName.setError("Please fill in medicine name");
      isValid = false;
    }

    if (TextUtils.isEmpty(etMedicineDesc.getText().toString().trim())) {
      etMedicineDesc.requestFocus();
      etMedicineDesc.setError("Please fill in medicine description");
      isValid = false;
    }

    if (TextUtils.isEmpty(etDateIssued.getText().toString().trim())) {
      etDateIssued.requestFocus();
      etDateIssued.setError("Please set date issued");
      isValid = false;
    }

    if (TextUtils.isEmpty(etQuantity.getText().toString().trim())) {
      etQuantity.requestFocus();
      etQuantity.setError("Please fill in quantity");
      isValid = false;
    }

    if (TextUtils.isEmpty(etDosage.getText().toString().trim())) {
      etDosage.requestFocus();
      etDosage.setError("Please fill in dosage");
      isValid = false;
    }

    if (TextUtils.isEmpty(etThreshold.getText().toString().trim())) {
      etThreshold.requestFocus();
      etThreshold.setError("Please fill in threshold");
      isValid = false;
    }
    if (TextUtils.isEmpty(etExpireFactor.getText().toString().trim())) {
      etExpireFactor.requestFocus();
      etExpireFactor.setError("Please fill in expiry factor");
      isValid = false;
    }
    return isValid;
  }

  private boolean isValidReminder(){
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
    Log.d("Reminder", "Time:"+calendar.getTimeInMillis());

  }
}
