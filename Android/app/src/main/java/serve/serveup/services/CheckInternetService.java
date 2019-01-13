package serve.serveup.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

public class CheckInternetService extends Service {

    public Context context = this;
    public Handler handler = new Handler();
    public static Runnable runnable = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        runnable = new Runnable() {
            public void run() {
                /*if(Utils.isConnectedToInternet(context)) {
                    Utils.showToast(context, "connected to internet");
                }
                else
                    Utils.showToast(context, "not connected to internet");

                handler.postDelayed(runnable, 3000)
                */
            }
        };

        handler.postDelayed(runnable, 5000);
    }

    @Override
    public void onDestroy() {
        /* IF YOU WANT THIS SERVICE KILLED WITH THE APP THEN UNCOMMENT THE FOLLOWING LINE */
        handler.removeCallbacks(runnable);
        Toast.makeText(this, "Service stopped", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart(Intent intent, int startid) {
        Toast.makeText(this, "Service started by user.", Toast.LENGTH_SHORT).show();


    }

}
