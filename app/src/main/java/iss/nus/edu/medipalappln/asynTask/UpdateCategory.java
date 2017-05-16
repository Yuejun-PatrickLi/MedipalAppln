package iss.nus.edu.medipalappln.asynTask;

import android.content.Context;
import android.os.AsyncTask;

import iss.nus.edu.medipalappln.dao.CategoryDAO;
import iss.nus.edu.medipalappln.medipal.Category;

/**
 * Created by rama on 3/21/2017.
 */

public class UpdateCategory extends AsyncTask<Category, Void, Long> {

    Category category = null;
    private CategoryDAO categoryDAO;

    public UpdateCategory(Context context) {
        this.categoryDAO = new CategoryDAO(context);
    }

    @Override
    protected Long doInBackground(Category... params) {
        long result = categoryDAO.update(params[0]);
        return result;
    }

    @Override
    protected void onPostExecute(Long result) {
        if (result != -1)

            if (categoryDAO != null)
                categoryDAO.close();
    }



}
