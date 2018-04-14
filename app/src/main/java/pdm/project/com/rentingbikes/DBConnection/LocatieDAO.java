package pdm.project.com.rentingbikes.DBConnection;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import pdm.project.com.rentingbikes.Clase.Locatie;

/**
 * Created by Claudia on 14-Apr-18.
 */

@Dao
public interface LocatieDAO {

    @Query("Select * from Locatie")
    List<Locatie> getAll();

    @Insert
    void insertLocatie(Locatie locatie);

    @Update
    void updateLocatie(Locatie locatie);

    @Delete
    void deleteLocatie(Locatie locatie);
}
