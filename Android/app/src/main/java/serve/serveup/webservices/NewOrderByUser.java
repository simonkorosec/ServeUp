package serve.serveup.webservices;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import serve.serveup.dataholder.apistatus.ApiStatus;
import serve.serveup.dataholder.order.Order;

public interface NewOrderByUser {

    /*  create a new order by the user and send to
        server via POST request by an Order data class,
        get an ApiStatus returned.
    */
    @POST("orders/new_order/")
    Call<ApiStatus> createNewOrderByUser(@Body Order myOrder);
}
