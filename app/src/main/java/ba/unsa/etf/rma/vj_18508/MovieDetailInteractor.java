package ba.unsa.etf.rma.vj_18508;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
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

    private MovieDBOpenHelper movieDBOpenHelper;

    SQLiteDatabase database;


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


    public void save (Movie movie, Context context){

        ContentResolver cr = context.getApplicationContext().getContentResolver();
        Uri moviesURI = Uri.parse("content://rma.provider.movies/elements");
        Uri castURI = Uri.parse("content://rma.provider.cast/elements");
        Uri similarURI = Uri.parse("content://rma.provider.similar/elements");
        ContentValues values = new ContentValues();
        values.put(MovieDBOpenHelper.MOVIE_ID, movie.getId());
        values.put(MovieDBOpenHelper.MOVIE_TITLE, movie.getName());
        values.put(MovieDBOpenHelper.MOVIE_GENRE, movie.getGenre());
        values.put(MovieDBOpenHelper.MOVIE_HOMEPAGE, movie.getHomepage());
        values.put(MovieDBOpenHelper.MOVIE_OVERVIEW, movie.getOverview());
        values.put(MovieDBOpenHelper.MOVIE_POSTERPATH, movie.getPosterPath());
        values.put(MovieDBOpenHelper.MOVIE_RELEASEDATE, movie.getReleaseDate());
        cr.insert(moviesURI,values);

        for (int i = 0; i < movie.getActors().size(); i++){
            String name = movie.getActors().get(i);
            ContentValues cast = new ContentValues();
            cast.put(MovieDBOpenHelper.CAST_NAME,name);
            cast.put(MovieDBOpenHelper.CAST_MOVIE_ID, movie.getId());
            cr.insert(castURI,cast);
        }

        for (int i = 0; i < movie.getSimilarMovies().size(); i++){
            String title = movie.getSimilarMovies().get(i);
            ContentValues similar = new ContentValues();
            similar.put(MovieDBOpenHelper.SMOVIE_TITLE,title);
            similar.put(MovieDBOpenHelper.SMOVIES_MOVIE_ID, movie.getId());
            cr.insert(similarURI,similar);
        }

    }

    //@Override
    public Movie getMovie(Context context, Integer id) {
        ContentResolver cr = context.getApplicationContext().getContentResolver();
        String[] kolone = null;
        Uri adresa = ContentUris.withAppendedId(Uri.parse("content://rma.provider.movies/elements"),id);
        String where = null;
        String whereArgs[] = null;
        String order = null;
        Cursor cursor = cr.query(adresa,kolone,where,whereArgs,order);
        if (cursor != null){
            cursor.moveToFirst();
            int idPos = cursor.getColumnIndexOrThrow(MovieDBOpenHelper.MOVIE_ID);
            int internalId = cursor.getColumnIndexOrThrow(MovieDBOpenHelper.MOVIE_INTERNAL_ID);
            int titlePos = cursor.getColumnIndexOrThrow(MovieDBOpenHelper.MOVIE_TITLE);
            int genrePos = cursor.getColumnIndexOrThrow(MovieDBOpenHelper.MOVIE_GENRE);
            int homepagePos = cursor.getColumnIndexOrThrow(MovieDBOpenHelper.MOVIE_HOMEPAGE);
            int posterPos = cursor.getColumnIndexOrThrow(MovieDBOpenHelper.MOVIE_POSTERPATH);
            int overviewPos = cursor.getColumnIndexOrThrow(MovieDBOpenHelper.MOVIE_OVERVIEW);
            int releasePos = cursor.getColumnIndexOrThrow(MovieDBOpenHelper.MOVIE_RELEASEDATE);
            movie = new Movie(cursor.getInt(idPos), cursor.getString(titlePos),
                    cursor.getString(overviewPos), cursor.getString(releasePos), cursor.getString(posterPos),
                    cursor.getString(homepagePos), cursor.getString(genrePos),cursor.getInt(internalId));

        }
        cursor.close();
        return movie;
    }

    //@Override
    public Cursor getCastCursor(Context context, int id) {
        ContentResolver cr = context.getApplicationContext().getContentResolver();
        String[] kolone = new String[]{
                MovieDBOpenHelper.CAST_ID,
                MovieDBOpenHelper.CAST_NAME,
        };
        Uri adresa = ContentUris.withAppendedId(Uri.parse("content://rma.provider.cast/elements"), id);
        String where = null;
        String whereArgs[] = null;
        String order = null;
        Cursor cur = cr.query(adresa, kolone, where, whereArgs, order);
        return cur;

    }

    //@Override
    public Cursor getSimilarCursor(Context context, int id) {
        ContentResolver cr = context.getApplicationContext().getContentResolver();
        String[] kolone = new String[]{
                MovieDBOpenHelper.SMOVIES_ID,
                MovieDBOpenHelper.SMOVIE_TITLE
        };
        Uri adresa = ContentUris.withAppendedId(Uri.parse("content://rma.provider.similar/elements"), id);
        String where = null;
        String whereArgs[] = null;
        String order = null;
        Cursor cur = cr.query(adresa, kolone, where, whereArgs, order);
        return cur;
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
