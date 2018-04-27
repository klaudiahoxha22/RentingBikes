package pdm.project.com.rentingbikes;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;

import pdm.project.com.rentingbikes.Activities.LocationActivity;
import pdm.project.com.rentingbikes.Activities.MainActivity;
import pdm.project.com.rentingbikes.Activities.RentActivity;
import pdm.project.com.rentingbikes.Clase.DeviceLocation;
import pdm.project.com.rentingbikes.Clase.Locatie;

public class ListaLocatiiAdaptor extends RecyclerView.Adapter<ListaLocatiiAdaptor.ViewHolder> {

    private List<Locatie> locatii;
    private Context context;
    private GoogleApiClient mGoogleApiClient;
    LocationActivity locationActivity;
    private double distance;
    Location mLocation;

    public ListaLocatiiAdaptor(List<Locatie> locatii, Context ctx, LocationActivity loc) {
        this.locatii = locatii;
        this.context = ctx;
        locationActivity = loc;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RentActivity.class);
                view.getContext().startActivity(intent);
            }
        });
        holder.tvDenumireLocatie.setText(locatii.get(position).getDenumire());
        holder.tvAdresa.setText(locatii.get(position).getAdresa());

//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//          Toast.makeText(context, "GPS is required", Toast.LENGTH_SHORT).show();
//        }
//        mLocation = LocationServices.FusedLocationApi
//                .getLastLocation(LocationActivity.mGoogleApiClient);
        //locationActivity.getDeviceLocation();

        distance = DeviceLocation.fromLatLngToKm(MainActivity.mLastKnownLocation.getLatitude(), MainActivity.mLastKnownLocation.getLongitude(),
                locatii.get(position).getLatitudine(), locatii.get(position).getLongitudine());
        holder.tvDistantaLocatie.setText(String.valueOf((int)distance)+ " km");
    }

    @Override
    public int getItemCount() {
        return locatii.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView tvDistantaLocatie;
        TextView tvDenumireLocatie;
        TextView tvAdresa;
        ImageView distImg;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardViewRand);
            tvDistantaLocatie = itemView.findViewById(R.id.tvDistantaLocatie);
            tvDenumireLocatie = itemView.findViewById(R.id.tvLocatie);
            tvAdresa = itemView.findViewById(R.id.tvAdresa);
            distImg = itemView.findViewById(R.id.distImg);
        }
    }
}
