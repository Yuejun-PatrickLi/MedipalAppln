package iss.nus.edu.medipalappln.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import iss.nus.edu.medipalappln.R;
import iss.nus.edu.medipalappln.dao.LoginDataBaseAdapter;

public class SignInActivity extends Activity {
    int counter;
    private LoginDataBaseAdapter loginDataBaseAdapter;
    public Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
            //it is the first time the fragment is being called
            counter = 0;
        }else{
            //not the first time so we will check SavedInstanceState bundle
            counter = savedInstanceState.getInt("value",0); //here zero is the default value
        }
        setContentView(R.layout.activity_sign_in);

    }

    public void signIn(){
        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter.open();
        session=new Session(this);

        final EditText editTextUserName=(EditText)findViewById(R.id.email);
        final EditText editTextPassword=(EditText)findViewById(R.id.password);
        Button btnSignIn=(Button)findViewById(R.id.signin);
        btnSignIn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // get The User name and Password
                String userName=editTextUserName.getText().toString();
                String password=editTextPassword.getText().toString();

                // fetch the Password form database for respective user name
                String storedPassword=loginDataBaseAdapter.getSingleEntry(userName);

                // check if the Stored password matches with  Password entered by user
                if(password.equals(storedPassword))
                {
                    session.setLoggedin(Boolean.TRUE,userName);
                    Toast.makeText(SignInActivity.this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();
                    Intent in=new Intent(getApplicationContext(),HelpScreen.class);

                    startActivity(in);
                    //dialog.dismiss();

                }
                else
                {
                    Toast.makeText(SignInActivity.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close The Database
        loginDataBaseAdapter.close();
    }
}
