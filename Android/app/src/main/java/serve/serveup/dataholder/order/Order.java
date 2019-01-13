package serve.serveup.dataholder.order;

import com.squareup.moshi.Json;
import java.util.List;
import serve.serveup.dataholder.MealInfo;

public class Order {

    @Json(name = "cas_prevzema")
    private String casPrevzema;
    @Json(name = "cas_narocila")
    private String casNarocila;
    @Json(name = "id_restavracija")
    private int restavracijaID;
    @Json(name = "id_uporabnik")
    private String uporabnikID;
    @Json(name = "jedi")
    private List<MealInfo> meals;


    public void setCasNarocila(String casNarocila) {
        this.casNarocila = casNarocila;
    }
    public void setCasPrevzema(String casPrevzema) {
        this.casPrevzema = casPrevzema;
    }
    public void setRestavracijaID(int restavracijaID) {
        this.restavracijaID = restavracijaID;
    }
    public void setUporabnikID(String uporabnikID) {
        this.uporabnikID = uporabnikID;
    }
    public void setMeals(List<MealInfo> meals) {
        this.meals = meals;
    }

    public String getCasNarocila() {
        return casNarocila;
    }
    public String getCasPrevzema() {
        return casPrevzema;
    }
    public int getRestavracijaID() {
        return restavracijaID;
    }
    public String getUporabnikID() {
        return uporabnikID;
    }
    public List<MealInfo> getMeals() {
        return meals;
    }
}
