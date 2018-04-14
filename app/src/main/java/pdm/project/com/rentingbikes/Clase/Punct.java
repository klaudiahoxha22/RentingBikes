package pdm.project.com.rentingbikes.Clase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Claudia on 14-Apr-18.
 */

@Entity(tableName = "Puncte" )
public class Punct {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="_id")
    private int id;
    @ColumnInfo(name="Latitudine")
    private double latitudine;
    @ColumnInfo(name="Longitudine")
    private double longitudine;
    @ColumnInfo(name="IdTraseu")
    private int idTraseu;

    public Punct() {
    }

    public Punct(int id, double latitudine, double longitudine, int idTraseu) {
        this.id = id;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.idTraseu = idTraseu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }

    public double getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
    }

    public int getIdTraseu() {
        return idTraseu;
    }

    public void setIdTraseu(int idTraseu) {
        this.idTraseu = idTraseu;
    }

    @Override
    public String toString() {
        return "Punct{" +
                "id=" + id +
                ", latitudine=" + latitudine +
                ", longitudine=" + longitudine +
                ", idTraseu=" + idTraseu +
                '}';
    }
}
