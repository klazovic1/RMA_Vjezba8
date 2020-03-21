package ba.unsa.etf.rma.vj_18508;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;
    private TextView overview;
    private TextView genre;
    private TextView link;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private Button btnShare;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_element);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        overview = findViewById(R.id.overview);
        genre = findViewById(R.id.genre);
        link = findViewById(R.id.link);
        listView = findViewById(R.id.glumciList);
        btnShare = findViewById(R.id.btnShare);
        Intent intent = getIntent();
        String receivedName = intent.getStringExtra("name");
        String receivedOverview = intent.getStringExtra("overview");
        String receivedGenre = intent.getStringExtra("genre");
        String receivedLink = intent.getStringExtra("link");
        List<String> receivedList = intent.getStringArrayListExtra("list");
        int receivedImg = intent.getIntExtra("image", 0);
        textView.setText(receivedName);
        overview.setText(receivedOverview);
        genre.setText(receivedGenre);
        link.setText(receivedLink);
        imageView.setImageResource(receivedImg);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, receivedList);
        listView.setAdapter(adapter);

       getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = (String) textView.getText();
                Intent intent1 = new Intent(Intent.ACTION_SEARCH);
                intent1.setPackage("com.google.android.youtube");
                intent1.putExtra("query", name + " trailer");   // bitno da pise tacno query
                //intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);

            }
        });

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = (String) link.getText();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tekst = (String)overview.getText();
                Intent btnIntent = new Intent (Intent.ACTION_SEND);
                btnIntent.putExtra(Intent.EXTRA_TEXT, tekst);
                btnIntent.setType("text/plain");
                startActivity(btnIntent);
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}

