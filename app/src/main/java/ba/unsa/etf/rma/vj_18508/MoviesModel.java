package ba.unsa.etf.rma.vj_18508;

import java.util.ArrayList;
import java.util.List;

public class MoviesModel {

    private List<Movie> movies = new ArrayList<>();

    MoviesModel() {

        this.popuni();
    }

    private void popuni() {

        movies.add( new Movie("Fight Club", "Drama", "https://www.imdb.com/title/tt0137523/",
                " 15 October 1999", "dobar bas", R.drawable.fight_club));

        movies.add( new Movie("Se7en", "Mystery", "https://www.imdb.com/title/tt0114369/?ref_=tt_sims_tt",
                "22 September 1995", "bas dobar bas bas", R.drawable.seven));

        movies.add( new Movie("The Silence of the Lambs", "Thriller", "https://www.imdb.com/title/tt0102926/?ref_=tt_sims_tt",
                " 14 February 1991", "arane znaci", R.drawable.filmic));

    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }


}
