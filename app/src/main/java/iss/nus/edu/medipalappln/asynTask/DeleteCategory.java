package iss.nus.edu.medipalappln.asynTask;

import android.content.Context;
import android.os.AsyncTask;

import iss.nus.edu.medipalappln.dao.CategoryDAO;
import iss.nus.edu.medipalappln.medipal.Category;

/**
 * Created by Darryl on 29/12/2016.
 */

public class DeleteCategory extends AsyncTask<Category, Void, Integer> {
    Category member = null;
    private CategoryDAO categoryDAO;

    public DeleteCategory(Context context) {
        this.categoryDAO = new CategoryDAO(context);
    }

    @Override
    protected Integer doInBackground(Category... params) {
        int result = categoryDAO.delete(params[0]);
        return result;
    }

    @Override
    protected void onPostExecute(Integer result) {
        if (result != -1)

            if (categoryDAO != null)
                categoryDAO.close();
    }
}
