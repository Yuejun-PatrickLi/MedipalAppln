package iss.nus.edu.medipalappln.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import iss.nus.edu.medipalappln.R;
import iss.nus.edu.medipalappln.activity.Session;
import iss.nus.edu.medipalappln.adapter.EmergencyListAdapter;
import iss.nus.edu.medipalappln.dao.EmergencyDataBaseAdapter;
import iss.nus.edu.medipalappln.medipal.App;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IceDetails.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IceDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IceDetails extends Fragment implements View.OnClickListener,OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText name,relation;
    private EditText phonenumber;


    private EmergencyDataBaseAdapter emergencyDataBaseAdapter;
    private Session session;
    public String spinnerText;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EmergencyListAdapter emergencyListAdapter;
    private Spinner priority;
    public String spinnerItem;
    //private EmergencyDataBaseAdapter emergencyDataBaseAdapter;
    private OnFragmentInteractionListener mListener;
    List<String> Priorties = new ArrayList<String>();
    public IceDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IceDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static IceDetails newInstance(String param1, String param2) {
        IceDetails fragment = new IceDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.icedetails,container,false);

        return view;

            }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emergencyDataBaseAdapter=new EmergencyDataBaseAdapter(this.getActivity());
        //emergencyDataBaseAdapter.close();
        emergencyDataBaseAdapter.open();
        session=new Session(this.getActivity());


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        name=(EditText)getView().findViewById(R.id.contactName);
        relation=(EditText)getView().findViewById(R.id.contactRelation);
        phonenumber=(EditText)getView().findViewById(R.id.contactPhone);
        final Button submit=(Button)getView().findViewById(R.id.submitphone);
        submit.setOnClickListener(this);

        addItemsOnSpinner2();



    }

    public void addItemsOnSpinner2() {

        // Spinner element
        priority = (Spinner) getView().findViewById(R.id.contactPriority);

        // Spinner click listener
        priority.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> Priorties = new ArrayList<String>();
        Priorties.add("1");
        Priorties.add("2");
        Priorties.add("3");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, Priorties);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        priority.setAdapter(dataAdapter);



    }
    @Override
    public void onClick(final View v) {
        App.user.addEmergency(1,name.getText().toString().trim(),phonenumber.getText().toString().trim(),spinnerItem,relation.getText().toString().trim(),getActivity());
        Toast.makeText(getActivity().getApplicationContext(),"Insert successfull",Toast.LENGTH_LONG).show();


    }




    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");


        }
    }


    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
        spinnerItem = parent.getItemAtPosition(position).toString();
        priority.setSelection(position);


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        emergencyDataBaseAdapter.close();
    }

}
