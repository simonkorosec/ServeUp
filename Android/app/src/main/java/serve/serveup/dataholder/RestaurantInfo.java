package serve.serveup.dataholder;

import android.support.annotation.NonNull;
import com.squareup.moshi.Json;

import java.io.Serializable;

public class RestaurantInfo implements Serializable {

    @NonNull
    @Json(name = "ime_restavracije")
    private String imeRestavracije;

    @NonNull
    @Json(name = "ocena")
    private float ocena;

    @NonNull
    @Json(name = "slika")
    private String slika;

    @NonNull
    @Json(name = "tip")
    private String tip;

    @Json(name = "id_restavracija")
    private int idRestavracija;
    @Json(name = "ulica")
    private String ulica;
    @Json(name = "hisna_stevilka")
    private int hisnaStevilka;
    @Json(name = "postna_stevilka")
    private int postnaStevilka;
    @Json(name = "kraj")
    private String kraj;


    public int getIdRestavracija() {
        return idRestavracija;
    }

    public void setIdRestavracija(int idRestavracija) {
        this.idRestavracija = idRestavracija;
    }

    public String getImeRestavracije() {
        return imeRestavracije;
    }

    public void setImeRestavracije(String imeRestavracije) {
        this.imeRestavracije = imeRestavracije;
    }

    public float getOcena() {
        return ocena;
    }

    public void setOcena(float ocena) {
        this.ocena = ocena;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public int getHisnaStevilka() {
        return hisnaStevilka;
    }

    public void setHisnaStevilka(int hisnaStevilka) {
        this.hisnaStevilka = hisnaStevilka;
    }

    public int getPostnaStevilka() {
        return postnaStevilka;
    }

    public void setPostnaStevilka(int postnaStevilka) {
        this.postnaStevilka = postnaStevilka;
    }

    public String getKraj() {
        return kraj;
    }

    public void setKraj(String kraj) {
        this.kraj = kraj;
    }

    public String getSlika() {
        return slika;
    }

    public void setSlika(String slika) {
        this.slika = slika;
    }

    @Override
    public String toString() {
        return "Ime: " + this.getImeRestavracije() + "\n" +
                "ID: " + this.getIdRestavracija() + "\n" +
                "Ocena: " + this.getOcena() + "\n" +
                "Tip: " + this.getTip() + "\n" +
                "Ulica: " + this.getUlica() + "\n" +
                "Hisna stevilka: " + this.getHisnaStevilka() + "\n" +
                "Postna stevilka: " + this.getPostnaStevilka() + "\n" +
                "Kraj: " + this.getKraj() + "\n" +
                "Slika: " +  " BASE 64 STRING which is too long to output :)" /*this.getSlika()*/ + "\n";
    }
}