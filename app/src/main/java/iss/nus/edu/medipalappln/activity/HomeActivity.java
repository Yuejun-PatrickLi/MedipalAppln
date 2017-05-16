package iss.nus.edu.medipalappln.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import iss.nus.edu.medipalappln.Encryption.CeaserCipher;
import iss.nus.edu.medipalappln.R;
import iss.nus.edu.medipalappln.dao.LoginDataBaseAdapter;
import iss.nus.edu.medipalappln.medipal.App;
import iss.nus.edu.medipalappln.medipal.PersonalBio;

public class HomeActivity extends Activity
{
    Button btnSignIn,btnSignUp;
    Button btnRegister, btnDate;
    private boolean isRegister = false;
    private PersonalBio personalBio;

    private EditText editTextname;
    private EditText editTextBlood;
    private TextView editTextDob;
    private EditText editTextPincode;
    private EditText editTextHeight;
    private EditText editTextAddress;
    private EditText editTextPhone;
    private EditText editTextNRIC;

    LoginDataBaseAdapter loginDataBaseAdapter;
    Session session;
    TextView register;
    CeaserCipher cipher;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new Session(this);
        first_time_check();

        try {
            setContentView(R.layout.activity_login);
            loginDataBaseAdapter = new LoginDataBaseAdapter(this);
            loginDataBaseAdapter = loginDataBaseAdapter.open();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        ////btnSignIn = (Button) findViewById(R.id.buttonSignIn);
        //btnSignUp = (Button) findViewById(R.id.buttonSignUP);
        //cipher=new CeaserCipher();
        ////register=(TextView)findViewById(R.id.buttonSignUp);
        btnRegister = (Button) findViewById(R.id.submit);
        btnDate = (Button) findViewById(R.id.dob_date);
        SpannableString styledString = new SpannableString("Personal MediPal");
        styledString.setSpan(new StyleSpan(Typeface.BOLD), 0, 7, 0);
        styledString.setSpan(new StyleSpan(Typeface.ITALIC), 8, 14, 0);

        session = new Session(this);
        //final EditText editTextUserName=(EditText)findViewById(R.id.input_email);
        //final EditText editTextPassword=(EditText)findViewById(R.id.input_password);
        editTextname = (EditText)findViewById(R.id.name);
        editTextBlood = (EditText)findViewById(R.id.Bloodtype);
        editTextDob = (TextView) findViewById(R.id.dob);
        editTextPincode = (EditText) findViewById(R.id.postalcode);
        editTextHeight = (EditText) findViewById(R.id.Height);
        editTextAddress = (EditText)findViewById(R.id.Address);
        editTextNRIC = (EditText)findViewById(R.id.nric);
        btnDate = (Button) findViewById(R.id.dob_date);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calenderEntry(v);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //register
                editTextname = (EditText) findViewById(R.id.name);
                editTextBlood = (EditText)findViewById(R.id.Bloodtype);
                editTextDob = (TextView) findViewById(R.id.dob);
                editTextPincode = (EditText) findViewById(R.id.postalcode);
                editTextHeight = (EditText) findViewById(R.id.Height);
                editTextAddress = (EditText)findViewById(R.id.Address);
                editTextNRIC = (EditText)findViewById(R.id.nric);

                insertEntry();

                //help screens
                Intent intentSignUp = new Intent(getApplicationContext(), HelpScreen.class);
                startActivity(intentSignUp);

            }
        });

        /// Create Intent for SignUpActivity  abd Start The Activity
        /*
        try {

        btnSignIn.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
                /*Intent intentSignIn = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intentSignIn);/
            // get The User name and Password
            String userName = editTextUserName.getText().toString();
            String password = editTextPassword.getText().toString();

            // fetch the Password form database for respective user name
            String storedPassword = loginDataBaseAdapter.getSingleEntry(userName);
            String decodedPasswd = cipher.decode(storedPassword, 5);

            // check if the Stored password matches with  Password entered by user
            if (password.equals(decodedPasswd)) {
                session.setLoggedin(Boolean.TRUE, userName);
                Toast.makeText(HomeActivity.this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();
                Intent in = new Intent(getApplicationContext(), HelpScreen.class);
//Intent in =new Intent(getApplicationContext(), FingerprintActivity.class);
                startActivity(in);
                //dialog.dismiss();

            } else {
                Toast.makeText(HomeActivity.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
            }


        }
        });
    }
    catch (Exception e){
        e.printStackTrace();
    }

        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                /// Create Intent for SignUpActivity  abd Start The Activity
                Intent intentSignUp = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intentSignUp);

            }
        });
        */
    }



    public void calenderEntry(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final DatePicker datePicker = new DatePicker(this);
        datePicker.setCalendarViewShown(false);

        builder.setTitle("Date of birth");
        builder.setView(datePicker);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String year, month, day;
                year = String.format("%04d", datePicker.getYear());
                month = String.format("%02d", datePicker.getMonth()+1); //index begin from 0
                day = String.format("%02d", datePicker.getDayOfMonth());

                editTextDob.setText(year + "-" + month + "-" + day);
            }
        });

        builder.show();
    }


    private void insertEntry() {
        final String name = editTextname.getText().toString();
        final String blood = editTextBlood.getText().toString();
        final String nric = editTextNRIC.getText().toString();
        final String postal = editTextPincode.getText().toString();
        final String dob = editTextDob.getText().toString();
        final String height = editTextHeight.getText().toString();
        final String address = editTextAddress.getText().toString();

        PersonalBio p = new PersonalBio();
        p.setName(name);
        p.setAddress(address);
        p.setBloodType(blood);
        p.setDOB(dob);
        p.setHeight(Integer.parseInt(height));
        p.setPostalCode(postal);
        p.setIDNo(nric);


        if (!App.user.createPersonalBio(this, p)) {
            Toast.makeText(this, "User already registered", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "User registration successful", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginDataBaseAdapter.close();
    }

    private boolean first_time_check() {
        Session session = new Session(this);
        personalBio = App.user.getPersonalBio(this);
        //if((session.loggedin())){
        if (personalBio != null){
            try {
                Intent i = new Intent(HomeActivity.this, Welcome.class);
                startActivity(i);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            finish();
        }
        return false;

    }
}
