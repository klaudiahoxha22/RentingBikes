package pdm.project.com.rentingbikes.Widget;

import android.app.LoaderManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

import pdm.project.com.rentingbikes.Activities.RentActivity;
import pdm.project.com.rentingbikes.Clase.DeviceLocation;
import pdm.project.com.rentingbikes.Clase.Punct;
import pdm.project.com.rentingbikes.R;

/**
 * Implementation of App Widget functionality.
 */
public class WidgetActivity extends AppWidgetProvider {

    public static void updateAppWidget(final Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_activity);
        Uri uriLastTraseu = TraseeContentProvider.CONTENT_URI_TRASEE_LAST;


        ContentResolver contentResolver = context.getContentResolver();
        Cursor traseuCursor = contentResolver.query(uriLastTraseu, null, null, null, null);
        ArrayList<Punct> listaPuncte = new ArrayList<Punct>();

        if (traseuCursor != null) {
            while (traseuCursor.moveToNext()) {
                int id = traseuCursor.getInt(traseuCursor.getColumnIndex("_id"));

                Uri uriPuncte = ContentUris.withAppendedId(TraseeContentProvider.CONTENT_URI_PUNCTE, id);
                Cursor cursorPuncte = contentResolver.query(uriPuncte, null, null, null, null);
                if (cursorPuncte != null)
                    while (cursorPuncte.moveToNext()) {
                        double lat = cursorPuncte.getDouble(cursorPuncte.getColumnIndex("Latitudine"));
                        double longitudine = cursorPuncte.getDouble(cursorPuncte.getColumnIndex("Longitudine"));

                        listaPuncte.add(new Punct(0, lat, longitudine, id));
                        Log.i("PROIECT", lat + "");
                    }

                double distanta = 0;
                for(int i=1;i<listaPuncte.size();i++){
                    distanta+= DeviceLocation.fromLatLngToKm(listaPuncte.get(i).getLatitudine(), listaPuncte.get(i).getLongitudine(),
                            listaPuncte.get(i-1).getLatitudine(), listaPuncte.get(i-1).getLongitudine());
                }
            }
        }


        if(RentActivity.distanta==-1||RentActivity.calculareCalorii()==-1){

            views.setTextViewText(R.id.distantaParcursa,"0 km");
            views.setTextViewText(R.id.calorii, "0 kcal");
            views.setTextViewText(R.id.timp,  " 0 sec");

        }
        else {
            //aici trebuie puse cele calculate
            DecimalFormat df = new DecimalFormat("#.00");
            CharSequence distance = df.format(RentActivity.distanta);
            CharSequence calories = df.format(RentActivity.calculareCalorii());
            CharSequence timp = df.format(RentActivity.calculareTimp());
            // Construct the RemoteViews object
            views.setTextViewText(R.id.distantaParcursa, distance + " km");
            views.setTextViewText(R.id.calorii, calories + " kcal");
            views.setTextViewText(R.id.timp, timp + " sec");
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

