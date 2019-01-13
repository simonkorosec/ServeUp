package serve.serveup.webservices;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import serve.serveup.dataholder.apistatus.ApiStatus;

public interface LoginStatus {

    // get Api returned status when you post userID to the server (UID)
    @POST("user/register/")
    @FormUrlEncoded
    Call<ApiStatus> getLoginStatus(@Field("id_uporabnik")  String id_uporabnik);
}
