package ba.unsa.etf.rma.vj_18508;

import android.content.Context;
import android.os.Parcelable;

import java.util.ArrayList;

public class MovieDetailPresenter {

    private Context context;
    private Movie movie;
    private MovieListInteractor movieListInteractor;

    MovieDetailPresenter() {

    }

    public void create(String title, String overview, String releaseDate, String genre, String homepage, ArrayList<String> actors) {
        this.movie = new Movie(title,overview,releaseDate,homepage,genre,actors);
    }

    public void setMovie(Parcelable movie){
        this.movie = (Movie)movie;
    }

    public Movie getMovie() {
        return movie;
    }

}
