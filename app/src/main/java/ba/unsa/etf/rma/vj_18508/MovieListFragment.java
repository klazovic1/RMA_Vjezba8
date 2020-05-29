package ba.unsa.etf.rma.vj_18508;

import android.content.Intent;
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

    public interface OnItemClick {
        void onItemClicked(Movie movie);
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
        listView = fragmentView.findViewById(R.id.listView);
        listView.setAdapter(movieListAdapter);
        listView.setOnItemClickListener(listItemClickListener);
        editText = fragmentView.findViewById(R.id.editText);
        button = fragmentView.findViewById(R.id.button);

        try {
            onItemClick = (OnItemClick)getActivity();
        }catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString() + "Treba implementirati OnItemClick");
        }

        getPresenter().getMovies();

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
            onItemClick.onItemClicked(movie);
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






}
