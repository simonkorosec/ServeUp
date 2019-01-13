package serve.serveup.webservices;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import serve.serveup.dataholder.order.ReturnedOrderApiResponse;

public interface GetUserOrders {

    @POST("user/get_orders/")
    @FormUrlEncoded
    Call<ReturnedOrderApiResponse> getUserOrders(@Field("id_uporabnik") String uporabnikID, @Field("num_orders") int numOrders);
}
