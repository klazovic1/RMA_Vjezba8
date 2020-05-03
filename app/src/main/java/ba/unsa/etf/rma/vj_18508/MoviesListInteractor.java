package ba.unsa.etf.rma.vj_18508;

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
//    public MoviesListInteractor() {
//
//        model = new MoviesModel();
//
//    }
//
//    public MoviesListInteractor(MoviesModel model) {
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
