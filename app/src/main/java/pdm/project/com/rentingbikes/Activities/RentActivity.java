package pdm.project.com.rentingbikes.Activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pdm.project.com.rentingbikes.Clase.Punct;
import pdm.project.com.rentingbikes.Clase.Traseu;
import pdm.project.com.rentingbikes.DBConnection.DataBase;
import pdm.project.com.rentingbikes.LocalizationService;
import pdm.project.com.rentingbikes.R;

public class RentActivity extends AppCompatActivity {
    Intent serviceLocalization;
    Button btnStartCursa;
    Button btnStopCursa;
    private final int COD_PERMISIUNE = 1;
    private ArrayList<Punct> listaPuncte;
    private Traseu traseu;
    BroadcastReceiver broadcastReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);
        serviceLocalization = new Intent(this, LocalizationService.class);
        btnStartCursa = findViewById(R.id.btnStartCursa);
        btnStopCursa = findViewById(R.id.btnStopCursa);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                listaPuncte = intent.getParcelableArrayListExtra("listaPuncte");
                DataBase dataBase = DataBase.getInstance(context);
                long id = dataBase.getTraseeDao().insert(traseu);
                for (int i = 0; i < listaPuncte.size(); i++) {
                    Punct punctCurent = listaPuncte.get(i);
                    punctCurent.setIdTraseu((int) id);
                    dataBase.getPuncte().insertPunct(punctCurent);
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter(LocalizationService.FINISH_COURSE);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    public void onClickStartCursa(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Toast.makeText(AddTraseuActivity.this, "Check your permission", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, COD_PERMISIUNE);
        } else {
            StartServiciuLocalizare();
        }
    }

    private void StartServiciuLocalizare() {
        traseu = new Traseu();
        traseu.setDataStart(new Date());
        traseu.setDenumire("Nume traseu");

        startService(serviceLocalization);
        btnStartCursa.setVisibility(View.GONE);
        btnStopCursa.setVisibility(View.VISIBLE);
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

    public void onClickStopCursa(View view) {
        stopService(serviceLocalization);
        Toast.makeText(this, "Cursa s-a finalizat", Toast.LENGTH_SHORT).show();
        finish();
    }


    @Override
    protected void onDestroy() {
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
        super.onDestroy();
    }
}
