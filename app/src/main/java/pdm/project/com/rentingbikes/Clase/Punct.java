package pdm.project.com.rentingbikes.Clase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "Puncte", foreignKeys = @ForeignKey(entity = Traseu.class,
        parentColumns = "_id",
        childColumns = "IdTraseu",
        onDelete = CASCADE))
public class Punct implements /*Parcelable,*/ Serializable {

    public static final String TABLE_NAME ="Puncte" ;
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int id;
    @ColumnInfo(name = "Latitudine")
    private double latitudine;
    @ColumnInfo(name = "Longitudine")
    private double longitudine;
    @ColumnInfo(name = "IdTraseu")
    private int idTraseu;

    public Punct() {
    }

    public Punct(int id, double latitudine, double longitudine, int idTraseu) {
        this.id = id;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.idTraseu = idTraseu;
    }


    ///TODO Aici am sters id si idTraseu, pentru ca eu trimit obiectul inainte sa am id si idtrase, daca aveti nelamuriri imi scrieti
    protected Punct(Parcel in) {
//        id = in.readInt();
        latitudine = in.readDouble();
        longitudine = in.readDouble();
//        idTraseu = in.readInt();
    }

    /*public static final Creator<Punct> CREATOR = new Creator<Punct>() {
        @Override
        public Punct createFromParcel(Parcel in) {
            return new Punct(in);
        }

        @Override
        public Punct[] newArray(int size) {
            return new Punct[size];
        }
    };*/

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

   /* @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(latitudine);
        parcel.writeDouble(longitudine);
    }*/
}
