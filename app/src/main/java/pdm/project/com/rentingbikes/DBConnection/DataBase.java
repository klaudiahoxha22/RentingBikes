package pdm.project.com.rentingbikes.DBConnection;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import pdm.project.com.rentingbikes.Clase.Locatie;
import pdm.project.com.rentingbikes.Clase.Punct;
import pdm.project.com.rentingbikes.Clase.Traseu;

/**
 * Created by Claudia on 14-Apr-18.
 */

@Database(entities = {Locatie.class, Punct.class, Traseu.class}, version = 1)
@TypeConverters({Converter.class})
public abstract class DataBase extends RoomDatabase {

    public static final String DB_NAME = "rentingbikes.db";
    private static volatile DataBase instance;

    public static synchronized DataBase getInstance(Context context) {
        if(instance==null) {
            instance= Room.databaseBuilder(
                    context, DataBase.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

    public abstract TraseeDAO getTraseeDao();

    public abstract PuncteDAO getPuncte();
}
