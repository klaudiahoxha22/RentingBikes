package pdm.project.com.rentingbikes.Widget;

import android.app.LoaderManager;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.RemoteViews;

import pdm.project.com.rentingbikes.Activities.RentActivity;
import pdm.project.com.rentingbikes.R;

/**
 * Implementation of App Widget functionality.
 */
public class WidgetActivity extends AppWidgetProvider {

    static void updateAppWidget(final Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        //aici trebuie puse cele calculate
        CharSequence distance = context.getString(R.string.distantaParcursa);
        CharSequence calories = context.getString(R.string.calorii);
        CharSequence timp = Double.toString(RentActivity.calculareTimp());
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_activity);
        views.setTextViewText(R.id.distantaParcursa, distance);
        views.setTextViewText(R.id.calorii, calories);
        views.setTextViewText(R.id.timp, timp);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

        LoaderManager.LoaderCallbacks<Cursor> mLoaderCallbacks=
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
                };
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

