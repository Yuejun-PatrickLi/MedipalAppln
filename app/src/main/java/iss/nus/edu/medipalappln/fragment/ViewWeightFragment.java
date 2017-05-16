package iss.nus.edu.medipalappln.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import iss.nus.edu.medipalappln.R;
import iss.nus.edu.medipalappln.adapter.WeightListAdapter;

public class ViewWeightFragment extends Fragment {

    private static final String TAG = "ViewWeightFragment";

    private WeightListAdapter weightListAdapter;
    private TextView textViewEmpty, textViewHeader, textViewStartDate, textViewEndDate;
    private Button btnStartDate, btnEndDate, btnFilter;

    private OnFragmentInteractionListener mListener;

    public ViewWeightFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_measurement_list, container, false);
        ListView listView = (ListView) view.findViewById(R.id.list_view_measurement);
        textViewEmpty = (TextView) view.findViewById(R.id.text_view_empty);
        btnStartDate = (Button) view.findViewById(R.id.btn_start_date);
        btnEndDate = (Button) view.findViewById(R.id.btn_end_date);
        btnFilter = (Button) view.findViewById(R.id.btn_filter);
        textViewStartDate = (TextView) view.findViewById(R.id.text_view_start_date);
        textViewEndDate = (TextView) view.findViewById(R.id.text_view_end_date);
        textViewHeader = (TextView) view.findViewById(R.id.text_header);
        textViewHeader.setText("weight in kg");

        weightListAdapter = new WeightListAdapter(getActivity(),
                R.layout.measurement_row_layout, R.id.text_view_empty);
        listView.setAdapter(weightListAdapter);

        btnStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final DatePicker datePicker = new DatePicker(getContext());
                datePicker.setCalendarViewShown(false);

                builder.setTitle("Start date");
                builder.setView(datePicker);
                builder.setNegativeButton("Cancel", null);
                builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String year, month, day;
                        year = String.format("%04d", datePicker.getYear());
                        month = String.format("%02d", datePicker.getMonth()+1); //index begin from 0
                        day = String.format("%02d", datePicker.getDayOfMonth());

                        textViewStartDate.setText(year + "-" + month + "-" + day);
                    }
                });

                builder.show();
            }
        });

        btnEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final DatePicker datePicker = new DatePicker(getContext());
                datePicker.setCalendarViewShown(false);

                builder.setTitle("End date");
                builder.setView(datePicker);
                builder.setNegativeButton("Cancel", null);
                builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String year, month, day;
                        year = String.format("%04d", datePicker.getYear());
                        month = String.format("%02d", datePicker.getMonth()+1); //index begin from 0
                        day = String.format("%02d", datePicker.getDayOfMonth());

                        textViewEndDate.setText(year + "-" + month + "-" + day);
                    }
                });

                builder.show();
            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startDate = textViewStartDate.getText().toString().trim();
                String endDate = textViewEndDate.getText().toString().trim();
                Log.i(TAG, "Record(s) from " + startDate + " - " + endDate);

                weightListAdapter.refreshList(startDate, endDate);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (ViewWeightFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {
        super.onResume();
        weightListAdapter.refreshList();
        textViewEmpty.setVisibility(weightListAdapter.getCount() == 0 ? View.VISIBLE : View.GONE);
    }
}
