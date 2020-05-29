package ba.unsa.etf.rma.vj_18508;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public interface IMovieListView {

    void setMovies(List<Movie> movies);
    void notifyMovieListDataSetChanged();
    void setCursor(Cursor cursor);

}
