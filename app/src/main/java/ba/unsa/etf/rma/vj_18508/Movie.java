package ba.unsa.etf.rma.vj_18508;

public class Movie {

    private String name, genre, homepage, releaseDate, overviewč


    public Movie(String name, String genre, String homepage, String releaseDate, String overviewč) {
        this.name = name;
        this.genre = genre;
        this.homepage = homepage;
        this.releaseDate = releaseDate;
        this.overviewč = overviewč;
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

    public String getOverviewč() {
        return overviewč;
    }

    public void setOverviewč(String overviewč) {
        this.overviewč = overviewč;
    }
}
