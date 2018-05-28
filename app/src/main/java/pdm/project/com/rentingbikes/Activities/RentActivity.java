package pdm.project.com.rentingbikes.Activities;

import android.Manifest;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Date;

import pdm.project.com.rentingbikes.Clase.Punct;
import pdm.project.com.rentingbikes.Clase.Traseu;
import pdm.project.com.rentingbikes.DBConnection.DataBase;
import pdm.project.com.rentingbikes.LocalizationService;
import pdm.project.com.rentingbikes.R;

public class RentActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    private TextInputEditText denumireTraseu;
    private Button btnStartCursa;
    private Button btnStopCursa;

    private Traseu traseu;
    private DataBase dataBase;

    private ArrayList<Punct> listaPuncte = new ArrayList<>();
    private Intent serviceLocalization;

    private GoogleMap mGoogleMap;
    private SupportMapFragment mapFragment;
    private final int COD_PERMISIUNE = 1;
    int id;

    PolylineOptions options;

    BroadcastReceiver broadcastReceiver = null;
    boolean wasBackPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);

        btnStartCursa = findViewById(R.id.btnStartCursa);
        btnStopCursa = findViewById(R.id.btnStopCursa);
        denumireTraseu = findViewById(R.id.denumire_traseu);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);

        dataBase = DataBase.getInstance(this);

        serviceLocalization = new Intent(this, LocalizationService.class);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Punct punct = (Punct) intent.getExtras().get("listaPuncte");
                punct.setIdTraseu( id);
                listaPuncte.add(punct);
                Log.i("Rent activity-lat", String.valueOf(punct.getLatitudine()));
                Log.i("Rent activity-id", String.valueOf(punct.getIdTraseu()));

                dataBase.getPuncte().insertPunct(punct);
            }
        };
        IntentFilter intentFilter = new IntentFilter(LocalizationService.FINISH_COURSE);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    public void onClickStartCursa(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, COD_PERMISIUNE);
        } else {
            StartServiciuLocalizare();
            options = new PolylineOptions()
                        .add(new LatLng(MainActivity.mLastKnownLocation.getLatitude(),
                                MainActivity.mLastKnownLocation.getLongitude()))
            .color(Color.GREEN)
            .width(24)
            .geodesic(true);
        }
    }

    private void StartServiciuLocalizare() {
        traseu = new Traseu();
        traseu.setDataStart(new Date());
        if (denumireTraseu.getText().length() == 0) {
            traseu.setDenumire("Necunoscut");
        } else {
            traseu.setDenumire(denumireTraseu.getText().toString());
        }
        id = (int) dataBase.getTraseeDao().insert(traseu);
        Log.i("RentActivity-insertTr", String.valueOf(id));
        startService(serviceLocalization);
        btnStartCursa.setVisibility(View.GONE);
        btnStopCursa.setVisibility(View.VISIBLE);
    }

    public void onClickStopCursa(View view) {
        stopService(serviceLocalization);
        Toast.makeText(this, "Cursa s-a finalizat", Toast.LENGTH_SHORT).show();
        traseu.setDataEnd(new Date());
        traseu.setListaPuncte(listaPuncte);
        dataBase.getTraseeDao().updateTraseu(traseu.getDataEnd(), id);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == COD_PERMISIUNE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                StartServiciuLocalizare();
            } else {
                Toast.makeText(this, "Aici", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (!wasBackPressed) {
            wasBackPressed = true;
            Toast.makeText(this, "Daca apasati din nou pe butonul back cursa se va finaliza", Toast.LENGTH_SHORT).show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                        wasBackPressed = false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        onClickStopCursa(null);
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
        super.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RentActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        mGoogleMap.setMyLocationEnabled(true);
        LatLng bucuresti = new LatLng(44.426783, 26.102335);
        mGoogleMap.addMarker(new MarkerOptions().position(bucuresti).title("Bucuresti"));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bucuresti, 12.0f));

    }


    @Override
    public void onLocationChanged(Location location) {
        options.add(new LatLng(location.getLatitude(), location.getLongitude()));
        Log.i("RentAct-map", String.valueOf(location.getLatitude()));
        Polyline line = mGoogleMap.addPolyline(options);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 10f));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
