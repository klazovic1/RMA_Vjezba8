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

public class MovieDetailInteractor extends AsyncTask<String, Integer, Void> {

    private String tmdbAPIkey = "4923e81af87f0578853a71bfbf7eb12a";
    private Movie movie;
    private OnMovieSearchDone caller;


    public MovieDetailInteractor(OnMovieSearchDone onMovieSearchDone) {

        caller = onMovieSearchDone;

    }


    @Override
    protected Void doInBackground(String... strings) {

        String query = null;
        try {
            query = URLEncoder.encode(strings[0], "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url1 = "https://api.themoviedb.org/3/movie/"+query+"?api_key="+tmdbAPIkey;

        try {


            URL url = new URL(url1);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String rezultat = convertStreamToString(in);

            JSONObject jo = new JSONObject(rezultat);
            String title = jo.getString("title");
            Integer id = jo.getInt("id");
            String posterPath = jo.getString("poster_path");
            String overview = jo.getString("overview");
            String releaseDate = jo.getString("release_date");
            String homepage = jo.getString("homepage");
            String genre = jo.getJSONArray("genres").getJSONObject(0).getString("name");
            movie = new Movie(id, title, overview, releaseDate, posterPath, homepage, genre);

            AddCast(query);
            AddSimilarMovies(query);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    protected Void AddCast(String... params)
    {
        try {
            String  query = URLEncoder.encode(params[0], "utf-8");

            URL url = null;
            String url1 = "https://api.themoviedb.org/3/movie/"+query+"/credits?api_key="+tmdbAPIkey;
            try {
                url = new URL(url1);
            }catch (MalformedURLException e){
                e.printStackTrace();
            }
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String rezultat = convertStreamToString(in);
            JSONObject jo = new JSONObject(rezultat);
            JSONArray items = jo.getJSONArray("cast");
            ArrayList<String> list = new ArrayList<String>();
            for (int i = 0; i < items.length(); i++) {
                JSONObject song = items.getJSONObject(i);
                String name = song.getString("name");
                list.add(name);
                if (i==4) break;
            }
            movie.setActors(list);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    protected Void AddSimilarMovies(String... params)
    {
        try {
            String  query = URLEncoder.encode(params[0], "utf-8");

            URL url = null;
            String url1 = "https://api.themoviedb.org/3/movie/" + query+ "/similar?api_key="+ tmdbAPIkey;
            try {
                url = new URL(url1);
            }catch (MalformedURLException e){
                e.printStackTrace();
            }
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String rezultat = convertStreamToString(in);
            JSONObject jo = new JSONObject(rezultat);
            JSONArray items = jo.getJSONArray("results");
            ArrayList<String> list = new ArrayList<String>();
            for (int i = 0; i < items.length(); i++) {
                JSONObject similarMovie = items.getJSONObject(i);
                String name = similarMovie.getString("title");
                list.add(name);
                if (i==4) break;
            }
            movie.setSimilarMovies(list);
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
        BufferedReader reader = new BufferedReader(new
                InputStreamReader(is));
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


    @Override
    protected void onPostExecute(Void aVoid){
        super.onPostExecute(aVoid);
        caller.onDone(movie);
    }




}
