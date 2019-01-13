package serve.serveup.webservices;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import serve.serveup.dataholder.RestaurantInfo;

public interface GetRestaurants {

    // get restaurants based on passed LOCATION parameter
    @POST("restaurant/home/")
    @FormUrlEncoded
    Call<List<RestaurantInfo>> getRestaurantsByCity(@Field("location") String city);
}
