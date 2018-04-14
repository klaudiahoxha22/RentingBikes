package pdm.project.com.rentingbikes.DBConnection;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import pdm.project.com.rentingbikes.Clase.Trasee;

/**
 * Created by Claudia on 14-Apr-18.
 */

public interface TraseeDAO {

    @Query("Select * from trasee")
    List<Trasee> getAll();

    @Insert
    void insertAll(List<Trasee> trasee);

    @Insert
    void insert(Trasee treseu);

    @Update
    void update(Trasee traseu);

    @Delete
    void delete(Trasee traseu);
}
