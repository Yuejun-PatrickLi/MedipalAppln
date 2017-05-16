package iss.nus.edu.medipalappln.dao;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by kiruba on 3/21/17.
 */

public final class HealthBioContract {

    private HealthBioContract() {}

    public static final String CONTENT_AUTHORITY = "iss.nus.edu.medipalappln";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_HEALTH = "health";

    public static final class HealthBioEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_HEALTH);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_HEALTH;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_HEALTH;

        public final static String TABLE_NAME = "health";

        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_MEDICAL_CONDITION ="condition";
        public final static String COLUMN_START_DATE = "startdate";
        public final static String COLUMN_CONDITION_TYPE = "conditiontype";

        public static final int SELECT_VALUE = 0;
        public static final int CONDITION = 1;
        public static final int ALLERGY = 2;


        public static boolean isValidConType(int conditiontyp) {
            if (conditiontyp == SELECT_VALUE || conditiontyp == CONDITION || conditiontyp == ALLERGY) {
                return true;
            }
            return false;
        }
    }

}


