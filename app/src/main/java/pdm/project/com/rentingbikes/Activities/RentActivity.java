package pdm.project.com.rentingbikes.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import pdm.project.com.rentingbikes.LocalizationService;
import pdm.project.com.rentingbikes.R;

public class RentActivity extends AppCompatActivity {

    Intent serviceLocalization;
    Button btnStartCursa;
    Button btnStopCursa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);
        serviceLocalization = new Intent(this, LocalizationService.class);
        btnStartCursa = findViewById(R.id.btnStartCursa);
        btnStopCursa = findViewById(R.id.btnStopCursa);
    }

    public void onClickStartCursa(View view) {
        startService(serviceLocalization);
        btnStartCursa.setVisibility(View.GONE);
        btnStopCursa.setVisibility(View.VISIBLE);
    }

    public void onClickStopCursa(View view) {
        stopService(serviceLocalization);
    }
}
