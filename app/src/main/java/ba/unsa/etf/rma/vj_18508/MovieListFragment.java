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

public class MovieListFragment extends Fragment {

    private Button button;
    private EditText editText;
    private ListView listView;
    private ArrayAdapter<Movie> adapter;
    private MoviesListPresenter moviesListPresenter = new MoviesListPresenter();
    private MovieListAdapter movieListAdapter;

    public interface OnItemClick {
        void onItemClicked(Movie movie);
    }
    private OnItemClick onItemClick;

    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceData){

        View fragmentView = inflater.inflate(R.layout.fragment_list, container, false);

        movieListAdapter = new MovieListAdapter(getActivity(), moviesListPresenter.getMovieInteractor().getModel().getMovies());
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




        return fragmentView;
    }

    private AdapterView.OnItemClickListener listItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Movie movie = movieListAdapter.getMovie(position);
            onItemClick.onItemClicked(movie);
        }
    };






}
