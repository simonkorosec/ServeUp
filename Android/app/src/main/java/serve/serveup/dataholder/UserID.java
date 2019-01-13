package serve.serveup.dataholder;
import com.squareup.moshi.Json;

public class UserID {

    @Json(name = "id_uporabnik")
    private String idUporabnik;

    public String getIdUporabnik() {
        return idUporabnik;
    }
    public void setIdUporabnik(String idUporabnik) {
        this.idUporabnik = idUporabnik;
    }
}
