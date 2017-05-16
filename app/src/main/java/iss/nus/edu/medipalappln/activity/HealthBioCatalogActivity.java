package iss.nus.edu.medipalappln.activity;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import iss.nus.edu.medipalappln.R;
import iss.nus.edu.medipalappln.dao.HealthBioContract.HealthBioEntry;
import android.support.design.widget.FloatingActionButton;
import iss.nus.edu.medipalappln.adapter.HealthBioCursorAdapter;

/**
 * Created by kiruba on 3/21/17.
 */

public class HealthBioCatalogActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int HEALTH_LOADER = 0;

    HealthBioCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_bio_catalog);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HealthBioCatalogActivity.this, HealthBioEditorActivity.class);
                startActivity(intent);
            }
        });

        ListView healthBioListView = (ListView) findViewById(R.id.list);

        View emptyView = findViewById(R.id.empty_view);
        healthBioListView.setEmptyView(emptyView);

        mCursorAdapter = new HealthBioCursorAdapter(this, null);
        healthBioListView.setAdapter(mCursorAdapter);

        healthBioListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(HealthBioCatalogActivity.this, HealthBioEditorActivity.class);

                Uri currentPetUri = ContentUris.withAppendedId(HealthBioEntry.CONTENT_URI, id);

                intent.setData(currentPetUri);

                startActivity(intent);
            }
        });
        getLoaderManager().initLoader(HEALTH_LOADER, null, this);
    }

    private void deleteAllHealthBio() {
        int rowsDeleted = getContentResolver().delete(HealthBioEntry.CONTENT_URI, null, null);
        Log.v("CatalogActivity", rowsDeleted + " rows deleted from healthbio database");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_health_bio_catalog, menu);
        return true;
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

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                HealthBioEntry._ID,
                HealthBioEntry.COLUMN_MEDICAL_CONDITION,
                HealthBioEntry.COLUMN_START_DATE,
                //
                HealthBioEntry.COLUMN_CONDITION_TYPE
        };

        return new CursorLoader(this,
                HealthBioEntry.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}
