package iss.nus.edu.medipalappln.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import iss.nus.edu.medipalappln.R;
import iss.nus.edu.medipalappln.activity.HealthBioEditorActivity;
import iss.nus.edu.medipalappln.adapter.HealthBioCursorAdapter;
import iss.nus.edu.medipalappln.dao.HealthBioContract;

//import android.app.LoaderManager;
//import android.content.CursorLoader;
//import android.content.Loader;

public class HealthBioCatalogFragment extends ListFragment
                implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "HealthBioCFragment";

    private LoaderManager.LoaderCallbacks<Cursor> callBacks;

    private static final int HEALTH_LOADER = 0;
    HealthBioCursorAdapter mCursorAdapter;

    private OnFragmentInteractionListener mListener;

    public HealthBioCatalogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_health_bio_catalog, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HealthBioEditorActivity.class);
                startActivity(intent);
            }
        });

        /*ListView healthBioListView = (ListView) view.findViewById(R.id.list);

        View emptyView = view.findViewById(R.id.empty_view);
        //healthBioListView.setEmptyView(emptyView);

        mCursorAdapter = new HealthBioCursorAdapter(getContext(), null);
        //healthBioListView.setAdapter(mCursorAdapter);

        //healthBioListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), HealthBioEditorActivity.class);

                Uri currentPetUri = ContentUris.withAppendedId(HealthBioContract.HealthBioEntry.CONTENT_URI, id);

                intent.setData(currentPetUri);

                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(HEALTH_LOADER, null, this);
        */
        return view;
    }

    private void deleteAllHealthBio() {
        int rowsDeleted = getActivity().getContentResolver()
                .delete(HealthBioContract.HealthBioEntry.CONTENT_URI, null, null);
        Log.v("CatalogActivity", rowsDeleted + " rows deleted from healthbio database");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_health_bio_catalog, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_all_entries:
                deleteAllHealthBio();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /*
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri contentUri = HealthBioContract.HealthBioEntry.CONTENT_URI;
        String[] projection = {
                HealthBioContract.HealthBioEntry._ID,
                HealthBioContract.HealthBioEntry.COLUMN_MEDICAL_CONDITION,
                HealthBioContract.HealthBioEntry.COLUMN_START_DATE,
                HealthBioContract.HealthBioEntry.COLUMN_CONDITION_TYPE
        };
        //public android.content.Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        //return new CursorLoader(this, HealthBioContract.HealthBioEntry.CONTENT_URI, projection, null, null, null);
        Log.i(TAG, "Load Cursor");
        return new CursorLoader(getActivity(), contentUri, projection, null, null, null);
    }
    */

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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri contentUri = HealthBioContract.HealthBioEntry.CONTENT_URI;
        String[] projection = {
                HealthBioContract.HealthBioEntry._ID,
                HealthBioContract.HealthBioEntry.COLUMN_MEDICAL_CONDITION,
                HealthBioContract.HealthBioEntry.COLUMN_START_DATE,
                HealthBioContract.HealthBioEntry.COLUMN_CONDITION_TYPE
        };
        //public android.content.Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        //return new CursorLoader(this, HealthBioContract.HealthBioEntry.CONTENT_URI, projection, null, null, null);
        Log.i(TAG, "Load Cursor");
        return new CursorLoader(getActivity(), contentUri, projection, null, null, null);
        //return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor((Cursor)data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
