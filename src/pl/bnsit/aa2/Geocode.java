package pl.bnsit.aa2;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import pl.bnsit.aa2.geocode.provider.QueriesProvider;
import pl.bnsit.aa2.geocode.service.GeoCodingService;

public class Geocode extends FragmentActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor>,AdapterView.OnItemClickListener {

    static final String[] CONTACTS_SUMMARY_PROJECTION = new String[]{
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS
    };

    private static final int LOADER_CONTACTS_ID = 1;
    private static final int LOADER_QUERIES_ID = 2;
    private EditText editText;
    private Button button;
    private ListView listLocations;
    private ListView listContacts;
    private SimpleCursorAdapter locationsAdapter;
    private SimpleCursorAdapter contactsAdapter;
    private String mCurFilter;

    private final String TAG = Geocode.class.getSimpleName();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        button = (Button) findViewById(R.id.buttonGeocode);
        editText = (EditText) findViewById(R.id.editText);

        listLocations = (ListView) findViewById(R.id.locations);
        listContacts = (ListView) findViewById(R.id.contacts);

        listLocations.setOnItemClickListener(this);
        listContacts.setOnItemClickListener(this);


        button.setOnClickListener(this);

        getSupportLoaderManager().initLoader(LOADER_CONTACTS_ID, null, this);
        getSupportLoaderManager().initLoader(LOADER_QUERIES_ID, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onClick(View view) {
        if (view.equals(button)) {
            String value = editText.getText().toString();

            startGeocoding(value);
        }
    }

    private void startGeocoding(String address) {
        Intent intent = new Intent(this, GeoCodingService.class);
        intent.putExtra(GeoCodingService.GEOCODE_ADDRESS, address);
        final ComponentName componentName = startService(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        Loader<Cursor> loader = null;
        switch(id) {
            case LOADER_CONTACTS_ID:
                loader = createContactsLoader();
                break;

            case LOADER_QUERIES_ID:
                loader = createQueriesLoader();
                break;

        }
        return loader;
    }

    private Loader<Cursor> createQueriesLoader() {
        return new CursorLoader(this, QueriesProvider.CONTENT_URI, null, null, null, null );
    }


    private Loader<Cursor> createContactsLoader() {
        String select = "((" + ContactsContract.Contacts.DISPLAY_NAME + " NOTNULL) AND ("
                + ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS + " NOTNULL) AND ("
                + ContactsContract.Contacts.DISPLAY_NAME + " != '' ))";
        return new CursorLoader(this, ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
                CONTACTS_SUMMARY_PROJECTION, select, null,
                ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> objectLoader, Cursor cursor) {
        switch (objectLoader.getId()) {
            case LOADER_CONTACTS_ID:
                contactsAdapter = new SimpleCursorAdapter(this,
                        android.R.layout.simple_list_item_2, cursor,
                        new String[]{ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS},
                        new int[]{android.R.id.text1, android.R.id.text2}) {
                    @Override
                    public View newView(Context context, Cursor cursor, ViewGroup parent) {
                        View view = super.newView(context, cursor, parent);
                        final String formattedAddress = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS));
                        view.setTag(formattedAddress);
                        return view;
                    }
                };

                listContacts.setAdapter(contactsAdapter);
                break;
            case LOADER_QUERIES_ID:
                contactsAdapter = new SimpleCursorAdapter(this,
                        android.R.layout.simple_list_item_2, cursor,
                        new String[]{QueriesProvider.FORMATTED_ADDRESS, QueriesProvider.QUERY},
                        new int[]{android.R.id.text1, android.R.id.text2});

                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> objectLoader) {
        switch (objectLoader.getId()) {
            case LOADER_CONTACTS_ID:
                listContacts.setAdapter(null);
                break;
            case LOADER_QUERIES_ID:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if(adapterView.equals(listContacts)) {
            startGeocoding((String)view.getTag());
        }
        else if(adapterView.equals(listLocations)) {

        }

    }
}
