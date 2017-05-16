package iss.nus.edu.medipalappln.medipal;

import android.app.Application;

public class App extends Application {
    private static final String TAG = "App";
    public static User user;

    //TODO: link session to user object
    //public Session session

    @Override
    public void onCreate() {
        super.onCreate();
        user = new User();
        //user = new User(session.username());
    }
}
