package iss.nus.edu.medipalappln.fragment;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import iss.nus.edu.medipalappln.R;
import iss.nus.edu.medipalappln.activity.HealthBioEditorActivity;
import iss.nus.edu.medipalappln.adapter.HealthBioCursorAdapter;
import iss.nus.edu.medipalappln.dao.HealthBioContract;

public class MainHealthBioFragment extends Fragment implements LoaderManager.LoaderCallbacks<Object> {

    private static final String TAG = "MainHealthBioFragment";

    private static final int HEALTH_LOADER = 0;

    HealthBioCursorAdapter mCursorAdapter;

    private OnFragmentInteractionListener mListener;

    public MainHealthBioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_health_bio_catalog, container, false);
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(HealthBioCatalogActivity.this, HealthBioEditorActivity.class);
                //startActivity(intent);
                HealthBioEditorFragment fragment = new HealthBioEditorFragment();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flContent, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        ListView healthBioListView = (ListView) view.findViewById(R.id.list);

        View emptyView = view.findViewById(R.id.empty_view);
        healthBioListView.setEmptyView(emptyView);

        mCursorAdapter = new HealthBioCursorAdapter(getContext(), null);
        healthBioListView.setAdapter(mCursorAdapter);

        healthBioListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), HealthBioEditorActivity.class);

                Uri currentPetUri = ContentUris.withAppendedId(HealthBioContract.HealthBioEntry.CONTENT_URI, id);

                intent.setData(currentPetUri);

                startActivity(intent);
            }
        });
        getActivity().getLoaderManager().initLoader(HEALTH_LOADER, null, this);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
            mListener = (MainHealthBioFragment.OnFragmentInteractionListener) context;
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
    public Loader<Object> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {

    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
