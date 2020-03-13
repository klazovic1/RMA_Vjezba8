package ba.unsa.etf.rma.vj_18508;

public class MoviesListInteractor {

    private MoviesModel model;

    public MoviesListInteractor() {

        model = new MoviesModel();

    }

    public MoviesListInteractor(MoviesModel model) {
        this.model = model;
    }

    public MoviesModel getModel() {
        return model;
    }

    public void setModel(MoviesModel model) {
        this.model = model;
    }

    public void addMovie(Movie m){
        model.getMovies().add(m);
    }

    public void deleteMovie(Movie m){

        model.getMovies().remove(m);
    }
}
