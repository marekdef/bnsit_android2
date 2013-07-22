package pl.bnsit.aa2.geocode.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by marekdef on 04.06.13.
 */
public class GeoCodingService extends Service {
    public static final String GEOCODE_CONTACT_ADDRESS_BY_ID = "CONTACT_ADDRESS_BY_ID";
    public static final String GEOCODE_ADDRESS = "GEOCODE_ADDRESS";
    public static final String GEOCODING_URL = "http://maps.googleapis.com/maps/api/geocode/pl.bnsit.aa2.json?address=%s&sensor=false";
    private static final String TAG = GeoCodingService.class.getSimpleName();
    private ExecutorService executor;

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        executor = Executors.newFixedThreadPool(4);
        super.onCreate();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private class GeoceodeAddressFromId implements Runnable {

        @Override
        public void run() {

        }
    }

    private class GeocodeAddress implements Runnable {

        private final String url;

        public GeocodeAddress(String address) {
            url = String.format(GEOCODING_URL, URLEncoder.encode(address));
        }

        @Override
        public void run() {
            HttpURLConnection httpConnection = null;
            try {
                httpConnection = (HttpURLConnection) new URL(url).openConnection();

                if (httpConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    throw new IOException();
                }

                final InputStream inputStream = httpConnection.getInputStream();
                final String stringFromInput = getStringFromInput(inputStream);
                inputStream.close();

                final Gson gson = new Gson();


            } catch (IOException e) {

            } if (httpConnection != null) {
                httpConnection.disconnect();
            }
        }

        private String getStringFromInput(InputStream inputStream) throws IOException {
            int bytesRead = 0;
            byte[] buffer = new byte[1024];

            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }

            return bos.toString("utf-8");
        }
    }
}