package iss.nus.edu.medipalappln;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import iss.nus.edu.medipalappln.medipal.BloodPressure;
import iss.nus.edu.medipalappln.medipal.Pulse;
import iss.nus.edu.medipalappln.medipal.Temperature;
import iss.nus.edu.medipalappln.medipal.Weight;

import static org.junit.Assert.assertNotEquals;

public class MeasurementTest extends TestCase {

    String datePattern = "yyyy-MM-dd HH:mm";
    SimpleDateFormat testDate;

    private BloodPressure bp1, bp2;
    private Pulse pulse1, pulse2;
    private Temperature temperature1, temperature2;
    private Weight weight1, weight2;

    @Before public void setUp() throws Exception {

        testDate = new SimpleDateFormat(datePattern);

        bp1 = new BloodPressure(100, 80, "2017-01-02 11:00");
        bp2 = new BloodPressure(100, 80, "2017-01-02 11:00");
        pulse1 = new Pulse(90, "2016-01-02 11:00");
        pulse2 = new Pulse(90, "2016-01-02 11:00");
        temperature1 = new Temperature(36.5, "2012-01-02 11:00");
        temperature2 = new Temperature(36.5, "2012-01-02 11:00");
        weight1 = new Weight(41, "2017-01-02 18:00");
        weight2 = new Weight(41, "2017-01-02 18:00");

    }

    @After public void tearDown() throws Exception {
        bp1 = null; bp2 = null;
        pulse1 = null; pulse2 = null;
        temperature1 = null; temperature2 = null;
        weight1 = null; weight2 = null;
    }

    @Test public void testBloodPressure() throws ParseException {
        String date = "2017-01-02 11:00";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        BloodPressure bpAfter = new BloodPressure(110, 90, "2016-01-22 12:00");
        ArrayList<BloodPressure> list1 = new ArrayList<BloodPressure>();
        ArrayList<BloodPressure> list2 = new ArrayList<BloodPressure>();

        Date d = df.parse(date);

        assertNotNull(bp1);
        assertNotEquals(bp1, bp2);

        assertEquals(date, 0, bp1.getMeasuredOn().compareTo(date));
        assertTrue(d.equals(df.parse(bp1.getMeasuredOn())));
        assertTrue(d.after(df.parse(bpAfter.getMeasuredOn())));
        assertFalse(d.before(df.parse(bpAfter.getMeasuredOn())));

        assertTrue(bp1.getSystolic().intValue() == 100);
        assertTrue(bp1.getDiastolic().intValue() == 80);

        list1.add(0, bp1);
        list1.add(1, bp2);
        list1.add(2, bpAfter);
        list2.add(0, bp2);
        list2.add(1, bp1);
        list2.add(2, bp1);
        assertNotEquals(list1, list2);

        assertTrue(list1.get(0).equals(list2.get(1)));
    }

    @Test public void testPulse() throws ParseException {
        String date = "2016-01-02 11:00";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Pulse pAfter = new Pulse(95, "2016-01-02 05:01");
        ArrayList<Pulse> list1 = new ArrayList<Pulse>();
        ArrayList<Pulse> list2 = new ArrayList<Pulse>();

        Date d = df.parse(date);

        assertNotNull(pulse1);
        assertNotEquals(pulse1, pulse2);

        assertEquals(date, 0, pulse1.getMeasuredOn().compareTo(date));
        assertTrue(d.equals(df.parse(pulse1.getMeasuredOn())));
        assertTrue(d.after(df.parse(pAfter.getMeasuredOn())));
        assertFalse(d.before(df.parse(pAfter.getMeasuredOn())));

        assertTrue(pulse1.getPulse().intValue() == 90);

        list1.add(0, pulse1);
        list1.add(1, pulse2);
        list1.add(2, pAfter);
        list2.add(0, pulse2);
        list2.add(1, pulse1);
        list2.add(2, pulse1);
        assertNotEquals(list1, list2);

        assertTrue(list1.get(0).equals(list2.get(1)));
    }

    @Test public void testTemperature() throws ParseException {
        String date = "2016-01-02 11:00";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Temperature tAfter = new Temperature(36.5, "2016-01-02 05:01");
        Temperature tDecimal = new Temperature(36.52, "2016-01-02 05:01");
        ArrayList<Temperature> list1 = new ArrayList<Temperature>();
        ArrayList<Temperature> list2 = new ArrayList<Temperature>();

        Date d = df.parse(date);

        assertNotNull(temperature1);
        assertNotEquals(temperature1, temperature2);

        assertEquals(date, 0, pulse1.getMeasuredOn().compareTo(date));
        assertTrue(d.equals(df.parse(pulse1.getMeasuredOn())));
        assertTrue(d.after(df.parse(tAfter.getMeasuredOn())));
        assertFalse(d.before(df.parse(tAfter.getMeasuredOn())));

        assertTrue(temperature1.getTemperature().doubleValue() == 36.5);

        list1.add(0, temperature1);
        list1.add(1, temperature2);
        list1.add(2, tAfter);
        list2.add(0, temperature2);
        list2.add(1, temperature1);
        list2.add(2, temperature1);
        assertNotEquals(list1, list2);

        assertTrue(list1.get(0).equals(list2.get(1)));

        assertTrue(tDecimal.getTemperature().doubleValue() == 36.52);
    }

    @Test public void testWeight() throws ParseException {
        String date = "2017-01-02 18:00";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Weight wAfter = new Weight(41, "2016-01-02 05:01");
        ArrayList<Weight> list1 = new ArrayList<Weight>();
        ArrayList<Weight> list2 = new ArrayList<Weight>();

        Date d = df.parse(date);

        assertNotNull(weight1);
        assertNotEquals(weight1, weight2);

        assertEquals(date, 0, weight1.getMeasuredOn().compareTo(date));
        assertTrue(d.equals(df.parse(weight1.getMeasuredOn())));
        assertTrue(d.after(df.parse(wAfter.getMeasuredOn())));
        assertFalse(d.before(df.parse(wAfter.getMeasuredOn())));

        assertTrue(weight1.getWeight().intValue() == 41);

        list1.add(0, weight1);
        list1.add(1, weight2);
        list1.add(2, wAfter);
        list2.add(0, weight2);
        list2.add(1, weight1);
        list2.add(2, weight1);
        assertNotEquals(list1, list2);

        assertTrue(list1.get(0).equals(list2.get(1)));
    }

    @Test public void testShow() {

    }
}
