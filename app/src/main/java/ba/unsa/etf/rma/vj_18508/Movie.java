package ba.unsa.etf.rma.vj_18508;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    private String name, genre, homepage, releaseDate, overview;
    private int slika;
    private String link;
    private ArrayList<String> actors = new ArrayList<>();


    public Movie(String name, String genre, String homepage, String releaseDate, String overview, int s, String link, ArrayList<String> list) {
        this.name = name;
        this.genre = genre;
        this.homepage = homepage;
        this.releaseDate = releaseDate;
        this.overview = overview;
        slika = s;
        this.link = link;
        this.actors = list;
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
}
