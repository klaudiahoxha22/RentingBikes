package pdm.project.com.rentingbikes.DBConnection;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import pdm.project.com.rentingbikes.Clase.Traseu;

/**
 * Created by Claudia on 14-Apr-18.
 */

@Dao
public interface TraseeDAO {

    @Query("Select * from Trasee")
    List<Traseu> getAll();

    @Insert
    void insertAll(List<Traseu> trasee);

    @Insert
    long insert(Traseu treseu);

    @Update
    void update(Traseu traseu);

    @Delete
    void delete(Traseu traseu);
}
