package ba.unsa.etf.rma.vj_18508;

import android.content.Context;
import android.os.Parcelable;

public class MovieDetailPresenter {

    private Context context;
    private Movie movie;
    private MoviesListInteractor moviesListInteractor;

    MovieDetailPresenter() {

    }

    public void create() {
        moviesListInteractor.addMovie(movie);
    }

    public void setMovie(Parcelable movie){
        this.movie = (Movie)movie;
    }

    public Movie getMovie() {
        return movie;
    }

}
