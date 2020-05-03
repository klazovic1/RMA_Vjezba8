package ba.unsa.etf.rma.vj_18508;

import android.content.Context;

import java.util.ArrayList;

public class MoviesListPresenter implements OnMoviesSearchDone {

    private IMovieListView view;
    private MovieListInteractor interactor;
    private Context context;

    public MovieListPresenter(IMovieListView view, Context context) {
        this.view       = view;
        this.interactor = new MovieListInteractor();
        this.context    = context;
    }

    public MovieListInteractor getMovieInteractor() {
        return interactor;
    }

    public void setMovieInteractor(MovieListInteractor movieInteractor) {
        this.interactor = movieInteractor;
    }

    public void searchMovies(String query){
        new MovieListInteractor((OnMoviesSearchDone)this).execute(query);
    }



    @Override
    public void onDone(ArrayList<Movie> results) {
        view.setMovies(results);
        view.notifyMovieListDataSetChanged();
    }


}
