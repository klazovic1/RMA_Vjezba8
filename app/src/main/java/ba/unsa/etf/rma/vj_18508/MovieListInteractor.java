package ba.unsa.etf.rma.vj_18508;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MovieListInteractor extends AsyncTask<String, Integer, Void> {

    private String tmdbAPIkey = "4923e81af87f0578853a71bfbf7eb12a";

    private List<Movie> movies = new ArrayList<>();

    private List<Movie> recentlySearchedMovies = new ArrayList<>();

    private OnMoviesSearchDone caller;

    private MovieDBOpenHelper movieDBOpenHelper;

    SQLiteDatabase database;

    private Movie movie = null;


    public MovieListInteractor(OnMoviesSearchDone p){

        caller = p;
        movies = new ArrayList<Movie>();

    }



    @Override
    protected Void doInBackground(String... strings) {

        String query = null;
        try {
            query = URLEncoder.encode(strings[0], "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url1 = "https://api.themoviedb.org/3/search/movie?api_key="
                + tmdbAPIkey + "&query=" + query;

        try {


            URL url = new URL(url1);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String rezultat = convertStreamToString(in);

            JSONObject jo = new JSONObject(rezultat);
            JSONArray results = jo.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {

                JSONObject movie = results.getJSONObject(i);
                String title = movie.getString("title");
                Integer id = movie.getInt("id");
                String posterPath = movie.getString("poster_path");
                String overview = movie.getString("overview");
                String releaseDate = movie.getString("release_date");
                //Filmove dodajemo u listu koja je privatni atribut klase
                movies.add(new Movie(id, title, overview, releaseDate, posterPath));
                if (i==4) break;
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }


    public List<Movie> getMovies(Context context){

        recentlySearchedMovies = new ArrayList<>();
        movieDBOpenHelper = new MovieDBOpenHelper(context);
        database = movieDBOpenHelper.getWritableDatabase();
        String query = "SELECT *"  + " FROM "
                + MovieDBOpenHelper.MOVIE_TABLE +" ORDER BY " +MovieDBOpenHelper.MOVIE_INTERNAL_ID + " DESC LIMIT 5";

        Cursor cursor = database.rawQuery(query,null);
        if(cursor.moveToFirst()) {
            do{
                int idPos = cursor.getColumnIndexOrThrow(MovieDBOpenHelper.MOVIE_ID);
                int titlePos = cursor.getColumnIndexOrThrow(MovieDBOpenHelper.MOVIE_TITLE);
                int genrePos = cursor.getColumnIndexOrThrow(MovieDBOpenHelper.MOVIE_GENRE);
                int homepagePos = cursor.getColumnIndexOrThrow(MovieDBOpenHelper.MOVIE_HOMEPAGE);
                int posterPos = cursor.getColumnIndexOrThrow(MovieDBOpenHelper.MOVIE_POSTERPATH);
                int overviewPos = cursor.getColumnIndexOrThrow(MovieDBOpenHelper.MOVIE_OVERVIEW);
                int releasePos = cursor.getColumnIndexOrThrow(MovieDBOpenHelper.MOVIE_RELEASEDATE);

                movie = new Movie(cursor.getInt(idPos), cursor.getString(titlePos), cursor.getString(overviewPos),
                        cursor.getString(releasePos), cursor.getString(posterPos), cursor.getString(homepagePos), cursor.getString(genrePos));


                Details();
                recentlySearchedMovies.add(movie);
            }while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return recentlySearchedMovies;



    }

    private void Details() {

        String query;
        query = "SELECT " + MovieDBOpenHelper.CAST_NAME + " FROM " +
                MovieDBOpenHelper.CAST_TABLE + " WHERE "+
                MovieDBOpenHelper.CAST_MOVIE_ID + " = ?";

        Cursor cursor = database.rawQuery(query, new String[]{Integer.toString(movie.getId())});
        ArrayList<String> cast = new ArrayList<String>();

        if(cursor.moveToFirst()){
            do{

                int castNamePos = cursor.getColumnIndexOrThrow(MovieDBOpenHelper.CAST_NAME);
                cast.add(cursor.getString(castNamePos));

            }while(cursor.moveToNext());
        }

        movie.setActors(cast);
        cursor.close();

        query = "SELECT " + MovieDBOpenHelper.SMOVIE_TITLE + " FROM "
                + MovieDBOpenHelper.SIMILIAR_MOVIES + " WHERE "
                + MovieDBOpenHelper.SMOVIES_MOVIE_ID + " = ?";

        Cursor cursor2 = database.rawQuery(query, new String[]{Integer.toString(movie.getId())});
        ArrayList<String> similarMovies = new ArrayList<String>();

        if(cursor2.moveToFirst()){
            do{

                int similarMovieNamePos = cursor2.getColumnIndexOrThrow(MovieDBOpenHelper.SMOVIE_TITLE);
                similarMovies.add(cursor2.getString(similarMovieNamePos));

            }while(cursor2.moveToNext());
        }

        movie.setSimilarMovies(similarMovies);
        cursor2.close();



    }


    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
        caller.onDone(movies);
    }



    public String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
        return sb.toString();
    }




//    private MoviesModel model;
//
//    public MovieListInteractor() {
//
//        model = new MoviesModel();
//
//    }
//
//    public MovieListInteractor(MoviesModel model) {
//        this.model = model;
//    }
//
//    public MoviesModel getModel() {
//        return model;
//    }
//
//    public void setModel(MoviesModel model) {
//        this.model = model;
//    }
//
//    public void addMovie(Movie m){
//        model.getMovies().add(m);
//    }
//
//    public void deleteMovie(Movie m){
//
//        model.getMovies().remove(m);
//    }
}
