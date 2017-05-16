package iss.nus.edu.medipalappln.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

import iss.nus.edu.medipalappln.R;
import iss.nus.edu.medipalappln.activity.Session;
import iss.nus.edu.medipalappln.dao.BioDataBaseAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PersonalBioForm.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PersonalBioForm#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonalBioForm extends Fragment implements View.OnClickListener,DatePickerDialog.OnDateSetListener
{

    Button btnAddPersonal;
    EditText dob;
    private int _day;
    private int _month;
    private int _birthYear;
    private Context _context;
   
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
   Session session;
    private BioDataBaseAdapter bioDataBaseAdapter;

    private OnFragmentInteractionListener mListener;

    public PersonalBioForm() {
    }


    // TODO: Rename and change types and number of parameters
    public static PersonalBioForm newInstance(String param1, String param2) {
        PersonalBioForm fragment = new PersonalBioForm();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.personalbioform, container, false);
        return view;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bioDataBaseAdapter = new BioDataBaseAdapter(this.getActivity());
        bioDataBaseAdapter.open();
        session=new Session(getContext());

    }


    private void insertEntry() {
        final EditText editTextname=(EditText)getView().findViewById(R.id.name);

        final EditText editTextBlood = (EditText)getView().findViewById(R.id.Bloodtype);
        TextView editTextDob = (TextView) getView().findViewById(R.id.dob);
        final EditText editTextPincode = (EditText) getView().findViewById(R.id.postalcode);
        final EditText editTextHeight = (EditText) getView().findViewById(R.id.Height);
        //final EditText editTextWeight = (EditText) getView().findViewById(R.id.weight);
        final EditText editTextAddress = (EditText)getView().findViewById(R.id.Address);
        //final EditText editTextPhone=(EditText)getView().findViewById(R.id.mobilenumber);
        final EditText editTextNRIC=(EditText)getView().findViewById(R.id.nric);
        final String name= editTextname.getText().toString();
        final String Blood = editTextBlood.getText().toString();
        final String nric=editTextNRIC.getText().toString();
        final String Pincode = editTextPincode.getText().toString();
        final String Dob = editTextDob.getText().toString();
        final String Height = editTextHeight.getText().toString();
        //final String Weight = editTextWeight.getText().toString();
        final String Address = editTextAddress.getText().toString();
        //final String Phone=editTextPhone.getText().toString();

        //App.user.addPersonal(session.username(),name,Dob,nric,Address,Pincode,Height,Blood,Phone, _context);
        //bioDataBaseAdapter.insertEntry(session.username(),Blood, Dob, Height, Weight, Address, Pincode,Phone);
        Toast.makeText(getContext(),"INSERTEDsuccesfully",Toast.LENGTH_LONG).show();
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
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Button btnAddPersonal = (Button) getView().findViewById(R.id.submit);
        btnAddPersonal.setOnClickListener(this);
        TextView editTextDOB=(TextView)view.findViewById(R.id.dob);
        editTextDOB.setOnClickListener(this);
        TextView textView = (TextView) getView().findViewById(R.id.MarqueeText);
        textView.setSelected(true);
        //textView.setSingleLine(true);
    }

    @Override
    public void onClick(final View v){
        switch(v.getId()){
            case R.id.submit:
                insertEntry();
                break;
            case R.id.dob:
                calenderEntry(view);
                break;
        }
    }


    public void updateLabel() {
        dob.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(_day).append("/").append(_month + 1).append("/").append(_birthYear).append(" "));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    // final TextView tvDt = (TextView) getView().findViewById(R.id.dob);

    //final  Calendar myCalendar = Calendar.getInstance();
    public void calenderEntry(View v) {
        LayoutInflater inflater = LayoutInflater.from( getContext() );
        //DatePicker datePicker = (DatePicker)inflater.inflate( R.layout.DatePicker, null );
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DATE);
        //DatePickerDialog datePicker;
        DatePickerDialog dialogDatePicker = new DatePickerDialog(v.getContext(),this,year, month,day);
        dialogDatePicker.getDatePicker();

        dialogDatePicker.getDatePicker().setCalendarViewShown(false);
        /* datePicker = new DatePickerDialog(v.getContext(),
                R.style.Theme_AppCompat_DayNight, datePickerListener,
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));*/
        dialogDatePicker.setCancelable(true);
        dialogDatePicker.getWindow().setLayout(500,500);
        dialogDatePicker.setTitle("Select your DOB");
        dialogDatePicker.show();


    }
   DatePickerDialog.OnDateSetListener datePickerListener;


           /*= new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


            /*String year1 = String.valueOf(year);
            String month1 = String.valueOf(month + 1);
            String day1 = String.valueOf(dayOfMonth);
            setText(year1,month1,day1);
            //

        }


    } ;*/


    public void setText(String year1, String month1, String day1) {
        final TextView tvDt = (TextView) view.findViewById(R.id.dob);
        tvDt.setText(day1 + "/" + month1 + "/" + year1);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        String year1 = String.valueOf(year);
        String month1 = String.valueOf(month + 1);
        String day1 = String.valueOf(dayOfMonth);
        setText(year1,month1,day1);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        bioDataBaseAdapter.close();
    }
}




