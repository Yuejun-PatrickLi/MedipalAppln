package iss.nus.edu.medipalappln.fragment;
/**
 * Created by Raghu on 7/3/17.
 */

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import iss.nus.edu.medipalappln.R;
import iss.nus.edu.medipalappln.adapter.EmergencyListAdapter;
import iss.nus.edu.medipalappln.medipal.Emergency;

public class ShowEmergency extends Fragment implements View.OnClickListener {
    private List<Emergency> emergencies = new ArrayList<>();
    private static final String TAG = "View Emergency";
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView textViewEmpty;
    private EmergencyListAdapter emergenyListAdapter;
    private OnFragmentInteractionListener mListener;
    private TextView tvPhone,tvName,tvRelation;
    private TextView tvPhone2,tvName2,tvRelation2;
    private TextView tvPhone3,tvName3,getTvRelation2;
    private String name="";private String phone="";private String relation="";
    private String name1="";private String phone1="";private String relation1="";
    private String name2="";private String phone2="";private String relation2="";
    public ShowEmergency() {
        // Required empty public constructor
    }

    public static ShowEmergency newInstance(String param1, String param2) {
        ShowEmergency fragment = new ShowEmergency();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.emergencylistice, container, false);
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        ListView listView= (ListView) view.findViewById(R.id.list_view_ICE);
        emergenyListAdapter=new EmergencyListAdapter(getActivity().getApplicationContext());
        listView.setAdapter(emergenyListAdapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
        /*Emergency e1 =App.user.getEmergency("1",getContext());
        //Emergency e2 =App.user.getEmergency("2",getContext());
        Emergency e3 =App.user.getEmergency("3",getContext());
        tvName = (TextView) getView().findViewById(R.id.text_view_name);
        tvPhone=(TextView)getView().findViewById(R.id.phonenum);
        tvRelation=(TextView)getView().findViewById(R.id.relation);
        Button call=(Button)getView().findViewById(R.id.callButton);
        // call.setOnClickListener(this);
        tvName2 = (TextView) getView().findViewById(R.id.text_view_name2);
        tvPhone2=(TextView)getView().findViewById(R.id.phonenum2);
        tvRelation2=(TextView)getView().findViewById(R.id.relation2);
        tvName2.setText("");
        tvPhone2.setText("");
        tvPhone3.setText("");
        Button call2=(Button)getView().findViewById(R.id.callButton2);
        call.setOnClickListener(this);
        TextView tvName3 = (TextView) getView().findViewById(R.id.text_view_name3);
        tvPhone3=(TextView)getView().findViewById(R.id.phonenum3);
        TextView tvRelation3=(TextView)getView().findViewById(R.id.relation3);
        Button call3=(Button)getView().findViewById(R.id.callButton3);
        call.setOnClickListener(this);
        //viewHolder.btnRemove = (Button) convertView.findViewById(R.id.btn_remove);
        name=e1.getName();phone=e1.getPhone();relation=e1.getDesc();
       // name1=e2.getName();phone1=e2.getPhone();relation1=e2.getDesc();
        name2=e3.getName();phone2=e3.getPhone();relation2=e3.getDesc();
        tvName.setText(""+name);
        tvPhone.setText(""+phone);
        tvRelation.setText(""+relation);
        tvName2.setText(""+name1);
        tvPhone2.setText(""+phone1);
        tvRelation2.setText(""+relation1);
        tvName3.setText(""+name2);
        tvPhone3.setText(""+phone2);
        tvRelation3.setText(""+relation2);
*/



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (ShowEmergency.OnFragmentInteractionListener) context;
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

    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.callButton:
                //call();
        }


    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }




    @Override
    public void onResume() {
        super.onResume();
        // emergenyListAdapter.refreshList();
        //textViewEmpty.setVisibility(emergenyListAdapter.getCount() == 0 ? View.VISIBLE : View.GONE);
    }
}