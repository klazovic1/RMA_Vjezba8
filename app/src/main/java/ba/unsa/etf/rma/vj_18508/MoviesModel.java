package ba.unsa.etf.rma.vj_18508;

import java.util.ArrayList;
import java.util.List;

public class MoviesModel {

    private List<Movie> movies = new ArrayList<>();

    MoviesModel() {

        this.popuni();
    }

    private void popuni() {

        ArrayList<String> lista1 = new ArrayList<>();
        ArrayList<String> lista2 = new ArrayList<>();
        ArrayList<String> lista3 = new ArrayList<>();
        ArrayList<String> similar1 = new ArrayList<>();
        ArrayList<String> similar2 = new ArrayList<>();
        ArrayList<String> similar3 = new ArrayList<>();

        lista1.add("Edward Norton"); lista1.add("Brad Pitt"); lista1.add("Helena Bonham Carter"); lista1.add("Meat Loaf"); lista1.add("Zach Grenier");
        similar1.add("Reservoir Dogs"); similar1.add("Jackie Brown"); similar1.add("Pulp Fiction"); similar1.add("Memento"); similar1.add("Seven");


        movies.add( new Movie("Fight Club", "Drama", "https://www.imdb.com/title/tt0137523/",
                " 15 October 1999", "An insomniac office worker and a devil-may-care soapmaker form an underground fight club that evolves into something much, much more.",
                R.drawable.fight_club, "https://www.imdb.com/title/tt0137523/?ref_=tt_sims_tt",
                lista1, similar1));



        lista2.add("Morgan Freeman"); lista2.add("Brad Pitt"); lista2.add("Gwyneth Paltrow"); lista2.add("Kevin Spacey"); lista2.add("John Cassini");
        similar2.add("Fight Club"); similar2.add("Silence of the Lambs"); similar2.add("Inception"); similar2.add("Forest Gump"); similar2.add("Pulp Fiction");
        movies.add( new Movie("Se7en", "Mystery", "https://www.imdb.com/title/tt0114369/?ref_=tt_sims_tt",
                "22 September 1995", "Two detectives, a rookie and a veteran, hunt a serial killer who uses the seven deadly sins as his motives.",
                R.drawable.seven, "https://www.imdb.com/title/tt0114369/?ref_=tt_sims_tt",
                lista2, similar2));


        lista3.add("Anthony Hopkins"); lista3.add("Jodie Foster"); lista3.add("Ted Levine"); lista3.add("Scott Glenn"); lista3.add("Frankie Faison");
        similar3.add("Seven"); similar3.add("Fight Club"); similar3.add("Green Mile"); similar3.add("Saving Private Ryan"); similar3.add("Pulp Fiction"); similar3.add("Forest Gump");
        movies.add( new Movie("Silence of the Lambs", "Thriller", "https://www.imdb.com/title/tt0102926/?ref_=tt_sims_tt",
                " 14 February 1991", "A young F.B.I. cadet must receive the help of an incarcerated and manipulative cannibal killer to help catch another serial killer, a madman who skins his victims.",
                R.drawable.filmic, "https://www.imdb.com/title/tt0102926/?ref_=tt_sims_tt",
                lista3, similar3));

    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }


}
