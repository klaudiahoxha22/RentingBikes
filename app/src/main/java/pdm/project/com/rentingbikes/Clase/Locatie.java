package pdm.project.com.rentingbikes.Clase;

/**
 * Created by Claudia on 14-Apr-18.
 */

public class Locatie  {

    private int id;
    private String denumire;
    private String adresa;
    private double latitudine;
    private double longitudine;
    private int nrBiciclete;
    private double pret;

    public Locatie() {}

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
}
