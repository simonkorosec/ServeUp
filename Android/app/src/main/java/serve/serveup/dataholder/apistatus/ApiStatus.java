package serve.serveup.dataholder.apistatus;
import com.squareup.moshi.Json;

public class ApiStatus {


    @Json(name = "status")
    private int status;
    @Json(name = "description")
    private String description;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

