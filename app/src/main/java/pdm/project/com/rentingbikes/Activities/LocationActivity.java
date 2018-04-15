package pdm.project.com.rentingbikes.Activities;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pdm.project.com.rentingbikes.Clase.Locatie;
import pdm.project.com.rentingbikes.DBConnection.DataBase;
import pdm.project.com.rentingbikes.ListaLocatiiAdaptor;
import pdm.project.com.rentingbikes.R;

public class LocationActivity extends AppCompatActivity {

    private RecyclerView recyclerViewLocatii;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        recyclerViewLocatii = findViewById(R.id.recyclerViewLocatii);
        recyclerViewLocatii.setLayoutManager(new LinearLayoutManager(this));
        ///TODO Interogarea baza de date
        DataBase database = DataBase.getInstance(this);
        List<Locatie> listaLocatii = database.getLocatiiDao().getAll();
//        locatii.add(new Locatie("Herastrau", "Sos. Aviatorilor", 10, 10, 100, 5));

        ListaLocatiiAdaptor listaLocatiiAdaptor = new ListaLocatiiAdaptor(listaLocatii);
        recyclerViewLocatii.setAdapter(listaLocatiiAdaptor);
    }
}
