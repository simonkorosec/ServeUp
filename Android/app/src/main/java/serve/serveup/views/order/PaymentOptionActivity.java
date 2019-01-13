package serve.serveup.views.order;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;

import serve.serveup.R;
import serve.serveup.utils.Utils;

public class PaymentOptionActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private View payButton;
    private View backToOrderIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_option);

        radioGroup = findViewById(R.id.radioGroup);
        payButton = findViewById(R.id.payButton);
        backToOrderIcon = findViewById(R.id.backToBasketIcon);

        Intent myIntent = getIntent();

        backToOrderIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
             }
        });

        if(myIntent != null) {
            final String casPrevzema = myIntent.getStringExtra("order_pickup_time");

            payButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int radioID = radioGroup.getCheckedRadioButtonId();
                    View radioButton = findViewById(radioID);
                    int index = radioGroup.indexOfChild(radioButton);

                    if (index != -1) {
                        AlertDialog.Builder bulder = new AlertDialog.Builder(PaymentOptionActivity.this);
                        bulder.setTitle("Potrditev plačila");
                        bulder.setMessage("Potrjujem izbiro plačila");
                        bulder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent myIntent = new Intent(getApplicationContext(), ProcessingPaymentActivity.class);
                                myIntent.putExtra("payment_order_time", casPrevzema);
                                startActivity(myIntent);
                                finish();
                            }
                        }).create().show();
                    } else
                        Utils.showToast(getApplicationContext(), "Prosimo izberite možnost plačila");
                }
            });

        }

    }
}
