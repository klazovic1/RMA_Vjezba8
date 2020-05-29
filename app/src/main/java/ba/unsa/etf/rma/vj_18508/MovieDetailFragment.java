package ba.unsa.etf.rma.vj_18508;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;


public class MovieDetailFragment extends Fragment implements IMovieDetailView{

    private ImageView imageView;
    private TextView textView;
    private TextView overview;
    private TextView genre;
    private TextView link;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private Button btnShare;
    private ToggleButton toggleButton;
    private MovieDetailPresenter movieDetailPresenter ;
    private String posterPath="https://image.tmdb.org/t/p/w342";


    public MovieDetailPresenter getPresenter() {
        if (movieDetailPresenter == null)
            movieDetailPresenter = new MovieDetailPresenter(this, getActivity());
        return movieDetailPresenter;
    }



    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceData){

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        imageView = view.findViewById(R.id.imageView);
        textView = view.findViewById(R.id.textView);
        overview = view.findViewById(R.id.overview);
        genre = view.findViewById(R.id.genre);
        link = view.findViewById(R.id.link);
        btnShare = view.findViewById(R.id.btnShare);
        toggleButton = view.findViewById(R.id.toggle_button);

        link.setOnClickListener(linkOnClickListener);
        textView.setOnClickListener(titleOnClickListener);
        btnShare.setOnClickListener(shareOnClickListener);
        toggleButton.setOnCheckedChangeListener(toggleOnCheckedChangeListener);

        movieDetailPresenter = getPresenter();

        if(getArguments() != null && getArguments().containsKey("id")) {
            //movieDetailPresenter.setMovie(getArguments().getParcelable("movie"));
            int movieId = getArguments().getInt("id");
            movieDetailPresenter.searchMovie(Integer.toString(movieId));
        }

        if (getArguments() != null && getArguments().containsKey("internalId")) {
            int id = getArguments().getInt("internalId");
            getPresenter().getDatabaseMovie(id);
            refreshView();
        }


//            Movie currentMovie = movieDetailPresenter.getMovie();
//            imageView.setImageResource(currentMovie.getSlika());
//            textView.setText(currentMovie.getName());
//            overview.setText(currentMovie.getOverview());
//            genre.setText(currentMovie.getGenre());
//            link.setText(currentMovie.getLink());


//            Bundle arguments = new Bundle();
//            arguments.putStringArrayList("cast", currentMovie.getActors());
//            CastFragment castFragment = new CastFragment();
//            castFragment.setArguments(arguments);
//            getChildFragmentManager().beginTransaction()
//                    .add(R.id.frame, castFragment)
//                    .commit();










        return view;
    }


    private View.OnClickListener linkOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String url = (String) link.getText();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
    };

    private View.OnClickListener titleOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String name = (String) textView.getText();
            Intent intent1 = new Intent(Intent.ACTION_SEARCH);
            intent1.setPackage("com.google.android.youtube");
            intent1.putExtra("query", name + " trailer");   // bitno da pise tacno query
            //intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent1);
        }
    };

    private View.OnClickListener shareOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String tekst = (String)overview.getText();
            Intent btnIntent = new Intent (Intent.ACTION_SEND);
            btnIntent.putExtra(Intent.EXTRA_TEXT, tekst);
            btnIntent.setType("text/plain");
            startActivity(btnIntent);

        }
    };

    private CompoundButton.OnCheckedChangeListener toggleOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                Bundle arguments = new Bundle();
                arguments.putStringArrayList("similar", movieDetailPresenter.getMovie().getSimilarMovies());
                SimilarFragment similarFragment = new SimilarFragment();
                similarFragment.setArguments(arguments);
                getChildFragmentManager().beginTransaction()
                        .replace(R.id.frame, similarFragment).commit();
            } else {
                Bundle arguments = new Bundle();
                arguments.putStringArrayList("cast", movieDetailPresenter.getMovie().getActors());
                CastFragment castFragment = new CastFragment();
                castFragment.setArguments(arguments);
                getChildFragmentManager().beginTransaction()
                        .replace(R.id.frame, castFragment).commit();
            }
        }
    };



    @Override
    public void refreshView() {

        Movie movie = getPresenter().getMovie();
        textView.setText(movie.getName());
        genre.setText(movie.getGenre());
        overview.setText(movie.getOverview());
        //releaseDate.setText(movie.getReleaseDate());
        link.setText(movie.getHomepage());
        Glide.with(getContext())
                .load(posterPath+movie.getPosterPath())
                .centerCrop()
                .placeholder(R.drawable.filmic)
                .error(R.drawable.filmic)
                .fallback(R.drawable.filmic)
                .into(imageView);
        Bundle arguments = new Bundle();
        arguments.putStringArrayList("cast", movie.getActors());
        CastFragment castFragment = new CastFragment();
        castFragment.setArguments(arguments);
        getChildFragmentManager().beginTransaction()
                .add(R.id.frame, castFragment)
                .commit();



    }




}
