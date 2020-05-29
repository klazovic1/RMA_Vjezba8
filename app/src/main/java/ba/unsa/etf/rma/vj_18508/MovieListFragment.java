package ba.unsa.etf.rma.vj_18508;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class MovieListFragment extends Fragment implements IMovieListView{

    private Button button;
    private EditText editText;
    private ListView listView;
    private ArrayAdapter<Movie> adapter;
    private MovieListAdapter movieListAdapter;
    private MovieCursorAdapter movieCursorAdapter;

    public interface OnItemClick {
        void onItemClicked(Boolean inDatabase, int id);
    }
    private OnItemClick onItemClick;

    public MovieListPresenter getPresenter() {
        if (movieListPresenter == null) {
            movieListPresenter = new MovieListPresenter(this, getActivity());
        }
        return movieListPresenter;
    }

    private MovieListPresenter movieListPresenter ;

    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceData){

        View fragmentView = inflater.inflate(R.layout.fragment_list, container, false);

        movieListPresenter = getPresenter();
        movieListAdapter = new MovieListAdapter(getActivity(), new ArrayList<Movie>());
        movieCursorAdapter= new MovieCursorAdapter(getActivity(), R.layout.list_element,null,false);
        listView = fragmentView.findViewById(R.id.listView);
        listView.setAdapter(movieCursorAdapter);
        listView.setOnItemClickListener(listItemClickListener);
        editText = fragmentView.findViewById(R.id.editText);
        button = fragmentView.findViewById(R.id.button);


        try {
            onItemClick = (OnItemClick)getActivity();
        }catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString() + "Treba implementirati OnItemClick");
        }

        //getPresenter().getMovies();

        getPresenter().getMoviesCursor();
        Intent intent = getActivity().getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if(Intent.ACTION_SEND.equals(action) && type != null){

            if("text/plain".equals(type)){
                String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
                if(sharedText != null){
                    editText.setText(sharedText);
                }
            }

        }


        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                movieListPresenter.searchMovies(editText.getText().toString());
            }
        });


        return fragmentView;
    }

    private AdapterView.OnItemClickListener listItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Movie movie = movieListAdapter.getMovie(position);
            onItemClick.onItemClicked(false, movie.getId());
        }
    };

    private AdapterView.OnItemClickListener listCursorItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Cursor cursor = (Cursor) parent.getItemAtPosition(position);
            if(cursor != null) {
                onItemClick.onItemClicked(true, cursor.getInt(cursor.getColumnIndex(MovieDBOpenHelper.MOVIE_INTERNAL_ID)));
            }
        }
    };


    @Override
    public void setMovies(List<Movie> movies) {
        movieListAdapter = new MovieListAdapter(getActivity(), movies);
        listView.setAdapter(movieListAdapter);
        //movieListAdapter.setMovies(movies);
    }

    @Override
    public void notifyMovieListDataSetChanged() {
        movieListAdapter.notifyDataSetChanged();
    }


    @Override
    public void setCursor(Cursor cursor) {
        listView.setAdapter(movieCursorAdapter);
        listView.setOnItemClickListener(listCursorItemClickListener);
        movieCursorAdapter.changeCursor(cursor);
    }




}
