package serve.serveup.views.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import serve.serveup.R;

public class SearchActivity extends AppCompatActivity {

    private ImageView backButton;
    private LinearLayout searchButton;
    private TextView lokacija;
    private TextView tipRestavracije;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        backButton = findViewById(R.id.backButtonSearch);
        searchButton = findViewById(R.id.searchButton);
        lokacija = findViewById(R.id.lokacijaText);
        tipRestavracije = findViewById(R.id.tipRestavracijeText);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getApplicationContext(), SearchedRestaurantsActivity.class);
                myIntent.putExtra("lokacija_text", lokacija.getText().toString().trim());
                myIntent.putExtra("tip_restavracije_text", tipRestavracije.getText().toString().trim());
                startActivity(myIntent);
            }
        });

    }
}
