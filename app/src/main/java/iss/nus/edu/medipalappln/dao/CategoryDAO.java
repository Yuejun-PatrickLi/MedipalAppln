package iss.nus.edu.medipalappln.dao;

/**
 * Created by niv on 26/12/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import iss.nus.edu.medipalappln.medipal.Category;

public class CategoryDAO extends DBDAO {
    private static final String WHERE_ID_EQUALS = DataBaseHelper.CATEGORY.ID.toString() + " =?";
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    public CategoryDAO(Context context) {
        super(context);
    }

    public long save(Category category) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.CATEGORY.Name.toString(), category.getName());
        values.put(DataBaseHelper.CATEGORY.Description.toString(), category.getDescription());
        return database.insert(DataBaseHelper.TABLE_CONSUMPTION, null, values);
    }

    public long update(Category category) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.CATEGORY.Name.toString(), category.getName());
        values.put(DataBaseHelper.CATEGORY.Code.toString(), category.getCode());
        values.put(DataBaseHelper.CATEGORY.Description.toString(), category.getDescription());
        values.put(DataBaseHelper.CATEGORY.Remind.toString(), category.getReminder());
        //values.put(DataBaseHelper.FAC_DESC, formatter.format(category.getDescription()));

        long result = database.update(DataBaseHelper.TABLE_CATEGORY, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(category.getCatId()) });
        Log.d("Update Result:", "=" + result);
        return result;

    }

    public int delete(Category category) {
        return database.delete(DataBaseHelper.TABLE_CATEGORY, WHERE_ID_EQUALS,
                new String[] { category.getCatId() + "" });
    }

    //USING query() method
    public ArrayList<Category> getCategories() {
        ArrayList<Category> facilities = new ArrayList<Category>();
        String[] querystr = new String[]{DataBaseHelper.CATEGORY.ID.toString(), DataBaseHelper.CATEGORY.Name.toString(),
                DataBaseHelper.CATEGORY.Code.toString(), DataBaseHelper.CATEGORY.Description.toString(),
                DataBaseHelper.CATEGORY.Remind.toString()};
        Cursor cursor = database.query(DataBaseHelper.TABLE_CATEGORY,
                querystr, null, null, null,
                null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String fname = cursor.getString(1);
            String fcode = cursor.getString(2);
            String fdesc = cursor.getString(3);
            String freminder = cursor.getString(4);

            Category category = new Category(id, fname, fcode, fdesc, freminder);
            facilities.add(category);
        }
        return facilities;
    }

    //USING rawQuery() method


    //Retrieves a single facility record with the given id
    public Category getFacility(long id) {
        Category category = null;

        String sql = "SELECT * FROM " + DataBaseHelper.TABLE_CATEGORY
                + " WHERE " + DataBaseHelper.CATEGORY.ID.toString() + " = ?";

        Cursor cursor = database.rawQuery(sql, new String[] { id + "" });

        if (cursor.moveToNext()) {
            int fid = cursor.getInt(0);
            String fname = cursor.getString(1);
            String fcode = cursor.getString(2);
            String fdesc = cursor.getString(3);
            String freminder = cursor.getString(4);

            category = new Category(fid, fname, fcode, fdesc, freminder);

        }
        return category;
    }
}
