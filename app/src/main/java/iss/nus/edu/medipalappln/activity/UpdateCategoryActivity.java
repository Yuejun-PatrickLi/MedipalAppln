package iss.nus.edu.medipalappln.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import iss.nus.edu.medipalappln.R;
import iss.nus.edu.medipalappln.medipal.*;


/**
 * Created by rama on 3/21/2017.
 */

public class UpdateCategoryActivity extends AppCompatActivity {
    //PagerAdapter pagerAdapter;

    EditText editName, editCode, editDescription, editReminder;
    View mView;
    Button updateBtn;
    Spinner spnCategory;
    String reminderOption;
    Switch remindSwtich;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_category);

        mView = getLayoutInflater().inflate(R.layout.fragment_category, null);

        remindSwtich = (Switch) findViewById(R.id.switch_category);

        editName = (EditText) findViewById(R.id.et_catName);
        editCode = (EditText) findViewById(R.id.et_catCode);
        editDescription = (EditText) findViewById(R.id.et_catDesc);

        Intent intent = getIntent();
        final int id = intent.getExtras().getInt("Id");

        Category c1 = App.user.getFacility(id,context);
        editName.setText(c1.getName());
        editCode.setText(c1.getCode());
        editDescription.setText(c1.getDescription());

        remindSwtich.setChecked(c1.getReminder().equalsIgnoreCase("REMINDER ENABLED"));

        remindSwtich.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    remindSwtich.setText("ENABLED");
                    reminderOption = "REMINDER ENABLED";
                }else {
                    remindSwtich.setText("DISABLED");
                    reminderOption = "REMINDER DISABLED";
                }
            }
        });

        updateBtn = (Button) findViewById(R.id.btn_cat_update);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.user.updateFacility(id, editName.getText().toString(), editCode.getText().toString(), editDescription.getText().toString(),
                        reminderOption,getApplicationContext());
                finish();
            }
        });
    }

}
