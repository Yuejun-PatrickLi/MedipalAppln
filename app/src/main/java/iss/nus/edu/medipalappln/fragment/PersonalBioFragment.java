package iss.nus.edu.medipalappln.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import iss.nus.edu.medipalappln.R;
import iss.nus.edu.medipalappln.activity.Session;
import iss.nus.edu.medipalappln.medipal.App;
import iss.nus.edu.medipalappln.medipal.PersonalBio;

public class PersonalBioFragment extends Fragment
        implements View.OnClickListener {

    Button btnAddPersonal, btnDelete, btnDate;
    EditText dob;
    private boolean isRegister = false;
    private PersonalBio personalBio;

    private EditText editTextname;
    private EditText editTextBlood;
    private TextView editTextDob;
    private EditText editTextPincode;
    private EditText editTextHeight;
    private EditText editTextAddress;
    private EditText editTextPhone;
    private EditText editTextNRIC;

    Session session;
    //private BioDataBaseAdapter bioDataBaseAdapter;

    private OnFragmentInteractionListener mListener;

    public PersonalBioFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.personalbioform, container, false);

        btnDate = (Button) view.findViewById(R.id.dob_date);
        btnAddPersonal = (Button) view.findViewById(R.id.submit);
        //btnDelete = (Button) view.findViewById(R.id.delete);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calenderEntry(v);
            }
        });

        return view;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //bioDataBaseAdapter = new BioDataBaseAdapter(this.getActivity());
        //bioDataBaseAdapter.open();
        editTextname = (EditText)getView().findViewById(R.id.name);
        editTextBlood = (EditText)getView().findViewById(R.id.Bloodtype);
        editTextDob = (TextView) getView().findViewById(R.id.dob);
        editTextPincode = (EditText) getView().findViewById(R.id.postalcode);
        editTextHeight = (EditText) getView().findViewById(R.id.Height);
        editTextAddress = (EditText)getView().findViewById(R.id.Address);
        //editTextPhone = (EditText)getView().findViewById(R.id.mobilenumber);
        editTextNRIC = (EditText)getView().findViewById(R.id.nric);
        btnAddPersonal = (Button) getView().findViewById(R.id.submit);
        //btnDelete = (Button) getView().findViewById(R.id.delete);

        personalBio = App.user.getPersonalBio(getContext());

        if (personalBio == null) {
            Toast.makeText(getContext(), "Please register", Toast.LENGTH_LONG).show();
            btnAddPersonal.setText(R.string.text_register);
            isRegister = true;
        } else {
            editTextname.setText(personalBio.getName());
            editTextBlood.setText(personalBio.getBloodType());
            editTextDob.setText(personalBio.getDOB());
            editTextPincode.setText(personalBio.getPostalCode());
            editTextHeight.setText(personalBio.getHeight().toString());
            editTextAddress.setText(personalBio.getAddress());
            editTextNRIC.setText(personalBio.getIDNo());
            btnAddPersonal.setText(R.string.text_update);
            isRegister = false;
        }

        session = new Session(getContext());
    }

    private void insertEntry() {
        final String name = editTextname.getText().toString();
        final String blood = editTextBlood.getText().toString();
        final String nric = editTextNRIC.getText().toString();
        final String postal = editTextPincode.getText().toString();
        final String dob = editTextDob.getText().toString();
        final String height = editTextHeight.getText().toString();
        final String address = editTextAddress.getText().toString();

        PersonalBio p = new PersonalBio();
        p.setName(name);
        p.setAddress(address);
        p.setBloodType(blood);
        p.setDOB(dob);
        p.setHeight(Integer.parseInt(height));
        p.setPostalCode(postal);
        p.setIDNo(nric);

        if (isRegister) {
            if (!App.user.createPersonalBio(getContext(), p)) {
                Toast.makeText(getContext(), "User already registered", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "User registration successful", Toast.LENGTH_LONG).show();
            }
        } else {
            if (!App.user.updatePersonalBio(getContext(), p)) {
                Toast.makeText(getContext(), "Update failed", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Update successful", Toast.LENGTH_LONG).show();
            }
        }
    }


    private void deleteEntry() {
        final String nric = editTextNRIC.getText().toString();

        Toast.makeText(getContext(), "User " + personalBio.getID(), Toast.LENGTH_LONG).show();

        if (!App.user.deletePersonalBio(getContext(), personalBio.getID().toString())) {
            Toast.makeText(getContext(), "Delete failed", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "Personal Bio delete successful", Toast.LENGTH_LONG).show();
        }
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
            case R.id.dob_date:
                calenderEntry(view);
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void calenderEntry(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            final DatePicker datePicker = new DatePicker(getContext());
            datePicker.setCalendarViewShown(false);

            builder.setTitle("Measurement date");
            builder.setView(datePicker);
            builder.setNegativeButton("Cancel", null);
            builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String year, month, day;
                    year = String.format("%04d", datePicker.getYear());
                    month = String.format("%02d", datePicker.getMonth()+1); //index begin from 0
                    day = String.format("%02d", datePicker.getDayOfMonth());

                    editTextDob.setText(year + "-" + month + "-" + day);
                }
            });

            builder.show();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //bioDataBaseAdapter.close();
    }
}




