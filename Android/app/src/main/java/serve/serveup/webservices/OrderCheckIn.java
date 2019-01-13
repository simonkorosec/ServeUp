package serve.serveup.webservices;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import serve.serveup.dataholder.apistatus.ApiStatus;

public interface OrderCheckIn {

    @POST("user/check_in/")
    @FormUrlEncoded
    Call<ApiStatus> orderCheckIn(@Field("id_narocilo") int idNarocila, @Field("qr") String qrCode);
}
