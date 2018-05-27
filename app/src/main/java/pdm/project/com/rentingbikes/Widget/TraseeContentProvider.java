package pdm.project.com.rentingbikes.Widget;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import pdm.project.com.rentingbikes.DBConnection.DataBase;

public class TraseeContentProvider extends ContentProvider {

    static final UriMatcher uriMatcher;
    static final String PROVIDER_NAME = "pdm.project.com.rentingbikes.Widget";
    static final String URL_TRASEE_LAST = "content://" + PROVIDER_NAME + "/trasee/last";
    static final String URL_PUNCTE = "content://" + PROVIDER_NAME + "/puncte";
    static final Uri CONTENT_URI_TRASEE_LAST = Uri.parse(URL_TRASEE_LAST);
    static final Uri CONTENT_URI_PUNCTE = Uri.parse(URL_PUNCTE);
    private static final String TAG = "ContentProvider";

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "trasee/last", 1);
        uriMatcher.addURI(PROVIDER_NAME, "puncte/#", 2);
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case 1:
                return DataBase.getInstance(getContext()).getTraseeDao().getLastTraseu();

            case 2:
                return DataBase.getInstance(getContext())
                        .getPuncte()
                        .getPunctePentruTraseulCursor(Integer.parseInt(uri.getPathSegments().get(1)));
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}

