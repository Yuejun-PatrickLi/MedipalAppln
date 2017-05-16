package iss.nus.edu.medipalappln.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import iss.nus.edu.medipalappln.R;
import iss.nus.edu.medipalappln.dao.ConsumptionDAO;
import iss.nus.edu.medipalappln.medipal.App;
import iss.nus.edu.medipalappln.medipal.Category;
import iss.nus.edu.medipalappln.medipal.Consumption;
import iss.nus.edu.medipalappln.medipal.Medicine;

public class ConsumptionListAdapter extends ArrayAdapter<Consumption> {
  private Context context;
  private List<Consumption> consumptions = new ArrayList<>();
  private ArrayList<Consumption> arraylist;

    ConsumptionDAO cd ;

  public ConsumptionListAdapter(Context context) {
    super(context, R.layout.consumption_row);
    this.context = context;
    this.arraylist = new ArrayList<Consumption>();
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {
    ConsumptionListAdapter.ViewHolder viewHolder;

    if (convertView == null) {
      LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

      convertView = inflater.inflate(R.layout.consumption_row, parent, false);

      viewHolder = new ConsumptionListAdapter.ViewHolder();
      viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_conName);
      viewHolder.tvQuantity = (TextView) convertView.findViewById(R.id.tv_conQuantity);
      viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_conTime);
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ConsumptionListAdapter.ViewHolder) convertView.getTag();
    }

    final Consumption consumption = consumptions.get(position);
    ConsumptionDAO consumptionDAO = new ConsumptionDAO(context);
    Medicine med = consumptionDAO.getConsumptionMedicine(consumption);
    String medName = med.getMedName();

    // change type of in and date of consumption to string
    int quan = consumption.getQuantity();
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:MM");
    Date date=consumption.getConDate();
    String str=sdf.format(date);

    viewHolder.tvName.setText(medName);
    viewHolder.tvQuantity.setText(Integer.toString(quan));
    viewHolder.tvTime.setText(str);

    return convertView;
  }

  public void refreshConsumptions() {
    consumptions.clear();
    consumptions.addAll(App.user.getConsumptions(this.context));
    notifyDataSetChanged();
    //this.arraylist.addAll(consumptions);
  }
  public void refreshConsumptionsByCategory(Category c){
    consumptions.clear();
    consumptions.addAll(App.user.getConsumptionsByCategory(c,this.context));
    notifyDataSetChanged();
  }

  public void refreshConsumptionByMedicine(Medicine m){
    consumptions.clear();
    consumptions.addAll(App.user.getConsumptionsByMedicine(m,this.context));
    notifyDataSetChanged();;
  }



  @Override
  public int getCount() {
    return consumptions.size();
  }

  static class ViewHolder {
    TextView tvName, tvQuantity, tvTime;

  }

  // for searching
  // Filter Class
  public void filter(String charText) {
    charText = charText.toLowerCase(Locale.getDefault());
    consumptions.clear();

    if (charText.length() == 0) {
      consumptions.addAll(arraylist);
    } else {
      for (Consumption wp : arraylist) {
        if (wp.toString().toLowerCase(Locale.getDefault()).contains(charText)) {
          consumptions.add(wp);
        }
      }
    }
    notifyDataSetChanged();
  }

}
