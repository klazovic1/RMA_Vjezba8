package ba.unsa.etf.rma.vj_18508;

import android.content.Context;
import android.os.Parcelable;

import java.util.ArrayList;

public class MovieDetailPresenter implements OnMovieSearchDone{

    private Context context;
    private Movie movie;
    private MovieDetailInteractor interactor;

    private IMovieDetailView view;

    MovieDetailPresenter(IMovieDetailView view, Context context) {

        this.view = view;
        this.context = context;
        interactor = new MovieDetailInteractor(this);

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

    public void searchMovie (String query){

        new MovieDetailInteractor((OnMovieSearchDone)this).execute(query);

    }

    @Override
    public void onDone(Movie result){
        movie = result;
        interactor.save(movie, context.getApplicationContext());
        view.refreshView();
    }


}
