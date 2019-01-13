package serve.serveup.views.order;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import serve.serveup.R;
import serve.serveup.dataholder.apistatus.ApiStatus;
import serve.serveup.dataholder.apistatus.ApiStatusType;
import serve.serveup.dataholder.order.Order;
import serve.serveup.dataholder.session.Session;
import serve.serveup.dataholder.session.SessionContent;
import serve.serveup.utils.ContentStore;
import serve.serveup.utils.Utils;
import serve.serveup.webservices.RestManagement;

public class ProcessingPaymentActivity extends AppCompatActivity {

    private ContentStore cntStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing_payment);

        cntStore =  new ContentStore(getApplicationContext());
        Order myOrder = createNewOrder();

        Intent myIntent = getIntent();
        if(myIntent != null) {
            String casPrevzema = myIntent.getStringExtra("payment_order_time");
            myOrder.setCasPrevzema(casPrevzema);

            RestManagement.createNewOrderByUser(myOrder).enqueue(new Callback<ApiStatus>() {
                @Override
                public void onResponse(Call<ApiStatus> call, Response<ApiStatus> response) {
                    ApiStatus myStatus = response.body();
                    if(myStatus.getStatus() == ApiStatusType.OK_STATUS.getStatus()) {
                        Utils.logInfo("order created by user");
                        orderFinishedDelayAndClearBasketSession();
                    }
                    else
                        Utils.logInfo("error with creating new order :/");
                }
                @Override
                public void onFailure(Call<ApiStatus> call, Throwable t) {
                    Utils.logInfo("api 'orders/new_order/' failed");
                }
            });
        }
    }

    private Order createNewOrder() {
        Order newOrder = new Order();
        Session currentSesh = cntStore.getSession();
        newOrder.setCasNarocila(Utils.createDateTimeString());

        if (currentSesh.mealsNotEmpty() && currentSesh.userIsSet() && currentSesh.restaurantIsSet()) {
            newOrder.setRestavracijaID(currentSesh.getCurrentRestaurant().getIdRestavracija());
            newOrder.setUporabnikID(currentSesh.getCurrentUser());
            newOrder.setMeals(currentSesh.getAllMeals());
        }
        return newOrder;
    }

    public void orderFinishedDelayAndClearBasketSession() {
        Handler myHandler = new Handler();
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Utils.showToast(getApplicationContext(), "Naročilo uspešno opravljeno");

                cntStore.deleteFromSession(SessionContent.RESTUANRANT);
                cntStore.deleteFromSession(SessionContent.MEALS);
                finish();
            }}, 4000);
    }
}
