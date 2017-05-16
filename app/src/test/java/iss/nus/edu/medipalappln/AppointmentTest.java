package iss.nus.edu.medipalappln;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import iss.nus.edu.medipalappln.medipal.Appointment;

/**
 * Created by cherry on 2017/3/24.
 */

public class AppointmentTest extends TestCase{

    String datePattern = "yyyy-MM-dd HH:mm";
    SimpleDateFormat dateTest;

    private Appointment app1, app2;

    @Before public void init() throws Exception {

        dateTest = new SimpleDateFormat(datePattern);

        app1 = new Appointment("Shanghai","2017-06-28 14:00","Come back home");
    }

    @After public void clear() throws Exception {
        app1 = null;
    }

    @Test public void testBloodPressure() throws ParseException {
        String location = "Shanghai";
        String description = "Come back home";
        String date = "2017-06-28 14:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Appointment app3 = new Appointment("Chengdu","2019-05-30 20:00","Go for Hotpot");
        ArrayList<Appointment> appList1 = new ArrayList<Appointment>();
        ArrayList<Appointment> appList2 = new ArrayList<Appointment>();

        Date date1 = sdf.parse(date);

        assertNull(app1);
        assertSame(app1,app2);


        appList1.add(0,app1);
        appList1.add(1,app2);
        appList1.add(2,app3);

        appList2.add(app1);
        appList2.add(app2);
        appList2.add(app3);

        assertNotSame(appList1,appList2);
    }
}
