package pdm.project.com.rentingbikes.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import pdm.project.com.rentingbikes.Clase.Traseu;
import pdm.project.com.rentingbikes.DBConnection.DataBase;
import pdm.project.com.rentingbikes.ListaTraseeAdaptor;
import pdm.project.com.rentingbikes.R;

public class TraseeActivity extends AppCompatActivity {

    private RecyclerView recyclerViewTrasee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trasee);

        recyclerViewTrasee = findViewById(R.id.recyclerViewTrasee);
        recyclerViewTrasee.setLayoutManager(new LinearLayoutManager(this));

        DataBase database = DataBase.getInstance(this);
        List<Traseu> listaTrasee = database.getTraseeDao().getAll();
        ListaTraseeAdaptor listaTraseeAdaptor = new ListaTraseeAdaptor(listaTrasee);
        recyclerViewTrasee.setAdapter(listaTraseeAdaptor);
    }
}
