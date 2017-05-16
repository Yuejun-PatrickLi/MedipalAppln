package iss.nus.edu.medipalappln.activity;

/**
 * Created by root on 7/3/17.
 */

import android.content.Context;
import android.content.SharedPreferences;


public class Session {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    static public Boolean loggedin=false;
    Context ctx;
    public String Username;
    public Session(Context context) {
        this.ctx=context;
        preferences=ctx.getSharedPreferences("Login",Context.MODE_PRIVATE);
        editor=preferences.edit();

    }
    public void setLoggedin(Boolean Loggedin,String username){
        editor.putBoolean("loggedIN",Loggedin);
        editor.putString("username",username);
        editor.commit();
        Username=username;

    }
    public boolean loggedin(){
        return preferences.getBoolean("loggedIN",false);
    }
    public String username(){return preferences.getString("username",null);}
}