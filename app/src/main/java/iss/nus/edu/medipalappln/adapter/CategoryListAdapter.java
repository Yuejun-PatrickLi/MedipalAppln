package iss.nus.edu.medipalappln.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import iss.nus.edu.medipalappln.R;
import iss.nus.edu.medipalappln.activity.UpdateCategoryActivity;
import iss.nus.edu.medipalappln.medipal.App;
import iss.nus.edu.medipalappln.medipal.Category;

/**
 * Created by darryl on 26/12/2016.
 */

public class CategoryListAdapter extends ArrayAdapter<Category> {
    private Context context;
    private List<Category> facilities = new ArrayList<>();

    public CategoryListAdapter(Context context) {
        super(context, R.layout.med_fac_row_layout);
        this.context = context;
        refreshFacilities();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CategoryListAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.med_fac_row_layout, parent, false);

            viewHolder = new CategoryListAdapter.ViewHolder();
            viewHolder.tvCatName = (TextView) convertView.findViewById(R.id.tv_catName);
            viewHolder.tvCatDesc = (TextView) convertView.findViewById(R.id.tv_catDesc);
            viewHolder.tvCatCode = (TextView) convertView.findViewById(R.id.tv_catCode);
            viewHolder.tvCatReminder = (TextView) convertView.findViewById(R.id.tv_catReminder);
            viewHolder.btnUpdate = (Button) convertView.findViewById(R.id.btn_update);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CategoryListAdapter.ViewHolder) convertView.getTag();
        }

        final Category category = facilities.get(position);
        final int id = category.getCatId();
        final String catName = category.getName();
        final String catCode = category.getCode();
        final String catDesc = category.getDescription();
        final String catRemind = category.getReminder();

        viewHolder.tvCatName.setText(category.getName().toString());
        viewHolder.tvCatDesc.setText(category.getDescription().toString());
        viewHolder.tvCatCode.setText(category.getCode().toString());
        viewHolder.tvCatReminder.setText(category.getReminder().toString());

        viewHolder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, UpdateCategoryActivity.class);
                intent.putExtra("Id", category.getCatId());
                intent.putExtra("Name", category.getName());
                intent.putExtra("Code", catCode);
                intent.putExtra("Description", category.getDescription());
                intent.putExtra("Reminder", category.getReminder());

                context.startActivity(intent);

                /*App.club.removeFacility(category.getName());
                refreshFacilities();*/
            }
        });
        return convertView;
    }


    public void refreshFacilities() {
        facilities.clear();
        facilities.addAll(App.user.getFacilities(this.context));
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return facilities.size();
    }

    static class ViewHolder {
        TextView tvCatName, tvCatDesc, tvCatCode, tvCatReminder;
        Button btnUpdate;
    }
}
