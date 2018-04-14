package pdm.project.com.rentingbikes.Clase;

/**
 * Created by Claudia on 14-Apr-18.
 */

class Punct {

    private int id;
    private double latitudine;
    private double longitudine;
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
