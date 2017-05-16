package iss.nus.edu.medipalappln;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import iss.nus.edu.medipalappln.medipal.Medicine;

import static org.junit.Assert.assertNotEquals;

/**
 * Created by Zhuyi Gu on 2017/3/26.
 */

public class MedicineTest extends TestCase{
    String datePattern = "dd-MM-yyyy";
    SimpleDateFormat dateTest;

    Medicine medicine1,medicine2;

    @Before
    public void setUp() throws Exception {
        dateTest = new SimpleDateFormat(datePattern);

        Date date1 = dateTest.parse("09-01-2018");

        medicine1 = new Medicine(0,"AA","Cold",1,2,"0",100,5,90,date1,2);
        medicine2 = new Medicine(0,"AA","Cold",1,2,"0",100,5,90,date1,2);
    }

    @After
    public void tearDown() throws Exception {
        medicine1 = null;
        medicine2 = null;
    }

    @Test
    public void testMedicine() throws ParseException {
        String date = "10-01-2018";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date2 = sdf.parse(date);
        Medicine medicineAfter = new Medicine(1,"CC","Stomach",2,2,"1",50,5,45,date2,3);
        ArrayList<Medicine> medicineList1 = new ArrayList<Medicine>();
        ArrayList<Medicine> medicineList2 = new ArrayList<Medicine>();

        assertNotNull(medicine1);
        assertNotEquals(medicine1,medicine2);

        //assertTrue(date3.equals(sdf.parse((medicine1.getDateIssued().toString()))));
        //assertTrue(date3.after(sdf.parse((medicineAfter.getDateIssued().toString()))));
        //assertFalse(date3.before(sdf.parse((medicineAfter.getDateIssued().toString()))));

        assertTrue(medicine1.getMedId() == 0);
        assertTrue(medicine1.getMedName().toString() == "AA");
        assertTrue(medicine1.getMedDesc().toString() == "Cold");
        assertTrue(medicine1.getCatId() == 1);
        assertTrue(medicine1.getRemindId() == 2);
        assertTrue(medicine1.getRemindFlag().toString() == "0");
        assertTrue(medicine1.getQuantity()== 100);
        assertTrue(medicine1.getDosage()== 5);
        assertTrue(medicine1.getThreshold()== 90);
        assertTrue(medicine1.getExpireFactor()== 2);

        medicineList1.add(medicine1);
        medicineList1.add(medicine2);
        medicineList1.add(medicineAfter);

        medicineList2.add(medicine2);
        medicineList2.add(medicine1);
        medicineList2.add(medicine1);

        assertNotEquals(medicineList1,medicineList2);
        assertTrue(medicineList1.get(0).equals(medicineList2.get(1)));
    }
}