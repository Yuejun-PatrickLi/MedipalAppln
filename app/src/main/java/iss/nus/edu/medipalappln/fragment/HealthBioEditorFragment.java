package iss.nus.edu.medipalappln.fragment;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import iss.nus.edu.medipalappln.R;
import iss.nus.edu.medipalappln.dao.HealthBioContract;

public class HealthBioEditorFragment extends Fragment implements LoaderManager.LoaderCallbacks<Object> {

    private static final String TAG = "HealthBioEditorFragment";

    private static final int EXISTING_HEALTH_BIO_LOADER = 0;
    private Uri mCurrentHealthUri;
    private EditText mConEditText;

    private EditText etDate;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
    Calendar currentCal = Calendar.getInstance();
    Calendar selectedDate = Calendar.getInstance();

    private Spinner mConTypeSpinner;
    private int mConType = HealthBioContract.HealthBioEntry.SELECT_VALUE;
    private boolean mHealthChanged = false;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mHealthChanged = true;
            return false;
        }
    };

    private OnFragmentInteractionListener mListener;

    public HealthBioEditorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_health_bio_editor, container, false);
        //
        etDate = (EditText) view.findViewById(R.id.et_select_date);
        mConTypeSpinner = (Spinner) view.findViewById(R.id.spinner_condition_type);

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
                        new DatePickerDialog(getContext(), onDateSetListener,
                                currentCal.get(Calendar.YEAR), currentCal.get(Calendar.MONTH),
                                currentCal.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        Intent intent = getActivity().getIntent();
        mCurrentHealthUri = intent.getData();

        if (mCurrentHealthUri == null) {
            getActivity().setTitle(getString(R.string.editor_activity_title_add));
            getActivity().invalidateOptionsMenu();
        } else {
            getActivity().setTitle(getString(R.string.editor_activity_title_edit));
            getLoaderManager().initLoader(EXISTING_HEALTH_BIO_LOADER, null, this);
        }

        mConEditText = (EditText) view.findViewById(R.id.edit_health_con);
        etDate = (EditText) view.findViewById(R.id.et_select_date);
        mConTypeSpinner = (Spinner) view.findViewById(R.id.spinner_condition_type);

        mConEditText.setOnTouchListener(mTouchListener);
        etDate.setOnTouchListener(mTouchListener);
        mConTypeSpinner.setOnTouchListener(mTouchListener);

        setupSpinner();

        return view;
    }

    private void setupSpinner() {

        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mConTypeSpinner.setAdapter(genderSpinnerAdapter);
        mConTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.spinner_condition))) {
                        mConType = HealthBioContract.HealthBioEntry.CONDITION;
                    } else if (selection.equals(getString(R.string.spinner_allergy))) {
                        mConType = HealthBioContract.HealthBioEntry.ALLERGY;
                    } else {
                        mConType = HealthBioContract.HealthBioEntry.SELECT_VALUE;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mConType = HealthBioContract.HealthBioEntry.SELECT_VALUE;
            }
        });
    }

    private void saveHealthBio() {
        String medconString = mConEditText.getText().toString().trim();
        String stdateString = etDate.getText().toString().trim();

        if (mCurrentHealthUri == null &&
                TextUtils.isEmpty(medconString) && TextUtils.isEmpty(stdateString) &&
                mConType == HealthBioContract.HealthBioEntry.SELECT_VALUE) {
            return;
        }

        ContentValues values = new ContentValues();
        values.put(HealthBioContract.HealthBioEntry.COLUMN_MEDICAL_CONDITION, medconString);
        values.put(HealthBioContract.HealthBioEntry.COLUMN_START_DATE, stdateString);
        values.put(HealthBioContract.HealthBioEntry.COLUMN_CONDITION_TYPE, mConType);

        if (mCurrentHealthUri == null) {
            Uri newUri = getActivity().getContentResolver().insert(HealthBioContract.HealthBioEntry.CONTENT_URI, values);
            if (newUri == null) {
                Toast.makeText(getActivity(), getString(R.string.editor_insert_health_bio_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), getString(R.string.editor_insert_health_bio_successful),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            int rowsAffected = getActivity().getContentResolver().update(mCurrentHealthUri, values, null, null);
            if (rowsAffected == 0) {
                Toast.makeText(getActivity(), getString(R.string.editor_update_health_bio_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), getString(R.string.editor_updt_hlth_bio_success),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (mCurrentHealthUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                saveHealthBio();
                //finish();
                return true;
            case R.id.action_delete:
                showDeleteConfirmationDialog();
                return true;
            case android.R.id.home:
                if (!mHealthChanged) {
                    NavUtils.navigateUpFromSameTask(getActivity());
                    return true;
                }

                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                NavUtils.navigateUpFromSameTask(getActivity());
                            }
                        };
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
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

    public void onLoaderReset(Loader<Cursor> loader) {
        mConEditText.setText("");
        etDate.setText("");
        mConTypeSpinner.setSelection(0);
    }
    */

    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
            int rowsDeleted = getActivity().getContentResolver().delete(mCurrentHealthUri, null, null);
            if (rowsDeleted == 0) {
                Toast.makeText(getActivity(), getString(R.string.editor_delete_health_bio_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), getString(R.string.editor_delete_health_bio_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
        //finish();
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
