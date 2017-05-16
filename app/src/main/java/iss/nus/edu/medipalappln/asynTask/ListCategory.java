package iss.nus.edu.medipalappln.asynTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import iss.nus.edu.medipalappln.dao.CategoryDAO;
import iss.nus.edu.medipalappln.medipal.Category;

/**
 * Created by Darryl on 29/12/2016.
 */

public class ListCategory extends AsyncTask<Void, Void, ArrayList<Category>> {
    ArrayList<Category> facilities;
    private CategoryDAO categoryDAO;

    public ListCategory(Context context) {
        this.categoryDAO = new CategoryDAO(context);
    }

    @Override
    protected ArrayList<Category> doInBackground(Void... arg0) {
        ArrayList<Category> categoryList = categoryDAO.getCategories();
        return categoryList;
    }

    @Override
    protected void onPostExecute(ArrayList<Category> facList) {
        Log.d("facilities", facList.toString());
        facilities = facList;

        if (facList == null) {
            facilities = new ArrayList<Category>();
        }
    }
}
