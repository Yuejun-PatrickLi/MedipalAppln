package iss.nus.edu.medipalappln.asynTask;

import android.content.Context;
import android.os.AsyncTask;

import iss.nus.edu.medipalappln.dao.CategoryDAO;
import iss.nus.edu.medipalappln.medipal.Category;

/**
 * Created by Darryl on 29/12/2016.
 */

public class GetCategory extends AsyncTask<Integer, Void, Category> {
    //private final WeakReference<Activity> activityWeakRef;

    Category category;
    private CategoryDAO categoryDAO;

    public GetCategory(Context context) {
        //this.activityWeakRef = new WeakReference<Activity>(context);
        this.categoryDAO = new CategoryDAO(context);
    }

    @Override
    protected Category doInBackground(Integer... params) {
        category = categoryDAO.getFacility(params[0]);
        return category;
    }

    @Override
    protected void onPostExecute(Category f) {
        //if (activityWeakRef.get() != null && !activityWeakRef.get().isFinishing()) {
//            Log.d("category", f.toString());
            category = f;

        if (f != null)

            if (categoryDAO != null)
                categoryDAO.close();
    }

    public Category getCategory() {
        return this.category;
    }
}
