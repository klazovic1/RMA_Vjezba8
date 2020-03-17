package ba.unsa.etf.rma.vj_18508;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
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
        movieListAdapter = new MovieListAdapter(this, moviesListPresenter.getMovieInteractor().getModel().getMovies());
        listView.setAdapter(movieListAdapter);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String naziv = editText.getText().toString();
//                moviesListPresenter.getMovieInteractor().addMovie(new Movie(naziv, "opa kiki", "", "", "", R.drawable.filmic, "https://www.imdb.com/title/tt1316037/?ref_=ttls_li_tt"));
//                movieListAdapter.notifyDataSetChanged();
//                editText.setText("");
//            }
//        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie clickedMovie = moviesListPresenter.getMovieInteractor().getModel().getMovies().get(position);
                Intent intent = new Intent(getApplicationContext(), MovieDetailActivity.class);
                intent.putExtra("name", clickedMovie.getName());
                intent.putExtra("image", clickedMovie.getSlika());
                intent.putExtra("overview", clickedMovie.getOverview());
                intent.putExtra("genre", clickedMovie.getGenre());
                intent.putExtra("link", clickedMovie.getLink());
                intent.putStringArrayListExtra("list", clickedMovie.getActors());
                startActivity(intent);
            }
        });

    }



}
