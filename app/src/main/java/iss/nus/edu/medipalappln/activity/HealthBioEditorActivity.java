package iss.nus.edu.medipalappln.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Calendar;

import iss.nus.edu.medipalappln.R;
import iss.nus.edu.medipalappln.dao.HealthBioContract.HealthBioEntry;

/**
 * Created by kiruba on 3/21/17.
 */

public class HealthBioEditorActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_HEALTH_BIO_LOADER = 0;
    private Uri mCurrentHealthUri;
    private EditText mConEditText;

    private EditText etDate;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
    Calendar currentCal = Calendar.getInstance();
    Calendar selectedDate = Calendar.getInstance();

    private Spinner mConTypeSpinner;
    private int mConType = HealthBioEntry.SELECT_VALUE;
    private boolean mHealthChanged = false;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mHealthChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_bio_editor);

        //
        etDate = (EditText) findViewById(R.id.et_select_date);
        mConTypeSpinner = (Spinner) findViewById(R.id.spinner_condition_type);

        //
        etDate.setText(dateFormatter.format(selectedDate.getTime()));
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener onDateSetListener =
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                selectedDate = calendar;
                                etDate.setText(dateFormatter.format(calendar.getTime()));
                            }
                        };
                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(HealthBioEditorActivity.this, onDateSetListener,
                                currentCal.get(Calendar.YEAR), currentCal.get(Calendar.MONTH),
                                currentCal.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        Intent intent = getIntent();
        mCurrentHealthUri = intent.getData();

        if (mCurrentHealthUri == null) {
            setTitle(getString(R.string.editor_activity_title_add));
            invalidateOptionsMenu();
        } else {
            setTitle(getString(R.string.editor_activity_title_edit));
            getLoaderManager().initLoader(EXISTING_HEALTH_BIO_LOADER, null, this);
        }

        mConEditText = (EditText) findViewById(R.id.edit_health_con);
        etDate = (EditText) findViewById(R.id.et_select_date);
        mConTypeSpinner = (Spinner) findViewById(R.id.spinner_condition_type);

        mConEditText.setOnTouchListener(mTouchListener);
        etDate.setOnTouchListener(mTouchListener);
        mConTypeSpinner.setOnTouchListener(mTouchListener);

        setupSpinner();
    }


    private void setupSpinner() {

        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mConTypeSpinner.setAdapter(genderSpinnerAdapter);
        mConTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.spinner_condition))) {
                        mConType = HealthBioEntry.CONDITION;
                    } else if (selection.equals(getString(R.string.spinner_allergy))) {
                        mConType = HealthBioEntry.ALLERGY;
                    } else {
                        mConType = HealthBioEntry.SELECT_VALUE;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mConType = HealthBioEntry.SELECT_VALUE;
            }
        });
    }


    private void saveHealthBio() {
        String medconString = mConEditText.getText().toString().trim();
        String stdateString = etDate.getText().toString().trim();

        if (mCurrentHealthUri == null &&
                TextUtils.isEmpty(medconString) && TextUtils.isEmpty(stdateString) &&
                mConType == HealthBioEntry.SELECT_VALUE) {
            return;
        }

        ContentValues values = new ContentValues();
        values.put(HealthBioEntry.COLUMN_MEDICAL_CONDITION, medconString);
        values.put(HealthBioEntry.COLUMN_START_DATE, stdateString);
        values.put(HealthBioEntry.COLUMN_CONDITION_TYPE, mConType);

        if (mCurrentHealthUri == null) {
            Uri newUri = getContentResolver().insert(HealthBioEntry.CONTENT_URI, values);
            if (newUri == null) {
                Toast.makeText(this, getString(R.string.editor_insert_health_bio_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.editor_insert_health_bio_successful),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            int rowsAffected = getContentResolver().update(mCurrentHealthUri, values, null, null);
            if (rowsAffected == 0) {
                Toast.makeText(this, getString(R.string.editor_update_health_bio_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.editor_updt_hlth_bio_success),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_health_bio_editor, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (mCurrentHealthUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveHealthBio();
                finish();
                return true;
            case R.id.action_delete:
                showDeleteConfirmationDialog();
                return true;
            case android.R.id.home:
                if (!mHealthChanged) {
                    NavUtils.navigateUpFromSameTask(HealthBioEditorActivity.this);
                    return true;
                }

                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                NavUtils.navigateUpFromSameTask(HealthBioEditorActivity.this);
                            }
                        };
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!mHealthChanged) {
            super.onBackPressed();
            return;
        }

        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                };
        showUnsavedChangesDialog(discardButtonClickListener);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                HealthBioEntry._ID,
                HealthBioEntry.COLUMN_MEDICAL_CONDITION,
                HealthBioEntry.COLUMN_START_DATE,
                HealthBioEntry.COLUMN_CONDITION_TYPE
        };

        return new CursorLoader(this, mCurrentHealthUri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        if (cursor.moveToFirst()) {
            int medconColumnIndex = cursor.getColumnIndex(HealthBioEntry.COLUMN_MEDICAL_CONDITION);
            int stdateColumnIndex = cursor.getColumnIndex(HealthBioEntry.COLUMN_START_DATE);
            int contypeColumnIndex = cursor.getColumnIndex(HealthBioEntry.COLUMN_CONDITION_TYPE);

            String medcon = cursor.getString(medconColumnIndex);
            String stdate = cursor.getString(stdateColumnIndex);
            int contype = cursor.getInt(contypeColumnIndex);
            //String contype = cursor.getInt(contypeColumnIndex);


            // Update the views on the screen with the values from the database
            mConEditText.setText(medcon );
            etDate.setText(stdate);
            switch (contype) {
                case HealthBioEntry.CONDITION:
                    mConTypeSpinner.setSelection(1);
                    break;
                case HealthBioEntry.ALLERGY:
                    mConTypeSpinner.setSelection(2);
                    break;
                default:
                    mConTypeSpinner.setSelection(0);
                    break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mConEditText.setText("");
        etDate.setText("");
        mConTypeSpinner.setSelection(0);
    }

    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
         builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteHealthBio();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteHealthBio() {
        if (mCurrentHealthUri != null) {
            int rowsDeleted = getContentResolver().delete(mCurrentHealthUri, null, null);
            if (rowsDeleted == 0) {
                Toast.makeText(this, getString(R.string.editor_delete_health_bio_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.editor_delete_health_bio_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }
}

