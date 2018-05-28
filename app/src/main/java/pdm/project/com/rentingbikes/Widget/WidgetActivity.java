package pdm.project.com.rentingbikes.Widget;

import android.app.LoaderManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;

import java.text.DecimalFormat;

import pdm.project.com.rentingbikes.Activities.RentActivity;
import pdm.project.com.rentingbikes.R;

/**
 * Implementation of App Widget functionality.
 */
public class WidgetActivity extends AppWidgetProvider {

    public static void updateAppWidget(final Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_activity);
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

        /*LoaderManager.LoaderCallbacks<Cursor> mLoaderCallbacks=
                new LoaderManager.LoaderCallbacks<Cursor>() {
                    @Override
                    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
                        if(i ==2){
                            return new CursorLoader(context,MyContentProvider.URI_TRASEE,null,null,null,null);
                        }
                        return null;
                    }

                    @Override
                    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

                    }

                    @Override
                    public void onLoaderReset(Loader<Cursor> loader) {

                    }
                };*/
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

