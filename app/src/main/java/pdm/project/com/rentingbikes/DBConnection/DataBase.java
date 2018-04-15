package pdm.project.com.rentingbikes.DBConnection;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.concurrent.Executors;

import pdm.project.com.rentingbikes.Clase.Locatie;
import pdm.project.com.rentingbikes.Clase.Punct;
import pdm.project.com.rentingbikes.Clase.Traseu;



@Database(entities = {Locatie.class, Punct.class, Traseu.class}, version = 1)
@TypeConverters({Converter.class})
public abstract class DataBase extends RoomDatabase {

    public static final String DB_NAME = "rentingbikes.db";
    private static volatile DataBase instance;

    public static synchronized DataBase getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context, DataBase.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            Executors.newSingleThreadExecutor().execute(new Runnable() {
                                @Override
                                public void run() {
                                    getInstance(context).getLocatiiDao().insertAll(Locatie.populateData());
                                }
                            });
                        }
                    })
                    .build();
        }

        return instance;
    }

    public abstract TraseeDAO getTraseeDao();

    public abstract LocatieDAO getLocatiiDao();

    public abstract PuncteDAO getPuncte();
}
