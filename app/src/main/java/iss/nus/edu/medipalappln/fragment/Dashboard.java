package iss.nus.edu.medipalappln.fragment;

/**
 * Created by Raghu on 7/3/17.
 */

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import iss.nus.edu.medipalappln.R;
import iss.nus.edu.medipalappln.activity.DashboardAdapter;
import iss.nus.edu.medipalappln.activity.Session;
import iss.nus.edu.medipalappln.activity.dashboardcontent;
import iss.nus.edu.medipalappln.medipal.App;

public class Dashboard extends Fragment implements IceDetails.OnFragmentInteractionListener {

    private RecyclerView recyclerView;
    private DashboardAdapter adapter;
    private List<dashboardcontent> dashboardcontentList;
    private static final String TAG = "Dashboard";
    private Context _context;
    private ImageButton ibtnMeasurement, ibtnAppointment,
            ibtnMedicine, ibtnHealthBio, ibtnPersonalBio;

    private OnFragmentInteractionListener mListener;




    public Dashboard() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.recyclerview, container, false);
        ViewGroup fragmentview=(ViewGroup)getView();
        dashboardcontentList = new ArrayList<>();
        adapter = new DashboardAdapter(getActivity().getApplicationContext(), dashboardcontentList);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(),2);
        recyclerView.setLayoutManager(mLayoutManager);
        FragmentManager fragmentManager=getFragmentManager();

        recyclerView.addItemDecoration(new Dashboard.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Click ME", Toast.LENGTH_LONG).show();
            }
        });

        prepareView();

        try {
            Glide.with(this).load(R.drawable.cover).into((ImageView) getActivity().findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //check if user has registered
        Session session = new Session(getContext());
        Log.i(TAG, "Session: " + session.username().toString());
        Log.i(TAG, "IDNo: " + App.user.getPersonalBio(getContext()));

        /*
        ibtnPersonalBio = (ImageButton) view.findViewById(R.id.ibtn_personal_bio);
        ibtnPersonalBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Switch to personal bio fragment");
                //PersonalBioForm fragment = new PersonalBioForm();
                PersonalBioFragment fragment = new PersonalBioFragment();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flContent, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        ibtnMeasurement = (ImageButton) view.findViewById(R.id.ibtn_measurement);
        ibtnMeasurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Switch to main measurement activity");
                Intent intent = new Intent(getActivity(), MainMeasurementActivity.class);
                startActivity(intent);
            }
        });

        ibtnMedicine = (ImageButton) view.findViewById(R.id.ibtn_medicine);
        ibtnMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Switch to main medicine activity");
                Intent intent = new Intent(getActivity(), MainMedicineActivity.class);
                startActivity(intent);

            }
        });

        ibtnAppointment = (ImageButton) view.findViewById(R.id.ibtn_appt);
        ibtnAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Switch to main appointment activity");
                Intent intent = new Intent(getActivity(), MainAppointmentActivity.class);
                startActivity(intent);


            }
        });

        ibtnHealthBio = (ImageButton) view.findViewById(R.id.ibtn_health_bio);
        ibtnHealthBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Switch to main health bio fragment");
                Intent intent = new Intent(getActivity(), HealthBioCatalogActivity.class);
                startActivity(intent);
            }
        });
        */

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (Dashboard.OnFragmentInteractionListener) context;
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
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    // callICE();


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void prepareView() {
        int[] covers = new int[]{
                R.drawable.album8,
                R.drawable.album3,
                R.drawable.album4,
                R.drawable.album5,
                R.drawable.album5,
                R.drawable.album6,
        };



        dashboardcontent a = new dashboardcontent("Personal Bio", covers[0]);
        dashboardcontentList.add(a);

        a = new dashboardcontent("Emergency Contact", covers[1]);
        dashboardcontentList.add(a);

        a = new dashboardcontent("Health Bio",  covers[2]);
        dashboardcontentList.add(a);

        a = new dashboardcontent("Medication", covers[3]);
        dashboardcontentList.add(a);

        a = new dashboardcontent("Appointment", covers[4]);
        dashboardcontentList.add(a);

        a = new dashboardcontent("Measurement", covers[5]);
        dashboardcontentList.add(a);;

        adapter.notifyDataSetChanged();

    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}