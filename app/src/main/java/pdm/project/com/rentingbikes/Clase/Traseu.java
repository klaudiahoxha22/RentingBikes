package pdm.project.com.rentingbikes.Clase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity(tableName = "Trasee")
public class Traseu implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int id;
    @ColumnInfo(name = "Denumire")
    private String denumire;
    @ColumnInfo(name = "DataStart")
    private Date dataStart;
    @ColumnInfo(name = "DataEnd")
    private Date dataEnd;

    @Ignore
    private List<Punct> listaPuncte;

    public Traseu() {
    }

    public Traseu(int id, String denumire, Date dataStart, Date dataEnd, ArrayList<Punct> listaPuncte) {
        this.id = id;
        this.denumire = denumire;
        this.dataStart = dataStart;
        this.dataEnd = dataEnd;
        this.listaPuncte = listaPuncte;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public Date getDataStart() {
        return dataStart;
    }

    public void setDataStart(Date dataStart) {
        this.dataStart = dataStart;
    }

    public Date getDataEnd() {
        return dataEnd;
    }

    public void setDataEnd(Date dataEnd) {
        this.dataEnd = dataEnd;
    }

    public List<Punct> getListaPuncte() {
        return listaPuncte;
    }

    public void setListaPuncte(List<Punct> listaPuncte) {
        this.listaPuncte = listaPuncte;
    }

    @Override
    public String toString() {
        return "Traseu{" +
                "id=" + id +
                ", denumire='" + denumire + '\'' +
                ", dataStart=" + dataStart +
                ", dataEnd=" + dataEnd +
                ", listaPuncte=" + listaPuncte +
                '}';
    }
}
