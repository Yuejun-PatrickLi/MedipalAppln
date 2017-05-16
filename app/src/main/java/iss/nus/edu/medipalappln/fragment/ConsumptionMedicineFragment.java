package iss.nus.edu.medipalappln.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import iss.nus.edu.medipalappln.R;
import iss.nus.edu.medipalappln.activity.AddConsumptionActivity;
import iss.nus.edu.medipalappln.adapter.ConsumptionListAdapter;
import iss.nus.edu.medipalappln.medipal.App;
import iss.nus.edu.medipalappln.medipal.Medicine;


public class ConsumptionMedicineFragment extends Fragment {
    private TextView tvEmpty;
    private ConsumptionListAdapter consumptionListAdapter;
    private ListView bookingList;
    private List<Medicine> medicineList;
    Spinner spnMember;
    String item = "Default";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View fragmentView = inflater.inflate(R.layout.fragment_consumption_medicine, container, false);

        bookingList = (ListView) fragmentView.findViewById(R.id.lv_booking_list);
        consumptionListAdapter = new ConsumptionListAdapter(getActivity());
        bookingList.setAdapter(consumptionListAdapter);

        spnMember = (Spinner) fragmentView.findViewById(R.id.filter);
        medicineList = App.user.getMedicines(this.getContext());
        List<String> spnMemList = new ArrayList<>();
        spnMemList.add("Default");
        for (Medicine m : medicineList) {
            spnMemList.add(m.getMedName().toString());
        }
        ArrayAdapter<String> spnMemAdapter =
                new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, spnMemList);
        if(spnMemAdapter == null){
            Log.d("!!", "it's null");}
        spnMember.setAdapter(spnMemAdapter);
        spnMember.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("mark", i + " voke");
                //TODO filter
                item = (String)spnMember.getSelectedItem();
                if (!item.equals("Default")){
                    Log.d("Order", "get selecteditem--------------");
                    for (Medicine m:medicineList){
                        if (item.equals(m.getMedName())){
                            consumptionListAdapter.refreshConsumptionByMedicine(m);
                            break;
                        }
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        tvEmpty = (TextView) fragmentView.findViewById(R.id.tv_empty_value);
        FloatingActionButton floatingActionButton =
                (FloatingActionButton) fragmentView.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), AddConsumptionActivity.class));
            }
        });

        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        consumptionListAdapter.refreshConsumptions();
        tvEmpty.setVisibility(consumptionListAdapter.getCount() == 0 ? View.VISIBLE : View.GONE);
    }


}
