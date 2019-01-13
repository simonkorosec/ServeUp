package serve.serveup.webservices;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import serve.serveup.dataholder.MealInfo;

public interface GetRestaurantMeals {

    // get all the meals of certain category for the current passed restaurant ID
    @GET("meals/")
    Call<Map<String, List<MealInfo>>> getRestaurantMeals(@Query("id_restavracija") int id);
}
