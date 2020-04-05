package ba.unsa.etf.rma.vj_18508;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MovieListAdapter extends ArrayAdapter<Movie> {

    private Context mContext;
    private List<Movie> movies = new ArrayList<>();

    public MovieListAdapter (@NonNull Context context, @LayoutRes List<Movie> items){
        super(context, 0, items);
        mContext = context;
        movies = items;
    }

    @NonNull
    @Override
    public View getView (int position, @Nullable View convertView, @NonNull ViewGroup parent){

        View listItem = convertView;

        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_element,parent,false);

        Movie currentMovie = movies.get(position);


        ImageView image = (ImageView) listItem.findViewById(R.id.icon);
        image.setImageResource(currentMovie.getSlika());

        TextView name = (TextView) listItem.findViewById(R.id.itemName);
        name.setText(currentMovie.getName());

        TextView genre = (TextView) listItem.findViewById(R.id.itemGenre);
        genre.setText(currentMovie.getGenre());


        return listItem;
    }

    public Movie getMovie(int position){
        return movies.get(position);
    }

}
