package pdm.project.com.rentingbikes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import pdm.project.com.rentingbikes.Clase.Locatie;

public class LocationActivity extends AppCompatActivity {

    private RecyclerView recyclerViewLocatii;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        recyclerViewLocatii = findViewById(R.id.recyclerViewLocatii);
        recyclerViewLocatii.setLayoutManager(new LinearLayoutManager(this));
        ///TODO Interogarea baza de date
        ArrayList<Locatie> locatii = new ArrayList();
        locatii.add(new Locatie("Herastrau", "Sos. Aviatorilor", 10, 10, 100, 5));

        ListaLocatiiAdaptor listaLocatiiAdaptor = new ListaLocatiiAdaptor(locatii);
        recyclerViewLocatii.setAdapter(listaLocatiiAdaptor);
    }
}
