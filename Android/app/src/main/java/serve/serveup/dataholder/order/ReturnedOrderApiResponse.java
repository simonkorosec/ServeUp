package serve.serveup.dataholder.order;

import com.squareup.moshi.Json;

import java.util.List;

public class ReturnedOrderApiResponse {

    @Json(name = "status")
    private int status;
    @Json(name = "description")
    private String description;
    @Json(name = "orders")
    private List<ReturnedOrder> orders;


    public int getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public List<ReturnedOrder> getOrders() {
        return orders;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOrders(List<ReturnedOrder> orders) {
        this.orders = orders;
    }
}
