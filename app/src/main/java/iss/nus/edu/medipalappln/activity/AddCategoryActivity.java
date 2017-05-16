package iss.nus.edu.medipalappln.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import iss.nus.edu.medipalappln.R;
import iss.nus.edu.medipalappln.medipal.App;

public class AddCategoryActivity extends AppCompatActivity {
    private EditText etFacilityName, etFacilityDesc, etFacilityCode, etFacilityReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_category);

        etFacilityName = (EditText) findViewById(R.id.et_catName);
        etFacilityDesc = (EditText) findViewById(R.id.et_catDesc);

        Button btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    App.user.addFacility(etFacilityName.getText().toString().trim(), etFacilityDesc.getText().toString().trim(),
                            etFacilityCode.getText().toString().trim(),
                            etFacilityReminder.getText().toString().trim(),getApplicationContext());
                    Toast.makeText(AddCategoryActivity.this, getString(R.string.save_successful),
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private boolean isValid() {
        boolean isValid = true;
        if (TextUtils.isEmpty(etFacilityName.getText().toString().trim())) {
            etFacilityName.setError(getString(R.string.first_name_validation_msg));
            isValid = false;
        }
        if (TextUtils.isEmpty(etFacilityDesc.getText().toString().trim())) {
            etFacilityDesc.setError(getString(R.string.second_name_validation_msg));
            isValid = false;
        }

        return isValid;
    }
}
