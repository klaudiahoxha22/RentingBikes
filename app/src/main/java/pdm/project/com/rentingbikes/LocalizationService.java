package pdm.project.com.rentingbikes;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import pdm.project.com.rentingbikes.Clase.Punct;
import pdm.project.com.rentingbikes.Clase.Traseu;

public class LocalizationService extends Service {

    public static final String SERV = "pdm.project.com.rentingbikes.LocalizationService";

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    //private int permisiuneLocatie = PackageManager.PERMISSION_DENIED;

    private final int INTERVAL = 10000;
    private final int FAST_INTERVAL = 5000;

    public LocalizationService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = new LocationRequest();
        locationRequest.setInterval(INTERVAL);
        locationRequest.setFastestInterval(FAST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public IBinder onBind(Intent intent) {
        //throw new UnsupportedOperationException("Not yet implemented");
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
        super.onDestroy();
        stopLocationUpdate();
    }

    private void stopLocationUpdate() {
        mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }
}
