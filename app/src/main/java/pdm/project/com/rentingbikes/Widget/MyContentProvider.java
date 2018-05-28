package pdm.project.com.rentingbikes.Widget;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import pdm.project.com.rentingbikes.Clase.Punct;
import pdm.project.com.rentingbikes.Clase.Traseu;
import pdm.project.com.rentingbikes.DBConnection.DataBase;
import pdm.project.com.rentingbikes.DBConnection.TraseeDAO;

public class MyContentProvider extends ContentProvider {

    public static  final String AUTHORITY ="pdm.project.com.rentingbikes.Widget";
    public static  final Uri URI_TRASEE = Uri.parse("content://"+AUTHORITY+"/"+ Traseu.TABLE_NAME);
    public static  final Uri URI_PUNCTE = Uri.parse("content://"+AUTHORITY+"/"+ Punct.TABLE_NAME);

    public static final UriMatcher MATCHER=new UriMatcher(UriMatcher.NO_MATCH);

    static {
        MATCHER.addURI(AUTHORITY,Traseu.TABLE_NAME + "/#",2);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        final int code = MATCHER.match(uri);
        if(code==2){
            final Context context=getContext();
            if(context==null){
                return null;
            }
            TraseeDAO traseeDao = DataBase.getInstance((context)).getTraseeDao();
            Cursor cursor = null;
            if(code==2){
//                cursor=traseeDao.getLastTraseu((int) ContentUris.parseId(uri));
//                cursor.
            }
            cursor.setNotificationUri(context.getContentResolver(),uri);
            return cursor;
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
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
