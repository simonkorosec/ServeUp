package serve.serveup.webservices;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import serve.serveup.dataholder.UserID;

public interface GetUsers {

    // get all the user token IDs in a list
    @GET("user/")
    Call<List<UserID>> getAllUsers();
}
