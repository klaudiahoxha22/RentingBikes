package pdm.project.com.rentingbikes.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import pdm.project.com.rentingbikes.Clase.Locatie;
import pdm.project.com.rentingbikes.DBConnection.DataBase;
import pdm.project.com.rentingbikes.ListaLocatiiAdaptor;
import pdm.project.com.rentingbikes.R;

import com.google.android.gms.common.api.*;

public class LocationActivity extends AppCompatActivity {

    private RecyclerView recyclerViewLocatii;
    private List<Locatie> listaLocatii;
    private ListaLocatiiAdaptor listaLocatiiAdaptor;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private Location mLastKnownLocation;
    public static GoogleApiClient mGoogleApiClient;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        recyclerViewLocatii = findViewById(R.id.recyclerViewLocatii);
        recyclerViewLocatii.setLayoutManager(new LinearLayoutManager(this));
        progress=new ProgressDialog(this);
        progress.setMessage("Your data is comming");

        DataBase database = DataBase.getInstance(this);
        listaLocatii = database.getLocatiiDao().getAll();
        listaLocatiiAdaptor = new ListaLocatiiAdaptor(listaLocatii, this);
        recyclerViewLocatii.setAdapter(null);

        createGoogleApiClient();
        if(MainActivity.mLastKnownLocation==null){
            recyclerViewLocatii.setAdapter(null);
        }else {
            recyclerViewLocatii.setAdapter(listaLocatiiAdaptor);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(MainActivity.mLastKnownLocation==null){
            progress.show();
        }else{
            progress.dismiss();
            recyclerViewLocatii.setAdapter(listaLocatiiAdaptor);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(MainActivity.mLastKnownLocation !=null){
            progress.dismiss();
            recyclerViewLocatii.setAdapter(listaLocatiiAdaptor);
        }else{
            Toast.makeText(this, "Could not fetch data.Try again.", Toast.LENGTH_LONG).show();
            progress.dismiss();
            finish();
        }
    }

    private void createGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.locations_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.distance_filter:
                Toast.makeText(this, "Filter: By distance", Toast.LENGTH_SHORT).show();
                sortLocationByDist();
        }
        return super.onOptionsItemSelected(item);
    }

    private void sortLocationByDist() {
        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        boolean locationEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!locationEnabled) {
            Snackbar.make(findViewById(R.id.location_layout), "GPS disabled!", Snackbar.LENGTH_LONG)
                    .setAction("Enable", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(myIntent);
                        }
                    }).show();

        } else {
            getDeviceLocation();
        }
    }

    public void getDeviceLocation() {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Location disabled. Enable now?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(LocationActivity.this,
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).create();
                return;
            }
            if(MainActivity.locationPermissionGranted ) {
                mLastKnownLocation = LocationServices.FusedLocationApi
                        .getLastLocation(mGoogleApiClient);
                sortByDistance();
            }else {
                Toast.makeText(this, "Action not supported", Toast.LENGTH_LONG).show();
            }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    MainActivity.locationPermissionGranted = true;
                } else {
                    Toast.makeText(this, "GPS needed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void sortByDistance() {
        Comparator<Locatie> comparator = new Comparator<Locatie>() {
            @Override
            public int compare(Locatie loc1, Locatie loc2) {
                double dist1 = fromLatLngToKm(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude(),
                        loc1.getLatitudine(), loc1.getLongitudine());
                double dist2 = fromLatLngToKm(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude(),
                        loc2.getLatitudine(), loc2.getLongitudine());
                return Double.compare(dist1, dist2);
            }
        };

        //TODO de vazut daca merge cu onNotifyDataSetChange
        Collections.sort(listaLocatii, comparator);
        listaLocatiiAdaptor = new ListaLocatiiAdaptor(listaLocatii, this);
        recyclerViewLocatii.setAdapter(listaLocatiiAdaptor);
    }

    private double fromLatLngToKm(double lat1, double lng1, double lat2, double lng2) {
        double dist;
        double radius = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        dist = radius * c;
        return dist;
    }
}
