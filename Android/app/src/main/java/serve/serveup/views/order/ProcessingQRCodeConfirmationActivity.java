package serve.serveup.views.order;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import serve.serveup.R;
import serve.serveup.dataholder.apistatus.ApiStatus;
import serve.serveup.dataholder.apistatus.ApiStatusType;
import serve.serveup.utils.Utils;
import serve.serveup.webservices.RestManagement;

public class ProcessingQRCodeConfirmationActivity extends AppCompatActivity {


    private String qrCode;
    private int idNarocila;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing_qrcode_confirmation);

        if(getIntent() != null) {

            qrCode = getIntent().getStringExtra("qrcode");
            idNarocila = getIntent().getIntExtra("scan_id_narocilo", -1);

            Utils.logInfo("id narocila: " + idNarocila);
            Utils.logInfo("qrcode : " + qrCode);

            if(!Utils.isNullOrEmpty(qrCode)) {

                RestManagement.orderCheckIn(idNarocila, qrCode).enqueue(new Callback<ApiStatus>() {
                    @Override
                    public void onResponse(Call<ApiStatus> call, Response<ApiStatus> response) {

                        if(response.code() == 200) {

                            ApiStatus status = response.body();
                            if (status.getStatus() == ApiStatusType.OK_STATUS.getStatus()) {
                                Utils.logInfo("order confirmed with qrcode: " + qrCode);
                                Utils.logInfo("user checked in");
                                orderConfirmed();
                            }
                        }
                        else {
                            Utils.logInfo("response code for posting qrcode: " + response.code());
                        }
                    }
                    @Override
                    public void onFailure(Call<ApiStatus> call, Throwable t) {
                        Utils.logInfo("api 'user/check_in/' failed");
                    }
                });
            }
        }
    }

    public void orderConfirmed() {
        Handler myHandler = new Handler();
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Utils.showToast(getApplicationContext(), "Naročilo uspešno potrjeno");
                finish();
            }}, 3000);
    }
}
