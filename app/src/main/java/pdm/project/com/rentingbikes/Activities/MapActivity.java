package pdm.project.com.rentingbikes.Activities;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import pdm.project.com.rentingbikes.Clase.DeviceLocation;
import pdm.project.com.rentingbikes.Clase.Punct;
import pdm.project.com.rentingbikes.Clase.Traseu;
import pdm.project.com.rentingbikes.R;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private TextView denumireTraseu;
    private TextView distantaTraseu;
    private TextView timpTraseu;
    private TextView caloriiTraseu;

    private Traseu traseu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        denumireTraseu = findViewById(R.id.denumire_traseu_map);
        distantaTraseu = findViewById(R.id.distanta_traseu_map);
        timpTraseu = findViewById(R.id.timp_traseu_map);
        caloriiTraseu = findViewById(R.id.calorie_traseu_map);

        traseu = (Traseu) getIntent().getExtras().getSerializable("traseu");

        denumireTraseu.setText(traseu.getDenumire());
        distantaTraseu.setText("Distanta parcursa: " + String.valueOf(calcDistanta(traseu.getListaPuncte()) + "km"));
        timpTraseu.setText("Timpul total: " + calcTimp(traseu));
        caloriiTraseu.setText("Calorii consumate: " + calcCalori() + " kcal");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private double calcDistanta(List<Punct> listPuncte) {
        int sizeList= listPuncte.size();
        double distanta = DeviceLocation.fromLatLngToKm(listPuncte.get(0).getLatitudine(), listPuncte.get(0).getLongitudine(),
                listPuncte.get(sizeList-1).getLatitudine(), listPuncte.get(sizeList-1).getLongitudine());
        return distanta;
    }

    private String calcTimp(Traseu traseu){
        long timp = traseu.getDataEnd().getTime() - traseu.getDataStart().getTime();
        return String.format(Locale.GERMANY,"%02d min, %02d sec",
                TimeUnit.MILLISECONDS.toMinutes(timp),
                TimeUnit.MILLISECONDS.toSeconds(timp) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timp))
        );
    }

    private String calcCalori() {
        double timp = traseu.getDataEnd().getTime()-traseu.getDataEnd().getTime();
        double timpHour = (timp / (1000*60*60)) % 24;
        // for 168 lb -> 305 calories for 1 hour
        int calories = (int) (timpHour* 305);
        return String.valueOf(calories);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        PolylineOptions options = new PolylineOptions()
                .color(Color.BLUE)
                .width(25)
                .geodesic(true);
        for(Punct p : traseu.getListaPuncte()){
            LatLng latLng = new LatLng(p.getLatitudine(), p.getLongitudine());
            options.add(latLng);
        }
        mMap.addPolyline(options);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(traseu.getListaPuncte().get(0).getLatitudine(),
                traseu.getListaPuncte().get(0).getLongitudine()), 15f));
    }
}
