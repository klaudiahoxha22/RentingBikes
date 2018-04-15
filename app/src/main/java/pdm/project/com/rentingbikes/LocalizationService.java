package pdm.project.com.rentingbikes;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

import pdm.project.com.rentingbikes.Clase.Punct;
import pdm.project.com.rentingbikes.Clase.Traseu;

public class LocalizationService extends Service {

    public static final String SERV = "pdm.project.com.rentingbikes.LocalizationService";
    public static final String FINISH_COURSE = "pdm.project.com.rentingbikes.FINISH_COURSE";

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    //private int permisiuneLocatie = PackageManager.PERMISSION_DENIED;
    List<Punct> listaPuncte;
    private final int INTERVAL = 10000;
    private final int FAST_INTERVAL = 5000;

    public LocalizationService() {
        super();
    }

//    class MyServiceBinder extends Binder {
//        public LocalizationService getService() {
//            return LocalizationService.this;
//        }
//    }
//
//    private IBinder myBinder = new MyServiceBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        listaPuncte = new ArrayList<>();

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = new LocationRequest();
        locationRequest.setInterval(INTERVAL);
        locationRequest.setFastestInterval(FAST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public IBinder onBind(Intent intent) {
        //throw new UnsupportedOperationException("Not yet implemented");
//        return myBinder;
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startLocationUpdate();
        return START_NOT_STICKY;
    }

    private void startLocationUpdate() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                Punct punct = new Punct();
                punct.setLatitudine(location.getLatitude());
                punct.setLongitudine(location.getLongitude());
                Log.i("Latitudine", String.valueOf(location.getLatitude()));
                listaPuncte.add(punct);
                super.onLocationResult(locationResult);
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i("Serviciu de localizare", "No permission");
        } else {
            mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }

    @Override
    public void onDestroy() {
        stopLocationUpdate();
        super.onDestroy();
    }

    private void stopLocationUpdate() {
        mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra("listaPuncte", (ArrayList<? extends Parcelable>) listaPuncte);
        intent.setAction(FINISH_COURSE);
        sendBroadcast(intent);
    }
}
