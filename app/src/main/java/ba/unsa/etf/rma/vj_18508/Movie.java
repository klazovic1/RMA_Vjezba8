package ba.unsa.etf.rma.vj_18508;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Movie implements Parcelable {

    private String name, genre, homepage, releaseDate, overview;
    private int slika;
    private String link;
    private ArrayList<String> actors = new ArrayList<>();
    private ArrayList<String> similarMovies;

    private int id;
    private String posterPath;


    public Movie(int id, String name, String overview, String releaseDate, String posterPath){

        this.id = id;
        this.name = name;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;

    }

    public Movie(int id, String name, String overview, String releaseDate, String posterPath, String homepage, String genre){

        this.id = id;
        this.name = name;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.homepage = homepage;
        this.genre = genre;

    }

    public Movie(String name, String genre, String homepage, String releaseDate, String overview,
                 int s, String link, ArrayList<String> list, ArrayList<String> similarMovies) {
        this.name = name;
        this.genre = genre;
        this.homepage = homepage;
        this.releaseDate = releaseDate;
        this.overview = overview;
        slika = s;
        this.link = link;
        this.actors = list;
        this.similarMovies = similarMovies;

    }


    public Movie(String name, String genre, String homepage, String releaseDate, String overview,
                 int s, String link, ArrayList<String> list ) {
        this.name = name;
        this.genre = genre;
        this.homepage = homepage;
        this.releaseDate = releaseDate;
        this.overview = overview;
        slika = s;
        this.link = link;
        this.actors = list;
    }

    public Movie(String title, String overview, String releaseDate, String genre, String homepage, ArrayList<String> actors){

        this.name = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.genre = genre;
        this.homepage = homepage;
        this.actors = actors;

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getSlika() {
        return slika;
    }

    public void setSlika(int slika) {
        this.slika = slika;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(ArrayList<String> actors) {
        this.actors = actors;
    }


    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeString(genre);
        out.writeString(homepage);
        out.writeString(releaseDate);
        out.writeString(overview);
        out.writeString(link);
        out.writeInt(slika);
        out.writeStringList(actors);
        out.writeStringList(similarMovies);
        out.writeInt(id);
        out.writeString(posterPath);
    }

    protected Movie(Parcel in){

        name = in.readString();
        genre = in.readString();
        homepage = in.readString();
        releaseDate = in.readString();
        overview = in.readString();
        link = in.readString();
        slika = in.readInt();
        in.readStringList(actors);
        actors = in.createStringArrayList();
        similarMovies = in.createStringArrayList();
        id = in.readInt();
        posterPath = in.readString();

    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


    public ArrayList<String> getSimilarMovies() {
        return similarMovies;
    }

    public void setSimilarMovies(ArrayList<String> similarMovies) {
        this.similarMovies = similarMovies;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }


    public static Creator<Movie> getCREATOR() {
        return CREATOR;
    }

}
