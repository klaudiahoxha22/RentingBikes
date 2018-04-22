package pdm.project.com.rentingbikes.DBConnection;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.ArrayList;
import java.util.List;

import pdm.project.com.rentingbikes.Clase.Punct;


@Dao
public interface PuncteDAO {

    @Query("Select * from puncte")
    List<Punct> getAllPuncte();

    @Query("Select * from puncte WHERE _id=:id")
    List<Punct> getPunctePentruTraseul(int id);

    @Insert
    void insertPunct(Punct punct);

    @Insert
    void insertAll(ArrayList<Punct> puncts);
}
