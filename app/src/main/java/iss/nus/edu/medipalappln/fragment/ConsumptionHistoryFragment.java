package iss.nus.edu.medipalappln.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import iss.nus.edu.medipalappln.R;
import iss.nus.edu.medipalappln.activity.AddConsumptionActivity;
import iss.nus.edu.medipalappln.adapter.ConsumptionListAdapter;


public class ConsumptionHistoryFragment extends Fragment  {
    private TextView tvEmpty;
    private ConsumptionListAdapter consumptionListAdapter;
    private ListView consumptionList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View fragmentView = inflater.inflate(R.layout.fragment_consumption_history, container, false);

        consumptionList = (ListView) fragmentView.findViewById(R.id.lv_booking_list);
        consumptionListAdapter = new ConsumptionListAdapter(getActivity());
        consumptionList.setAdapter(consumptionListAdapter);

        tvEmpty = (TextView) fragmentView.findViewById(R.id.empty_value);

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
