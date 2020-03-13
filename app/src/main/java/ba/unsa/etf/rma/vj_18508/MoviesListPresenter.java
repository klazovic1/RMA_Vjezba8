package ba.unsa.etf.rma.vj_18508;

public class MoviesListPresenter {

    private MoviesListInteractor movieInteractor;

    MoviesListPresenter() {
        movieInteractor = new MoviesListInteractor();
    }

    public MoviesListInteractor getMovieInteractor() {
        return movieInteractor;
    }

    public void setMovieInteractor(MoviesListInteractor movieInteractor) {
        this.movieInteractor = movieInteractor;
    }
}
