package serve.serveup.webservices;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import serve.serveup.dataholder.MealInfo;
import serve.serveup.dataholder.RestaurantInfo;
import serve.serveup.dataholder.UserID;
import serve.serveup.dataholder.apistatus.ApiStatus;
import serve.serveup.dataholder.order.Order;
import serve.serveup.dataholder.order.ReturnedOrderApiResponse;

public class RestManagement {

    private static String baseURL = "https://serveup-backend.herokuapp.com/api/";

    private static Retrofit myRetrofit = new Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build();


    public static Call<ApiStatus> getLoginStatusCall(String id_uporabnik) {
        return myRetrofit
                .create(LoginStatus.class)
                .getLoginStatus(id_uporabnik);
    }

    public static Call<List<UserID>> getAllUsers() {
        return myRetrofit
                .create(GetUsers.class)
                .getAllUsers();
    }

    public static Call<List<RestaurantInfo>> getAllRestaurants(String location) {
        return myRetrofit
                .create(GetRestaurants.class)
                .getRestaurantsByCity(location);
    }

    public static Call<Map<String, List<MealInfo>>> getRestraurantMeals(int id) {
        return myRetrofit
                .create(GetRestaurantMeals.class)
                .getRestaurantMeals(id);
    }

    public static Call<ApiStatus> createNewOrderByUser(Order myOrder) {
        return myRetrofit
                .create(NewOrderByUser.class)
                .createNewOrderByUser(myOrder);
    }

    public static Call<ReturnedOrderApiResponse> getUserOrders(String userID, int numOrders) {
        return myRetrofit
                .create(GetUserOrders.class)
                .getUserOrders(userID, numOrders);
    }

    public static Call<ApiStatus> orderCheckIn(int idNarocila, String qrCode) {
        return myRetrofit
                .create(OrderCheckIn.class)
                .orderCheckIn(idNarocila, qrCode);
    }






}
