package iss.nus.edu.medipalappln;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import iss.nus.edu.medipalappln.medipal.Category;

import static org.junit.Assert.assertNotEquals;

/**
 * Created by Zhuyi Gu on 2017/3/26.
 */

public class CategoryTest extends TestCase{
    private Category c1,c2;

    @Before
    public void setUp() throws Exception{
        c1 = new Category(0,"Self Apply","SEL","self taken","1");
        c2 = new Category(0,"Self Apply","SEL","self taken","1");
    }

    @After
    public void tearDown() throws Exception{
        c1 = null;
        c2 = null;
    }

    @Test
    public void testCategory() throws Exception{
        Category cAfter = new Category(2,"Incidental","INL","incidental","2");
        ArrayList<Category> categoryList1 = new ArrayList<Category>();
        ArrayList<Category> categoryList2 = new ArrayList<Category>();

        assertNotNull(c1);

        assertTrue(c1.getCatId() == 0);
        assertTrue(c1.getName().toString() == "Self Apply");
        assertTrue(c1.getCode().toString() == "SEL");
        assertTrue(c1.getDescription().toString() == "self taken");
        assertTrue(c1.getReminder().toString() == "1");

        categoryList1.add(c1);
        categoryList1.add(c2);
        categoryList1.add(cAfter);

        categoryList2.add(c2);
        categoryList2.add(c1);
        categoryList2.add(c1);

        assertNotEquals(categoryList1,categoryList2);
        assertTrue(categoryList1.get(0).equals(categoryList2.get(1)));
    }
}