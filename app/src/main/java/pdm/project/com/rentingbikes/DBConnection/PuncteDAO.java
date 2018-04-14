package pdm.project.com.rentingbikes.DBConnection;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.ArrayList;
import java.util.List;

import pdm.project.com.rentingbikes.Clase.Punct;

/**
 * Created by Claudia on 14-Apr-18.
 */

public interface PuncteDAO {

    @Query("Select * from puncte where traseu_id = :idTraseu")
    List<Punct> getAllPuncte(int idTraseu);

    @Query("Select * from puncte")
    List<Punct> getPuncte();

    @Insert
    void insertPunct(Punct punct);

    @Insert
    void insertAll(ArrayList<Punct> puncts);
}
