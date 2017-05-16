package iss.nus.edu.medipalappln;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import iss.nus.edu.medipalappln.medipal.Consumption;

import static org.junit.Assert.assertNotEquals;

/**
 * Created by Zhuyi Gu on 2017/3/26.
 */

public class ConsumptionTest extends TestCase{
    String datePattern = "dd-MM-yyyy";
    SimpleDateFormat dateTest;

    private Consumption con1,con2;

    @Before
    public void setUp() throws Exception{
        dateTest = new SimpleDateFormat(datePattern);

        Date date1 = dateTest.parse("08-07-2018");

        con1 = new Consumption(1,2,100,date1);
        con2 = new Consumption(1,2,100,date1);
    }

    @After
    public void clearDown() throws Exception{
        con1 = null;
        con2 = null;
    }

    @Test
    public void testConsumption() throws ParseException{
        String date = "09-07-2018";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date2 = sdf.parse(date);
        Consumption conAfter = new Consumption(3,4,200,date2);
        ArrayList<Consumption> conList1 = new ArrayList<Consumption>();
        ArrayList<Consumption> conList2 = new ArrayList<Consumption>();

        assertNotNull(con1);
        assertNotEquals(con1,con2);

        assertTrue(con1.getMedId() == 2);
        assertTrue(con1.getQuantity() == 100);

        conList1.add(con1);
        conList1.add(con2);
        conList1.add(conAfter);

        conList2.add(con2);
        conList2.add(con1);
        conList2.add(con1);

        assertNotEquals(conList1,conList2);
        assertTrue(conList1.get(0).equals(conList2.get(1)));

    }

}