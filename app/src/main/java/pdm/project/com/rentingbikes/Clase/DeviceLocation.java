package pdm.project.com.rentingbikes.Clase;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.text.DecimalFormat;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Claudia on 20-Apr-18.
 */

public class DeviceLocation {


    public static double fromLatLngToKm(double lat1, double lng1, double lat2, double lng2) {
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
