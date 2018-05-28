package pdm.project.com.rentingbikes.Clase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Locatie")
public class Locatie {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int id;
    @ColumnInfo(name = "Denumire")
    private String denumire;
    @ColumnInfo(name = "Adresa")
    private String adresa;
    @ColumnInfo(name = "Latitudine")
    private double latitudine;
    @ColumnInfo(name = "Longitudine")
    private double longitudine;
    @ColumnInfo(name = "NrBiciclete")
    private int nrBiciclete;
    @ColumnInfo(name = "Pret")
    private double pret;

    public Locatie() {
    }

    public Locatie(String denumire, String adresa, double latitudine, double longitudine, int nrBiciclete, double pret) {
        this.denumire = denumire;
        this.adresa = adresa;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.nrBiciclete = nrBiciclete;
        this.pret = pret;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
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

    public int getNrBiciclete() {
        return nrBiciclete;
    }

    public void setNrBiciclete(int nrBiciclete) {
        this.nrBiciclete = nrBiciclete;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    @Override
    public String toString() {
        return "Locatie{" +
                "denumire='" + denumire + '\'' +
                ", adresa='" + adresa + '\'' +
                ", latitudine=" + latitudine +
                ", longitudine=" + longitudine +
                ", nrBiciclete=" + nrBiciclete +
                ", pret=" + pret +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

        public static Locatie[] populateData() {
        return new Locatie[]
                {
                        new Locatie("Herastrau","Soseaua Aviatorilor",44.4702053,26.080564,150,5),
                        new Locatie("Piata Universitatii","Bd. Regina Elisabeta",44.435782,26.103002,211,30),
                        new Locatie("Piata Romana","Bd. Magheru",44.4469735,26.0951414,211,30),
                        new Locatie("Tineretului","Bd. Sincai",44.4135527,26.1049582,211,30)
                };
    }
}
