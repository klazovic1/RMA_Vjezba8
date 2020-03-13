package ba.unsa.etf.rma.vj_18508;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private EditText editText;
    private ListView listView;
    private ArrayAdapter<Movie> adapter;
    private MoviesListPresenter moviesListPresenter = new MoviesListPresenter();
    private MovieListAdapter movieListAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editText);
        listView = (ListView) findViewById(R.id.list);
        movieListAdapter = new MovieListAdapter(this, R.layout.list_element, moviesListPresenter.getMovieInteractor().getModel().getMovies());
        listView.setAdapter(movieListAdapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String naziv = editText.getText().toString();
                moviesListPresenter.getMovieInteractor().addMovie(new Movie(naziv, "opa kiki", "", "", ""));
                adapter.notifyDataSetChanged();
                editText.setText("");
            }
        });
    }



}
