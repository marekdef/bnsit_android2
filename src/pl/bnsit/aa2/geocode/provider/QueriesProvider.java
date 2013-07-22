package pl.bnsit.aa2.geocode.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class QueriesProvider extends ContentProvider {
    private static final String AUTHORITY = "pl.bnsit.aa2.geocoding";
    private static final String BASE_PATH = "query";
    public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+'/'+BASE_PATH);

    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "queries.db";
    public static final String TABLE_NAME = "geocoded";

    public static final String ID = "_id";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longtitude";
    public static final String QUERY = "query";
    public static final String FORMATTED_ADDRESS = "formatted_address";
    public static final String STATUS = "status";
    public static final String CREATED = "created";

    public static final int STATUS_SENDING = 0;
    public static final int STATUS_ERROR = 1;
    public static final int STATUS_DONE = 2;


    SQLiteOpenHelper helper;
    @Override
    public boolean onCreate() {
        helper = new SQLiteOpenHelper(getContext(), DATABASE_NAME, null, DATABASE_VERSION) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL("CREATE TABLE " + TABLE_NAME +
                        " ( " +
                        ID + " integer primary key," +
                        STATUS + " integer NOT NULL" +
                        CREATED + " date NOT NULL" +
                        QUERY + " text NOT NULL, " +
                        FORMATTED_ADDRESS + " text NOT NULL, " +
                        LATITUDE +" REAL NOT NULL, " +
                        LONGITUDE + " REAL NOT NULL)");
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE GEOCODED IF EXISTS");
                onCreate(db);
            }
        };
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        SQLiteDatabase writableDatabase = helper.getWritableDatabase();

        qb.setTables(TABLE_NAME);
        Cursor cursor = qb.query(writableDatabase, projection, selection, selectionArgs, null, null, sortOrder);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return "pl.bnsit.aa2.geocoding.dir/pl.bnsit.aa2.geocoding.query";
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase writableDatabase = helper.getWritableDatabase();
        long insert = writableDatabase.insert(TABLE_NAME, null, values);

        Uri updateUri = Uri.withAppendedPath(CONTENT_URI, String.valueOf(insert));

        getContext().getContentResolver().notifyChange(updateUri, null);
        return updateUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase writableDatabase = helper.getWritableDatabase();

        throw new UnsupportedOperationException();
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }
}

