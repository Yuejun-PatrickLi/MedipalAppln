package iss.nus.edu.medipalappln.dao;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import iss.nus.edu.medipalappln.dao.HealthBioContract.HealthBioEntry;

/**
 * Created by kiruba on 3/21/17.
 */

public class HealthBioProvider extends ContentProvider {

    public static final String LOG_TAG = HealthBioProvider.class.getSimpleName();
    private static final int HEALTH = 100;
    private static final int HEALTH_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(HealthBioContract.CONTENT_AUTHORITY, HealthBioContract.PATH_HEALTH, HEALTH);

        sUriMatcher.addURI(HealthBioContract.CONTENT_AUTHORITY, HealthBioContract.PATH_HEALTH + "/#", HEALTH_ID);
    }

    private HealthBioDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new HealthBioDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case HEALTH:
                cursor = database.query(HealthBioEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case HEALTH_ID:
                selection = HealthBioEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                cursor = database.query(HealthBioEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case HEALTH:
                return insertHealthBio(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    // Insert
    private Uri insertHealthBio(Uri uri, ContentValues values) {
        String medcondition = values.getAsString(HealthBioEntry.COLUMN_MEDICAL_CONDITION);
        if (medcondition == null) {
            throw new IllegalArgumentException("Medical Condition info required");
        }
        Integer conditiontyp = values.getAsInteger(HealthBioEntry.COLUMN_CONDITION_TYPE);
        if (conditiontyp == null || !HealthBioEntry.isValidConType(conditiontyp)) {
            throw new IllegalArgumentException("Condition Type Required");
        }
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        long id = database.insert(HealthBioEntry.TABLE_NAME, null, values);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case HEALTH:
                return updateHealthBio(uri, contentValues, selection, selectionArgs);
            case HEALTH_ID:
                selection = HealthBioEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateHealthBio(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    //Update
    private int updateHealthBio(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.containsKey(HealthBioEntry.COLUMN_MEDICAL_CONDITION)) {
            String medcondition = values.getAsString(HealthBioEntry.COLUMN_MEDICAL_CONDITION);
            if (medcondition == null) {
                throw new IllegalArgumentException("Medical Condition info required");
            }
        }

        if (values.containsKey(HealthBioEntry.COLUMN_CONDITION_TYPE)) {
            Integer conditiontyp = values.getAsInteger(HealthBioEntry.COLUMN_CONDITION_TYPE);
            if (conditiontyp == null || !HealthBioEntry.isValidConType(conditiontyp)) {
                throw new IllegalArgumentException("Condition Type Required");
            }
        }
        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsUpdated = database.update(HealthBioEntry.TABLE_NAME, values, selection, selectionArgs);

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }


    //Delete
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case HEALTH:
                rowsDeleted = database.delete(HealthBioEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case HEALTH_ID:
                selection = HealthBioEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(HealthBioEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case HEALTH:
                return HealthBioEntry.CONTENT_LIST_TYPE;
            case HEALTH_ID:
                return HealthBioEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}
